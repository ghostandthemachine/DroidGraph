package com.android.droidgraph.util;

import javax.microedition.khronos.opengles.GL10;

public class Settings {
	
	public static GL10 glParent = null;
	
	
	
	public static void setGL(GL10 gl) {
		glParent = gl;
	}
	
	public static GL10 getGL() {
		return glParent;
	}

}
