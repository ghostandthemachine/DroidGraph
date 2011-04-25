package com.android.droidgraph.shape;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.util.SGColorF;

public interface IGLShape {
	public void draw(GL10 gl);
	
	public void loadGLTexture();
	
	public FloatBuffer getTextureBuffer();
	
	public void setColor(SGColorF color);
	public SGColorF getColor();

}
