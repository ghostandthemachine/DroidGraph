package com.android.droidgraph.util;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.android.droidgraph.shape.GLShape;

public class TextureLoader {
	
	GLShape primitive;
	
	public TextureLoader(GLShape primitive){
		this.primitive = primitive;
	}
	
	
	public void loadTexture(GL10 gl, Context context) {
		//Load the texture for the cube once during Surface creation
//		primitive.loadGLTexture(gl, context);
		
		
	}
	
	

}
