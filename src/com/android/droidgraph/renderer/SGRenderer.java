package com.android.droidgraph.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

import com.android.droidgraph.lighting.LightStudio;
import com.android.droidgraph.scene.SGAbstractShape;
import com.android.droidgraph.scene.SGGroup;
import com.android.droidgraph.scene.SGNode;
import com.android.droidgraph.scene.SGView;
import com.android.droidgraph.util.GLH;
import com.android.droidgraph.util.PrintLogUtil;
import com.android.droidgraph.util.SGColorI;
import com.android.droidgraph.util.SGLog;
import com.android.droidgraph.util.Settings;

public class SGRenderer implements GLSurfaceView.Renderer {

	// for debug
	PrintLogUtil log = new PrintLogUtil();
	private String TAG = "SGRenderer";

	private float[] background = { 0f, 0f, 0f, 1f };
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
		mSettings.setScreenDimensions(width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		checkGLError(gl);
		// Clear Screen And Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The Current Modelview Matrix
		gl.glClearColor(background[0],background[1],background[2],background[3]);
		GLU.gluLookAt(gl, lx, ly, 5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

		mLightStudio.render(gl);

		// Draw the root of the scene
		sceneGroup.render(gl);

		// call any kill methods to end the drawing cycle
		mLightStudio.killRender(gl);
		
		if (mSettings.pick()) {
			pickReadPixels(gl, 1, 1);
		}

	}

	private void pickReadPixels(GL10 gl, int height, int width) {
		boolean screenshot = mSettings.pick();

		float x = mSettings.getPickPoint()[0];

		float y = mSettings.getPickPoint()[1];

		if (screenshot) {
			int screenshotSize = 1;
			ByteBuffer bb = ByteBuffer.allocateDirect(screenshotSize * 4);
			bb.order(ByteOrder.nativeOrder());

			gl.glReadPixels((int) x, mSettings.getScreenHeight() - (int) y, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb);

			int pixelsBuffer[] = new int[screenshotSize];
			bb.asIntBuffer().get(pixelsBuffer);
			bb = null;
			Bitmap bitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.RGB_565);
			bitmap.setPixels(pixelsBuffer, screenshotSize - width, -width, 0,
					0, width, height);

			Log.d(TAG, "" + bitmap.getPixel(0, 0));

			pixelsBuffer = null;

			mSettings.pick(false);
		}
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
		HashSet<SGAbstractShape> gNodeIDMap = mSettings.getNodeIDMap();
		for(SGAbstractShape node : gNodeIDMap) {
			if(inputColor.equals(node.getColorID())){
				node.setSelected(true);
			}
		}
	}

	static void checkGLError(GL gl) {
		int error = ((GL10) gl).glGetError();
		if (error != GL10.GL_NO_ERROR) {
			throw new RuntimeException("GLError 0x"
					+ Integer.toHexString(error));
		}
	}


}
