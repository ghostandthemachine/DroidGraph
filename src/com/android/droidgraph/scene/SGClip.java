package com.android.droidgraph.scene;
///*
// * Copyright 2007 Sun Microsystems, Inc.  All Rights Reserved.
// * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
// *
// * This code is free software; you can redistribute it and/or modify it
// * under the terms of the GNU General Public License version 2 only, as
// * published by the Free Software Foundation.
// *
// * This code is distributed in the hope that it will be useful, but WITHOUT
// * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
// * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
// * version 2 for more details (a copy is included in the LICENSE file that
// * accompanied this code).
// *
// * You should have received a copy of the GNU General Public License version
// * 2 along with this work; if not, write to the Free Software Foundation,
// * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
// *
// * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
// * CA 95054 USA or visit www.sun.com if you need additional information or
// * have any questions.
// */
//
//package com.android.graphdroid.scene;
//
//
//
//import com.android.graphdroid.geom.BoundingBox;
//import com.android.graphdroid.geom.Bounds;
//import com.android.graphdroid.geom.Transform3D;
//import com.android.graphdroid.shape.GLShape;
//import com.android.graphdroid.vecmath.Matrix3f;
//import com.android.graphdroid.vecmath.Point3d;
//import com.android.graphdroid.vecmath.Vector3f;
//
///**
// * @author Chris Campbell
// */
//public class SGClip extends SGFilter {
//
//    // Soft clipping requires AlphaComposite.SrcIn, but that is not
//    // available on AGUI, so fall back on hard clipping when not available
//    private static boolean aaAvailable;
//    static {
//        try {
//            aaAvailable = (AlphaComposite.SrcIn != AlphaComposite.Src);
//        } catch (NoSuchFieldError e) {
//        }
//    }
//    
//    // TODO: override contains() to return true only when transformed
//    // clip shape contains point and child.contains() returns true...
//    
//    private GLShape shape;
//    private boolean antialiased;
//    private Transform3D filterXform;
//    
//    public SGClip() {
//    }
//    
//    public final GLShape getShape() {
//        return shape;
//    }
//    
//    public void setShape(GLShape shape) {
//        this.shape = shape;
//    }
//    
//    @Override
//    public BoundingBox getBounds(Transform3D xform) {
//    	Vector3f trans = new Vector3f(0,0,0);
//    	xform.get(trans);
//        SGNode child = getChild();
//        if (child == null) {
//            // just an empty rectangle
//        	BoundingBox b = new BoundingBox();
//            return b;
//        }
//        Transform3D t = new Transform3D();
//        t.set(new Matrix3f(trans.x,0,0,0,trans.y,0,0,0,trans.z));
//        BoundingBox childXformBounds = (BoundingBox) child.getBounds(t);
//        if (shape == null) {
//            return childXformBounds;
//        }
//        BoundingBox shapeBounds = (BoundingBox) shape.getBounds();
//        if (xform != null) {
//            shapeBounds = new BoundingBox();
//            shapeBounds.combine(new Point3d(trans.x, trans.y, trans.z));
//        }
//        
//        shapeBounds.combine((Bounds) childXformBounds);
//        return shapeBounds;
//    }
//
//    // TODO: sort out the whole calculateAccumBounds()/getBounds() mess...
//    @Override
//    BoundingBox calculateAccumBounds() {
//        return getBounds(getCumulativeTransform());
//    }
//
//    @Override
//    public boolean canSkipRendering() {
//        return (shape == null);
//    }
//  
//    @Override 
//    public boolean contains(Point3d point) {
//        return (shape == null) ? super.contains(point) : shape.contains(point);
//    }
//
//}
