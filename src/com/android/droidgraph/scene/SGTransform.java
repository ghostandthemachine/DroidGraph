package com.android.droidgraph.scene;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.geom.Bounds;
import com.android.droidgraph.geom.Transform3D;
import com.android.droidgraph.vecmath.Point3f;
import com.android.droidgraph.vecmath.Vector3f;



/**
 *
 */
public abstract class SGTransform extends SGFilter {
	/**
	 * This class provides factory methods to create specific subclasses of the
	 * {@link SGTransform} class for various common affine transformations.
	 * 
	 * @see Translate
	 * @see Scale
	 * @see Rotate
	 * @see Shear
	 * @see Affine
	 * 
	 */
	static abstract class Factory {
		/** Creates a new instance of TransformFactory */
		Factory() {
		}

		abstract Translate makeTranslate(float tx, float ty, float tz, SGNode node);

		abstract Scale makeScale(float sx, float sy, float sz, SGNode node);

		abstract Rotate makeRotate(float thetax, float thetay, float thetaz, SGNode node);

		abstract Shear makeShear(float shx, float shy, float shz, SGNode node);

		abstract Affine makeAffine(Transform3D at, SGNode node);
	}

	static Factory theFactory;

	static {
		// REMIND: This should be switch selectable
		theFactory = new DesktopSGTransformFactory();
	}

	/**
	 * Returns an instance of {@link Translate} which translates points by the
	 * specified amounts.
	 * 
	 * @param tx
	 *            the initial {@code X} translation for the filter
	 * @param ty
	 *            the initial {@code Y} translation for the filter
	 * @param tz           
	 *            the initial {@code Z} translation for the filter  
	 * @param child
	 *            an optional child for the filter
	 * @return a {@code Translate} object
	 */
	public static Translate createTranslation(float tx, float ty, float tz, SGNode child) {
		Translate t = theFactory.makeTranslate(tx, ty, tz, child);
		if (child != null) {
			t.setChild(child);
		}
		return t;
	}

	/**
	 * Returns an instance of {@link Scale} which scales points by the specified
	 * amounts.
	 * 
	 * @param sx
	 *            the initial {@code X} scale for the filter
	 * @param sy
	 *            the initial {@code Y} scale for the filter
	 * @param sz
	 *            the initial {@code Z} scale for the filter
	 * @param child
	 *            an optional child for the filter
	 * @return a {@code Scale} object
	 */
	public static Scale createScale(float sx, float sy, float sz,
			SGNode child) {
		Scale s = theFactory.makeScale(sx, sy, sz, child);
		if (child != null) {
			s.setChild(child);
		}
		return s;
	}

	/**
	 * Returns an instance of {@link Rotate} which rotates points by the
	 * specified angle, measured in radians.
	 * 
	 * @param theta
	 *            the initial rotation for the filter
	 * @param child
	 *            an optional child for the filter
	 * @return a {@code Rotate} object
	 */
	public static Rotate createRotation(float thetax, float thetay, float thetaz, SGNode child) {
		Rotate r = theFactory.makeRotate(thetax, thetay, thetaz, child);
		if (child != null) {
			r.setChild(child);
		}
		return r;
	}

	/**
	 * Returns an instance of {@link Shear} which shears points by the specified
	 * amounts.
	 * 
	 * @param shx
	 *            the initial {@code X} shear for the filter
	 * @param shy
	 *            the initial {@code Y} shear for the filter
	 * @param child
	 *            an optional child for the filter
	 * @return a {@code Shear} object
	 */
	public static Shear createShear(float shx, float shy, float shz, SGNode child) {
		Shear s = theFactory.makeShear(shx, shy, shz, child);
		if (child != null) {
			s.setChild(child);
		}
		return s;
	}

	/**
	 * Returns an instance of {@link Affine} which transforms points by the
	 * specified generalized affine transformation. If the
	 * {@link Transform3D} parameter is null then an identity transform is
	 * used initially.
	 * 
	 * @param at
	 *            an optional initial transform for the filter
	 * @param child
	 *            an optional child for the filter
	 * @return an {@code Affine} object
	 */
	public static Affine createAffine(Transform3D at, SGNode child) {
		Affine a = theFactory.makeAffine(at, child);
		if (child != null) {
			a.setChild(child);
		}
		return a;
	}

	/** Creates a new instance of SGTransform */
	SGTransform() {
	}

	/**
	 * Transforms a single input point specified by a {@link Point2D} object
	 * into an optionally specified output point and returns the object used to
	 * store the results. The {@code dst} argument may be null in which case a
	 * new {@code Point2D} object will be created for the return value,
	 * otherwise the specified object will be used to store the point and
	 * returned from the method.
	 * 
	 * @param src
	 *            the input point to be transformed
	 * @param dst
	 *            an optional output point for storing the result
	 * @return the {@code Point2D} object used to store the result
	 */
	public abstract Point3f transform(Point3f src, Point3f dst);

	/**
	 * Untransforms (transforms by the inverse of this transform) a single input
	 * point specified by a {@link Point2D} object into an optionally specified
	 * output point and returns the object used to store the results. The
	 * {@code dst} argument may be null in which case a new {@code Point2D}
	 * object will be created for the return value, otherwise the specified
	 * object will be used to store the point and returned from the method.
	 * <p>
	 * Note that this method does not throw a
	 * <code>NoninvertibleTransformException</code> as similar methods in the
	 * {@link Transform3D} class do. The subclasses will make a
	 * "best effort" to inverse transform the coordinates of the point depending
	 * on what pieces of the inverse calculations can be performed and may
	 * return the original {@code src} location if necessary. The forgiving
	 * nature of this method should reduce or eliminate unnecessary
	 * {@code catch} clauses without reducing its utility. If desired, the
	 * associated transform can be retrieved and queried for invertibility.
	 * 
	 * @param src
	 *            the input point to be inverse transformed
	 * @param dst
	 *            an optional output point for storing the result
	 * @return the {@code Point2D} object used to store the result
	 */
	public abstract Point3f inverseTransform(Point3f src, Point3f dst);

	/**
	 * Applies the simple transform operation of this {@code Transform} to a
	 * more general {@link Transform3D} object. The transform operation of
	 * this object is appended to the existing transform operations already
	 * represented in the {@code Transform3D} object as if by appending a
	 * new matrix.
	 * 
	 * @param at
	 *            the {@code Transform3D} object to append this transform to
	 */
	public abstract void concatenateInto(Transform3D at);

	/**
	 * Sets the {@link Transform3D} object to represent the same transform
	 * as the simple transform of this {@code Transform}. The transform
	 * operation of this object replaces the existing transform operations that
	 * may have been representd in the {@code Transform3D} object.
	 * 
	 * @param at
	 *            the {@code Transform3D} object to store this transform
	 *            into
	 */
	public abstract void getTransform(Transform3D at);

	/**
	 * Creates a new Transform3D representing the same transform operation
	 * as this object.
	 * 
	 * @return a new {@code Transform3D} object representing this transform.
	 */
	public Transform3D createAffine() {
		Transform3D at = new Transform3D();
		getTransform(at);
		return at;
	}

	/**
	 * Resets this transform node to an identity operation which has no effect
	 * on the input points.
	 */
	public abstract void reset();

	protected void invalidateTransform() {
		
//		log.pl("right here", this);
	}

	@Override
	public boolean canSkipRendering() {
		return true;
	}

	/**
	 * Calculates the accumulated product of all transforms back to the root of
	 * the tree. The inherited implementation simply returns a shared value from
	 * the parent, but SGTransform nodes must append their individual transform
	 * to a copy of that inherited object.
	 */
	@Override
	final Transform3D calculateCumulativeTransform() {
		Transform3D xform = super.calculateCumulativeTransform();
		xform = new Transform3D(xform);
		concatenateInto(xform);
		return xform;
	}

	@Override
	public Bounds getBounds(Transform3D transform) {
		SGNode child = getChild();
		if (child == null) {
			// just an empty rectangle
			return new BoundingBox();
		} else {
			Transform3D childTx = createAffine();
			if (childTx != null && childTx.getType() != Transform3D.IDENTITY) {
				if (transform != null && transform.getType() != Transform3D.IDENTITY) {
					
					///////////////
//					AffineTransforms' preConcatenate converted for Transform3D
					childTx.mul(childTx, transform);
					//////////////
					
				}
				transform = childTx;
			}
			return child.getBounds(transform);
		}
	}

	/**
	 * A subclass of {@link SGTransform} that applies a simple translation
	 * transform. A translation transform simply adds a constant value to the
	 * {@code X} and {@code Y} coordinates of the source coordinates. The
	 * transform {@code x',y'} of a source point {@code x,y} is represented by
	 * the equations:
	 * 
	 * <pre>
	 *     x' = x + transx;
	 *     y' = y + transy;
	 * </pre>
	 * 
	 * Instances of this class can only be created by calling the
	 * {@link SGTransform#createTranslation} factory method.
	 * 
	 * @author Flar
	 */
	public static abstract class Translate extends SGTransform {

		/** Creates a new instance of Translate */
		Translate() {
		}

		/**
		 * Returns the translation offset applied to the {@code X} coordinates.
		 * 
		 * @return the {@code} X translation
		 */
		public abstract float getTranslateX();

		/**
		 * Returns the translation offset applied to the {@code Y} coordinates.
		 * 
		 * @return the {@code} Y translation
		 */
		public abstract float getTranslateY();

		/**
		 * Returns the translation offset applied to the {@code Z} coordinates.
		 * 
		 * @return the {@code} Z translation
		 */
		public abstract float getTranslateZ();

		/**
		 * Sets the translation offset applied to the {@code X} coordinates.
		 * 
		 * @param tx
		 *            the new {@code} X translation
		 */
		public abstract void setTranslateX(float tx);

		/**
		 * Sets the translation offset applied to the {@code Y} coordinates.
		 * 
		 * @param ty
		 *            the new {@code} Y translation
		 */
		public abstract void setTranslateY(float ty);

		/**
		 * Sets the translation offset applied to the {@code Z} coordinates.
		 * 
		 * @param tz
		 *            the new {@code} Z translation
		 */
		public abstract void setTranslateZ(float tz);

		/**
		 * Sets the {@code X} and {@code Y} translation offets to the specified
		 * new values.
		 * 
		 * @param tx
		 *            the new X translation offset
		 * @param ty
		 *            the new Y translation offset
		 */
		public abstract void setTranslation(float tx, float ty, float tz);

		/**
		 * Offsets the {@code X} and {@code Y} translation offets by the
		 * specified additional offset values. This method is equivalent to:
		 * 
		 * <pre>
		 * tt.setTranslation(tt.getTranslateX() + tx, tt.getTranslateY() + ty);
		 * </pre>
		 * 
		 * @param tx
		 *            the additional X translation offset
		 * @param ty
		 *            the additional Y translation offset
		 */
		public abstract void translateBy(float tx, float ty, float tz);

		@Override
		public void reset() {
			setTranslation(0, 0, 0);
		}
	}

	/**
	 * This class implements a basic 2 Dimensional scale transform. All input
	 * points are scaled independently in the {@code X} and {@code Y} directions
	 * by the {@code ScaleX} and {@code ScaleY} values. The transform
	 * {@code x',y'} of a source point {@code x,y} is represented by the
	 * equations:
	 * 
	 * <pre>
	 *     x' = x * scalex;
	 *     y' = y * scaley;
	 *     z' = z * scalez;
	 * </pre>
	 * 
	 * Instances of this class can only be created by calling the
	 * {@link SGTransform#createScale} factory method.
	 * 
	 * @author Flar
	 */
	public static abstract class Scale extends SGTransform {

		/** Creates a new instance of Scale */
		Scale() {
		}

		/**
		 * Returns the scale factor for the {@code X} coordinates.
		 * 
		 * @return the {@code X} scale factor
		 */
		public abstract float getScaleX();

		/**
		 * Returns the scale factor for the {@code Y} coordinates.
		 * 
		 * @return the {@code Y} scale factor
		 */
		public abstract float getScaleY();

		/**
		 * Returns the scale factor for the {@code Z} coordinates.
		 * 
		 * @return the {@code Z} scale factor
		 */
		public abstract float getScaleZ();

		/**
		 * Sets the scale factor for the {@code X} coordinates.
		 * 
		 * @param sx
		 *            the new {@code X} scale factor
		 */
		public abstract void setScaleX(float sx);

		/**
		 * Sets the scale factor for the {@code Y} coordinates.
		 * 
		 * @param sy
		 *            the new {@code Y} scale factor
		 */
		public abstract void setScaleY(float sy);

		/**
		 * Sets the scale factor for the {@code Z} coordinates.
		 * 
		 * @param sz
		 *            the new {@code Z} scale factor
		 */
		public abstract void setScaleZ(float sz);

		/**
		 * Sets the scale factors for the {@code X} and {@code Y} coordinates.
		 * 
		 * @param sx
		 *            the new {@code X} scale factor
		 * @param sy
		 *            the new {@code Y} scale factor
		 */
		public abstract void setScale(float sx, float sy, float sz);

		/**
		 * Scales (multiplies) the scale factors for the {@code X} and {@code Y}
		 * coordinates by additional scale factors. This method is equivalent
		 * to:
		 * 
		 * <pre>
		 * st.setScale(st.getScaleX() * sx, st.getScaleY() * sy, st.getScaleZ() * sz);
		 * </pre>
		 * 
		 * @param sx
		 *            the new {@code X} scale factor
		 * @param sy
		 *            the new {@code Y} scale factor
		 * @param sz
		 *            the new {@code Z} scale factor
		 */
		public abstract void scaleBy(float sx, float sy, float sz);

		@Override
		public void reset() {
			setScale(1, 1, 1);
		}
	}

	/**
	 */
	public static abstract class Rotate extends SGTransform {

		/** Creates a new instance of Rotate */
		Rotate() {
		}

		/**
		 * Returns the angle, measured in radians, by which the source points
		 * are rotated around the origin. The direction of rotation for positive
		 * angles will rotate the positive {@code X} axis towards the positive
		 * {@code Y} axis.
		 * 
		 * @return the angle, measured in radians
		 */
		public abstract Vector3f getRotation();
		public abstract float getRotationX();
		public abstract float getRotationY();
		public abstract float getRotationZ();

		/**
		 * Sets the angle, measured in radians, by which the source points are
		 * rotated around the origin. The direction of rotation for positive
		 * angles will rotate the positive {@code X} axis towards the positive
		 * {@code Y} axis.
		 * 
		 * @param theta
		 *            the angle, measured in radians
		 */
		public abstract void setRotation(float thetax, float thetay, float thetaz);

		/**
		 * Adjusts the angle, measured in radians, by which the source points
		 * are rotated around the origin. The direction of rotation for positive
		 * angles will rotate the positive {@code X} axis towards the positive
		 * {@code Y} axis. This method is equivalent to:
		 * 
		 * <pre>
		 * rt.setRotation(rt.getRotation() + theta);
		 * </pre>
		 * 
		 * @param theta
		 *            the angle to be added, measured in radians
		 */
		
		public abstract void rotateBy(Vector3f v);
		public abstract void rotateXBy(float thetax);
		public abstract void rotateYBy(float thetay);
		public abstract void rotateZBy(float thetaz);

		
		
		@Override
		public void reset() {
			setRotation(0, 0, 0);
		}
	}

	/**
	 * This class implements a simple 2 Dimensional shearing transform. Input
	 * points are slanted by applying a scaling factor perpendicular to each
	 * axis and that increases linearly along the axes such that points near the
	 * origin are not moved at all, but positive coordinates far from the origin
	 * are moved by ever increasing distances perpendicular to the axes.
	 * Negative coordinates are moved in the opposite direction as positive
	 * coordinates. The transform {@code x',y'} of a source point {@code x,y} is
	 * represented by the equations:
	 * 
	 * <pre>
	 *     x' = x + y * shearx;
	 *     y' = y + x * sheary;
	 *     z' = z + x * shearz;
	 * </pre>
	 * 
	 * Instances of this class can only be created by calling the
	 * {@link SGTransform#createShear} factory method.
	 * 
	 * @author Flar
	 */
	public static abstract class Shear extends SGTransform {

		/** Creates a new instance of Shear */
		Shear() {
		}

		/**
		 * Returns the factor by which positive {@code X} coordinates are moved
		 * in the direction of the positive {@code Y} axis. Negative coordinates
		 * are moved similarly in the opposite direction.
		 * 
		 * @return the {@code X} shearing factor
		 */
		public abstract float getShearX();

		/**
		 * Returns the factor by which positive {@code Y} coordinates are moved
		 * in the direction of the positive {@code X} axis. Negative coordinates
		 * are moved similarly in the opposite direction.
		 * 
		 * @return the {@code Y} shearing factor
		 */
		public abstract float getShearY();

		/**
		 * Returns the factor by which positive {@code Z} coordinates are moved
		 * in the direction of the positive {@code X} axis. Negative coordinates
		 * are moved similarly in the opposite direction.
		 * 
		 * @return the {@code Z} shearing factor
		 */
		public abstract float getShearZ();

		/**
		 * Sets the factor by which positive {@code X} coordinates are moved in
		 * the direction of the positive {@code Y} axis. Negative coordinates
		 * are moved similarly in the opposite direction.
		 * 
		 * @param shx
		 *            the new {@code X} shearing factor
		 */
		public abstract void setShearX(float shx);

		/**
		 * Sets the factor by which positive {@code Y} coordinates are moved in
		 * the direction of the positive {@code X} axis. Negative coordinates
		 * are moved similarly in the opposite direction.
		 * 
		 * @param shy
		 *            the new {@code Y} shearing factor
		 */
		public abstract void setShearY(float shy);

		/**
		 * Sets the factor by which positive {@code Z} coordinates are moved in
		 * the direction of the positive {@code X} axis. Negative coordinates
		 * are moved similarly in the opposite direction.
		 * 
		 * @param shy
		 *            the new {@code Z} shearing factor
		 */
		public abstract void setShearZ(float shz);

		/**
		 * Sets the {@code X} and {@code Y} shearing factors to new values.
		 * 
		 * @param shx
		 *            the new {@code X} shearing factor
		 * @param shy
		 *            the new {@code Y} shearing factor
		 */
		public abstract void setShear(float shx, float shy, float shz);

		/**
		 * Scales the {@code X} and {@code Y} shearing factors by the specified
		 * factors. This method is equivalent to:
		 * 
		 * <pre>
		 * st.setShear(st.getShearX() * shx, st.getShearY() * shy);
		 * </pre>
		 * 
		 * @param shx
		 *            the scale applied to the {@code X} shearing factor
		 * @param shy
		 *            the scale applied to the {@code Y} shearing factor
		 */
		public abstract void shearBy(float shx, float shy, float shz);

		@Override
		public void reset() {
			setShear(0, 0, 0);
		}
	}

	/**
	 * This class implements a general 2 Dimensional affine transform. Input
	 * points are transformed by applying the generalized affine transform
	 * specified by an {@link Transform3D} object.
	 * 
	 * Instances of this class can only be created by calling the
	 * {@link SGTransform#createAffine} factory method.
	 * 
	 * @author Flar
	 */
	public static abstract class Affine extends SGTransform {

		/** Creates a new instance of Affine */
		Affine() {
		}

		/**
		 * Returns the {@link Transform3D} object which controls how
		 * coordinates are transformed by this node.
		 * 
		 * @return the {@code Transform3D} object
		 */
		public abstract Transform3D getAffine();

		/**
		 * Sets the {@link Transform3D} object which controls how
		 * coordinates are transformed by this node.
		 * 
		 * @param at
		 *            the {@code Transform3D} object
		 */
		public abstract void setAffine(Transform3D at);

		/**
		 * Concatenates the existing transform with an additional
		 * {@link Transform3D} object as per the implementation of
		 * {@link Transform3D#concatenate}.
		 * 
		 * @param at
		 *            the {@code Transform3D} to be concatenated
		 */
		public abstract void transformBy(Transform3D at);
	}
}
