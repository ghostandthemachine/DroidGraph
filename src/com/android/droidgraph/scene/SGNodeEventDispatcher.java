package com.android.droidgraph.scene;
//package com.android.graphdroid.scene;
//
//import java.util.HashSet;
//import java.util.Set;
//
//
//class SGNodeEventDispatcher {
//
//    private static Set<SGNode> pendingNodeEvents = new HashSet<SGNode>();
//
//    static void addNodeEvent(SGNode node) {
//        pendingNodeEvents.add(node);
//    }
//    
//    static boolean hasPendingEvents() {
//        return !pendingNodeEvents.isEmpty();
//    }
//
//    static void dispatchPendingNodeEvents() {
//        if (pendingNodeEvents.isEmpty()) {
//            // nothing to do...
//            return;
//        }
//        
//        // we will iterate over a local reference to the set of pending
//        // node events, but we create a new set here so that any new events
//        // triggered by listener code will be processed separately
//        // (we will process those on the next recursive pass)
//        Set<SGNode> pendingTemp = pendingNodeEvents;
//        pendingNodeEvents = new HashSet<SGNode>();
//        for (SGNode node : pendingTemp) {
//            node.dispatchNodeEvent();
//        }
//        pendingTemp.clear();
//        
//        // now repeat the process in case other bounds changes were
//        // triggered by the last pass
//        dispatchPendingNodeEvents();
//    }
//
//    /**
//     * Private constructor to prevent instantiation.
//     */
//    private SGNodeEventDispatcher() {
//    }
//}
