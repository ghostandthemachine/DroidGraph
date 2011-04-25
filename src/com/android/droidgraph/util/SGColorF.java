package com.android.droidgraph.util;

public class SGColorF {

	public float[] color = {1,1,1,1};

	public SGColorF() {

	}

	public SGColorF(float red, float green, float blue, float alpha) {
		color[0] = red;
		color[1] = green;
		color[2] = blue;
		color[3] = alpha;
	}

	public SGColorF(float gray, float alpha) {
		color[0] = color[1] = color[2] = gray;
		color[3] = alpha;
	}
	
}
