package com.android.droidgraph.test;

import com.android.droidgraph.fx.FXGroup;
import com.android.droidgraph.scene.Light;
import com.android.droidgraph.scene.LightStudio;
import com.android.droidgraph.scene.SGTransform;
import com.android.droidgraph.util.Settings;

public class TestNode extends FXGroup {

	SGTransform.Translate translation;
	SGTransform.Rotate r;

	LightStudio studio;
	
	RotatingCube stuff = new RotatingCube();

	public TestNode() {

		setTranslation(0, 0, -5f);

		studio = Settings.lightStudio;
		studio.createLight("main", new float[] { 5, 5, 15f, 1 });
		Light light = studio.getLight("main");
		
		add(stuff);
		
		
//
//		FXShape disc = new FXShape();
//		disc.setShape(new Disc());
//		Material discMat = new Material(disc.getShape());
//		discMat.setAmbientAndDiffuse(1, 0, 0, 0.2f);
//		disc.addMaterial(discMat);
//		disc.setScale(2, 2, 2);
//		add(disc);
//		
//		discMat.disable();
//		
//		FXShape box = new FXShape();
//		box.setShape(new Cube());
//		
//		Material boxMat = new Material(box.getShape());
//		boxMat.setAmbientAndDiffuse(0, 0, 1, 0.2f);
//
//		
//		TextureMaterial texMat = new TextureMaterial(box.getShape(), R.drawable.crate);
//		
//		box.addMaterial(texMat);
//		
//		box.addMaterial(boxMat);
//		
//		add(box);

	}

}
