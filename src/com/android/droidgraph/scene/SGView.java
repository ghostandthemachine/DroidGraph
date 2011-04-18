package com.android.droidgraph.scene;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.android.droidgraph.util.PrintLogUtil;
import com.android.droidgraph.util.Settings;

public class SGView extends GLSurfaceView {
	
	PrintLogUtil log = new PrintLogUtil();

	// the renderer that handles all drawing
	private final SGViewRenderer mRenderer;

	private SGGroup sceneGroup;

	private SGNode scene;

	public SGView(Context context) {
		super(context);
		mRenderer = new SGViewRenderer(this);
		mRenderer.setContext(context);
		setRenderer(mRenderer);
		Settings.setContext(context);
	}

	@Override
	public void setRenderMode(int mode) {
		super.setRenderMode(mode);
		mRenderer.setRenderMode(mode);
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

		mRenderer.setSceneGroup(scene);
	}

	final void markDirty() {
		// mark dirty
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


	@Override
    public boolean onTouchEvent(final MotionEvent event) {
		dumpEvent(event);
		handleEvent(event);
		
		mRenderer.setLookAtX( - (event.getX() / getWidth()) * 4);
		mRenderer.setLookAtY( - (event.getY() / getHeight()) * 4);
		
            return true;
        }

	private void handleEvent(MotionEvent event) {
//		sceneGroup.handleMotionEvent(event);
	}

	/** Show an event in the LogCat view, for debugging */
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
		
		if(actionCode == MotionEvent.ACTION_POINTER_DOWN) {
			Settings.lightStudio.disable();
		} else if (actionCode == MotionEvent.ACTION_POINTER_UP) {
			Settings.lightStudio.enable();
		}
	}
}
