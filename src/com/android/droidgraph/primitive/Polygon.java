package com.android.droidgraph.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.opengles.GL10;

public class Polygon extends SGLNode{

	/**
	 * The Square constructor.
	 * 
	 * Initiate the buffers.
	 */
	public Polygon(float[] verts) {
		
		colors = new float[] { 1.0f, 1.0f, 1.0f, 1.0f, // Bottom right color
				1.0f, 1.0f, 1.0f, 1.0f, // Top Right color
				1.0f, 1.0f, 1.0f, 1.0f, // Bottom left color
				1.0f, 1.0f, 1.0f, 1.0f // Top Left color
		};

		vertices = verts;
		//
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

//		byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
//		byteBuf.order(ByteOrder.nativeOrder());
//		colorBuffer = byteBuf.asFloatBuffer();
//		colorBuffer.put(colors);
//		colorBuffer.position(0);

	}

	public void setColor(float r, float g, float b, float a) {
			
//		colors = new float[]{
//				r,g,b,a,
//				r,g,b,a,
//				r,g,b,a,
//				r,g,b,a
//			};
//
//			ByteBuffer byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
//			byteBuf.order(ByteOrder.nativeOrder());
//
//			// clear the old buffer first
//			colorBuffer.clear();
//			
//			// now update with new vals
//			colorBuffer = byteBuf.asFloatBuffer();
//			colorBuffer.put(colors);
//			colorBuffer.position(0);

	}

	/**
	 * The object own drawing function. Called from the renderer to redraw this
	 * instance with possible changes in values.
	 * 
	 * @param gl
	 *            - The GL Context
	 */
	public void draw(GL10 gl) {
		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);

		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT_AND_DIFFUSE, colorBuffer);
		


		// Enable vertex buffer
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}

}
