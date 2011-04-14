package com.android.droidgraph.util;

import com.android.droidgraph.geom.Transform3D;


public class MatrixUtilities {
	
	
	public static Transform3D multiply(Transform3D m1, Transform3D m2) {
		
		Transform3D rt = new Transform3D();
		
		rt.mul(m1, m2);
	
		
		return rt;
	}
	
	
	

}
