package com.android.droidgraph.primitive;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.android.droidgraph.event.GLFocusListener;
import com.android.droidgraph.event.GLTouchListener;

public abstract class SGLNode {

	/** The buffer holding the vertices */
	protected FloatBuffer vertexBuffer;

	/** The initial vertex definition */
	protected float[] vertices;
	protected FloatBuffer colorBuffer;
	/** The initial color definition */
	protected float[] colors;

	private Object parent;
	private Map<String, Object> attributeMap;

	private List<GLTouchListener> touchListeners = null;

	private List<GLFocusListener> focusListeners = Collections.EMPTY_LIST;

	private boolean visible = true;

	private String id;

	// if touch event should not be dispatched to the nodes underneath this one.
	private boolean isTouchBlocker = false;

	private final boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		if (this.visible != visible) {
			if (visible) {
				this.visible = true;
			} else {
				this.visible = false;
			}
		}
	}

	/**
	 * @return {@code true} if touch event should not be dispatched to the nodes
	 *         underneath this one.
	 */
	public final boolean isTouchBlocker() {
		return isTouchBlocker;
	}

	public final void setMouseBlocker(boolean value) {
		if (value != isTouchBlocker) {
			isTouchBlocker = value;
		}
	}

	public final String getID() {
		return id;
	}

	public final void setID(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id + " " + super.toString();
	}

}
