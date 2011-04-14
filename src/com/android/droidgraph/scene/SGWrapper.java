package com.android.droidgraph.scene;

import java.util.Collections;
import java.util.List;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.geom.Transform3D;

/**
 * Base class for nodes that maintain an internal graph of nodes.
 * 
 */
public abstract class SGWrapper extends SGParent {

	private List<SGNode> singletonList;

	protected abstract SGNode getRoot();

	protected void initParent() {
		// TODO: this is a hack; we could just make it the responsibility
		// of the subclass to do this, but SGNode.setParent() is package-private
		getRoot().setParent(this);
	}

	public List<SGNode> getChildren() {
		SGNode root = getRoot();
		if (root == null) {
			return Collections.emptyList();
		} else {
			if (singletonList == null || singletonList.get(0) != root) {
				singletonList = Collections.singletonList(root);
			}
			return singletonList;
		}
	}

	@Override
	public BoundingBox getBounds(Transform3D transform) {
		return (BoundingBox) getRoot().getBounds(transform);
	}
}