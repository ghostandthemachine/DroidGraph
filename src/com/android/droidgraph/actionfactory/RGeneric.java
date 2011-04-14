package com.android.droidgraph.actionfactory;

import com.android.droidgraph.scene.SGShape;

public class RGeneric extends Runner {

	protected SGShape node;

	public RGeneric(SGShape node) {
		this.node = node;
	}

	public SGShape getNode() {
		return node;
	}

	@Override
	public void run() {
		
	}

}
