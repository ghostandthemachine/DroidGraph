package com.android.droidgraph.material;

import javax.microedition.khronos.opengles.GL10;



public interface IMaterial {
	
	public void setDiffuse(float red, float green, float blue, float alpha);
	public float[] getDiffuse();
	
	public void setAmbient(float red, float green, float blue, float alpha);
	public float[] getAmbient();
	
	public void setSpecular(float red, float green, float blue, float alpha);
	public float[] getSpecular();
	
	public void setShininess(float shininess);
	public float getShininess();
	
	public void setPosition(float x, float y, float z);
	public float[] getPosition();
	
	public void loadMaterial(GL10 gl);
	
	public void draw(GL10 gl);
	public void killDraw(GL10 gl);

	public void enable();
	public void disable();
}
