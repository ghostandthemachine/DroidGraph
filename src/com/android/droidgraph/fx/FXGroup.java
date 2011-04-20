package com.android.droidgraph.fx;

import com.android.droidgraph.scene.SGGroup;
import com.android.droidgraph.scene.SGTransform;
import com.android.droidgraph.vecmath.Vector3f;

public class FXGroup extends SGGroup{

	private SGTransform.Translate translate;
	private float translateX = 0;
	private float translateY = 0;
	private float translateZ = 0;
	
	private SGTransform.Rotate rotate;
	private float rotateX = 0;
	private float rotateY = 0;
	private float rotateZ = 0;	
	
	private SGTransform.Scale scale;
	private float scaleX = 1;
	private float scaleY = 1;
	private float scaleZ = 1;
	
	public FXGroup() {
		translate = SGTransform.createTranslation(0, 0, 0, this);
		rotate = SGTransform.createRotation(0, 0, 0, this);
		scale = SGTransform.createScale(1, 1, 1, this);
	}
	
	
	/*
	 * getters and setters
	 *
	 * translation
	 */

	public float getTranslateX() {
		return translate.getTranslateX();
	}

	public float getTranslateY() {
		return translate.getTranslateY();
	}

	public float getTranslateZ() {
		return translate.getTranslateZ();
	}

	public void setTranslateX(float tx) {
		translateX = tx;
		translate.setTranslateX(tx);
	}

	public void setTranslateY(float ty) {
		translateY = ty;
		translate.setTranslateY(ty);
	}

	public void setTranslateZ(float tz) {
		translateZ = tz;
		translate.setTranslateZ(tz);
	}

	public void setTranslation(float tx, float ty, float tz) {
		translateX = tx;
		translateY = ty;
		translateZ = tz;
		translate.setTranslation(tx, ty, tz);
	}

	public void translateBy(float tx, float ty, float tz) {
		setTranslation(translateX += tx, translateY += ty, translateZ += tz);
	}
	
	/*
	 * rotation
	 */
	public Vector3f getRotation() {
		return rotate.getRotation();
	}

	public float getRotationX() {
		return rotate.getRotationX();
	}

	public float getRotationY() {
		return rotate.getRotationY();
	}

	public float getRotationZ() {
		return rotate.getRotationZ();
	}
	
	public void setRotation(float thetax, float thetay, float thetaz) {
		rotate.setRotation(thetax, thetay, thetaz);
		rotateX = thetax;
		rotateY = thetay;
		rotateZ = thetaz;
	}
	
	public void setRotateX(float rx) {
		rotateX = rx;
		rotate.setRotation(rotateX, rotateY, rotateZ);
	}
	
	public void setRotateY(float ry) {
		rotateY = ry;
		rotate.setRotation(rotateX, rotateY, rotateZ);
	}
	
	public void setRotateZ(float rz) {
		rotateZ = rz;
		rotate.setRotation(rotateX, rotateY, rotateZ);
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
		return scale.getScaleX();
	}

	public float getScaleY() {
		return scale.getScaleY();
	}

	public float getScaleZ() {
		return scale.getScaleZ();
	}

	public void setScaleX(float sx) {
		scale.setScaleX(sx);
	}

	public void setScaleY(float sy) {
		scale.setScaleY(sy);
	}

	public void setScaleZ(float sz) {
		scale.setScaleZ(sz);
	}

	public void setScale(float sx, float sy, float sz) {
		scale.setScale(sx, sy, sz);
	}

	public void scaleBy(float sx, float sy, float sz) {
		setScale(scaleX += sx, scaleY += sy, scaleZ += sz);
	}
}
