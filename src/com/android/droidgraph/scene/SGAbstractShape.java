package com.android.droidgraph.scene;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.geom.Transform3D;
import com.android.droidgraph.shape.GLShape;
import com.android.droidgraph.util.SGColor;

public abstract class SGAbstractShape extends SGLeaf {

	public enum Mode {
		STROKE, FILL, STROKE_FILL
	};

	Mode mode = Mode.FILL;
	SGColor drawPaint = new SGColor(255, 255, 255, 255);
	SGColor fillPaint = new SGColor(0, 0, 0, 255);
	
	
	public SGAbstractShape() {
	}
	

	public abstract GLShape getShape();

	public final Mode getMode() {
		return mode;
	}

	public final void setMode(Mode mode) {
		if (mode == null) {
			throw new IllegalArgumentException("null mode");
		}
		this.mode = mode;
	}

	public final SGColor getDrawPaint() {
		return drawPaint;
	}

	public void setDrawPaint(SGColor drawPaint) {
		if (drawPaint == null) {
			throw new IllegalArgumentException("null drawPaint");
		}
		this.drawPaint = drawPaint;
	}

	public final SGColor getFillPaint() {
		return fillPaint;
	}

	public void setFillPaint(SGColor fillPaint) {
		if (fillPaint == null) {
			throw new IllegalArgumentException("null fillPaint");
		}
		this.fillPaint = fillPaint;
	}

	@Override
	public void paint(GL10 gl) {
		
	}

	public BoundingBox getBounds(Transform3D transform) {
		return null;
	}
	
}
