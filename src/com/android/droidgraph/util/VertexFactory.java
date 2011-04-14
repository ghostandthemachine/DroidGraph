package com.android.droidgraph.util;

import android.util.Log;

public class VertexFactory {
	
public static float[] makeDiscVerts() {
	return makeDiscVerts(36, 0.5f, 1.5f);
}

public static float[] makeDiscVerts(int segs) {
	return makeDiscVerts(segs, 0.5f, 1.5f);
}
	
public static float[] makeDiscVerts(int segs, float iR, float oR) {

	  int segments = segs;
	  float segSize = (float) Math.PI / segments;

	  float innerRadius = iR;
	  float outerRadius = oR;

	  float defaultZ = 3;

	  float[] verts = new float[segments * 6];

	  for (int i = 0; i < verts.length; i += 6) {

	    float tx = i * segSize;
	    tx = (float) Math.cos(tx);

	    float ty = i * segSize;
	    ty = (float) Math.sin(ty);

	    verts[i] = tx * innerRadius;		// inside verts
	    verts[i + 1] = ty * innerRadius;	//	
	    verts[i + 2] = defaultZ;			//

	    Log.d("inner : ", Float.toString(verts[i]) + ", " + Float.toString(verts[i+1]) + ", " + Float.toString(verts[i+2]));

	    verts[i + 3] = tx * outerRadius;	// outer verts
	    verts[i + 4] = ty * outerRadius;	//
	    verts[i + 5] = defaultZ;			//	

	    Log.d("outer : ", Float.toString(verts[i+3]) + ", " + Float.toString(verts[i+4]) + ", " + Float.toString(verts[i+5]));
	  
	}

	  return verts;
	}



}
