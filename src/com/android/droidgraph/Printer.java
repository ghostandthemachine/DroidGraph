package com.android.droidgraph;

public class Printer {
	
	
	public static void pl(Object label, Object ... args) {
		System.out.print(label.toString() + " :");
		StringBuilder s = new StringBuilder("");
		for (Object arg : args) {
			s.append(arg.toString());
			s.append(", ");
		}
		System.out.print(s);
		System.out.println();
	}

}
