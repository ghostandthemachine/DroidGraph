package com.android.droidgraph;

import android.animation.ObjectAnimator;
import android.view.animation.BounceInterpolator;

import com.android.droidgraph.fx.FXShape;
import com.android.droidgraph.primitive.Cube;
import com.android.droidgraph.primitive.Disc;
import com.android.droidgraph.scene.SGGroup;
import com.android.droidgraph.scene.SGTransform;

public class TestNode extends SGGroup {

	SGTransform.Translate translation;
	SGTransform.Rotate r;

	public TestNode() {
		
		translation = SGTransform.createTranslation(0, 0, 0, this);
		translation.setTranslation(0, 0, -15f);

		FXShape shape3 = new FXShape();
		shape3.setShape(new Disc());
		shape3.translateBy(-1.5f, 0, 0);
		shape3.rotateYBy(45f);
		add(shape3);
		
		ObjectAnimator a4 = ObjectAnimator.ofFloat(shape3, "translateX", 0f, -5f);
		a4.setDuration(3000);
		a4.setRepeatCount(ObjectAnimator.INFINITE);
		a4.setRepeatMode(ObjectAnimator.REVERSE);
		a4.setInterpolator(new BounceInterpolator());
		a4.start();

		ObjectAnimator a = ObjectAnimator.ofFloat(shape3, "translateY", 0f, -5f);
		a.setDuration(3000);
		a.setRepeatCount(ObjectAnimator.INFINITE);
		a.setRepeatMode(ObjectAnimator.REVERSE);
		a.setInterpolator(new BounceInterpolator());
		a.start();

		FXShape shape2 = new FXShape();
		shape2.setShape(new Disc());
		shape2.setScale(1.5f, 1.5f, 1.5f);
		shape2.translateBy(1.5f, 0.5f, 0);
		shape2.rotateZBy(45f);
		add(shape2);

		ObjectAnimator a2 = ObjectAnimator.ofFloat(shape3, "rotateY", 1f, 360f);
		a2.setDuration(1080);
		a2.setRepeatCount(ObjectAnimator.INFINITE);
		a2.start();
		
		ObjectAnimator a5 = ObjectAnimator.ofFloat(shape2, "translateZ", -8f, 3f);
		a5.setDuration(3000);
		a5.setRepeatCount(ObjectAnimator.INFINITE);
		a5.setRepeatMode(ObjectAnimator.REVERSE);
		a5.setInterpolator(new BounceInterpolator());
		a5.start();
		
		FXShape shape4 = new FXShape();
		shape4.setShape(new Disc());
		shape4.setTranslateY(-2f);
		add(shape4);

		ObjectAnimator a3 = ObjectAnimator.ofFloat(shape4, "rotateZ", 1f, 360f);
		a3.setDuration(1080);
		a3.setRepeatCount(ObjectAnimator.INFINITE);
		a3.start();
		
		FXShape shape5 = new FXShape();
		shape5.setShape(new Cube());
		add(shape5);
		
		
		ObjectAnimator z = ObjectAnimator.ofFloat(shape5, "translateX", -5f, 5f);
		z.setDuration(3000);
		z.setRepeatCount(ObjectAnimator.INFINITE);
		z.setRepeatMode(ObjectAnimator.REVERSE);
		z.start();
		
		ObjectAnimator x = ObjectAnimator.ofFloat(shape5, "scaleY", 1, 5f);
		x.setDuration(125);
		x.setRepeatCount(ObjectAnimator.INFINITE);
		x.setRepeatMode(ObjectAnimator.REVERSE);
		x.start();
		
	}

}
