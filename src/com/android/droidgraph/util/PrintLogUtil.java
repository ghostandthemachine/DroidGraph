package com.android.droidgraph.util;

import android.util.Log;

public class PrintLogUtil {

	private String pl(String label, Object ... args) {
		
		StringBuilder rs = new StringBuilder();
		
		for(Object object : args) {
			
			rs.append(object);
			rs.append(", ");
		
		}
		
		//Print to android logcat
		Log.d(label, rs.toString());
		
		// Return as a string so it can be used as an expandable .toString() compiler
		return rs.toString();
	}
	
	public String pl(Object label, Object ... args) {
		return pl(label.toString(), args);
	}
	
}
