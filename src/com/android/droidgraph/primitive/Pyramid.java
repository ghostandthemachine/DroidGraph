package com.android.droidgraph.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.shape.GLShape;

/**
 * 
 * @author Savas Ziplies (nea/INsanityDesign)
 */
public class Pyramid extends GLShape {

	/** The buffer holding the vertices */
	private FloatBuffer vertexBuffer;
	/** The buffer holding the color values */
	private FloatBuffer colorBuffer;

	/** The initial vertex definition */
	private float vertices[] = { 0.0f, 1.0f, 0.0f, // Top Of Triangle (Front)
			-1.0f, -1.0f, 1.0f, // Left Of Triangle (Front)
			1.0f, -1.0f, 1.0f, // Right Of Triangle (Front)
			0.0f, 1.0f, 0.0f, // Top Of Triangle (Right)
			1.0f, -1.0f, 1.0f, // Left Of Triangle (Right)
			1.0f, -1.0f, -1.0f, // Right Of Triangle (Right)
			0.0f, 1.0f, 0.0f, // Top Of Triangle (Back)
			1.0f, -1.0f, -1.0f, // Left Of Triangle (Back)
			-1.0f, -1.0f, -1.0f, // Right Of Triangle (Back)
			0.0f, 1.0f, 0.0f, // Top Of Triangle (Left)
			-1.0f, -1.0f, -1.0f, // Left Of Triangle (Left)
			-1.0f, -1.0f, 1.0f, // Right Of Triangle (Left)
			1f, -1f, 1f, // Bottom - front left
			-1f, -1f, -1f, // Bottom - back left
			-1f, -1f, 1f, // Bottom - front right
			1f, -1f, 1f, -1f, -1f, -1f, 1f, -1f, -1f };
	/** The initial color definition */
	private float colors[] = { 
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
			0.0f, 1.0f, 0.0f, 1.0f, // Green
	};

	/**
	 * The Pyramid constructor.
	 * 
	 * Initiate the buffers.
	 */
	public Pyramid() {
		//
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		//
		byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		colorBuffer = byteBuf.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
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

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

		// Point to our buffers
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);

		// Enable the vertex and color state
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// Draw the vertices as triangles
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices.length / 3);

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}

	public void setColor(float r, float g, float b, float a) {
		colorBuffer.position(0);
		for (int i = 0; i < 18; i++) {
			colorBuffer.put(r);
			colorBuffer.put(g);
			colorBuffer.put(b);
			colorBuffer.put(a);
		}
		colorBuffer.position(0);
	}

	@Override
	public void loadGLTexture() {
		// TODO Auto-generated method stub

	}

	public FloatBuffer getColorBuffer() {
		return colorBuffer;
	}

	@Override
	public FloatBuffer getTextureBuffer() {
		// TODO Auto-generated method stub
		return null;
	}
}
