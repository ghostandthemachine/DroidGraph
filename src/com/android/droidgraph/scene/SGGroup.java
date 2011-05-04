package com.android.droidgraph.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.geom.Bounds;
import com.android.droidgraph.geom.Transform3D;

/**
 * Defines a transformed coordinate system for a list of SGNodes.
 * 
 */
public class SGGroup extends SGParent {
	private List<SGNode> children;
	private List<SGNode> childrenUnmodifiable;
	
	protected long lifetime = 0;
	

	public final List<SGNode> getChildren() {
		if (children == null) {
			return Collections.emptyList();
		} else {
			if (childrenUnmodifiable == null) {
				childrenUnmodifiable = Collections.unmodifiableList(children);
			}
			return childrenUnmodifiable;
		}
	}

	public void add(int index, SGNode child) {
		if (child == null) {
			throw new IllegalArgumentException("null child");
		}
		if (children == null) {
			children = new ArrayList<SGNode>(1); // common case: one child
		}
		if ((index < -1) || (index > children.size())) {
			throw new IndexOutOfBoundsException("invalid index");
		}

		SGParent oldParent = child.getParent();
		if (oldParent == this) {
			children.remove(child);
		} else if (oldParent != null) {
			oldParent.remove(child);
		}
		if (index == -1) {
			children.add(child);
		} else {
			children.add(index, child);
		}
		child.setParent(this);
	}

	public final void add(SGNode child) {
		add(-1, child);
	}

	@Override
	public void remove(SGNode child) {
		if (child == null) {
			throw new IllegalArgumentException("null child");
		}
		if (children != null) {
			children.remove(child);
			child.setParent(null);
		}
	}

	public final void remove(int index) {
		if (children != null) {
			SGNode child = children.get(index);
			if (child != null) {
				remove(child);
			}
		}
	}

	public final Bounds getBounds(Transform3D transform) {
		Bounds bounds = null;
		if (isVisible() && children != null && !children.isEmpty()) {
			// for now, just create the union of all the bounding boxes
			// of all the children; later, we may want to create something
			// more minimal, such as the overall convex hull, or a
			// Region/Area object containing only the actual child bounds
			for (int i = 0; i < children.size(); i++) {
				SGNode child = children.get(i);
				if (child.isVisible()) {
					Bounds rc = child.getBounds(transform);
					bounds = accumulate(bounds, rc, true);
				}
			}
		}
		if (bounds == null) {
			// just an empty rectangle
			bounds = new BoundingBox();
		}
		return bounds;
	}

	public long getLifeTime() {
		return lifetime;
	}

	public void load(GL10 gl) {
		if(children != null) {
			for(SGNode child : children) {
				if(child instanceof SGAbstractShape) {
					((SGAbstractShape) child).load(gl);
				} else if (child instanceof SGGroup) {
					((SGGroup) child).load(gl);
				}
			}
		}
	}


}
