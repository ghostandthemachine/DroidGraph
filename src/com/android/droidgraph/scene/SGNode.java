package com.android.droidgraph.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

import com.android.droidgraph.event.GraphNodeEvent;
import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.geom.Bounds;
import com.android.droidgraph.geom.Transform3D;
import com.android.droidgraph.material.IMaterial;
import com.android.droidgraph.material.Material;
import com.android.droidgraph.material.TextureMaterial;
import com.android.droidgraph.util.GLH;
import com.android.droidgraph.util.PrintLogUtil;
import com.android.droidgraph.util.SGColorI;
import com.android.droidgraph.vecmath.Point3d;

public abstract class SGNode {

	protected PrintLogUtil log = new PrintLogUtil();

	private Object parent;
	private Map<String, Object> attributeMap;
	private List<SGMotionListener> motionListeners = null;
	//

	private HashSet<GraphNodeEvent> translationEvents = null;
	private HashSet<GraphNodeEvent> rotationEvents = null;
	private HashSet<GraphNodeEvent> scaleEvents = null;
	
	//
	private boolean visible = true;
	private String id;

	private Bounds cachedAccumBounds;
	private Transform3D cachedAccumXform;

	private boolean isTouchBlocker = false;

	public final boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		if (this.visible != visible) {
			if (visible) {
				this.visible = true;
			} else {
				this.visible = false;
			}
		}
	}

	/**
	 * @return {@code true} if mouse event should not be dispatched to the nodes
	 *         underneath this one.
	 */
	public final boolean isTouchBlocker() {
		return isTouchBlocker;
	}

	public final void setTouchBlocker(boolean value) {
		if (value != isTouchBlocker) {
			isTouchBlocker = value;
		}
	}

	public final String getID() {
		return id;
	}

	public final void setID(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id + " " + super.toString();
	}

	public SGParent getParent() {
		return (parent instanceof SGView) ? null : (SGParent) parent;
	}

	final void setParent(Object parent) {
		assert (parent == null || parent instanceof SGParent || parent instanceof SGView);
		this.parent = parent;

	}

	public HashSet<GraphNodeEvent> getPendingTranslationEvents() {
		return translationEvents;
	}

	public void addTranslationEvent(GraphNodeEvent e) {
		if (translationEvents == null) {
			translationEvents = new HashSet<GraphNodeEvent>();
		}
		translationEvents.add(e);
	}

	public void addTranslationEvent(HashSet<GraphNodeEvent> list) {
		if (translationEvents == null) {
			translationEvents = new HashSet<GraphNodeEvent>();
		}
		translationEvents.addAll(list);
	}

	
	public HashSet<GraphNodeEvent> getPendingRotationEvents() {
		return rotationEvents;
	}

	public void addRotationEvent(GraphNodeEvent e) {
		if (rotationEvents == null) {
			rotationEvents = new HashSet<GraphNodeEvent>();
		}
		rotationEvents.add(e);
	}

	public void addRotationEvent(HashSet<GraphNodeEvent> list) {
		if (rotationEvents == null) {
			rotationEvents = new HashSet<GraphNodeEvent>();
		}
		rotationEvents.addAll(list);
	}
	
	public HashSet<GraphNodeEvent> getPendingScaleEvents() {
		return scaleEvents;
	}

	public void addScaleEvent(GraphNodeEvent e) {
		if (scaleEvents == null) {
			scaleEvents = new HashSet<GraphNodeEvent>();
		}
		scaleEvents.add(e);
	}

	public void addScaleEvent(HashSet<GraphNodeEvent> list) {
		if (scaleEvents == null) {
			scaleEvents = new HashSet<GraphNodeEvent>();
		}
		scaleEvents.addAll(list);
	}
	
	
	public SGView getPanel() {
		Object node = parent;
		while (node != null) {
			if (node instanceof SGView) {
				return (SGView) node;
			} else {
				node = ((SGNode) node).parent;
			}
		}
		return null;
	}

	public SGView getView() {
		Object node = parent;
		while (node != null) {
			if (node instanceof SGView) {
				return (SGView) node;
			} else {
				node = ((SGNode) node).parent;
			}
		}
		return null;
	}

	/**
	 * Returns the bounding box of this node in the coordinate space inherited
	 * from the parent. This is a convenience method, equivalent to calling
	 * {@code getBounds(null)}.
	 */
	public final Bounds getBounds() {
		return getBounds(null);
	}

	/**
	 * Returns the bounding box of this node relative to the specified
	 * coordinate space.
	 * 
	 * @param transform
	 *            the transform applied to the geometry
	 */
	public abstract Bounds getBounds(Transform3D transform);

	/**
	 * Transforms the bounds of this node by the "cumulative transform", and
	 * then returns the bounding box of that transformed shape.
	 */
	final Bounds getTransformedBoundsRelativeToRoot() {
		if (cachedAccumBounds == null) {
			cachedAccumBounds = calculateAccumBounds();
		}
		return ((Bounds) cachedAccumBounds);
	}

	/**
	 * Calculate the accumulated bounds object representing the global bounds
	 * relative to the root of the tree. The default implementation calculates
	 * new bounds based on the accumulated transform, but SGFilter nodes
	 * override this method to return a shared accumulated bounds object from
	 * their child.
	 */
	Bounds calculateAccumBounds() {
		return getBounds(getCumulativeTransform());
	}

	/**
	 * Returns the "cumulative transform", which is the concatenation of all
	 * ancestor transforms plus the transform of this node (if present).
	 */
	final Transform3D getCumulativeTransform() {
		if (cachedAccumXform == null) {
			cachedAccumXform = calculateCumulativeTransform();
		}
		return cachedAccumXform;
	}

	/**
	 * Calculates the accumulated product of all transforms back to the root of
	 * the tree. The default implementation simply returns a shared value from
	 * the parent, but SGTransform nodes will override this method to return a
	 * new modified transform.
	 */
	Transform3D calculateCumulativeTransform() {
		SGNode parent = getParent();
		if (parent == null) {
			return new Transform3D();
		} else {
			return parent.getCumulativeTransform();
		}
	}

	/**
	 * Transforms a point from the global coordinate system of the root node
	 * (typically a {@link JSGPanel}) into the local coordinate space of this
	 * SGLNode. The {@code global} parameter must not be null. If the
	 * {@code local} parameter is null then a new {@link Point3d} object will be
	 * created and returned after transforming the point. The {@code global} and
	 * {@code local} parameters may be the same object and the coordinates will
	 * be correctly updated with the transformed coordinates.
	 * 
	 * @param global
	 *            the coordinates in the global coordinate system to be
	 *            transformed
	 * @param local
	 *            a {@code Point3d} object to store the results in
	 * @return a {@code Point3d} object containig the transformed coordinates
	 */
	public Point3d globalToLocal(Point3d global, Point3d local) {

		Transform3D t = (Transform3D) getCumulativeTransform();

		// return .inverseTransform(global, local);
		return new Point3d();
	}

	/**
	 * Transforms a point from the local coordinate space of this SGNode into
	 * the global coordinate system of the root node (typically a {@link SGView}
	 * ). The {@code local} parameter must not be null. If the {@code global}
	 * parameter is null then a new {@link Point3d} object will be created and
	 * returned after transforming the point. The {@code local} and
	 * {@code global} parameters may be the same object and the coordinates will
	 * be correctly updated with the transformed coordinates.
	 * 
	 * @param local
	 *            the coordinates in the local coordinate system to be
	 *            transformed
	 * @param global
	 *            a {@code Point3d} object to store the results in
	 * @return a {@code Point3d} object containig the transformed coordinates
	 */
	public Point3d localToGlobal(Point3d local, Point3d global) {
		// return getCumulativeTransform().transform(global, local);
		return new Point3d();
	}

	/**
	 * Process MotionEvent
	 * 
	 * @param e
	 */
	final void processMotionEvent(MotionEvent e) {
		if ((motionListeners != null) && (motionListeners.size() > 0)) {
			for (SGMotionListener tl : motionListeners) {
				switch (e.getAction()) {
				case MotionEvent.ACTION_DOWN:
					tl.actionDown(e, this);
					break;
				case MotionEvent.ACTION_UP:
					tl.actionUp(e, this);
					break;
				case MotionEvent.ACTION_MOVE:
					tl.actionMove(e, this);
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					tl.actionPointerDown(e, this);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					tl.actionPointerUp(e, this);
					break;
				}
			}
		}
	}

	public void addMotionEventListener(SGMotionListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("null listener");
		}
		if (motionListeners == null) {
			motionListeners = new ArrayList<SGMotionListener>(1);
		}
		motionListeners.add(listener);
	}

	public void removeMotionEventListener(SGMotionListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("null listener");
		}
		if (motionListeners != null) {
			motionListeners.remove(listener);
		}
	}

	/*
	 * Attribute-related methods below...
	 */

	public final Object getAttribute(String key) {
		if (key == null) {
			throw new IllegalArgumentException("null key");
		}
		return (attributeMap == null) ? null : attributeMap.get(key);
	}

	public final void putAttribute(String key, Object value) {
		if (attributeMap == null) {
			attributeMap = new HashMap<String, Object>(1);
		}
		attributeMap.put(key, value);
	}

	public boolean contains(Point3d point) {
		if (point == null) {
			throw new IllegalArgumentException("null point");
		}
		Bounds bounds = getBounds(null);
		return bounds.intersect(point);
	}

	/**
	 * This node is completely clean, and so are all of its descendents.
	 */
	static final int DIRTY_NONE = (0 << 0);
	/**
	 * This node has changed its overall visual state.
	 */
	static final int DIRTY_VISUAL = (1 << 0);
	/**
	 * This node has changed only a subregion of its overall visual state. (Only
	 * applicable to SGLeaf nodes.)
	 */
	static final int DIRTY_SUBREGION = (1 << 1);
	/**
	 * This node has changed its bounds, so it is important to account for both
	 * the former bounds and its new, updated bounds.
	 */
	static final int DIRTY_BOUNDS = (1 << 2);
	/**
	 * One or more of this node's descendents has changed its visual state.
	 * (Only applicable to SGGroup and SGFilter nodes.)
	 */
	static final int DIRTY_CHILDREN_VISUAL = (1 << 3);
	/**
	 * One or more of this node's descendents has had a change in bounds, which
	 * means that the overall bounds of this group will need recalculation.
	 * (Only applicable to SGGroup and SGFilter nodes.)
	 */
	static final int DIRTY_CHILDREN_BOUNDS = (1 << 4);

	/**
	 * The dirty state of this node. This is initialized to DIRTY_VISUAL so that
	 * this node is painted for the very first paint cycle.
	 */
	private int dirtyState = DIRTY_VISUAL;

	/**
	 * The most recently painted bounds of this node (transformed relative to
	 * the root node, i.e., in device space). This field is initially set to
	 * null and is updated everytime the node is actually rendered to the
	 * destination. It is later used in the case of DIRTY_BOUNDS for the
	 * purposes of accumulating the former (dirty) bounds of a particular node.
	 */
	private Bounds lastRenderedBounds;

	static Bounds accumulate(Bounds accumulator, Bounds newBox) {
		return accumulate(accumulator, newBox, false);
	}

	static Bounds accumulate(Bounds accumulator, Bounds newBox,
			boolean newBoxShareable) {

		if (newBox == null || newBox.isEmpty()) {
			return accumulator;
		}
		if (accumulator == null) {
			// TODO: We really shouldn't be so trusting of the incoming
			// Rectangle type - we should instantiate a (platform sensitive)
			// specific type like R2D.Double (desktop) or R2D.Float (phone)
			return (newBoxShareable ? newBox : (BoundingBox) newBox.clone());
		}
		accumulator.combine(newBox);
		return accumulator;
	}

	/*
	 * Rendering code below...
	 */

	/**
	 * Render the tree of nodes to the specified {@link Graphics2D} object
	 * descending from this node as the root. The {@code dirtyRegion} parameter
	 * can be used to cull the rendering operations on the tree so that only
	 * parts of the tree that intersect the indicated rectangle (in device
	 * space) will be visited and rendered. If the {@code dirtyRegion} parameter
	 * is null then all parts of the tree will be visited and rendered whether
	 * they will eventually be visible or not.
	 * 
	 * @param g
	 *            the {@code Graphics2D} object to render into
	 * @param dirtyRegion
	 *            a Rectangle to cull which parts of the tree to operate on, or
	 *            null if the full tree should be visited and rendered
	 * 
	 * 
	 * 
	 *            Note: not sure yet if I want to attempt render clipping in
	 *            opengl es... --- ignore the dirtyRegion for now ---
	 */
	final void render(GL10 gl) {
		if (!isVisible()) {
			return;
		}

		gl.glPushMatrix();
		
		/*
		 * run scale events
		 */
		if (scaleEvents != null) {
			final HashSet<GraphNodeEvent> events = scaleEvents;
			for (GraphNodeEvent scale : events) {
				scale.run();
				
			}
		}

		/*
		 * run translation events
		 */
		if (translationEvents != null) {
			final HashSet<GraphNodeEvent> events = translationEvents;
			for (GraphNodeEvent translation : events) {
				translation.run();
			}
		}
		
		/*
		 * run rotation events
		 */
		if (rotationEvents != null) {
			final HashSet<GraphNodeEvent> events = rotationEvents;
			for (GraphNodeEvent rotation : events) {
				rotation.run();
			}
		}
		
		
		/*
		 * render children if a parent
		 */
		if (this instanceof SGParent) {
			for (SGNode child : ((SGParent) this).getChildren()) {
				child.render(gl);
			}
			
		/*
		 * else, paint if you are a shape
		 */
		} else if (this instanceof SGAbstractShape) {
			((SGAbstractShape) this).paint(gl);
		}
		
		
		/*
		 * pop out of this nodes matrix
		 */
		gl.glPopMatrix();

	}

	

}
