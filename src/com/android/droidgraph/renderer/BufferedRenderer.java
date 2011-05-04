package com.android.droidgraph.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11ExtensionPack;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.MotionEvent;

import com.android.droidgraph.fx.FXGroup;
import com.android.droidgraph.lighting.LightStudio;
import com.android.droidgraph.primitive.Cube;
import com.android.droidgraph.primitive.Triangle;
import com.android.droidgraph.scene.SGAbstractShape;
import com.android.droidgraph.scene.SGNode;
import com.android.droidgraph.scene.SGView;
import com.android.droidgraph.util.GLH;
import com.android.droidgraph.util.PrintLogUtil;
import com.android.droidgraph.util.SGColorI;
import com.android.droidgraph.util.SGLog;
import com.android.droidgraph.util.Settings;

public class BufferedRenderer implements GLSurfaceView.Renderer {

	// for debug
	PrintLogUtil log = new PrintLogUtil();
	String TAG = "SGRenderer";

	private boolean mContextSupportsFrameBufferObject;
//	private int mTargetTexture;
	private int mFramebuffer;
	private int mFramebufferWidth = 512;
	private int mFramebufferHeight = 512;
	private int mSurfaceWidth;
	private int mSurfaceHeight;

	private float[] background = { 0f, 0f, 0f, 1f };
	private int renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY;
	private FXGroup mSceneGroup;
	private float lookx = 0f;
	private float looky = 0f;

	private LightStudio mLightStudio;
	private Settings mSettings;

	private static final boolean DEBUG_RENDER_OFFSCREEN_ONSCREEN = false;
	
	Triangle triangle;
	Cube cube;

	public BufferedRenderer(SGView view, Settings settings, SGNode... nodes) {
		mSettings = settings;
		mSettings.setRenderer(this);
		
		mLightStudio = new LightStudio(mSettings);
	}


	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		checkGLError(gl);
		
		mSurfaceWidth = width;
		mSurfaceHeight = height;
		
		gl.glViewport(0, 0, width, height); // Reset The Current Viewport
		mSettings.setScreenDimensions(width, height);
	}


	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig eglc) {
		GLH.setGL(gl);
		mSettings.setGL(gl);
		
		mContextSupportsFrameBufferObject = checkIfContextSupportsFrameBufferObject(gl);
		if (mContextSupportsFrameBufferObject) {
//			mTargetTexture = createTargetTexture(gl, mFramebufferWidth, mFramebufferHeight);
			mFramebuffer = createFrameBuffer(gl, mFramebufferWidth,	mFramebufferHeight);
			
			triangle = new Triangle();
			cube = new Cube();
			
			mLightStudio.load(gl);
			mSceneGroup.load(gl);
		}
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		checkGLError(gl);
		if (mContextSupportsFrameBufferObject) {
			GL11ExtensionPack gl11ep = (GL11ExtensionPack) gl;
			if (DEBUG_RENDER_OFFSCREEN_ONSCREEN) {
				drawOffscreenImage(gl, mSurfaceWidth, mSurfaceHeight);
			} else {
				if(mSettings.pick()){
					Log.d(TAG, "pick method called");
					gl11ep.glBindFramebufferOES(GL11ExtensionPack.GL_FRAMEBUFFER_OES, mFramebuffer);
					drawToBuffer(gl, mSurfaceWidth, mSurfaceWidth);
					mSettings.pick(false);
					
				} else {
					gl11ep.glBindFramebufferOES(GL11ExtensionPack.GL_FRAMEBUFFER_OES, 0);
					drawOnscreen(gl, mSurfaceWidth, mSurfaceHeight);
				}
			}
		} else {
			// Current context doesn't support frame buffer objects.
			// Indicate this by drawing a red background.
			gl.glClearColor(1, 0, 0, 0);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		}
	}

	// Here is where the main drawing loop which is displayed 
	// on the screen takes place. 
	private void drawOnscreen(GL10 gl, int width, int height) {
		 gl.glViewport(0, 0, width, height);
         float ratio = (float) width / height;
         gl.glMatrixMode(GL10.GL_PROJECTION);
         gl.glLoadIdentity();
         gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);

         gl.glClearColor(background[0], background[1], background[2], background[3]);
         gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

         gl.glMatrixMode(GL10.GL_MODELVIEW);
         gl.glLoadIdentity();

         GLU.gluLookAt(gl, lookx, looky, 5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

         gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
         gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

//         mLightStudio.render(gl);
         
         mSceneGroup.render(gl);
         
//         mLightStudio.killRender(gl);
         
         // Restore default state so the other renderer is not affected.
         gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
         gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	private void drawOffscreenImage(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		float ratio = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);
		
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glClearColor(background[0], background[1], background[2], background[3]);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        GLU.gluLookAt(gl, lookx, looky, 5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// Render the pick color ID here
        mSceneGroup.render(gl);

//		pickReadPixels(gl, 3, 3);

		// Restore default state so the other renderer is not affected.
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
	
	private void drawToBuffer(GL10 gl, int width, int height) {
		 gl.glViewport(0, 0, width, height);
         float ratio = (float) width / height;
         gl.glMatrixMode(GL10.GL_PROJECTION);
         gl.glLoadIdentity();
         gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);

         gl.glClearColor(background[0], background[1], background[2], background[3]);
         gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

         gl.glMatrixMode(GL10.GL_MODELVIEW);
         gl.glLoadIdentity();

         GLU.gluLookAt(gl, lookx, looky, 5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

         gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
         gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
         
         mSceneGroup.renderColorID(gl);
         
         // Restore default state so the other renderer is not affected.
         gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
         gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	private void pickReadPixels(GL10 gl, int height, int width) {
		float x = mSettings.getPickPoint()[0];
		float y = mSettings.getPickPoint()[1];
		int screenshotSize = width * height;
		ByteBuffer bb = ByteBuffer.allocateDirect(screenshotSize * 4);
		bb.order(ByteOrder.nativeOrder());
		mSettings.getGL().glReadPixels((int) x, (int) y, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb);
		int pixelsBuffer[] = new int[screenshotSize];
		bb.asIntBuffer().get(pixelsBuffer);
		bb = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for(int i = 0; i < pixelsBuffer.length; i++) {
			sb.append(", " + pixelsBuffer[i]);
		}
		
		Log.d(TAG, sb.toString());
		
		pixelsBuffer = null;
	}

	public void setSceneGroup(SGNode group) {
		// SGGroup sggroup = (SGGroup) group;
		mSceneGroup = (FXGroup) group;

	}

	public void setLookAtX(float x) {
		this.lookx = x;
	}

	public void setLookAtY(float y) {
		this.looky = y;
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
		for (SGAbstractShape node : gNodeIDMap) {
			if (inputColor.equals(node.getColorID())) {
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

	private boolean checkIfContextSupportsFrameBufferObject(GL10 gl) {
		return checkIfContextSupportsExtension(gl, "GL_OES_framebuffer_object");
	}

	/**
	 * This is not the fastest way to check for an extension, but fine if we are
	 * only checking for a few extensions each time a context is created.
	 * 
	 * @param gl
	 * @param extension
	 * @return true if the extension is present in the current context.
	 */
	private boolean checkIfContextSupportsExtension(GL10 gl, String extension) {
		String extensions = " " + gl.glGetString(GL10.GL_EXTENSIONS) + " ";
		// The extensions string is padded with spaces between extensions, but
		// not
		// necessarily at the beginning or end. For simplicity, add spaces at
		// the
		// beginning and end of the extensions string and the extension string.
		// This means we can avoid special-case checks for the first or last
		// extension, as well as avoid special-case checks when an extension
		// name
		// is the same as the first part of another extension name.
		return extensions.indexOf(" " + extension + " ") >= 0;
	}

	private int createTargetTexture(GL10 gl, int width, int height) {
		int texture;
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		texture = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, width, height, 0, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, null);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		return texture;
	}

	private int createFrameBuffer(GL10 gl, int width, int height
//			int targetTextureId
			) {
		GL11ExtensionPack gl11ep = (GL11ExtensionPack) gl;
		int framebuffer;
		int[] framebuffers = new int[1];
		gl11ep.glGenFramebuffersOES(1, framebuffers, 0);
		framebuffer = framebuffers[0];
		gl11ep.glBindFramebufferOES(GL11ExtensionPack.GL_FRAMEBUFFER_OES, framebuffer);

		int depthbuffer;
		int[] renderbuffers = new int[1];
		gl11ep.glGenRenderbuffersOES(1, renderbuffers, 0);
		depthbuffer = renderbuffers[0];

		gl11ep.glBindRenderbufferOES(
				GL11ExtensionPack.GL_RENDERBUFFER_OES,	
				depthbuffer
				);
		
		gl11ep.glRenderbufferStorageOES(
				GL11ExtensionPack.GL_RENDERBUFFER_OES, 
				GL11ExtensionPack.GL_DEPTH_COMPONENT16, 
				width, 
				height
				);
		
		gl11ep.glFramebufferRenderbufferOES(
				GL11ExtensionPack.GL_FRAMEBUFFER_OES,
				GL11ExtensionPack.GL_DEPTH_ATTACHMENT_OES,
				GL11ExtensionPack.GL_RENDERBUFFER_OES, 
				depthbuffer
				);

//		gl11ep.glFramebufferTexture2DOES(
//				GL11ExtensionPack.GL_FRAMEBUFFER_OES,
//				GL11ExtensionPack.GL_COLOR_ATTACHMENT0_OES, 
//				GL10.GL_TEXTURE_2D,
//				targetTextureId, 
//				0
//				);
		
		int status = gl11ep.glCheckFramebufferStatusOES(GL11ExtensionPack.GL_FRAMEBUFFER_OES);
		
		if (status != GL11ExtensionPack.GL_FRAMEBUFFER_COMPLETE_OES) {
			throw new RuntimeException("Framebuffer is not complete: "
					+ Integer.toHexString(status));
		}
		
		gl11ep.glBindFramebufferOES(GL11ExtensionPack.GL_FRAMEBUFFER_OES, 0);
		
		return framebuffer;
	}

}



