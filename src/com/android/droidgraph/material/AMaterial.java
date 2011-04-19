package com.android.droidgraph.material;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.shape.GLShape;
import com.android.droidgraph.util.GLH;

public class AMaterial implements IMaterial {

	// Material Properties
	private float[] diffuse = { 0.8f, 0.8f, 0.8f, 1.0f };
	private float[] ambient = { 0.8f, 0.8f, 0.8f, 1.0f };
	private float[] specular = { 0.2f, 0.2f, 0.2f, 0.5f };
	private float shininess = 12f;
	private float[] position = { 0.0f, 0.0f, 0.0f };

	// Material Buffers
	private FloatBuffer ambientBuffer;
	private FloatBuffer diffuseBuffer;
	private FloatBuffer specularBuffer;
	private FloatBuffer positionBuffer;

	protected GLShape parent;
	protected Material defaultMaterial = GLH.getDefaultMaterial();
	
	protected boolean enabled = true;

	public AMaterial(GLShape glshape) {
		parent = glshape;
	}

	public void setShape(GLShape glshape) {
		parent = glshape;
	}

	/*
	 * Ambient
	 */
	@Override
	public void setAmbient(float red, float green, float blue, float alpha) {
		ambient[0] = red;
		ambient[1] = green;
		ambient[2] = blue;
		ambient[3] = alpha;
		updateAmbientBuffer();
		
		defaultMaterial = GLH.defaultMaterial;
	}

	public void setAmbient(float[] ambient) {
		this.ambient = ambient;
	}

	@Override
	public float[] getAmbient() {
		return ambient;
	}

	public void initAmbientBuffer() {
		ByteBuffer byteBuff = ByteBuffer.allocateDirect(ambient.length * 4);
		byteBuff.order(ByteOrder.nativeOrder());
		ambientBuffer = byteBuff.asFloatBuffer();
		ambientBuffer.put(ambient);
		ambientBuffer.position(0);
	}

	public void updateAmbientBuffer() {
		ambientBuffer.position(0);
		ambientBuffer.put(ambient);
		ambientBuffer.position(0);
	}
	
	public FloatBuffer getAmbientBuffer() {
		return ambientBuffer;
	}

	/*
	 * Diffusse
	 */
	@Override
	public void setDiffuse(float red, float green, float blue, float alpha) {
		diffuse[0] = red;
		diffuse[1] = green;
		diffuse[2] = blue;
		diffuse[3] = alpha;
		updateDiffuseBuffer();
	}

	public void setDiffuse(float[] diffuse) {
		this.diffuse = diffuse;
		updateDiffuseBuffer();
	}

	@Override
	public float[] getDiffuse() {
		return diffuse;
	}
	
	public FloatBuffer getDiffuseBuffer() {
		return diffuseBuffer;
	}

	public void initDiffuseBuffer() {
		ByteBuffer byteBuff = ByteBuffer.allocateDirect(diffuse.length * 4);
		byteBuff.order(ByteOrder.nativeOrder());
		diffuseBuffer = byteBuff.asFloatBuffer();
		diffuseBuffer.put(diffuse);
		diffuseBuffer.position(0);
	}

	public void updateDiffuseBuffer() {
		diffuseBuffer.position(0);
		diffuseBuffer.put(diffuse);
		diffuseBuffer.position(0);
	}

	/*
	 * Ambient and Diffuse
	 */
	public void setAmbientAndDiffuse(float red, float green, float blue,
			float alpha) {
		setAmbient(red, green, blue, alpha);
		setDiffuse(red, green, blue, alpha);
	}

	/*
	 * Specular
	 */
	@Override
	public void setSpecular(float red, float green, float blue, float alpha) {
		specular[0] = red;
		specular[1] = green;
		specular[2] = blue;
		specular[3] = alpha;
		updateSpecularBuffer();
	}

	public void setSpecular(float[] specular) {
		this.specular = specular;
		updateSpecularBuffer();
	}

	@Override
	public float[] getSpecular() {
		return specular;
	}
	
	public FloatBuffer getSpecularBuffer() {
		return specularBuffer;
	}

	public void initSpecularBuffer() {
		ByteBuffer byteBuff = ByteBuffer.allocateDirect(specular.length * 4);
		byteBuff.order(ByteOrder.nativeOrder());
		specularBuffer = byteBuff.asFloatBuffer();
		specularBuffer.put(specular);
		specularBuffer.position(0);
	}

	public void updateSpecularBuffer() {
		specularBuffer.position(0);
		specularBuffer.put(specular);
		specularBuffer.position(0);
	}

	/*
	 * Shininess
	 */
	@Override
	public void setShininess(float shininess) {
		this.shininess = shininess;
	}

	@Override
	public float getShininess() {
		return shininess;
	}

	/*
	 * Position
	 */
	@Override
	public void setPosition(float x, float y, float z) {
		position[0] = x;
		position[1] = y;
		position[2] = z;
		updatePositionBuffer();
	}

	public void setPosition(float[] position) {
		this.position = position;
		updatePositionBuffer();
	}

	@Override
	public float[] getPosition() {
		return position;
	}

	public void initPositionBuffer() {
		ByteBuffer byteBuff = ByteBuffer.allocateDirect(position.length * 4);
		byteBuff.order(ByteOrder.nativeOrder());
		positionBuffer = byteBuff.asFloatBuffer();
		positionBuffer.put(position);
		positionBuffer.position(0);
	}

	public void updatePositionBuffer() {
		positionBuffer.position(0);
		positionBuffer.put(position);
		positionBuffer.position(0);
	}

	protected void initBuffers() {
		initAmbientBuffer();
		initDiffuseBuffer();
		initSpecularBuffer();
		initPositionBuffer();
	}

	protected void updateBuffers() {
		updateAmbientBuffer();
		updateDiffuseBuffer();
		updateSpecularBuffer();
		updatePositionBuffer();
	}

	@Override
	public void loadMaterial(GL10 gl) {
	}

	@Override
	public void draw(GL10 gl) {
		if (parent != null) {
			if(enabled) {
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
					ambientBuffer);
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
					diffuseBuffer);
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
					specularBuffer);
			gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS,
					Math.min(shininess, 128));
			}
		}
	}
	
	@Override
	public void killDraw(GL10 gl) {
		if (parent != null) {
			if(enabled) {
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
					defaultMaterial.getAmbientBuffer());
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
					defaultMaterial.getDiffuseBuffer());
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
					defaultMaterial.getSpecularBuffer());
			gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS,
					Math.min(defaultMaterial.getShininess(), 128));
			}
		}
	}


	@Override
	public void enable() {
		enabled = true;
	}

	@Override
	public void disable() {
		enabled = false;
	}

}
