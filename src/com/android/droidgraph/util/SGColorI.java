package com.android.droidgraph.util;

public class SGColorI {

	public int[] color = {0,0,0,0};

	public SGColorI() {

	}

	public SGColorI(int red, int green, int blue, int alpha) {
		color[0] = red;
		color[1] = green;
		color[2] = blue;
		color[3] = alpha;
	}

	public SGColorI(int gray,  int alpha) {
		color[0] = color[1] = color[2] = gray;
		color[3] = alpha;
	}
	
	
	public float getRedF() {
		return color[0] / 255;
	}
	
	public float getGreenF() {
		return color[1] / 255;
	}
	
	public float getBlueF() {
		return color[2] / 255;
	}
	
	public float getAlphaF() {
		return color[3] / 255;
	}
	
	public void setColor(int red, int green , int blue, int alpha) {
		color[0] = red;
		color[1] = green;
		color[2] = blue;
		color[3] = alpha;
	}
	
	public boolean equals(SGColorI c) {
		return c.color[0] == color[0] && c.color[1] == color[1] && c.color[2] == color[2] && c.color[3] == color[3] ? true : false;
	}
	
	public boolean equals(SGColorF c) {
		return ((int) c.color[0] * 255.0f) == color[0] && ((int) c.color[1] * 255.0f) == color[1] && ((int) c.color[2] * 255.0f) == color[2] && ((int) c.color[3] * 255.0f) == color[3] ? true : false;
	}
	
}
