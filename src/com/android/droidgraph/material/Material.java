package com.android.droidgraph.material;

import com.android.droidgraph.shape.GLShape;

public class Material extends AMaterial{
	
	public Material(GLShape shape, float[] position, float[] ambient, float[] diffuse, float[] specular, float shininess) {
		super(shape);
//		First init the buffers
		initBuffers();
		
//		Set them
		this.setAmbient(ambient);
		this.setDiffuse(diffuse);
		this.setSpecular(specular);
		this.setShininess(shininess);
		this.setPosition(position);
		
//		...and update them
		updateBuffers();
		
	}
	
	public Material(GLShape shape) {
		super(shape);
		initBuffers();			// init with default params
	}

}
