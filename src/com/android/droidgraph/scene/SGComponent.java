package com.android.droidgraph.scene;
//package com.android.opengl.sgl.scene;
//
//import java.awt.AWTEvent;
//import java.awt.BorderLayout;
//import java.awt.Component;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.event.FocusEvent;
//import java.awt.geom.AffineTransform;
//import java.awt.geom.Rectangle2D;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.swing.JComponent;
//import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//
//
///**
// * A scene graph node that renders a Swing component.
// * 
// * @author Chet Haase
// * @author Hans Muller
// * @author Igor Kushnirskiy
// */
//public class SGComponent extends SGLeaf {
//   
//    private static final boolean havePrinterGraphics; 
//    static {
//        boolean tmpBoolean = false;
//        try {
//            Class.forName("java.awt.print.PrinterGraphics");
//            tmpBoolean = true;
//        } catch (ClassNotFoundException ignore) {
//        }
//        havePrinterGraphics = tmpBoolean;
//    }
//    private Component component;
//    private final SGShell shell;
//    private JPanel container = null;
//    private Dimension size = null;
//   
//    public SGComponent() {
//        shell = new SGShell();
//    }
//    
//    public final Component getComponent() { 
//        return component;
//    }
//
//    JPanel getContainer() {
//        return container;
//    }
//    
//    private void checkContainer() {
//        if (container == null) {
//            container = new JPanel(null) {
//                private static final long serialVersionUID = 1L;
//
//                @Override
//                public boolean contains(int x, int y) {
//                    if (getComponentCount() > 0) {
//                        return getComponent(0).contains(x, y);
//                    } else {
//                        return super.contains(x,y);
//                    }
//                }
//            };
//            container.setBounds(0, 0, 0, 0);
//        }
//        if (container.getParent() == null 
//                || container.getParent() != getPanel()) {
//            JComponent panel = getPanel();
//            if (panel != null) {
//                panel.add(container);
//            }
//        } 
//    }
//    
//    public void setComponent(Component component) {
//        this.component = component;
//        shell.removeAll();
//        if (component != null) {
//            shell.add(component, BorderLayout.CENTER);
//            FocusHandler.addNotify(this);
//        }
//        shell.setContains(false);
//        shell.validate();
//        repaint(true);
//    }
//    
//    /**
//     * Returns the target size of this {@code SGComponent}, which may be null
//     * if it has not already been set, or if it was explicitly set to null.
//     */
//    public Dimension getSize() {
//        return size;
//    }
//    
//    /**
//     * Sets the target size of this {@code SGComponent}.  If {@code size}
//     * is null, this node will be laid out according to the preferred size
//     * of its {@code Component}.
//     */
//    public void setSize(Dimension size) {
//        this.size = size;
//        shell.validate();
//    }
//    
//    /**
//     * Sets the target size of this {@code SGComponent}.  This is
//     * equivalent to calling:
//     * <pre>
//     *     setSize(new Dimension(width, height));
//     * </pre>
//     */
//    public void setSize(int width, int height) {
//        setSize(new Dimension(width, height));
//    }
//
//    /*
//     * turns doublebuffering off for the subtree when isReset is false
//     * and restores the original states otherwise.
//     */
//    private static void resetDoubleBuffering(Component root, 
//            Map<JComponent, Boolean> oldStateMap, boolean isReset) {
//        if (root instanceof JComponent) {
//            JComponent jComponent = (JComponent) root;
//            if (isReset) {
//                Boolean oldState = oldStateMap.get(jComponent);
//                if (oldState != null) {
//                    jComponent.setDoubleBuffered(oldState);
//                }
//            } else {
//                oldStateMap.put(jComponent, jComponent.isDoubleBuffered());
//                jComponent.setDoubleBuffered(false);
//            }
//        }
//        if (root instanceof Container) {
//            Container container = (Container) root;
//            for (int i = container.getComponentCount() - 1; i >= 0; i--) {
//                resetDoubleBuffering(container.getComponent(i), oldStateMap, isReset);
//            }
//        }
//    }
//    @Override
//    public void paint(Graphics2D g) {
//        if (component != null) {
//            checkContainer();
//            Graphics2D g2d = g;
//            boolean needsDispose = false;
//            if (havePrinterGraphics) {
//                /*
//                 * in case graphics has non trivial transform use
//                 * SGComponentGraphics2D
//                 */
//                AffineTransform transform = g.getTransform();
//                if (transform.getScaleX() != 1
//                        || transform.getScaleY() != 1
//                        || transform.getShearX() != 0
//                        || transform.getShearY() != 0 ) {
//                    //TODO idk: SGComponentGraphics2D can be reused
//                    g2d = SGComponentsGraphicsFactory.createGraphics(g.create());
//                    needsDispose = true;
//                }
//            }
//            Map<JComponent, Boolean> oldState = 
//                new HashMap<JComponent, Boolean>();
//            resetDoubleBuffering(shell, oldState, false);
//            if (DO_PAINT) {
//                shell.paint(g2d);
//            }
//            resetDoubleBuffering(shell, oldState, true);
//            if (needsDispose) {
//                g2d.dispose();
//            }
//        }
//    } 
//    /*
//     * this class is used so we do not try to load SGComponentGraphics class on 
//     * mobile.
//     */
//    private static class SGComponentsGraphicsFactory {
//        static Graphics2D createGraphics(Graphics g) {
//            return new SGComponentGraphics((Graphics2D) g);
//        }
//    }
//
//    @Override
//    public final Rectangle2D getBounds(AffineTransform transform) {
//        if (component == null) {
//            return new Rectangle2D.Float();
//        }
//        checkContainer();
//        if (!shell.isValid()) {
//            shell.validate();
//        }
//        Rectangle2D bounds = new Rectangle(0, 0, shell.getWidth(), shell.getHeight());
//        if (transform != null && !transform.isIdentity()) {
//            bounds = transform.createTransformedShape(bounds).getBounds2D();
//        }
//        return bounds;
//    }
//    
//    
//    @Override
//    boolean isFocusable() {
//        return super.isFocusable() || (isVisible() && getComponent() != null);
//    }
//
//    @Override
//    boolean hasOverlappingContents() {
//        // TODO: Should we check double buffering?
//        return true;
//    }
//
//    class SGShell extends JPanel {
//        
//        private static final long serialVersionUID = 1L;
//
//        SGShell() {
//            super(new BorderLayout());
//            setOpaque(false);
//            setVisible(true);
//            setBorder(null);
//            enableEvents(AWTEvent.FOCUS_EVENT_MASK
//                    | AWTEvent.MOUSE_EVENT_MASK
//                    | AWTEvent.MOUSE_MOTION_EVENT_MASK
//                    | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
//            setFocusCycleRoot(true);
//            setFocusTraversalPolicy(FocusHandler.getFocusTraversalPolicy());
//            checkContainer();
//            SwingGlueLayer.getSwingGlueLayer().registerRepaintManager(this);
//            container.add(this);
//        }
//        SGLeaf getNode() {
//            return SGComponent.this;
//        }
//        private boolean contains = false;
//        @Override
//        public boolean contains(int x, int y) {
//            return contains;
//        }
//        void setContains(boolean contains) {
//            this.contains = contains;
//        }
//        @Override
//        protected void processFocusEvent(FocusEvent e) {
//            if (focusLogger.isEnabled(Level.MESSAGE)) {
//                String str = "";
//                switch(e.getID()) {
//                case FocusEvent.FOCUS_GAINED:
//                    str = "FOCUS_GAINED";
//                    break;
//                case FocusEvent.FOCUS_LOST:
//                    str = "FOCUS_LOST";
//                }
//                focusLogger.message(str + " on peer " + SGComponent.this); 
//            }
//            switch (e.getID()) {
//            case FocusEvent.FOCUS_GAINED: 
//                FocusHandler.purgeAllExcept(SGComponent.this);
//                SGComponent.this.getPanel().setFocusOwner(SGComponent.this);
//                break;
//            case FocusEvent.FOCUS_LOST:
//                SGComponent.this.getPanel().setFocusOwner(null);
//                break;
//            }
//            //TODO idk: FOCUS_LOST do we want to handle it?
//            FocusEvent event = new FocusEvent(e.getComponent(), 
//                    e.getID(), e.isTemporary(), e.getOppositeComponent());
//            SGComponent.this.processFocusEvent(event);
//        }
//        
//        @Override
//        public void repaint(long tm, int x, int y, int width, int height) {
//            SGComponent.this.repaint(
//                new Rectangle2D.Float(x, y, width, height));
//        }
//
//        @Override
//        public boolean isShowing() {
//            return true;
//        }
//        
//        @Override
//        public boolean isVisible() {
//            return true;
//        }
//        
//        
//        /*
//         * note: too bad we can not use concurrent package because of mobile 
//         * platform.
//         */
//        //to be accessed on the EDT only
//        private boolean validateRunnableDone = true;
//        private Runnable validateRunnable = new Runnable() {
//            public void run() {
//                validate();
//                validateRunnableDone = true;
//            }
//        };
//        @Override
//        public void invalidate() {
//            super.invalidate();
//            if (validateRunnableDone) {
//                validateRunnableDone = false;
//                SwingUtilities.invokeLater(validateRunnable);
//            } 
//        }
//        
//        @Override
//        public void validate() {
//            Dimension oldSize = getSize();
//            Dimension newSize;
//            if (component != null) {
//                if (size != null) {
//                    newSize = size;
//                } else {
//                    newSize = component.getPreferredSize();
//                }
//            } else {
//                newSize = new Dimension(0, 0);
//            }
//            if (!newSize.equals(oldSize)) {
//                SGComponent.this.repaint(true);
//                setSize(newSize);
//            }
//            super.validate();
//        }
//    }
//}
