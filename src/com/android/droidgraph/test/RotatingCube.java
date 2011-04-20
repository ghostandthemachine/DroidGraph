package com.android.droidgraph.test;

import android.animation.ObjectAnimator;
import android.view.animation.LinearInterpolator;

import com.android.droidgraph.fx.FXGroup;
import com.android.droidgraph.fx.FXShape;
import com.android.droidgraph.material.Material;
import com.android.droidgraph.primitive.Cube;

public class RotatingCube extends FXGroup{
	
	FXShape cubeOne = new FXShape();
	FXShape cubeTwo = new FXShape();
	
	public RotatingCube() {
		initFXShape(cubeOne);
		initFXShape(cubeTwo);
		createAnimations();
		
//		setRotation(-45,-45,0);
	}
	
	
	
	private void initFXShape(FXShape node) {
		node.setShape(new Cube());
		Material material = new Material(node.getShape());
		material.setAmbientAndDiffuse(1, 0.5f, 0, 1);
		node.addMaterial(material);
		this.add(node);
	}



	private void createAnimations() {
		
		ObjectAnimator groupAnim = ObjectAnimator.ofFloat(this, "rotateZ", 0,360);
		groupAnim.setDuration(5000);
		groupAnim.setInterpolator(new LinearInterpolator());
		groupAnim.setRepeatCount(ObjectAnimator.INFINITE);
		groupAnim.start();
		
		ObjectAnimator one = ObjectAnimator.ofFloat(cubeOne, "scaleX", 1,4);
		one.setDuration(1000);
		one.setRepeatCount(ObjectAnimator.INFINITE);
		one.setRepeatMode(ObjectAnimator.REVERSE);
		one.setInterpolator(new LinearInterpolator());
		one.setStartDelay(0);
		one.start();
		
		ObjectAnimator three = ObjectAnimator.ofFloat(cubeTwo, "scaleY", 4,1);
		three.setDuration(1000);
		three.setRepeatMode(ObjectAnimator.REVERSE);
		three.setRepeatCount(ObjectAnimator.INFINITE);
		three.setInterpolator(new LinearInterpolator());
		three.start();

	}

}
