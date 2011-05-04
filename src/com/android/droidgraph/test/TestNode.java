package com.android.droidgraph.test;

import com.android.droidgraph.fx.FXGroup;
import com.android.droidgraph.fx.FXShape;
import com.android.droidgraph.lighting.LightStudio;
import com.android.droidgraph.material.Material;
import com.android.droidgraph.primitive.Rect;
import com.android.droidgraph.scene.SGTransform;
import com.android.droidgraph.util.Settings;

public class TestNode extends FXGroup {

	SGTransform.Translate translation;
	SGTransform.Rotate r;
	LightStudio studio;
	private String TAG = "TestNode";
	private Settings mSettings;
	
	public TestNode(Settings settings) {
		mSettings = settings;
		
		setTranslation(0, 0, -2f);
		
		FXShape testNode = new FXShape(mSettings);
		testNode.setShape(new Rect(0,0,2,1));
		testNode.setTranslation(-2, 0, 0);
		
		Material mat = new Material(testNode);
		mat.setAmbientAndDiffuse(1, 0, 0, 1);
		
		testNode.addMaterial(mat);
		
		add(testNode);
	}


}
