package com.android.droidgraph.scene;

import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.android.droidgraph.renderer.BufferedRenderer;
import com.android.droidgraph.renderer.SGRenderer;
import com.android.droidgraph.util.Settings;

public class SGView extends GLSurfaceView {

//	private String TAG = "SGRenderer";
	// the renderer that handles all drawing
	private SGRenderer mRenderer;
	private BufferedRenderer mBufferedRenderer;
	private SGGroup sceneGroup;
	private SGNode scene;
	private Settings mSettings;
	private int[] viewport = new int[4];
	
	public SGView(Context context, Settings settings) {
		super(context);

		mSettings = settings;
		mSettings.setContext(context);
		mSettings.setView(this);
		
		mRenderer = new SGRenderer(this, settings);
		mBufferedRenderer = new BufferedRenderer(this, settings);
		
		mBufferedRenderer.setContext(context);
		mRenderer.setContext(context);
		
		setRenderer(mBufferedRenderer);
//		setRenderer(mRenderer);

		// Request focus, otherwise buttons won't react
		this.requestFocus();
		this.setFocusableInTouchMode(true);

		viewport[0] = 0;
		viewport[2] = 0;
		viewport[2] = viewport[3] = 1;
	}

	@Override
	protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
		super.onSizeChanged(xNew, yNew, xOld, yOld);
		viewport[2] = xNew;
		viewport[3] = yNew;
	}

	@Override
	public void setRenderMode(int mode) {
		super.setRenderMode(mode);
		mBufferedRenderer.setRenderMode(mode);
	}

	SGGroup getSceneGroup() {
		return sceneGroup;
	}

	public final SGNode getScene() {
		return scene;
	}

	void removeScene() {
		scene = null;
		sceneGroup = null;
		markDirty();
	}

	public void setScene(SGNode scene) {
		SGView oldview = scene.getPanel();
		if (oldview != null && oldview.getScene() == scene) {
			oldview.removeScene();
		}
		SGParent oldParent = scene.getParent();
		if (oldParent != null) {
			oldParent.remove(scene);
		}

		this.scene = scene;
		sceneGroup = new SGGroup();
		sceneGroup.add(scene);
		sceneGroup.setParent(this);

		mBufferedRenderer.setSceneGroup(scene);
		mRenderer.setSceneGroup(scene);
	}

	final void markDirty() {
		// mark dirty
	}

	/**
	 * Override the touch screen listener.
	 * 
	 * React to moves and presses on the touchscreen.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		handleEvent(e);
		// We handled the event
		return true;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
			queueEvent(new Runnable() {
				// This method will be called on the rendering
				// thread:
				public void run() {

					// call to renderer
				}
			});
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/** Show an event in the LogCat view, for debugging */
	@SuppressWarnings("unused")
	private void dumpEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
				"POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);
		if (actionCode == MotionEvent.ACTION_POINTER_DOWN
				|| actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(
					action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}
		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append((float) event.getX(i) / getWidth());
			sb.append(",").append((float) event.getY(i) / getHeight());
			if (i + 1 < event.getPointerCount())
				sb.append(";");
		}
		sb.append("]");
//		Log.d("TouchEvent info", sb.toString());
	}

	/** Show an event in the LogCat view, for debugging */
	private void handleEvent(MotionEvent event) {
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		if (actionCode == 0) {
			// Paint with colorID color
			mSettings.setPickPoint(event);
			mSettings.pick(true);
			
		} else if (actionCode == 2) {
		}
	}

	public static byte floatToByteValue(float f) {
	    return (byte) ((int) (f * 255f));
	}
	
	
	static String saveBitmap(Bitmap bitmap, String dir, String baseName) {
		try {
			File sdcard = Environment.getExternalStorageDirectory();
			File pictureDir = new File(sdcard, dir);
			pictureDir.mkdirs();
			File f = null;
			for (int i = 1; i < 200; ++i) {
				String name = baseName + i + ".png";
				f = new File(pictureDir, name);
				if (!f.exists()) {
					break;
				}
			}
			if (!f.exists()) {
				String name = f.getAbsolutePath();
				FileOutputStream fos = new FileOutputStream(name);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				fos.flush();
				fos.close();
				return name;
			}
		} catch (Exception e) {
		} finally {
			/*
			 * if (fos != null) { fos.close(); }
			 */
		}
		return null;
	}
}
