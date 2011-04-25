package com.android.droidgraph.util;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

import com.android.droidgraph.geom.Transform3D;
import com.android.droidgraph.material.Material;
import com.android.droidgraph.vecmath.Matrix3f;
import com.android.droidgraph.vecmath.Point3f;
import com.android.droidgraph.vecmath.Vector3f;

//	GL Helper class
public class GLH {

	static PrintLogUtil l = new PrintLogUtil();
	public static GL10 gl;

	public static void transform(final GL10 _gl, Transform3D transform) {
		final Vector3f t = new Vector3f();
		transform.get(t);
		gl.glTranslatef(t.x, t.y, t.z);
	}

	public static void pushMatrix() {
		gl.glPushMatrix();
	}

	public static void popMatrix() {
		gl.glPopMatrix();
	}

	public static Transform3D pointToTransform(final Point3f p1,
			final Point3f p2) {
		// Position Vector
		final Vector3f v = new Vector3f(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
		// Rotation Matrix
		final Matrix3f m = new Matrix3f(0, 0, 0, 0, 0, 0, 0, 0, 0);
		// Scale
		final float scale = 1f;

		// l.pl("GLH.pointToTransform(p1, p2)", v);

		return new Transform3D(m, v, scale);
	}

	public static void translate(float x, float y, float z) {
		gl.glTranslatef(x, y, z);
	}

	public static void rotate(float thetax, float thetay, float thetaz) {
		gl.glRotatef(thetax, 1, 0, 0);
		gl.glRotatef(thetay, 0, 1, 0);
		gl.glRotatef(thetaz, 0, 0, 1);
	}

	public static void scale(float sx, float sy, float sz) {
		gl.glScalef(sx, sy, sz);
	}

	public static void color(SGColorF c) {
		gl.glColor4f(c.color[0], c.color[1], c.color[2], c.color[3]);
	}

	public static void color(float r, float g, float b, float a) {
		gl.glColor4f(r, g, b, a);
	}

	public static void setGL(GL10 _gl) {
		gl = _gl;
	}

	public static void surfaceCreateTranslucentBackground() {
		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		gl.glClearColor(0, 0, 0, 0);
		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);
	}
	
	public static void initTranslucentGLSurfaceView(GLSurfaceView view) {
		view.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		view.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	}
	
	
	public static Material defaultMaterial;
	
	public static Material getDefaultMaterial() {
		return defaultMaterial;
	}
	public static void setDefaultMaterial(Material material) {
		defaultMaterial = material;
	}

}
