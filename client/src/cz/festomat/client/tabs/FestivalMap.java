package cz.festomat.client.tabs;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.maps.MapActivity;

import cz.festomat.client.data.beans.FestivalListBean;

/**
 * 
 * Activita by mela obsahovat mapu s jednotlivymi festaky. Mapa bude omezena na festivaly z vyhledavani.
 * 
 * @author Luboš Horáček
 */
public class FestivalMap extends MapActivity {

	private List<FestivalListBean>	festivals;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Intent myIntent = new Intent();
		myIntent = this.getIntent();

		festivals = (List<FestivalListBean>) myIntent.getSerializableExtra("festivalsArrayList");
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
