package com.android.droidgraph.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.shape.GLShape;
import com.android.droidgraph.vecmath.Point3d;

/**
 * This class is an object representation of a Triangle containing the vertex
 * information, color information and drawing functionality, which is called by
 * the renderer.
 * 
 * @author Savas Ziplies (nea/INsanityDesign)
 */
public class Triangle extends GLShape {

	/** The buffer holding the vertices */
	private FloatBuffer vertexBuffer;
	/** The buffer holding the colors */
	private FloatBuffer colorBuffer;

	Point3d lower = new Point3d();
	Point3d upper = new Point3d();

	/** The initial vertex definition */
	private float vertices[] = { 0.0f, 1.0f, 0.0f, // Top
			-1.0f, -1.0f, 0.0f, // Bottom Left
			1.0f, -1.0f, 0.0f // Bottom Right
	};

	/** The initial color definition */
	private float colors[] = { 1.0f, 0.0f, 0.0f, 1.0f, // Set The Color To Red,
														// last value 100%
														// luminance
			0.0f, 1.0f, 0.0f, 1.0f, // Set The Color To Green, last value 100%
									// luminance
			0.0f, 0.0f, 1.0f, 1.0f // Set The Color To Blue, last value 100%
									// luminance
	};

	/**
	 * The Triangle constructor.
	 * 
	 * Initiate the buffers.
	 */
	public Triangle() {
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

	@Override
	public BoundingBox getBounds() {
		calculateBoundsVerts();
		BoundingBox bounds = new BoundingBox();
		bounds.setLower(lower);
		bounds.setUpper(upper);
		return bounds;
	}

	private void calculateBoundsVerts() {
		float[] verts = vertices;
		for (int i = 0; i < verts.length; i++) {
			if (verts[i + 3] < lower.x) {
				lower.x = verts[i + 3];
			} else {
				upper.x = verts[i + 3];
			}
			if (verts[i + 4] < lower.y) {
				lower.y = verts[i + 4];
			} else {
				upper.y = verts[i + 4];
			}
			if (verts[i + 5] < lower.z) {
				lower.z = verts[i + 5];
			} else {
				upper.z = verts[i + 6];
			}
		}
	}

	@Override
	public void loadGLTexture(GL10 gl, Context context) {
		// TODO Auto-generated method stub
		
	}
}
