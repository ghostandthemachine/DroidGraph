package com.android.droidgraph.scene;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.drawable.shapes.Shape;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.geom.Transform3D;
import com.android.droidgraph.material.Material;
import com.android.droidgraph.shape.GLShape;
import com.android.droidgraph.vecmath.Point3d;

/**
 * A scene graph node that renders a Shape.
 * 
 */
public class SGShape extends SGAbstractShape {

	private GLShape glshape;
	private Shape cachedStrokeShape;	
	protected BoundingBox bounds = new BoundingBox();

	// // Store animation action runners
	// private ArrayList<RotationTransform> rotationRunners = new
	// ArrayList<RotationTransform>();
	// private ArrayList<RGeneric> genericRunners = new ArrayList<RGeneric>();

	private long lifetime = 0;

	/**
	 * Returns a reference to (not a copy of) the {@code Shape} of this node.
	 * The default value of this property is null.
	 * <p>
	 * Typically the {@code shape} property will be set once when the
	 * {@code SGShape} is first constructed. If thereafter the {@code shape}
	 * object is modified, it is the user's responsibility to call
	 * {@code setShape()} to ensure that the node state is properly updated.
	 * 
	 * @return the {@code Shape} of this node
	 */
	public final GLShape getShape() {
		return glshape;
	}

	/**
	 * Sets the {@code Shape} of this node.
	 * <p>
	 * Typically the {@code shape} property will be set once when the
	 * {@code SGShape} is first constructed. If thereafter the {@code shape}
	 * object is modified, it is the user's responsibility to call
	 * {@code setShape()} to ensure that the node state is properly updated.
	 * 
	 * @param glshape
	 *            the {@code Shape} of this node
	 */
	public void setShape(GLShape glshape) {
		this.glshape = glshape;
	}
	
	@Override
	public void paint(GL10 gl) {
		if (glshape == null) {
			return;
		}
		
		/*
		 * Materials
		 */
		
		final ArrayList<Material> ms = materials;
		if (materials != null) {
			for (Material material : ms) {
				material.draw(gl);
			}
		}
		
		glshape.draw(gl);
		
		
		/*
		 * kill the materials
		 */
		if (materials != null) {
			for (Material material : ms) {
				material.killDraw(gl);
			}
		}
		
		// update lifetime
		lifetime++;
	}

	@Override
	public BoundingBox getBounds(Transform3D transform) {
		if (glshape == null) {
			return new BoundingBox();
		}
		return bounds;
	}

	private void accumulate(GLShape s) {
		this.bounds.combine(s.getBounds());
	}

	public long getLifeTime() {
		return lifetime;
	}

	@Override
	public boolean contains(Point3d point) {
		return (glshape == null) ? false : glshape.contains(point);
	}

}
