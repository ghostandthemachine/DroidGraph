package com.android.droidgraph;

import android.app.Activity;
import android.os.Bundle;

import com.android.droidgraph.scene.SGView;
import com.android.droidgraph.util.PrintLogUtil;

public class Run extends Activity {
	SGView view;
	PrintLogUtil log = new PrintLogUtil();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new SGView(this);
		setContentView(view);
		view.setScene(new TestNode());
	}
}
