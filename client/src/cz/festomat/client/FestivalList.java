package cz.festomat.client;

import java.util.ArrayList;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FestivalList extends ListActivity {
	
	private ArrayList<Fest> festivals = null;
	private FestAdapter f_adapter;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        festivals = getFests();
        
        f_adapter = new FestAdapter(this,
				android.R.layout.simple_list_item_1, festivals);
		setListAdapter(f_adapter);
        
    }
    
    
    public ArrayList<Fest> getFests() {
    	
    	ArrayList<Fest> fests = new ArrayList<Fest>();
    	fests.add(new Fest(1,"Rock for People"));
    	return fests;
    	
    }
    
	protected void onListItemClick(ListView l, View v, int position, long id) {

		Bundle bundle = new Bundle();

		bundle.putLong("festivalId", festivals.get(position).getId());

		Intent i = new Intent(this, Festival.class);
		i.putExtras(bundle);

		startActivityForResult(i, 0);

		super.onListItemClick(l, v, position, id);

	}

    
    private class FestAdapter extends ArrayAdapter<Fest> {

		private ArrayList<Fest> items;

		public FestAdapter(Context context, int textViewResourceId,
				ArrayList<Fest> items) {
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
			Fest o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(android.R.id.text1);
				if (tt != null) {
					tt.setText(o.getName());
				}
			}
			return v;
		}
	}

    
}