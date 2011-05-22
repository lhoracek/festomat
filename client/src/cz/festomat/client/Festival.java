package cz.festomat.client;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class Festival extends TabActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, Program.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("program").setIndicator("Program",
	                      null)
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	   
	    
	    
	    intent = new Intent().setClass(this, Chat.class);
	    spec = tabHost.newTabSpec("chat").setIndicator("Chat",
	                      null)
	                  .setContent(intent);
	    tabHost.addTab(spec);

	
	    tabHost.setCurrentTab(1);
        
        
        setContentView(R.layout.festival);
      
    }
}
