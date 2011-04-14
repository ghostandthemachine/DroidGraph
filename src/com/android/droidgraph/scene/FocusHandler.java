package com.android.droidgraph.scene;
//package com.android.graphdroid.scene;
//
///**
// * FocusHandler manages focus for scene-graph.
// *
// */
//
//
//import java.awt.AWTEvent;
//import java.awt.Component;
//import java.awt.Container;
//import java.awt.FocusTraversalPolicy;
//import java.awt.event.ActionListener;
//import java.awt.event.FocusEvent;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Set;
//import java.util.logging.Logger;
//
//import javax.swing.LayoutFocusTraversalPolicy;
//import javax.swing.Timer;
//
//
//
//class FocusHandler {
//    private static final Logger logger = 
//        Logger.getLogger(FocusHandler.class.getName());
////    private static final SGFocusTraversalPolicy focusTraversalPolicy =
////        new SGFocusTraversalPolicy();
//    private static final Set<SGNode> focusRequestPostponed = 
//        new HashSet<SGNode>();
////    static FocusTraversalPolicy getFocusTraversalPolicy() {
////        return focusTraversalPolicy;
////    }
//    static void requestFocus(SGNode toFocus) {
//        if (toFocus == null) {
//            throw new IllegalArgumentException("toFocus " 
//                + " should not be null");
//        }
//        Component toFocusComponent = null;
//        SGView view = toFocus.getPanel();
//        if (view != null) {
//            if (view.getFocusOwner() == toFocus) {
//                //no need to request focus for the focused component
//                return;
//            } else {
//                toFocusComponent = createFocusOwnerContainer(toFocus);
//            }
//        }
//        if (toFocusComponent != null) {
//            toFocusComponent.requestFocusInWindow();
//        } else {
//            focusRequestPostponed.add(toFocus);
//        }
//    }
//    private static SGNode getLastLeaf(SGNode top) {
//        SGNode node = top;
//        if (top instanceof SGParent) {
//            SGParent group = (SGParent) top;
//            List<SGNode> children = group.getChildren();
//            if (! children.isEmpty()) {
//                int childIndex = children.size() - 1;
//                node = getLastLeaf(children.get(childIndex));
//            }
//        }
//        return node;
//    }
//    
//    private static boolean checkPostponedFocusRequest(final SGNode node) {
//        boolean rv = false;
//        if (focusRequestPostponed.contains(node)) {
//            focusRequestPostponed.remove(node);
//            // we postpone focus request because we want it to run after default
//            // focus is set. Hopefully 1000/3 millis is enough time for that.
//            Timer timer = new Timer(1000 / 3, new ActionListener() {
//                public void actionPerformed(java.awt.event.ActionEvent e) {
//                    requestFocus(node);
//                }
//            });
//            timer.setRepeats(false);
//            timer.start();
//            rv = true;
//        } else if (node instanceof SGParent) {
//            for (SGNode child : ((SGParent) node).getChildren()) {
//                rv = checkPostponedFocusRequest(child);
//                if (rv) {
//                    break;
//                }
//            }
//        }
//        return rv;
//    }
//    static void addNotify(SGNode node) {
//        if (! focusRequestPostponed.isEmpty() && node.getPanel() != null) {
//            checkPostponedFocusRequest(node);
//        }
//    }
//    //moves focus in case focused node is removed
//    static void removeNotify(SGNode node) {
////        SGView panel = node.getPanel();
////        if (panel == null) {
////            return;
////        } 
////        FocusOwnerContainer focusOwner = null;
////        for (int i = 0; i < panel.getComponentCount(); i++) {
////            Component child = panel.getComponent(i);
////            if (child instanceof FocusOwnerContainer) {
////                //there should be no more than one FocusOwnerContainer
////                focusOwner = (FocusOwnerContainer) child;
////                break;
////            }
////        }
////        if (focusOwner == null) {
////            return;
////        }
////        SGNode focusOwnerNode = focusOwner.peer;
////        //check if focusOwnerNode in 'node' subtree
////        while (focusOwnerNode != null) {
////            if (focusOwnerNode == node) {
////                //move focus to the next node
////                Component component = 
////                    getFocusTraversalPolicy().getComponentAfter(panel, focusOwner);
////                if (component != null) {
////                    component.requestFocusInWindow();
////                } else {
////                    panel.setFocusOwner(null);
////                }
////                break;
////            }
////            focusOwnerNode = focusOwnerNode.getParent();
////        }
//    }
//   
//    private static FocusOwnerContainer createFocusOwnerContainer(SGNode node) {
////        if (logger.isEnabled(Level.MESSAGE)) {
////            logger.message("createFocusOwnerContainer for " + node);
////        }
//        SGView panel = node.getPanel();
//        if (panel == null) {
//            return null;
//        }
//        
////        return FocusOwnerContainer.getFocusOwnerContainer(panel, node);
//        return null;
//    }
//    
//    //cleans all the containers except for the node
//    static void purgeAllExcept(SGNode node) {
//        SGView panel = node.getPanel();
//        if (panel == null) {
//            return;
//        }
////        for (int i = 0; i < panel.getComponentCount(); i++) {
////            Component child = panel.getComponent(i);
////            if (child instanceof FocusOwnerContainer
////                    && ((FocusOwnerContainer) child).peer != node) {
////                if (logger.isEnabled(Level.MESSAGE)) {
////                    logger.message("cleaning " + child);
////                }
////                panel.remove(child);
////            }
////        }
//    }
//    private static class SGTreeIterator implements Iterator<SGNode> {
//        private final boolean isForward;
//        private SGNode current;
//        private boolean gotNext = false;
//        private SGNode next;
//        SGTreeIterator(SGNode node, boolean isForward) {
//            this.isForward = isForward;
//            current = node;
//        }
//        public boolean hasNext() {
//            if (! gotNext) {
//                doNext();
//            }
//            return next != null;
//        }
//        public SGNode next() {
//            if (! hasNext()) {
//                throw new NoSuchElementException();
//            } else {
//                current = next;
//                gotNext = false;
//            }
//            return current;
//        }
//        public void remove() {
//            if (! gotNext) {
//                doNext();
//            }
//            SGParent parent = current.getParent();
//            if (parent != null) {
//                parent.remove(current);
//            }
//        }
//        private void doNext() {
//            SGNode node = null;
//            if (isForward) {
//                if (current instanceof SGParent) {
//                    List<SGNode> children = ((SGParent) current).getChildren();
//                    if (children.size() > 0) {
//                        node = children.get(0);
//                    }
//                }
//            } 
//            if (node == null) {
//                node = getNextNode(current.getParent(), current);
//            }
//            next = node;
//            gotNext = true;
//        }
//        private SGNode getNextNode(SGNode current, SGNode child) {
//            if (current == null) {
//                return null;
//            }
//            SGNode node = null;
//            if (current instanceof SGParent) {
//                List<SGNode> children = ((SGParent) current).getChildren();
//                int childIndex = -1;
//                for (int i = 0; i < children.size(); i++) {
//                    if (children.get(i) == child) {
//                        childIndex = i;
//                        break;
//                    }
//                }
//                if (childIndex == -1) {
//                    throw new AssertionError("child " + child 
//                            + " should be in parent " + current);
//                }
//                int nextIndex = (isForward) ? childIndex + 1 
//                        : childIndex - 1;
//                if (nextIndex < 0) {
//                    //moving backward. move up
//                    node = current;
//                } else if (nextIndex >= children.size()) {
//                    node = getNextNode(current.getParent(), current);
//                } else {
//                    node = children.get(nextIndex);
//                    if (! isForward) {
//                        node = getLastLeaf(node);
//                    }
//                }
//            }
//            return node; 
//        }
//    }
//    private static class SGFocusTraversalPolicy extends FocusTraversalPolicy {
//        private static FocusTraversalPolicy 
//            shellFocusTraversalPolicy =
//                new LayoutFocusTraversalPolicy();
//        private static SGNode getNode(Component component) {
//            SGNode rv = null;
//            if (component instanceof FocusOwnerContainer) {
//                rv = ((FocusOwnerContainer) component).peer; 
////            } else {
////                if (! (component instanceof SGComponent.SGShell)) {
////                  component = 
////                      SwingUtilities.getAncestorOfClass(
////                              SGComponent.SGShell.class,
////                              component);
////                }
////                if (component instanceof SGComponent.SGShell) {
////                    rv = ((SGComponent.SGShell) component).getNode();
////                }  
//            }
//            return rv;
//        }
//        
//        private Component getComponent(final Container container, 
//                final Component component, final boolean isAfter) {
//            Component rv = null;
////            if (logger.isEnabled(Level.MESSAGE)) {
////                logger.message("container " + container 
////                    + "\ncomponent " + component
////                    + "\nisAfter " + isAfter);
////            }
//            if (! (component instanceof FocusOwnerContainer)) {
//                //component is embedded into SGLComponent
//                if (isAfter) {
//                    Component toFocusComponent = 
//                        shellFocusTraversalPolicy.getComponentAfter(
//                                container, component);
//                    //check if we need to pass focus to the next node
//                    if (toFocusComponent != 
//                          shellFocusTraversalPolicy.getFirstComponent(
//                                  container)) {
//                        rv = toFocusComponent;
//                    }
//                } else {
//                    Component toFocusComponent = 
//                        shellFocusTraversalPolicy.getComponentBefore(
//                                container, component);
//                    //check if we need to pass focus to the previous node
//                    if (toFocusComponent != 
//                        shellFocusTraversalPolicy.getLastComponent(
//                                container)) {
//                        rv = toFocusComponent;
//                    }
//                } 
//            } 
//            if (rv == null){
//                SGNode focused = getNode(component);
//                SGTreeIterator iterator = new SGTreeIterator(focused, isAfter);
//                SGNode toFocus = null;
//                while (iterator.hasNext()) {
//                    SGNode next = iterator.next();
//                    if (next.isFocusable()) {
//                        toFocus = next;
//                        break;
//                    }
//                }
//                if (toFocus != null) {
//                    rv = getComponent(toFocus, isAfter);
//                }
////                 else {
////                    if (isAfter) {
////                        rv = getFirstComponent(focused.getPanel());
////                    } else {
////                        rv = getLastComponent(focused.getPanel());
////                    }
////                }
//            }
////            if (logger.isEnabled(Level.MESSAGE)) {
////                logger.message("container " + container 
////                    + "\ncomponent " + component
////                    + "\nisAfter " + isAfter
////                    + "\nresult " + rv);
////            }
//            return rv;
//        }
//        @Override
//        public Component getComponentAfter(Container container,
//                Component component) {
//            return getComponent(container, component, true);
//        }
//
//        @Override
//        public Component getComponentBefore(Container container,
//                Component component) {
//            return getComponent(container, component, false);
//        }
//
//        @Override
//        public Component getDefaultComponent(Container container) {
////            if (container instanceof SGComponent.SGShell) {
////                return shellFocusTraversalPolicy.getDefaultComponent(container);
////            } else { 
////                return getFirstComponent(container);
////            }
//        	return null;
//        }
//        private Component getComponent(SGNode node, boolean isFirst) {
////            Component component = null;
////            if (node instanceof SGComponent) {
////                Container focusComponentParent = 
////                    ((SGComponent) node).getComponent().getParent();
////                if (isFirst) {
////                    component = shellFocusTraversalPolicy.getFirstComponent(
////                        focusComponentParent);
////                } else {
////                    component = shellFocusTraversalPolicy.getLastComponent(
////                        focusComponentParent); 
////                }
////            } else {
////                component = createFocusOwnerContainer(node);
////            }
////            return component;
//        	return null;
//        }
//        private Component getComponent(Container container, boolean isFirst) {
////            if (container instanceof JSGPanel) {
////                Component rv = null;
////                SGNode node = ((JSGPanel) container).getSceneGroup();
////                
////                if (!isFirst) {
////                    node = getLastLeaf(node);
////                }
////                SGNode toFocus = (isFirst) ? node : getLastLeaf(node);
////                if ((toFocus != null) && (! toFocus.isFocusable())) {
////                    SGTreeIterator iterator = new SGTreeIterator(toFocus, isFirst);
////                    while (iterator.hasNext()) {
////                        toFocus = iterator.next();
////                        if (toFocus.isFocusable()) {
////                            break;
////                        }
////                    }
////                }
////                if (toFocus != null) {
////                    rv = getComponent(toFocus, isFirst);
////                }
////                return rv;
////            } else {
////                return (isFirst) 
////                  ? shellFocusTraversalPolicy.getFirstComponent(container)
////                  : shellFocusTraversalPolicy.getLastComponent(container);
////                }
//        	return null;
//        }
//        @Override
//        public Component getFirstComponent(Container container) {
//            return getComponent(container, true);
//        }
//
//        @Override
//        public Component getLastComponent(Container container) {
//            return getComponent(container, false);
//        }
//    }
//    
//    private static class FocusOwnerContainer extends Container  {
//        private static final long serialVersionUID = 1L;
//        private final SGNode peer;
//        private static FocusOwnerContainer getFocusOwnerContainer(
//                Container container,
//                SGNode peer) {
//            for (int i = container.getComponentCount() - 1; i >= 0; i--) {
//                Component child = container.getComponent(i);
//                if (child instanceof FocusOwnerContainer) {
//                    if (((FocusOwnerContainer) child).peer == peer) {
//                       return (FocusOwnerContainer) child;
//                    }
//                }
//            }
//            FocusOwnerContainer focusOwner = new FocusOwnerContainer(peer);
//            container.add(focusOwner);
//            return focusOwner;
//        }
//        private FocusOwnerContainer(SGNode peer) {
//            this.peer = peer;
//            enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
//        }
//   
//        @Override
//        protected void processFocusEvent(FocusEvent e) {
////            if (logger.isEnabled(Level.MESSAGE)) {
////                String str = "";
////                switch(e.getID()) {
////                case FocusEvent.FOCUS_GAINED:
////                    str = "FOCUS_GAINED";
////                    break;
////                case FocusEvent.FOCUS_LOST:
////                    str = "FOCUS_LOST";
////                    break;
////                }
////                logger.message(str + " on peer " + peer); 
////            }
////            switch (e.getID()) {
////            case FocusEvent.FOCUS_GAINED: {
////                purgeAllExcept(peer);
////                peer.getPanel().setFocusOwner(peer);
////                break;
////            }
////            case FocusEvent.FOCUS_LOST: {
////                JSGPanel jsgpanel = peer.getPanel();
////                if (jsgpanel != null) {
////                    jsgpanel.setFocusOwner(null);
////                }
////                break;
////            }
////            }
////            FocusEvent event = new FocusEvent(e.getComponent(), 
////                    e.getID(), e.isTemporary(), e.getOppositeComponent());
////            peer.processFocusEvent(event);
//        }
//        @Override
//        public String toString() {
//            String className = getClass().getName();
//            int i = className.lastIndexOf('$');
//            return className.substring(i + 1) + "[" + peer + "]";
//        }
//    }
//}
