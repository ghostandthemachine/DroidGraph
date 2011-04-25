package com.android.droidgraph.hardware;

import java.nio.ByteBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;

// ----------------------------------------------------------------------

public class DGCamera implements Camera.PreviewCallback {
	Camera mCamera;
	int numberOfCameras;
	int cameraCurrentlyLocked;

	// The first rear facing camera
	int defaultCameraId;

	byte[] glCameraFrame = new byte[256 * 256]; // size of a texture must be a
												// power of 2
	int[] cameraTexture;

	public DGCamera() {
		synchronized (this) {
			mCamera = Camera.open();

			Camera.Parameters p = mCamera.getParameters();
			p.setPreviewSize(240, 160);
			mCamera.setParameters(p);

			mCamera.startPreview();
			mCamera.setPreviewCallback(this);
			mCamera.addCallbackBuffer(glCameraFrame);
		}

		// Find the total number of cameras available
		numberOfCameras = Camera.getNumberOfCameras();

		// Find the ID of the default camera
		CameraInfo cameraInfo = new CameraInfo();
		for (int i = 0; i < numberOfCameras; i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
				defaultCameraId = i;
			}
		}

	}

	public void start() {
		resume();
	}

	protected void resume() {
		// Open the default i.e. the first rear facing camera.
		mCamera = Camera.open();
		cameraCurrentlyLocked = defaultCameraId;
	}

	public void stop() {
		pause();
	}

	protected void pause() {
		// Because the Camera object is a shared resource, it's very
		// important to release it when the activity is paused.
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}

	/**
	 * Generates a texture from the black and white array filled by the
	 * onPreviewFrame method.
	 */
	void bindCameraTexture(GL10 gl) {
		synchronized (this) {
			if (cameraTexture == null)
				cameraTexture = new int[1];
			else
				gl.glDeleteTextures(1, cameraTexture, 0);

			gl.glGenTextures(1, cameraTexture, 0);
			int tex = cameraTexture[0];
			gl.glBindTexture(GL10.GL_TEXTURE_2D, tex);
			gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_LUMINANCE, 256, 256,
					0, GL10.GL_LUMINANCE, GL10.GL_UNSIGNED_BYTE,
					ByteBuffer.wrap(glCameraFrame));
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
					GL10.GL_LINEAR);
		}
	}


	@Override
	public void onPreviewFrame(byte[] yuvs, Camera camera) {
		int bwCounter = 0;
		int yuvsCounter = 0;
		for (int y = 0; y < 160; y++) {
			System.arraycopy(yuvs, yuvsCounter, glCameraFrame, bwCounter, 240);
			yuvsCounter = yuvsCounter + 240;
			bwCounter = bwCounter + 256;
		}
	}

}

