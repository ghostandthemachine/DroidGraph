package com.android.droidgraph.scene;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.android.droidgraph.util.GLH;

public class Light {

	/* The initial light values */
	private float[] ambient = { 0.5f, 0.5f, 0.5f, 1.0f };
	private float[] diffuse = { 0.8f, 0.8f, 0.8f, 1.0f };
	private float[] specular = { 0.5f, 0.5f, 0.5f, 1.0f};
	private float[] position = { 0.0f, 0.0f, 0.0f, 1.0f };
	
	public float translateX = 0;
	public float translateY = 0;
	public float translateZ = 0;
	
	private float red = 1;
	private float green = 1;
	private float blue = 1;
	private float alpha = 1;
	
	/* The buffers for our light values */
	private FloatBuffer ambientBuffer;
	private FloatBuffer diffuseBuffer;
	private FloatBuffer specularBuffer;
	private FloatBuffer positionBuffer;
	
	private int id;

	private GL10 gl;

	public Light(float[] ambient, float[] diffuse, float[] specular, float[] position, int id) {

		gl = GLH.gl;

		this.ambient = ambient;
		this.diffuse = diffuse;
		this.specular = specular;
		this.position = position;
		this.id = id;

		// Max id val is 7
		if (this.id > 7) {
			this.id = 7;
		} else if (this.id < 0) {
			this.id = 0;
		}
		this.id += GL10.GL_LIGHT0;

		initBuffers();
	}

	public Light(float[] position, int id) {
		this.position = position;
		this.id = id;
		
		// Max id val is 7
		if (this.id > 7) {
			this.id = 7;
		} else if (this.id < 0) {
			this.id = 0;
		}
		this.id += GL10.GL_LIGHT0;

		initBuffers();
	}
	
	public Light() {
		this(new float[] {0,0,0}, GL10.GL_LIGHT0);
	}

	public Light(int id) {
		this.id = id;
		initBuffers();
	}

	private void initBuffers() {
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(ambient.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		ambientBuffer = byteBuf.asFloatBuffer();
		ambientBuffer.put(ambient);
		ambientBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(diffuse.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		diffuseBuffer = byteBuf.asFloatBuffer();
		diffuseBuffer.put(diffuse);
		diffuseBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(specular.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		specularBuffer = byteBuf.asFloatBuffer();
		specularBuffer.put(specular);
		specularBuffer.position(0);
		

		byteBuf = ByteBuffer.allocateDirect(position.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		positionBuffer = byteBuf.asFloatBuffer();
		positionBuffer.put(position);
		positionBuffer.position(0);
	}

	public void onSurfaceCreated(GL10 gl) {
		gl.glLightfv(id, GL10.GL_AMBIENT, ambientBuffer);
		gl.glLightfv(id, GL10.GL_POSITION, positionBuffer);
		gl.glLightfv(id, GL10.GL_SPECULAR, specularBuffer);
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
	
	public void setTranslation(float x, float y, float z) {
		position[0] = x;
		translateX = x;
		position[1] = y;
		translateY = y;
		position[2] = z;
		translateZ = z;
		updatePositionBuffer();
	}
	
	public void setTranslateX(float x) {
		position[0] = x;
		translateX = x;
		updatePositionBuffer();
		Log.d("TranslateX", Float.toString(x));
	}
	
	public void setTranslateY(float y) {
		position[1] = y;
		translateY = y;
		updatePositionBuffer();
	}
	
	public void setTranslateZ(float z) {
		position[2] = z;
		translateX = z;
		updatePositionBuffer();
	}
	
	public void setRed(float r) {
		setAmbient(r, green, blue, alpha);
		setDiffuse(r, green, blue, alpha);
		red = r;
	}
	
	public float getRed() {
		return red;
	}
	
	public void setGreen(float g) {
		setAmbient(red, g, blue, alpha);
		setDiffuse(red, g, blue, alpha);
		green = g;
	}
	
	public float getGreen() {
		return green;
	}
	
	public void setBlue(float b) {
		setAmbient(red, green, b, alpha);
		setDiffuse(red, green, b, alpha);
		blue = b;
	}
	
	public float getBlue() {
		return blue;
	}
	
	public void setAlpha(float a) {
		setAmbient(red, green, blue, a);
		setDiffuse(red, green, blue, a);
		alpha = a;
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public void setAmbient(float x, float y, float z, float a) {
		ambient[0] = x;
		ambient[1] = y;
		ambient[2] = z;
		ambient[3] = a;
		updateAmbientBuffer();
	}
	
	public void setDiffuse(float r, float g, float b, float a) {
		diffuse[0] = r;
		diffuse[1] = g;
		diffuse[2] = b;
		diffuse[3] = a;
		updateDiffuseBuffer();
	}
	
	public void setSpecular(float r, float g, float b, float a) {
		specular[0] = r;
		specular[1] = g;
		specular[2] = b;
		specular[3] = a;
		updateSpecularBuffer();
	}
	
	private void updateAmbientBuffer() {
		ambientBuffer.clear();
		ambientBuffer.put(ambient);
		ambientBuffer.position(0);
	}
	
	private void updatePositionBuffer() {
		positionBuffer.clear();
		positionBuffer.put(position);
		positionBuffer.position(0);
	}
	
	private void updateDiffuseBuffer() {
		diffuseBuffer.clear();
		diffuseBuffer.put(diffuse);
		diffuseBuffer.position(0);
	}
	
	private void updateSpecularBuffer() {
		specularBuffer.clear();
		specularBuffer.put(specular);
		specularBuffer.position(0);
	}
}
