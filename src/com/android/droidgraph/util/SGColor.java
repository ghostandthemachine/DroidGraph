package com.android.droidgraph.util;

public class SGColor {

	public float[] color = {1,1,1,1};

	public SGColor() {

	}

	public SGColor(float red, float green, float blue, float alpha) {
		color[0] = red;
		color[1] = green;
		color[2] = blue;
		color[3] = alpha;
	}

	public SGColor(float gray, float alpha) {
		color[0] = color[1] = color[2] = gray;
		color[3] = alpha;
	}
	
}
