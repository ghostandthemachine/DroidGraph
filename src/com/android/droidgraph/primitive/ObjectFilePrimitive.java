package com.android.droidgraph.primitive;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.android.droidgraph.loader.Loader;
import com.android.droidgraph.shape.GLShape;
import com.android.droidgraph.util.Settings;

public class ObjectFilePrimitive extends GLShape {

	private Loader mLoader;

	public ObjectFilePrimitive(Settings settings) {
		mLoader = new Loader(settings.getContext(), "test.obj");
		mLoader.init(settings.getGL());
	}

	@Override
	public void draw(GL10 gl) {

		mLoader.draw((GL11) gl);

	}


	@Override
	public void loadGLTexture() {
		// TODO Auto-generated method stub
	}

	@Override
	public FloatBuffer getTextureBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

}
