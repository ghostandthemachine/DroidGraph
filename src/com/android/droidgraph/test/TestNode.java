package com.android.droidgraph.test;

import com.android.droidgraph.fx.FXGroup;
import com.android.droidgraph.fx.FXShape;
import com.android.droidgraph.hardware.CameraTexture;
import com.android.droidgraph.lighting.LightStudio;
import com.android.droidgraph.primitive.Cube;
import com.android.droidgraph.scene.SGTransform;
import com.android.droidgraph.util.Settings;

public class TestNode extends FXGroup {

	SGTransform.Translate translation;
	SGTransform.Rotate r;

	LightStudio studio;
	
	RotatingCube stuff = new RotatingCube();
	
	private CameraTexture camTex;
	private Cube mCube;
	

	private Settings mSettings;
	
	public TestNode(Settings settings) {

		mSettings = settings;
		setTranslation(0, 0, -2f);

		
		FXShape cam = new FXShape();		
		mCube = new Cube();
		cam.setShape(mCube);
		add(cam);


	}


}
