package com.android.droidgraph.scene;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.android.droidgraph.material.Material;
import com.android.droidgraph.util.GLH;
import com.android.droidgraph.util.PrintLogUtil;
import com.android.droidgraph.util.Settings;

public class SGView extends GLSurfaceView {

	PrintLogUtil log = new PrintLogUtil();

	// the renderer that handles all drawing
	private final SGRenderer mRenderer;

	private SGGroup sceneGroup;

	private SGNode scene;

	private Settings mSettings;

	private IntBuffer viewportBuffer;
	private int[] viewport = new int[4];

	private Bitmap mSavedBM = null;
	

	public SGView(Context context, Settings settings) {
		super(context);
		mRenderer = new SGRenderer(this, settings);
		mRenderer.setContext(context);
		setRenderer(mRenderer);

		// Request focus, otherwise buttons won't react
		this.requestFocus();
		this.setFocusableInTouchMode(true);

		mSettings = settings;
		mSettings.setContext(context);
		mSettings.setView(this);
		GLH.setDefaultMaterial(new Material(null));

		viewport[0] = 0;
		viewport[2] = 0;
		viewport[2] = viewport[3] = 1;

		log.pl("SGView viewport", viewport[0], viewport[1], viewport[2],
				viewport[3]);
		viewportBuffer = createIntBuffer(viewport);

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

	private FloatBuffer createFloatBuffer(float[] in) {
		ByteBuffer buff = ByteBuffer.allocateDirect(in.length * 4);
		buff.order(ByteOrder.nativeOrder());
		FloatBuffer outBuff = buff.asFloatBuffer();
		outBuff.put(in);
		outBuff.position(0);
		return outBuff;
	}

	private void updateFloatBuffer(FloatBuffer buff, float[] in) {
		buff.put(in);
		buff.position(0);
	}

	private void updateIntBuffer(IntBuffer buff, int[] in) {
		buff.put(in);
		buff.position(0);
	}

	private IntBuffer createIntBuffer(int[] in) {
		ByteBuffer buff = ByteBuffer.allocateDirect(in.length * 4);
		buff.order(ByteOrder.nativeOrder());
		IntBuffer outBuff = buff.asIntBuffer();
		outBuff.put(in);
		outBuff.position(0);
		return outBuff;
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
		Log.d("TouchEvent info", sb.toString());
	}

	/** Show an event in the LogCat view, for debugging */
	private void handleEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		if (actionCode == 0) {
			// Paint with colorID color
			mSettings.picking(true);
			dumpEvent(event);
			final GL10 gl = mSettings.getGL();

			int w = viewport[2];
			int h = viewport[3];
			
			ByteBuffer PixelBuffer = ByteBuffer.allocateDirect(4);
			PixelBuffer.order(ByteOrder.nativeOrder());
			gl.glReadPixels(x, y, 1, 1, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, PixelBuffer);
			byte b[] = new byte[4];
			PixelBuffer.get(b);
			String key = "" + b[0] + b[1] + b[2];
			
			Log.d("HandleEvent", x + "  " + y + "  " + key);

			// Check for selection
			// mRenderer.processSelection(event, new SGColorI(pixel[0],
			// pixel[1], pixel[2], pixel[3]));

		} else if (actionCode == 2) {
			mSettings.picking(false);
		}
	}

	public static byte floatToByteValue(float f) {
	    return (byte) ((int) (f * 255f));
	}
	
	public void savePixels(int x, int y, int w, int h, GL10 gl) {
		if (gl == null)
			return;
		synchronized (this) {
			if (mSavedBM != null) {
				mSavedBM.recycle();
				mSavedBM = null;
			}
		}
		int b[] = new int[w * (y + h)];
		int bt[] = new int[w * h];
		IntBuffer ib = IntBuffer.wrap(b);
		ib.position(0);
		gl.glReadPixels(x, 0, w, y + h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);
		for (int i = 0, k = 0; i < h; i++, k++) {
			// OpenGLbitmap is incompatible with Android bitmap
			// and so, some corrections need to be done.
			for (int j = 0; j < w; j++) {
				int pix = b[i * w + j];
				int pb = (pix >> 16) & 0xff;
				int pr = (pix << 16) & 0x00ff0000;
				int pix1 = (pix & 0xff00ff00) | pr | pb;
				bt[(h - k - 1) * w + j] = pix1;
			}
		}
		Bitmap sb = Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888);
		synchronized (this) {
			mSavedBM = sb;
		}
		int test = sb.getPixel(x, y);
		Log.d("SavePixels", Integer.toString(test));
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
