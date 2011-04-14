package com.android.droidgraph.scene;

import java.util.List;

/**
 * Base class for nodes that contain one or more children.
 * 
 */
public abstract class SGParent extends SGNode {

    public abstract List<SGNode> getChildren();
    public abstract void remove(SGNode node);

}