package com.android.droidgraph.loader;

public class RGBColor {
	float R;
	float G;
	float B;
	public RGBColor(float r, float g, float b) {
		super();
		R = r;
		G = g;
		B = b;
	}
	public float getR() {
		return R;
	}
	public void setR(float r) {
		R = r;
	}
	public float getG() {
		return G;
	}
	public void setG(float g) {
		G = g;
	}
	public float getB() {
		return B;
	}
	public void setB(float b) {
		B = b;
	}
	public String toString(){
		String str=new String();
		str+="R:"+R+" G:"+G+" B:"+B;
		return str;
	}
}
