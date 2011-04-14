package com.android.droidgraph.shape;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.vecmath.Point3d;

public interface IGLShape {
	public BoundingBox getBounds();
	
	public void draw(GL10 gl);

	public boolean contains(Point3d point);
	
	public void loadGLTexture(GL10 gl, Context context);
}
