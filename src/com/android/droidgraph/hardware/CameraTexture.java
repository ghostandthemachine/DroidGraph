package com.android.droidgraph.hardware;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.material.TextureMaterial;
import com.android.droidgraph.scene.SGAbstractShape;
import com.android.droidgraph.util.Settings;


public class CameraTexture extends TextureMaterial {

	private DGCamera mDGCamera;
	private Settings mSettings;
	
	public CameraTexture(SGAbstractShape glshape, Settings settings) {
		super(glshape, settings);
		
		mSettings = settings;
		mDGCamera = settings.getCamera();
	}
	
	@Override
	public void loadMaterial(GL10 gl) {
		
	}
	
	
	@Override
	public void draw(GL10 gl) {
//		Grab the camera frame and make a texture out of it
		mDGCamera.bindCameraTexture(gl);
	}
}