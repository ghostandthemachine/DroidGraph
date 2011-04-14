package com.android.droidgraph.scene;

import java.util.HashSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.android.droidgraph.util.PrintLogUtil;
import com.android.droidgraph.util.SGLog;
import com.android.droidgraph.util.Settings;
import com.android.droidgraph.util.TextureLoader;

class SGViewRenderer implements GLSurfaceView.Renderer {

	// for debug
	PrintLogUtil log = new PrintLogUtil();

	private float[] background = { 1f, 1f, 1f, 1f };
	private boolean light = false;
	private int renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY;
	private SGView view;
	private SGGroup sceneGroup;
	private float lx = 0f;
	private float ly = 0f;
	
	private HashSet<TextureLoader> textureLoaders = new HashSet<TextureLoader>();

	public SGViewRenderer(SGView view) {
		this.view = view;
	}

	public void setSceneGroup(SGNode group) {
		// SGGroup sggroup = (SGGroup) group;
		sceneGroup = (SGGroup) group;
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if (height == 0) { // Prevent A Divide By Zero By
			height = 1; // Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); // Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); // Select The Projection Matrix
		gl.glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select The Modelview Matrix
		gl.glLoadIdentity(); // Reset The Modelview Matrix
		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// Clear Screen And Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The Current Modelview Matrix

	    GLU.gluLookAt(gl, lx, ly, 5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		
		// Draw the root of the scene
		sceneGroup.render(gl);

	}
	
	
	public void setLookAtX(float x) {
		this.lx = x;
	}
	
	public void setLookAtY(float y) {
		this.ly = y;
	}
	

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig eglc) {
		// set the global gl
		Settings.setGL(gl);

		// Blending
		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f); // Full Brightness. 50% Alpha (
												// NEW )
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE); // Set The Blending
														// Function For
														// Translucency ( NEW )
	}

	public void setRenderMode(int mode) {
		if (mode == 0 || renderMode == 1) {
			renderMode = mode;
		} else {
			SGLog.error("render mode must be 0 - GLSurfaceView.RENDERMODE_CONTINUOUSLY, or 1 - GLSurfaceView.RENDERMODE_WHEN_DIRTY");
		}
	}

}
