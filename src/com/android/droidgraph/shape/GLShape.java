package com.android.droidgraph.shape;

import com.android.droidgraph.vecmath.Point3d;

public abstract class GLShape implements IGLShape{
	
	public boolean contains(Point3d point){
		return this.getBounds().intersect(point);
	}
	
	
}
