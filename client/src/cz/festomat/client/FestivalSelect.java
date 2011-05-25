/**
 * 
 */
package cz.festomat.client;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import cz.festomat.client.tabs.Chat;
import cz.festomat.client.tabs.FestivalList;

/**
 * @author Luboš Horáček
 *
 */
public class FestivalSelect extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.festival);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, FestivalList.class);
		spec = tabHost.newTabSpec("list").setIndicator("Seznam", null).setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, Chat.class);
		spec = tabHost.newTabSpec("map").setIndicator("Maps", null).setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}
}
