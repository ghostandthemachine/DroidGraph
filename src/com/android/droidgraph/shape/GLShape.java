package com.android.droidgraph.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import com.android.droidgraph.geom.Bounds;
import com.android.droidgraph.util.SGColorF;
import com.android.droidgraph.vecmath.Point3d;


public abstract class GLShape implements IGLShape{

	SGColorF color = new SGColorF(1,1,1,1);
	FloatBuffer colorBuffer;
	
	public GLShape() {
		initBuffer();
		setColor(new SGColorF(1,1,1,1));
	}
	
	public void setColor(SGColorF color) {
		this.color = color;
		updateBuffer();
	}
	
	public SGColorF getColor() {
		return color;
	}
	
	private void initBuffer() {
		ByteBuffer byteBuff = ByteBuffer.allocateDirect(color.color.length * 4);
		byteBuff.order(ByteOrder.nativeOrder());
		colorBuffer = byteBuff.asFloatBuffer();
		colorBuffer.put(color.color);
		colorBuffer.position(0);
	}
	
	private void updateBuffer() {
		colorBuffer.put(color.color);
		colorBuffer.position(0);
	}

	public Bounds getBounds() {
		return null;
	}

	public boolean contains(Point3d point) {
		return false;
	}
}
