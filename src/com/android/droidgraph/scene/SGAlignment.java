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
//import java.awt.geom.AffineTransform;
//import java.awt.geom.Point2D;
//import java.awt.geom.Rectangle2D;
//
//import javax.swing.SwingConstants;
//
//import com.android.graphdroid.geom.Transform3D;
//import com.android.graphdroid.vecmath.Point3d;
//
///**
// * @author Chris Campbell
// */
//public class SGAlignment extends SGTransform {
//    
//    private int halign = SwingConstants.LEADING;
//    private int valign = SwingConstants.TOP;
//    private double tx;
//    private double ty;
//    private boolean transformValid;
//    
//    public SGAlignment() {
//    }
//
//    public int getHorizontalAlignment() {
//        return halign;
//    }
//    
//    public void setHorizontalAlignment(int halign) {
//        if (halign != SwingConstants.LEADING &&
//            halign != SwingConstants.CENTER &&
//            halign != SwingConstants.TRAILING)
//        {
//            throw new IllegalArgumentException("invalid halign value");            
//        }
//        this.halign = halign;
//        invalidateTransform();
//    }
//    
//    public int getVerticalAlignment() {
//        return valign;
//    }
//    
//    public void setVerticalAlignment(int valign) {
//        if (valign != SwingConstants.TOP &&
//            valign != SwingConstants.CENTER &&
//            valign != SwingConstants.BOTTOM)
//        {
//            throw new IllegalArgumentException("invalid valign value");
//        }
//        this.valign = valign;
//        invalidateTransform();
//    }
//    
//    private void updateTransform() {
//        if (halign == SwingConstants.LEADING && valign == SwingConstants.TOP) {
//            tx = ty = 0.0;
//            return;
//        }
//        
//        Rectangle2D bounds = getBounds();
//        if (halign == SwingConstants.TRAILING) {
//            tx = -bounds.getWidth();
//        } else if (halign == SwingConstants.CENTER) {
//            tx = -bounds.getWidth()/2;
//        } else {
//            tx = 0.0;
//        }
//        if (valign == SwingConstants.BOTTOM) {
//            ty = -bounds.getHeight();
//        } else if (valign == SwingConstants.CENTER) {
//            ty = -bounds.getHeight()/2;
//        } else {
//            ty = 0.0;
//        }
//    }
//    
//    private void validateTransform() {
//        if (!transformValid) {
//            transformValid = true;
//            updateTransform();
//        }
//    }
//    
//    @Override
//    protected void invalidateTransform() {
//        super.invalidateTransform();
//        transformValid = false;
//    }
//    
//    @Override
//    void invalidateLocalBounds() {
//        super.invalidateLocalBounds();
//        transformValid = false;
//    }
//    
//    private static Point2D setPoint(Point2D dst, double x, double y) {
//        if (dst == null) {
//            dst = new Point2D.Float();
//        }
//        dst.setLocation(x, y);
//        return dst;
//    }
//    
//    @Override
//    public Point3d transform(Point3d src, Point3d dst) {
//        validateTransform();
//        return setPoint(dst, src.getX() + tx, src.getY() + ty);
//    }
//
//    @Override
//    public Point3d inverseTransform(Point3d src, Point3d dst) {
//        validateTransform();
//        return setPoint(dst, src.getX() - tx, src.getY() - ty);
//    }
//
//    @Override
//    public void concatenateInto(Transform3D at) {
//        validateTransform();
//        at.translate(tx, ty);
//    }
//
//    @Override
//    public void getTransform(T at) {
//        validateTransform();
//        at.setToTranslation(tx, ty);
//    }
//    
//    @Override
//    public void reset() {
//        setHorizontalAlignment(SwingConstants.LEADING);
//        setVerticalAlignment(SwingConstants.TOP);
//    }
//}
