package com.android.droidgraph.scene;

import java.util.HashSet;
import java.util.Map.Entry;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;

import com.android.droidgraph.lighting.LightStudio;
import com.android.droidgraph.util.GLH;
import com.android.droidgraph.util.PrintLogUtil;
import com.android.droidgraph.util.SGColorI;
import com.android.droidgraph.util.SGLog;
import com.android.droidgraph.util.Settings;

class SGRenderer implements GLSurfaceView.Renderer {

	// for debug
	PrintLogUtil log = new PrintLogUtil();

	private float[] background = { 1f, 1f, 1f, 1f };
	private int renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY;
	private SGGroup sceneGroup;
	private float lx = 0f;
	private float ly = 0f;

	private LightStudio mLightStudio;
	
	private Settings mSettings;

	public SGRenderer(SGView view, Settings settings) {
		mSettings = settings;
		mSettings.setRenderer(this);
		
		mLightStudio = new LightStudio(mSettings);

	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig eglc) {
		GLH.setGL(gl);
		mSettings.setGL(gl);
		//Settings
		gl.glDisable(GL10.GL_DITHER);				//Disable dithering ( NEW )
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		//Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
		
		mLightStudio.load(gl);
		sceneGroup.load(gl);
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
		gl.glClearColor(background[0],background[1],background[2],background[3]);
		GLU.gluLookAt(gl, lx, ly, 5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

//		mLightStudio.draw(gl);
		
		// Draw the root of the scene
		sceneGroup.render(gl);
		
		// call any kill methods to end the drawing cycle
//		mLightStudio.killDraw(gl);

	}

	public void setSceneGroup(SGNode group) {
		// SGGroup sggroup = (SGGroup) group;
		sceneGroup = (SGGroup) group;
	}
	
	public void setLookAtX(float x) {
		this.lx = x;
	}

	public void setLookAtY(float y) {
		this.ly = y;
	}

	public void setRenderMode(int mode) {
		if (mode == 0 || renderMode == 1) {
			renderMode = mode;
		} else {
			SGLog.error("render mode must be 0 - GLSurfaceView.RENDERMODE_CONTINUOUSLY, or 1 - GLSurfaceView.RENDERMODE_WHEN_DIRTY");
		}
	}

	public void setContext(Context context) {
		mSettings.setContext(context);
	}
	
	public LightStudio getLightStudio() {
		return mLightStudio;
	}

	public void processSelection(MotionEvent e, SGColorI inputColor) {
		HashSet<SGAbstractShape> gNodeIDMap = Settings.getNodeIDMap();
		for(SGAbstractShape node : gNodeIDMap) {
			if(inputColor.equals(node.getColorID())){
				node.setSelected(true);
			}
		}
	}

	
	

}