package com.android.droidgraph;

import java.util.HashSet;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;

import com.android.droidgraph.scene.SGAbstractShape;
import com.android.droidgraph.scene.SGView;
import com.android.droidgraph.test.TestNode;
import com.android.droidgraph.util.PrintLogUtil;
import com.android.droidgraph.util.Settings;

public class Run extends Activity {
	SGView mView;
	PrintLogUtil log = new PrintLogUtil();
	
	Settings mSettings = new Settings();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
//		create a new global HashMap for quick picking lookup 
		mSettings.setNodeIDMap(new HashSet<SGAbstractShape>());
		
		mView = new SGView(this, mSettings);
		setContentView(mView);
		
//		mSettings.setScreenDimensions(mView.getWidth(), mView.getHeight());	
		mView.setScene(new TestNode(mSettings));
		
		
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mSettings.setScreenDimensions(dm.widthPixels, dm.heightPixels);
        
	}


	@Override
	public void onPause() {
		super.onPause();
		mView.onPause();
	}
	
	@Override 
	public void onResume() {
		super.onResume();
		mView.onResume();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate our menu which can gather user input for switching camera
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.camera_menu, menu);
        return true;
    }
   
}
