package cz.festomat.client.tabs;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import cz.festomat.client.R;
import cz.festomat.client.data.beans.FestivalListBean;

/**
 * 
 * Activita by mela obsahovat mapu s jednotlivymi festaky. Mapa bude omezena na festivaly z vyhledavani.
 * 
 * @author Luboš Horáček
 */
public class FestivalMap extends MapActivity {

	private List<FestivalListBean>	festivals;
	private MapView mapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void onResume() {
		Intent myIntent = getParent().getIntent();

		festivals = (List<FestivalListBean>) myIntent.getSerializableExtra("festivalsArrayList");
		
		if (festivals != null) {
			List<Overlay> mapOverlays;
			Drawable drawable;
			FestivalItemizedOverlay itemizedOverlay;

			mapOverlays = mapView.getOverlays();
			drawable = this.getResources().getDrawable(R.drawable.mapmarker);
			itemizedOverlay = new FestivalItemizedOverlay(drawable);

			for (FestivalListBean fest : festivals) {
				int lat = convertToMicrodegrees(fest.getLat());
				int lng = convertToMicrodegrees(fest.getLng());
				GeoPoint point = new GeoPoint(lat, lng);
				OverlayItem overlayitem = new OverlayItem(point,
						fest.getName(), ""+fest.getStart());
				itemizedOverlay.addOverlay(overlayitem);
			}
			mapOverlays.clear();
			mapOverlays.add(itemizedOverlay);

		}
		super.onResume();
	}
	
	protected int convertToMicrodegrees(String degree) {
		return Integer.parseInt(degree.replace(".", ""));
	}

}
