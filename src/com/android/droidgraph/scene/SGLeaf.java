package com.android.droidgraph.scene;

import javax.microedition.khronos.opengles.GL10;

import com.android.droidgraph.geom.BoundingBox;
import com.android.droidgraph.settings.Settings;



/**
 * Base class for nodes that can paint and handle mouse/keyboard input.
 * 
 */

public abstract class SGLeaf extends SGNode {
    private BoundingBox subregionBounds;

    final static boolean DO_PAINT;
    static {
        String pkg = SGLeaf.class.getPackage().getName();
        DO_PAINT = !Settings.getBoolean(pkg + ".skippaint");
    }

    public abstract void paint(GL10 gl);
    


    /**
     * This method must be called whenever a change is made to a node that
     * affects only a subregion of its overall visual state.  Calling this
     * method is similar to calling {@code repaint(false)}, but potentially
     * more efficient in cases where only a small portion of this node is
     * changing at any given time.
     * <p>
     * Usage example:
     * <pre>
     *     public void setIndicatorEnabled(boolean b) {
     *         this.indicatorEnabled = indicatorEnabled;
     *         repaint(indicatorBounds);
     *     }
     * </pre>
     *
     * @param subregionBounds a rectangle representing the subregion (in
     *     the untransformed coordinate space of this leaf node) that
     *     needs to be repainted
     * @throws IllegalArgumentException if {@code subregionBounds} is null
     */
    protected final void repaint(BoundingBox subregionBounds) {
        if (subregionBounds == null) {
            throw new IllegalArgumentException("subregion bounds must be non-null");
        }
        BoundingBox oldBounds = this.subregionBounds;
        BoundingBox newBounds = (BoundingBox) accumulate(oldBounds, subregionBounds, false);
        this.subregionBounds = newBounds;
    }
    
    final BoundingBox getSubregionBounds() {
        return subregionBounds;
    }

}
