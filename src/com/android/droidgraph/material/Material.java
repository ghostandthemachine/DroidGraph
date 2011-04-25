package com.android.droidgraph.material;

import com.android.droidgraph.scene.SGAbstractShape;

public class Material extends AMaterial{
	
	public Material(SGAbstractShape shape, float[] position, float[] ambient, float[] diffuse, float[] specular, float shininess) {
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
	
	public Material(SGAbstractShape node) {
		super(node);
		initBuffers();			// init with default params
	}


}
