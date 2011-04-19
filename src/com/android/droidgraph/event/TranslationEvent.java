package com.android.droidgraph.event;

import com.android.droidgraph.util.GLH;
import com.android.droidgraph.vecmath.Vector3f;

public class TranslationEvent extends GraphNodeEvent {

	float x;
	float y;
	float z;

	public TranslationEvent() {
		this(0, 0, 0);
	}

	public TranslationEvent(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void run() {
		GLH.translate(this.x, this.y, this.z);
	}

	public void setTranslation(float tx, float ty, float tz) {
		this.x = tx;
		this.y = ty;
		this.z = tz;
	}

	public Vector3f getTranslation() {
		return new Vector3f(x, y, z);
	}

}
