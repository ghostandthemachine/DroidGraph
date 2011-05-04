package com.android.droidgraph.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.shape.GLShape;

/**
 * This class is an object representation of 
 * a Square containing the vertex information
 * and drawing functionality, which is called 
 * by the renderer.
 * 
 * @author Savas Ziplies (nea/INsanityDesign)
 */
public class Rect extends GLShape{
		
	/** The buffer holding the vertices */
	private FloatBuffer vertexBuffer;

	/** The initial vertex definition */
	private float[] vertices;
	
	private FloatBuffer colorBuffer;
	/** The initial color definition */
	private float colors[] = {
		    					0.0f, 0.0f, 1.0f, 1.0f, //Set The Color To Red, last value 100% luminance
		    					0.0f, 0.0f, 1.0f, 1.0f, //Set The Color To Green, last value 100% luminance
		    					1.0f, 1.0f, 0.0f, 1.0f, 	//Set The Color To Blue, last value 100% luminance
		    					1.0f, 1.0f, 0.0f, 1.0f
					    							};
	
	/**
	 * The Square constructor.
	 * 
	 * Initiate the buffers.
	 */
	public Rect(float tx, float ty, float w, float h) {
		
		vertices = new float[]{
				tx, ty + h, 0f,
				tx + w, ty + h, 0f,
				tx, ty, 0f,
				tx + w, ty, 0f
		};
		//
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		colorBuffer = byteBuf.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}

	/**
	 * The object own drawing function.
	 * Called from the renderer to redraw this instance
	 * with possible changes in values.
	 * 
	 * @param gl - The GL Context
	 */
	@Override
	public void draw(GL10 gl) {		
		//Set the face rotation
		gl.glFrontFace(GL10.GL_CW);
		
		//Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		
		//Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		
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
