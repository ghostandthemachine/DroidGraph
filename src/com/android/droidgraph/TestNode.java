package com.android.droidgraph;

import android.animation.ObjectAnimator;

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
		translation.setTranslation(0, 0, -5f);

		studio = Settings.lightStudio;
		studio.createLight("main", new float[] { 5, 5, 15f, 1 });

		FXShape disc = new FXShape();
		disc.setShape(new Disc());
		Material discMat = new Material(disc.getShape());
		discMat.setAmbientAndDiffuse(1, 0, 0, 0.2f);
		disc.addMaterial(discMat);
		disc.setScale(2, 2, 2);
		add(disc);
		
		discMat.disable();
		
		FXShape box = new FXShape();
		box.setShape(new Cube());
		Material boxMat = new Material(box.getShape());
		boxMat.setAmbientAndDiffuse(0, 0, 1, 0.2f);
		box.addMaterial(boxMat);
		add(box);

	}

}
