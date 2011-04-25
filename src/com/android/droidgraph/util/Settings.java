package com.android.droidgraph.util;

import java.util.HashSet;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;

import com.android.droidgraph.hardware.DGCamera;
import com.android.droidgraph.lighting.LightStudio;
import com.android.droidgraph.scene.SGAbstractShape;
import com.android.droidgraph.scene.SGNode;

public class Settings implements SurfaceHolder.Callback {

	private static Settings gSettingsInstance;

	private static HashSet<SGAbstractShape> nodeIDMap;

	private GL10 gl = null;
	private Context context = null;
	private int width = 0;
	private int height = 0;

	private boolean picking = false;

	private GLSurfaceView.Renderer renderer = null;

	public GLSurfaceView view = null;

	/*
	 * Lights
	 */
	private LightStudio lightStudio;

	/*
	 * Camera
	 */
//	private DGCamera mDGCamera = new DGCamera();

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
	}

	public GLSurfaceView.Renderer getRenderer() {
		return renderer;
	}

	public void setScreenDimensions(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setLightStudio(LightStudio lightStudio) {
		this.lightStudio = lightStudio;
	}

	public LightStudio getLightStudio() {
		return lightStudio;
	}

	public void setView(GLSurfaceView view) {
		this.view = view;
	}

	public GLSurfaceView getGLSurfaceView() {
		return view;
	}

	public DGCamera getCamera() {
//		return mDGCamera;
		return null;
	}

	public void startCameraFeed() {
//		mDGCamera.start();
	}

	public void stopCameraFeed() {
//		mDGCamera.stop();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		startCameraFeed();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopCameraFeed();
	}

	public void picking(boolean pick) {
		picking = pick;
	}

	public boolean isPicking() {
		return picking;
	}

	public static Settings getSceneSettingsInstance() {
		return gSettingsInstance;
	}

	public static void setSceneSettingsInstance(Settings sceneSettings) {
		gSettingsInstance = sceneSettings;
	}

	public static void setNodeIDMap(HashSet<SGAbstractShape> hashSet) {
		nodeIDMap = hashSet;
	}

	public static HashSet<SGAbstractShape> getNodeIDMap() {
		return nodeIDMap;
	}

//	public static void applySelectionMaterial(GL10 gl, SGColorI color) {
//		final GL10 gl10 = gl;
//		gl10.glColor4f(color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF());
//	}

}
