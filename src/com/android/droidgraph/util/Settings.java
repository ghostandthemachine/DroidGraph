package com.android.droidgraph.util;

import java.util.HashSet;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.android.droidgraph.PixelBuffer;
import com.android.droidgraph.hardware.DGCamera;
import com.android.droidgraph.lighting.LightStudio;
import com.android.droidgraph.scene.SGAbstractShape;

public class Settings implements SurfaceHolder.Callback {

	private HashSet<SGAbstractShape> nodeIDMap;

	private GL10 gl;
	private Context context;
	private int width = 1280;
	private int height = 696;

	private int lastScreenArea = width + height;

	private boolean picking = false;

	private GLSurfaceView.Renderer renderer;

	public GLSurfaceView mView;;

	private PixelBuffer mPixelBuffer;

	private String TAG = "Settings";

	/*
	 * Lights
	 */
	private LightStudio lightStudio;

	/*
	 * Camera
	 */
	// private DGCamera mDGCamera = new DGCamera();

	public void setGL(GL10 gl) {
		this.gl = gl;
	}

	public GL10 getGL() {
		return gl;
	}

	public void setContext(final Context context) {
		this.context = context;
	}

	public Context getContext() {
		return context;
	}

	public void setRenderer(GLSurfaceView.Renderer renderer) {
		this.renderer = renderer;

//		mPixelBuffer = new PixelBuffer(width, height);
//		mPixelBuffer.setRenderer(this.renderer);
	}

	public GLSurfaceView.Renderer getRenderer() {
		return renderer;
	}

	public void setScreenDimensions(int width, int height) {
		if (lastScreenArea != width + height) {
			if (width + height > 10) {
				this.width = width;
				this.height = height;
//				mPixelBuffer.setDimensions(this.width, this.height);
				lastScreenArea = width + height;
//				Log.d("Settings.setScreenDim()", width + "  " + height);
			}
		}
	}

	public int getScreenWidth() {
		return width;
	}

	public int getScreenHeight() {
		return height;
	}

	public void setLightStudio(LightStudio lightStudio) {
		this.lightStudio = lightStudio;
	}

	public LightStudio getLightStudio() {
		return lightStudio;
	}

	public void setView(GLSurfaceView view) {
		this.mView = view;
	}

	public GLSurfaceView getGLSurfaceView() {
		return mView;
	}

	public DGCamera getCamera() {
		// return mDGCamera;
		return null;
	}

	public void startCameraFeed() {
		// mDGCamera.start();
	}

	public void stopCameraFeed() {
		// mDGCamera.stop();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		startCameraFeed();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopCameraFeed();
	}


	private Bitmap getPickBitmap() {
//		return mPixelBuffer.getBitmap();
		return null;
	}

	public boolean pick() {
		return picking;
	}
	
	private int pickX;
	private int pickY;
	
	public void setPickPoint(MotionEvent event) {
		pickX = (int) event.getX();
		pickY = (int) event.getY();
	}

	public int[] getPickPoint() {
		return new int[] {pickX, pickY};
	}
	
	public Settings getSceneSettingsInstance() {
		return this;
	}

	public void setNodeIDMap(HashSet<SGAbstractShape> hashSet) {
		nodeIDMap = hashSet;
	}

	public HashSet<SGAbstractShape> getNodeIDMap() {
		return nodeIDMap;
	}

	public void pick(boolean b) {
		picking = b;
	}

	// public void applySelectionMaterial(GL10 gl, SGColorI color) {
	// final GL10 gl10 = gl;
	// gl10.glColor4f(color.getRedF(), color.getGreenF(), color.getBlueF(),
	// color.getAlphaF());
	// }
	
	

}
