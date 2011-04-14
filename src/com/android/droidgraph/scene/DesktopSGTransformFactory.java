package com.android.droidgraph.scene;

import com.android.droidgraph.event.RotationEvent;
import com.android.droidgraph.event.ScaleEventDispatcher;
import com.android.droidgraph.event.TranslationEvent;
import com.android.droidgraph.geom.Transform3D;
import com.android.droidgraph.util.GLH;
import com.android.droidgraph.vecmath.Point3f;
import com.android.droidgraph.vecmath.Vector3f;

/**
 *
 */
class DesktopSGTransformFactory extends SGTransform.Factory {

	/** Creates a new instance of DesktopSGTransformFactory */
	public DesktopSGTransformFactory() {
	}

	@Override
	SGTransform.Affine makeAffine(Transform3D at, SGNode child) {
		return new Affine(at, child);
	}

	@Override
	SGTransform.Translate makeTranslate(float tx, float ty, float tz, SGNode child) {
		return new Translate(tx, ty, tz, child);
	}

	@Override
	SGTransform.Scale makeScale(float sx, float sy, float sz, SGNode child) {
		return new Scale(sx, sy, sz, child);
	}

	@Override
	SGTransform.Rotate makeRotate(float thetax, float thetay, float thetaz,
			SGNode child) {
		return new Rotate(thetax, thetay, thetaz, child);
	}

	@Override
	SGTransform.Shear makeShear(float shx, float shy, float shz, SGNode child) {
		return new Shear(shx, shy, shz, child);
	}

	static Point3f setPoint(Point3f dst, float x, float y, float z) {
		if (dst == null) {
			// REMIND: This should be changed back to float when we
			// create a separate Phone/Embedded factory...
			dst = new Point3f();
		}
		dst.set(x, y, z);
		return dst;
	}

	// REMIND: These classes must be public for beans property setters to work
	public static final class Translate extends SGTransform.Translate {
		private TranslationEvent t;

		public Translate(float tx, float ty, float tz, SGNode child) {
			t = new TranslationEvent(tx, ty, tz);
			child.addTranslationEvent(t);
		}

		@Override
		public float getTranslateX() {
			return t.getTranslation().x;
		}

		@Override
		public float getTranslateY() {
			return t.getTranslation().y;
		}

		@Override
		public float getTranslateZ() {
			return t.getTranslation().z;
		}

		@Override
		public void setTranslateX(float tx) {
			t.setTranslation(tx, t.getTranslation().y, t.getTranslation().z);
		}

		@Override
		public void setTranslateY(float ty) {
			t.setTranslation(t.getTranslation().x, ty, t.getTranslation().z);
		}

		@Override
		public void setTranslateZ(float tz) {
			t.setTranslation(t.getTranslation().x, t.getTranslation().y, tz);
		}

		@Override
		public void setTranslation(float tx, float ty, float tz) {
			t.setTranslation(tx, ty, tz);
		}

		@Override
		public void translateBy(float tx, float ty, float tz) {
			t.setTranslation(t.getTranslation().x += tx,
					t.getTranslation().y += ty, t.getTranslation().z += tz);
		}

		@Override
		public Point3f transform(Point3f src, Point3f dst) {
			return null;
		}

		@Override
		public Point3f inverseTransform(Point3f src, Point3f dst) {
			return null;
		}

		@Override
		public void concatenateInto(Transform3D at) {
		}

		@Override
		public void getTransform(Transform3D at) {
		}
	}

	// REMIND: These classes must be public for beans property setters to work
	public static final class Scale extends SGTransform.Scale {
		private ScaleEventDispatcher s = new ScaleEventDispatcher(1f, 1f, 1f);

		public Scale(float sx, float sy, float sz, SGNode child) {
			s.setScale(sx, sy, sz);
			child.addScaleEvent(s);
		}

		@Override
		public Point3f transform(Point3f src, Point3f dst) {
			return null;
		}

		@Override
		public Point3f inverseTransform(Point3f src, Point3f dst) {
			return null;
		}

		@Override
		public void concatenateInto(Transform3D at) {
			// at.setScale(new Vector3d((float) sx, (float) sy, (float) sz));
		}

		@Override
		public void getTransform(Transform3D at) {
			// Transform3D t = new Transform3D();
			// t.set(new Vector3f((float) sx, (float) sy, (float) sz));
			// at.mul(t, at);
		}

		@Override
		public float getScaleX() {
			return s.getScale().x;
		}

		@Override
		public float getScaleY() {
			return s.getScale().y;
		}

		@Override
		public float getScaleZ() {
			return s.getScale().z;
		}

		@Override
		public void setScaleX(float sx) {
			s.setScaleX(sx);
		}

		@Override
		public void setScaleY(float sy) {
			s.setScaleY(sy);
		}

		@Override
		public void setScaleZ(float sz) {
			s.setScaleZ(sz);
		}

		@Override
		public void setScale(float sx, float sy, float sz) {
			s.setScale(sx, sy, sz);
		}

		@Override
		public void scaleBy(float sx, float sy, float sz) {
			s.scaleBy(sx, sy, sz);
		}
	}

	// REMIND: These classes must be public for beans property setters to work
	public static final class Rotate extends SGTransform.Rotate {
		RotationEvent re = new RotationEvent();

		public Rotate(float thetax, float thetay, float thetaz, SGNode child) {
			re.setRotation(thetax, thetay, thetaz);
			child.addRotationEvent(re);

		}

		@Override
		public Point3f transform(Point3f src, Point3f dst) {
			log.pl("DeskTop-Rotate transform() returns null", this);
			return null;
		}

		@Override
		public Point3f inverseTransform(Point3f src, Point3f dst) {
			log.pl("DeskTop-Rotate inverseTransform() returns null", this);
			return null;
		}

		@Override
		public void concatenateInto(Transform3D at) {
			// at.rotate(theta);
		}

		@Override
		public void getTransform(Transform3D at) {
			// at.setToRotation(theta);
		}

		@Override
		public Vector3f getRotation() {
			return re.getRotation();
		}

		@Override
		public float getRotationX() {
			return re.getRotation().x;
		}

		@Override
		public float getRotationY() {
			return re.getRotation().y;
		}

		@Override
		public float getRotationZ() {
			return re.getRotation().z;
		}

		@Override
		public void setRotation(float thetax, float thetay, float thetaz) {
			re.setRotation(thetax, thetay, thetaz);
		}

		@Override
		public void rotateBy(Vector3f v) {
			re.rotateBy(v);
		}

		@Override
		public void rotateXBy(float thetax) {
			re.rotateBy(thetax, 0, 0);
		}

		@Override
		public void rotateYBy(float thetay) {
			re.rotateBy(0, thetay, 0);
		}

		@Override
		public void rotateZBy(float thetaz) {
			re.rotateBy(0, 0, thetaz);
		}
	}

	// REMIND: These classes must be public for beans property setters to work
	public static final class Shear extends SGTransform.Shear {
		private float shx;
		private float shy;
		private float shz;
		private SGNode child;

		public Shear(float shx, float shy, float shz, SGNode child) {
			this.shx = shx;
			this.shy = shy;
			this.shz = shz;
			this.child = child;
		}

		@Override
		public Point3f transform(Point3f src, Point3f dst) {
			float x = src.getX();
			float y = src.getY();
			float z = src.getZ();
			float retx = x + shx * y;
			float rety = y + shy * x;
			float retz = z + shz * z;
			return setPoint(dst, retx, rety, retz);
		}

		@Override
		public Point3f inverseTransform(Point3f src, Point3f dst) {
			float x = src.getX();
			float y = src.getY();
			float z = src.getZ();
			float det = 1 - shx * shy * shz;
			float retx = x;
			float rety = y;
			float retz = z;
			// REMIND: are x,y really the best answer if non-invertible?
			if (det != 0) {
				retx -= shx * y;
				rety -= shy * x;
				retz -= shz * z;
				retx /= det;
				rety /= det;
				retz /= det;
			}
			return setPoint(dst, retx, rety, retz);
		}

		@Override
		public void concatenateInto(Transform3D at) {
			// at.shear(shx, shy);
		}

		@Override
		public void getTransform(Transform3D at) {
			// at.setToShear(shx, shy);
		}

		@Override
		public float getShearX() {
			return shx;
		}

		@Override
		public float getShearY() {
			return shy;
		}

		@Override
		public float getShearZ() {
			return shz;
		}

		@Override
		public void setShearX(float shx) {
			this.shx = shx;
			invalidateTransform();
		}

		@Override
		public void setShearY(float shy) {
			this.shy = shy;
			invalidateTransform();
		}

		@Override
		public void setShearZ(float shz) {
			this.shz = shz;
			invalidateTransform();
		}

		@Override
		public void setShear(float shx, float shy, float shz) {
			this.shx = shx;
			this.shy = shy;
			this.shz = shz;
			invalidateTransform();
		}

		@Override
		public void shearBy(float shx, float shy, float shz) {
			// REMIND: Is this correct?
			this.shx *= shx;
			this.shy *= shy;
			this.shz *= shz;
			invalidateTransform();
		}
	}

	// REMIND: These classes must be public for beans property setters to work
	public static final class Affine extends SGTransform.Affine {
		private Transform3D at;

		public Affine(Transform3D at, SGNode child) {
			if (at == null) {
				this.at = new Transform3D();
			} else {
				this.at = new Transform3D();
			}
		}

		@Override
		public Point3f transform(Point3f src, Point3f dst) {
			// return at.transform(src, dst);
			GLH.pointToTransform(src, dst);
			return null;
		}

		@Override
		public Point3f inverseTransform(Point3f src, Point3f dst) {
			// try {
			// return at.inverseTransform(src, dst);
			// } catch (NoninvertibleTransformException e) {
			// return setPoint(dst, src.getX(), src.getY(), src.getZ());
			// }
			return null;
		}

		@Override
		public void concatenateInto(Transform3D at) {
			// at.concatenate(this.at);
		}

		@Override
		public void getTransform(Transform3D at) {
			// at.setTransform(this.at);
		}

		@Override
		public Transform3D getAffine() {
			// return new Transform3D(at);
			return null;
		}

		@Override
		public void setAffine(Transform3D at) {
			// this.at.setTr(at);
			// invalidateTransform();
		}

		@Override
		public void transformBy(Transform3D at) {
			// this.at.concatenate(at);
			invalidateTransform();
		}

		@Override
		public void reset() {
			// this.at.setToIdentity();
			// invalidateTransform();
		}
	}

}
