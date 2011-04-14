package com.android.droidgraph.event;

import com.android.droidgraph.util.GLH;
import com.android.droidgraph.vecmath.Vector3f;

public class ScaleEventDispatcher extends GraphNodeEvent{
	
	private Vector3f scale;
	
	public ScaleEventDispatcher(float sx, float sy, float sz) {
		scale = new Vector3f(sx,sy,sz);
	}
	
	
	@Override
	public void run() {
		GLH.scale(scale.x, scale.y, scale.z);
	}
	
	
	public void setScale(float sx, float sy, float sz) {
		scale.set(sx,sy,sz);
	}
	
	public void setScaleX(float sx) {
		scale.x = sx;
	}
	
	public void setScaleY(float sy) {
		scale.y = sy;
	}
	
	public void setScaleZ(float sz) {
		scale.z = sz;
	}
	
	public Vector3f getScale() {
		return scale;
	}
	
	public void scaleBy(float sx, float sy, float sz) {
		scale.set(scale.x + sx, scale.y + sy, scale.z + sz);
	}
	
	public void scaleXBy(float sx) {
		scale.x += sx;
	}
	
	public void scaleYBy(float sy) {
		scale.y += sy;
	}
	
	public void scaleZBy(float sz) {
		scale.z += sz;
	}
	

}
