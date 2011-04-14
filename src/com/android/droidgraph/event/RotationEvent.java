package com.android.droidgraph.event;

import com.android.droidgraph.util.GLH;
import com.android.droidgraph.vecmath.Vector3f;

public class RotationEvent extends GraphNodeEvent {
	
	private float thetax;
	private float thetay;
	private float thetaz;

	public RotationEvent(float thetax, float thetay, float thetaz) {
		this.thetax = thetax;
		this.thetay = thetay;
		this.thetaz = thetaz;
	}	
	
	public RotationEvent() {
		this(0,0,0);
	}

	@Override
	public void run() {
		
		GLH.rotate(thetax, thetay, thetaz);
		
	}
	
	public void setRotation(Vector3f v) {
		this.thetax = v.x;
		this.thetay = v.y;
		this.thetaz = v.z;
	}
	
	public void setRotation(float rx, float ry, float rz) {
		setRotation(new Vector3f(rx, ry, rz));
	}
	public void rotateBy(Vector3f v) {
		this.thetax += v.x;
		this.thetay += v.y;
		this.thetaz += v.z;
	}
	
	public void rotateBy(float rx, float ry, float rz) {
		rotateBy(thetax + rx, thetay + ry, thetaz + rz);
	}
	
	public Vector3f getRotation() {
		return new Vector3f(thetax,thetay,thetaz);
	}
	
	


}
