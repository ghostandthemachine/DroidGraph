package com.android.droidgraph.actionfactory;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.scene.SGShape;

public class RGenericGL extends RGeneric {

	protected GL10 gl;

	public RGenericGL(SGShape node) {
		super(node);
	}

	@Override
	public void run() {

	}

	public void run(GL10 gl2) {
		
	}

}
