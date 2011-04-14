package com.android.droidgraph.actionfactory;

import javax.microedition.khronos.opengles.GL10;


public class RotationTransform {
	
	public float x;
	public float y;
	public float z;
	public float angle;
	
	public RotationTransform(final float angle, final float x, final float y, final float z) {
		
		this.x = x;
		this.y = y;
		this.z = z;
		this.angle = angle;
		
	}
	
	public void run(final GL10 gl) {
		gl.glRotatef(angle, x, y, z);
	}

}
