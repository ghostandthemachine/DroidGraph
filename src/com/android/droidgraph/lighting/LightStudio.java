package com.android.droidgraph.lighting;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.android.droidgraph.util.Settings;

public class LightStudio {
	
	Collection<Light> lights = new HashSet<Light>();
	HashMap<String, Light> names = new HashMap<String, Light>();
	
	private int numLights = 0;
	
	private boolean lightEnabled = true;
	
	private GL10 gl;
	
	private Settings mSettings;
	
	public LightStudio(Settings settings) {
		mSettings = settings;
		gl = mSettings.getGL();
		mSettings.setLightStudio(this);
	}
	
	public void load(GL10 gl) {
		for (Light light : lights) {
			light.onSurfaceCreated(gl);
			Log.d("LightStudio", light.toString());
			}
	}
	
	public void createLight(String name, float[] ambient, float[] diffuse, float[] specular, float[] position){
		Light light = new Light(ambient, diffuse, specular, position, lights.size() - 1);
		lights.add(light);
		names.put(name, light);
		numLights++;
	}
	
	public void createLight(String name, float[] pos) {
		Light light = new Light(pos, lights.size() - 1);
		lights.add(light);
		names.put(name, light);
		numLights++;
	}
	
	public void render(GL10 gl) {
		// Check if the light flag has been set to enable/disable lighting
		if (lightEnabled) {
			gl.glEnable(GL10.GL_LIGHTING);
		} 
	}
	
	public void killRender(GL10 gl) {
		// anything that needs to happen at the end
		if(lightEnabled) {
			gl.glDisable(GL10.GL_LIGHTING);
		}
	}
	
	public Light enableLight(String name) {
		Light light = names.get(name);
		gl.glEnable(light.getID());
		return light;
	}
	
	public Light disableLight(String name) {
		Light light = names.get(name);
		gl.glDisable(light.getID());
		return light;
	}
	
	public void enable() {
		lightEnabled = true;
	}
	
	public void disable() {
		lightEnabled = false;
	}
	
	public Light getLight(String name) {
		return names.get(name);
	}

}
