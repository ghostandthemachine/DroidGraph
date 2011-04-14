package com.android.droidgraph.scene;

import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class LightStudio {
	
	HashMap<String, Light> lights = new HashMap<String, Light>();
	ArrayList<String> names = new ArrayList<String>();
	
	private int numLights = 0;
	
	private boolean light = true;
	
	private GL10 gl;
	
	public LightStudio(GL10 _gl) {
		gl = _gl;
	}
	
	
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		for (String name : names) {
			Light light = lights.get(name);
			light.onSurfaceCreated(gl, config);
			
		}
		
	}
	
	
	public void createLight(String name, float[] ambient, float[] diffuse, float[] position){
		Light light = new Light(this, ambient, diffuse, position, lights.size() - 1);
		lights.put(name, light);
		names.add(name);
		numLights+=1;
	}
	
	public void onDrawFrame(GL10 gl) {
		// Check if the light flag has been set to enable/disable lighting
		if (light) {
			gl.glEnable(GL10.GL_LIGHTING);
		} else {
			gl.glDisable(GL10.GL_LIGHTING);
		}
	}
	
	public GL10 getGL() {
		return gl;
	}
	
	
	public Light enableLight(String name) {
		Light light = lights.get(name);
		gl.glEnable(light.getID());
		return light;
	}
	
	public Light disableLight(String name) {
		Light light = lights.get(name);
		gl.glDisable(light.getID());
		return light;
	}
	
	public void enable() {
		light = true;
	}
	
	public void disable() {
		light = false;
	}
	

}
