package com.android.droidgraph.util;


public class SGColor {

	public float red; 
	public float green;
	public float blue;
	public float alpha;

	public SGColor() {

	}

	public SGColor(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public SGColor(float gray, float alpha) {
		this.red = this.green = this.blue = gray;
		this.alpha = alpha;
	}
	
}
