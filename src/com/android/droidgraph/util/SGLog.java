package com.android.droidgraph.util;

import android.util.Log;

public class SGLog {
	
	private final static String errorLabel = "SGL Error";
	
	public SGLog() {
		
	}
	
	
	public static void error(String errorMessage) {
		Log.d(errorLabel, errorMessage);
	}

}
