package com.android.droidgraph.scene;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Light {

	/* The initial light values */
	private float[] ambient = { 0.5f, 0.5f, 0.5f, 1.0f };
	private float[] diffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
	private float[] position = { 0.0f, 0.0f, 2.0f, 1.0f };

	/* The buffers for our light values */
	private FloatBuffer lightAmbientBuffer;
	private FloatBuffer lightDiffuseBuffer;
	private FloatBuffer lightPositionBuffer;

	private int id;
	
	private GL10 gl;
	private LightStudio studio;

	public Light(LightStudio _studio, float[] _ambient, float[] _diffuse,
			float[] _position, int _id) {

		gl = studio.getGL();
		studio = _studio;
		
		ambient = _ambient;
		diffuse = _diffuse;
		position = _position;

		// Max id val is 7
		id = (_id <= 7) ? _id : 7;
		id += GL10.GL_LIGHT0;
		//
		
		initBuffers();
	}

	public Light(LightStudio studio, float[] _position, int _id) {

		position = _position;

		// Max id val is 7
		id = (_id <= 7) ? _id : 7;
		id += GL10.GL_LIGHT0;
		//
		
		initBuffers();
	}

	private void initBuffers() {

		ByteBuffer byteBuf = ByteBuffer.allocateDirect(ambient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightAmbientBuffer = byteBuf.asFloatBuffer();
		lightAmbientBuffer.put(ambient);
		lightAmbientBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(diffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightDiffuseBuffer = byteBuf.asFloatBuffer();
		lightDiffuseBuffer.put(diffuse);
		lightDiffuseBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(position.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		lightPositionBuffer = byteBuf.asFloatBuffer();
		lightPositionBuffer.put(position);
		lightPositionBuffer.position(0);

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glLightfv(id, GL10.GL_AMBIENT, lightAmbientBuffer);
		gl.glLightfv(id, GL10.GL_DIFFUSE, lightDiffuseBuffer);
		gl.glLightfv(id, GL10.GL_POSITION, lightPositionBuffer);
		gl.glEnable(id); // Enable Light 0 + id value (up to 7)
	}
	
	public void disable() {
		// Kill this light
		gl.glDisable(id);
	}
	
	public void enable() {
		gl.glEnable(id);
	}
	
	
	public int getID() {
		return id;
	}
	

}
