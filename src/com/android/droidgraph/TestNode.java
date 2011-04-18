package com.android.droidgraph;

import android.animation.ObjectAnimator;
import android.view.animation.BounceInterpolator;

import com.android.droidgraph.fx.FXShape;
import com.android.droidgraph.material.Material;
import com.android.droidgraph.material.TextureMaterial;
import com.android.droidgraph.primitive.Cube;
import com.android.droidgraph.primitive.Disc;
import com.android.droidgraph.scene.LightStudio;
import com.android.droidgraph.scene.SGGroup;
import com.android.droidgraph.scene.SGTransform;
import com.android.droidgraph.util.Settings;

public class TestNode extends SGGroup {

	SGTransform.Translate translation;
	SGTransform.Rotate r;

	LightStudio studio;

	public TestNode() {

		translation = SGTransform.createTranslation(0, 0, 0, this);
		translation.setTranslation(0, 0, -15f);

		studio = Settings.lightStudio;
		studio.createLight("main", new float[] { 5, 5, 0f, 1 });

		// Light light = studio.getLight("main");

		// ObjectAnimator ani = ObjectAnimator.ofFloat(light, "translateX", -3f,
		// 3f);
		// ani.setDuration(3000).setRepeatCount(ObjectAnimator.INFINITE);
		// ani.setRepeatMode(ObjectAnimator.REVERSE);
		// ani.setInterpolator(new BounceInterpolator());
		// ani.start();

		// log.pl("Settings.context", Settings.conext);
		// OBJParser parser = new OBJParser(Settings.conext);
		//
		// GLShape shape = parser.parseOBJ("test.obj");
		// FXShape os = new FXShape();
		// os.setShape(shape);
		// os.setScale(0.15f, 0.15f, 0.15f);
		// os.setTranslation(2f, -3f, -7f);
		// add(os);
		//

		FXShape shape3 = new FXShape();
		shape3.setShape(new Disc());
		shape3.translateBy(-1.5f, 0, 0);
		shape3.rotateYBy(45f);
		add(shape3);

		ObjectAnimator a4 = ObjectAnimator.ofFloat(shape3, "translateX", 0f,
				-5f);
		a4.setDuration(3000);
		a4.setRepeatCount(ObjectAnimator.INFINITE);
		a4.setRepeatMode(ObjectAnimator.REVERSE);
		a4.setInterpolator(new BounceInterpolator());
		a4.start();

		ObjectAnimator a = ObjectAnimator
				.ofFloat(shape3, "translateY", 0f, -5f);
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

		Material mat = new Material(shape2.getShape());
		mat.setAmbientAndDiffuse(1, 0, 0, 1);
		shape2.addMaterial(mat);
		add(shape2);

		ObjectAnimator a5 = ObjectAnimator.ofFloat(shape2, "translateZ", -8f,
				3f);
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
		shape5.addMaterial(new TextureMaterial(shape5.getShape(),
				R.drawable.crate));
		add(shape5);

		ObjectAnimator z = ObjectAnimator
				.ofFloat(shape5, "translateX", -5f, 5f);
		z.setDuration(3000);
		z.setRepeatCount(ObjectAnimator.INFINITE);
		z.setRepeatMode(ObjectAnimator.REVERSE);
		z.start();

		ObjectAnimator x = ObjectAnimator.ofFloat(shape5, "scaleY", 1, 5f);
		x.setDuration(125);
		x.setRepeatCount(ObjectAnimator.INFINITE);
		x.setRepeatMode(ObjectAnimator.REVERSE);
		x.start();

		ObjectAnimator a2 = ObjectAnimator.ofFloat(shape5, "rotateZ", 1f, 360f);
		a2.setDuration(1080);
		a2.setRepeatCount(ObjectAnimator.INFINITE);
		a2.start();

	}

}
