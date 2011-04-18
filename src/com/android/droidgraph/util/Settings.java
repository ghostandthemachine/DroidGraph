package com.android.droidgraph.util;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.android.droidgraph.scene.LightStudio;

public class Settings {
	
	public static GL10 gl = null;
	public static Context context = null;
	public static int width = 0;
	public static int height = 0;
	
	public static GLSurfaceView.Renderer renderer = null;
	
	public static LightStudio lightStudio;
	
	public static void setGL(GL10 gl) {
		gl = gl;
	}
	
	public static GL10 getGL() {
		return gl;
	}
	
	public static void setContext(final Context c) {
		context = c;
	}


	public static void setRenderer(GLSurfaceView.Renderer r) {
		renderer = r;
	}

	public static void setScreenDimensions(int w, int h) {
		width = w;
		height = h;
	}

	public static void setLightStudio(LightStudio lightstudio) {
		// TODO Auto-generated method stub
		lightStudio = lightstudio;
	}

}
