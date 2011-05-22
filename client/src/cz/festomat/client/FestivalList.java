package cz.festomat.client;

import cz.festomat.client.data.DataSourceImpl;
import cz.festomat.client.data.DataSourceImplTest;
import cz.festomat.client.data.FestivalBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FestivalList extends ListActivity {
	public static final String TAG = "Festomat";
	private ArrayList<String> festivals = null;
	private ArrayList<String> filtered = null;

	private Map<String,String> festNames = null;
	private Map<String,String> filteredFestNames = null;
	//private ArrayList<String> names = null;

	private DataSourceImplTest ds;
	
	private FestAdapter f_adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		festivals =  new ArrayList<String>();
		filtered = new ArrayList<String>();
		
		f_adapter = new FestAdapter(this, android.R.layout.simple_list_item_1,
				filtered);
		setListAdapter(f_adapter);

		((EditText) findViewById(R.id.search))
				.addTextChangedListener(new TextWatcher() {

					public void afterTextChanged(Editable s) {
						Log.d(TAG, "afterTextChanged");
					}

					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
						Log.d(TAG, "beforeTextChanged");
					}

					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						Log.d(TAG, "onTextChanged");
						filterList(s.toString());
					}
				});
		new LoaderTask().execute();

	}

	private void filterList(String text) {
		
		if (!text.equals("")) {
			filtered.clear();
			filteredFestNames = new HashMap<String,String>();
			int i = 0;
			String currentFest = null;
			
			ArrayList<String> keys = new ArrayList<String>(festNames.keySet());
			
			
			for(i=0;i<keys.size();i++) {
//				currentFest = festivals.get(i); 
				currentFest = festNames.get(keys.get(i)); 

				if (currentFest.toLowerCase().contains(text.toLowerCase())) {
					filtered.add(currentFest);
					filteredFestNames.put(keys.get(i),currentFest);
				}
				
			
			}
			
		}
		else {
//			filtered = new ArrayList<String>(festivals);
			filtered = new ArrayList<String>(new ArrayList<String>(festNames.values()));
			filteredFestNames = ds.getAllFestivalls();
		}
		f_adapter = new FestAdapter(FestivalList.this,
				android.R.layout.simple_list_item_1, filtered);
		setListAdapter(f_adapter);
	}
	
	public void loadFests() {
		Log.i(TAG,"loadFests");
		ds = new DataSourceImplTest();
		
		
		festNames = 
			ds.getAllFestivalls();
		filteredFestNames = 
			ds.getAllFestivalls();
	
		
		
		festivals = new ArrayList<String>(festNames.values());
		
		
		filtered = new ArrayList<String>(festivals);
		
		
	}
	
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Bundle bundle = new Bundle();

		ArrayList<String> keys = new ArrayList<String>(festNames.keySet());
		
		String fid = (String) keys.get(position);
		
		Log.i(TAG,"posilam:"+position+" "+fid);
		
		bundle.putString("festivalId",  fid);

		Intent i = new Intent(this, Festival.class);
		i.putExtras(bundle);

		startActivityForResult(i, 0);

		super.onListItemClick(l, v, position, id);

	}

	private class LoaderTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progressDialog;

		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(FestivalList.this, "",
					"Loading. Please wait...", true);
		}

		protected Void doInBackground(Void... arg0) {
			try {

				loadFests();

			} catch (Exception e) {

			}

			return null;
		}

		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			f_adapter = new FestAdapter(FestivalList.this,
					android.R.layout.simple_list_item_1, filtered);
			setListAdapter(f_adapter);

		}

	}

	private class FestAdapter extends ArrayAdapter<String> {

		private ArrayList<String> items;

		public FestAdapter(Context context, int textViewResourceId,
				ArrayList<String> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(android.R.layout.simple_list_item_1, null);

			}
			String o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(android.R.id.text1);
				if (tt != null) {
					tt.setText(o);
				}
			}
			return v;
		}
	}

}