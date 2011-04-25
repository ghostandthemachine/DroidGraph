package com.android.droidgraph;

import java.util.HashSet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.android.droidgraph.scene.SGAbstractShape;
import com.android.droidgraph.scene.SGView;
import com.android.droidgraph.test.TestNode;
import com.android.droidgraph.util.PrintLogUtil;
import com.android.droidgraph.util.Settings;

public class Run extends Activity {
	SGView view;
	PrintLogUtil log = new PrintLogUtil();
	
	Settings mSettings = new Settings();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		Set the global scene instance for passing around
		Settings.setSceneSettingsInstance(mSettings);
		
//		create a new global HashMap for quick picking lookup 
		Settings.setNodeIDMap(new HashSet<SGAbstractShape>());
		
		view = new SGView(this, mSettings);
		setContentView(view);
		view.setScene(new TestNode(mSettings));
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
