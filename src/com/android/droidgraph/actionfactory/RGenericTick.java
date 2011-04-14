package com.android.droidgraph.actionfactory;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.scene.SGShape;
import com.android.droidgraph.util.Settings;

public class RGenericTick extends RGenericGL{

	public RGenericTick(SGShape node) {
		super(node);
	}
	
	public void run(GL10 _gl) {

	}

	@Override 
	public void run() {
		run(Settings.getGL());
	}
}
