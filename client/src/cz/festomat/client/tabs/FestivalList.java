package cz.festomat.client.tabs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cz.festomat.client.Festival;
import cz.festomat.client.R;
import cz.festomat.client.data.DataSource;
import cz.festomat.client.data.IDataSource;
import cz.festomat.client.data.beans.FestivalListBean;

public class FestivalList extends ListActivity {
	public static final String	TAG					= "Festomat";
	private List<FestivalListBean>		festivals			= null;
	private List<FestivalListBean>		filtered			= null;
	// private ArrayList<String> names = null;

	private IDataSource			source;

	private FestAdapter			f_adapter;

	private EditText			search;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		festivals = new ArrayList<FestivalListBean>();
		filtered = new ArrayList<FestivalListBean>();

		f_adapter = new FestAdapter(this, android.R.layout.simple_list_item_1, filtered);
		setListAdapter(f_adapter);

		search = ((EditText) findViewById(R.id.search));

		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				Log.d(TAG, "afterTextChanged");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				Log.d(TAG, "beforeTextChanged");
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.d(TAG, "onTextChanged");
				filterList(s.toString());
			}
		});
		new LoaderTask().execute();

	}

	private void filterList(String text) {

		if (!text.equals("")) {
			filtered.clear();
			int i = 0;

			for (FestivalListBean fb : festivals) {
				if (fb.getName().toLowerCase().contains(text.toLowerCase())) {
					filtered.add(fb);
				}

			}

		} else {
			filtered.clear();
			filtered.addAll(festivals);
		}
		f_adapter = new FestAdapter(FestivalList.this, android.R.layout.simple_list_item_1, filtered);
		setListAdapter(f_adapter);
	}

	public void loadFests() {
		Log.i(TAG, "loadFests");

		source = DataSource.getInstance();
		filtered = new ArrayList<FestivalListBean>(festivals = source.getAllFestivalls());
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(search.getWindowToken(), 0);

		Bundle bundle = new Bundle();
		bundle.putString("festivalId", ((FestivalListBean) getListAdapter().getItem(position)).getId());

		Intent i = new Intent(this, Festival.class);
		i.putExtras(bundle);

		startActivity(i);

		super.onListItemClick(l, v, position, id);

	}

	private class LoaderTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog	progressDialog;

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(FestivalList.this, "", "Načítám...", true);
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				loadFests();
			} catch (Exception e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			f_adapter = new FestAdapter(FestivalList.this, android.R.layout.simple_list_item_1, filtered);
			setListAdapter(f_adapter);
		}
	}

	private class FestAdapter extends ArrayAdapter<FestivalListBean> {

		private final List<FestivalListBean>	items;

		public FestAdapter(Context context, int textViewResourceId, List<FestivalListBean> items) {
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

			FestivalListBean o = items.get(position);

			if (o != null) {
				TextView tt = (TextView) v.findViewById(android.R.id.text1);
				if (tt != null) {
					tt.setText(o.getName());
				}
			}
			return v;
		}
	}

	/** Called when the activity looses focus **/
	@Override
	public void onPause() {
		Intent myIntent = new Intent();
		myIntent.putExtra("festivalsArrayList", (Serializable) filtered);
		this.setIntent(myIntent);
		super.onPause();
	}

}
