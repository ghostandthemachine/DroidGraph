package com.android.droidgraph.util;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.geom.Transform3D;
import com.android.droidgraph.vecmath.Matrix3f;
import com.android.droidgraph.vecmath.Point3f;
import com.android.droidgraph.vecmath.Vector3f;

//	GL Helper class
public class GLH {
	
	static PrintLogUtil l = new PrintLogUtil();
	final static GL10 gl = Settings.getGL();
	
	public static void transform(final GL10 _gl, Transform3D transform) {
		final Vector3f t = new Vector3f();
		transform.get(t);
		gl.glTranslatef(t.x, t.y, t.z);
		l.pl("GLH translate", t.toString());
	}
	
	public static void pushMatrix() {
		gl.glPushMatrix();
	}
	
	public static void popMatrix() {
		gl.glPopMatrix();
	}
	
	public static Transform3D pointToTransform(final Point3f p1, final Point3f p2) {
//		Position Vector
		final Vector3f v = new Vector3f(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
//		Rotation Matrix
		final Matrix3f m = new Matrix3f(
				0, 0, 0,
				0, 0, 0,
				0, 0, 0);
//		Scale
		final float scale = 1f;
		
		l.pl("GLH.pointToTransform(p1, p2)", v);

		return new Transform3D(m, v, scale);
	}

	public static void translate(float x, float y, float z) {
		gl.glTranslatef(x,y,z);		
	}

	public static void rotate(float thetax, float thetay, float thetaz) {
		gl.glRotatef(thetax, 1, 0, 0);
		gl.glRotatef(thetay, 0, 1, 0);
		gl.glRotatef(thetaz, 0, 0, 1);
	}

	public static void scale(float sx, float sy, float sz) {
		gl.glScalef(sx, sy, sz);
	}
	
	public static void color(SGColor c) {
		gl.glColor4f(c.red, c.green, c.blue, c.alpha);
	}
	
	public static void color(float r, float g, float b, float a) {
		gl.glColor4f(r, g, b, a);
	}

}
