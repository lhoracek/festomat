package cz.festomat.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cz.festomat.client.data.CommentBean;
import cz.festomat.client.data.DataSource;
import cz.festomat.client.data.IDataSource;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Chat extends ListActivity {
	
	private ArrayList<Fest> chat = null;
	private FestAdapter c_adapter;
	
	private CharSequence autor = "";
	private CharSequence text = "";
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        
        Bundle festivalBund = getIntent().getExtras();
        final String festivalId = festivalBund.getString("festivalId");
        
        final EditText aut = (EditText)findViewById(R.id.editTextA);
        final EditText msg = (EditText)findViewById(R.id.editTextM);
        aut.setHint("autor");
        msg.setHint("text zpravy...");
        Button b = (Button)findViewById(R.id.buttonS);
        b.setOnClickListener(new View.OnClickListener() {                        
                public void onClick(View v) {
                	autor = aut.getText();
                	text = msg.getText();
                	CommentBean bean = new CommentBean(autor.toString(), new Date(), text.toString());
                	IDataSource source = DataSource.getInstance();
                	source.sendComment(festivalId,bean);
                }
        });
        
        Button bE = (Button)findViewById(R.id.buttonR);
        bE.setOnClickListener(new View.OnClickListener() {                        
                public void onClick(View v) {
                	chat = refreshChat(festivalId);
                    c_adapter = new FestAdapter(Chat.this,
            				android.R.layout.simple_list_item_1, chat);
                    setListAdapter(c_adapter);
                }
        });
        
        chat = refreshChat(festivalId);
        c_adapter = new FestAdapter(this,
				android.R.layout.simple_list_item_1, chat);
        setListAdapter(c_adapter);
    }
    
    public ArrayList<Fest> refreshChat(String idFest) {
    	IDataSource source = DataSource.getInstance();
    	ArrayList<Fest> chat = new ArrayList<Fest>();
    	List<CommentBean> raw = new ArrayList<CommentBean>();
    	CommentBean row;
    	raw = source.getAllComments(idFest);
    	for (int i = 0; i < raw.size(); i++) {
    		row = raw.get(i);
    		chat.add(new Fest(i,row.getAuthor()+" "+row.getTime()+" "+row.getText()));
    	}
    	return chat;
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