package com.android.droidgraph.fx;

import com.android.droidgraph.scene.SGShape;
import com.android.droidgraph.scene.SGTransform;
import com.android.droidgraph.vecmath.Vector3f;

public class FXShape extends SGShape {

	private SGTransform.Translate t;
	private float translateX = 0;
	private float translateY = 0;
	private float translateZ = 0;
	
	private SGTransform.Rotate r;
	private float rotateX = 0;
	private float rotateY = 0;
	private float rotateZ = 0;	
	
	private SGTransform.Scale s;
	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;
	
	public FXShape() {
		t = SGTransform.createTranslation(0, 0, 0, this);
		r = SGTransform.createRotation(0, 0, 0, this);
		s = SGTransform.createScale(1, 1, 1, this);
	}

	
	
	
	/*
	 * getters and setters
	 */
	
	/*
	 * translation
	 */

	public float getTranslateX() {
		return t.getTranslateX();
	}

	public float getTranslateY() {
		return t.getTranslateY();
	}

	public float getTranslateZ() {
		return t.getTranslateZ();
	}

	public void setTranslateX(float tx) {
		translateX = tx;
		t.setTranslateX(tx);
	}

	public void setTranslateY(float ty) {
		translateY = ty;
		t.setTranslateY(ty);
	}

	public void setTranslateZ(float tz) {
		translateZ = tz;
		t.setTranslateZ(tz);
	}

	public void setTranslation(float tx, float ty, float tz) {
		translateX = tx;
		translateY = ty;
		translateZ = tz;
		t.setTranslation(tx, tx, tx);
		log.pl("FXShape - setTranslation()", translateX, translateY, translateZ);
	}

	public void translateBy(float tx, float ty, float tz) {
		setTranslation(translateX += tx, translateY += ty, translateZ += tz);
	}
	
	/*
	 * rotation
	 */
	public Vector3f getRotation() {
		return r.getRotation();
	}

	public float getRotationX() {
		return r.getRotationX();
	}

	public float getRotationY() {
		return r.getRotationY();
	}

	public float getRotationZ() {
		return r.getRotationZ();
	}
	
	public void setRotation(float thetax, float thetay, float thetaz) {
		r.setRotation(thetax, thetay, thetaz);
		rotateX = thetax;
		rotateY = thetay;
		rotateZ = thetaz;
	}
	
	public void setRotateX(float rx) {
		rotateX = rx;
		r.setRotation(rotateZ, rotateY, rotateZ);
	}
	
	public void setRotateY(float ry) {
		rotateY = ry;
		r.setRotation(rotateZ, rotateY, rotateZ);
	}
	
	public void setRotateZ(float rz) {
		rotateZ = rz;
		r.setRotation(rotateZ, rotateY, rotateZ);
	}

	public void rotateBy(Vector3f v) {
		setRotation(rotateX += v.x, rotateY += v.y, rotateZ += v.z);
	}

	public void rotateXBy(float thetax) {
		setRotateX(rotateX += thetax);
	}

	public void rotateYBy(float thetay) {
		setRotateY(rotateY += thetay);
	}

	public void rotateZBy(float thetaz) {
		setRotateZ(rotateZ += thetaz);
	}
	
	/*
	 * scale
	 */
	public float getScaleX() {
		return s.getScaleX();
	}

	public float getScaleY() {
		return s.getScaleY();
	}

	public float getScaleZ() {
		return s.getScaleZ();
	}

	public void setScaleX(float sx) {
		s.setScaleX(sx);
	}

	public void setScaleY(float sy) {
		s.setScaleY(sy);
	}

	public void setScaleZ(float sz) {
		s.setScaleZ(sz);
	}

	public void setScale(float sx, float sy, float sz) {
		s.setScale(sx, sy, sz);
	}

	public void scaleBy(float sx, float sy, float sz) {
		setScale(scaleX += sx, scaleY += sy, scaleZ += sz);
	}

}