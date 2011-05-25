package cz.festomat.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cz.festomat.client.data.DataSource;
import cz.festomat.client.data.IDataSource;
import cz.festomat.client.data.beans.CommentBean;

public class Chat extends ListActivity {

	private List<CommentBean>	chat	= null;
	private BaseAdapter			c_adapter;

	private CharSequence		autor	= "";
	private CharSequence		text	= "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		showWaitDialog();
		
		setContentView(R.layout.chat);
		Bundle festivalBund = getIntent().getExtras();
		final String festivalId = festivalBund.getString("festivalId");

		final EditText aut = (EditText) findViewById(R.id.editTextA);
		final EditText msg = (EditText) findViewById(R.id.editTextM);
		Button b = (Button) findViewById(R.id.buttonS);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				autor = aut.getText();
				text = msg.getText();
				CommentBean bean = new CommentBean(autor.toString(), new Date(), text.toString());
				IDataSource source = DataSource.getInstance();
				source.sendComment(festivalId, bean);
			}
		});

		Button bE = (Button) findViewById(R.id.buttonR);
		bE.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showWaitDialog();
				chat = refreshChat(festivalId);
				c_adapter = new CommentAdapter(Chat.this, android.R.layout.simple_list_item_1, chat);
				setListAdapter(c_adapter);
				hideWaitDialog();
			}
		});

		chat = refreshChat(festivalId);
		c_adapter = new CommentAdapter(this, android.R.layout.simple_list_item_1, chat);
		setListAdapter(c_adapter);
		hideWaitDialog();

	}

	public List<CommentBean> refreshChat(String idFest) {
		IDataSource source = DataSource.getInstance();

		return source.getAllComments(idFest);
	}

	private class CommentAdapter extends ArrayAdapter<CommentBean> {

		private final List<CommentBean>	items;

		public CommentAdapter(Context context, int textViewResourceId, List<CommentBean> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.c_chat_item_layout, null);

			}
			CommentBean o = items.get(position);

			// TODO sestavit hezke view

			if (o != null) {
				TextView tt1 = (TextView) v.findViewById(R.id.author);
				tt1.setText(o.getAuthor());
				TextView tt2 = (TextView) v.findViewById(R.id.datum);
				tt2.setText(SimpleDateFormat.getInstance().format(o.getTime()));
				TextView tt3 = (TextView) v.findViewById(R.id.text);
				tt3.setText(o.getText());

			}
			return v;
		}
	}

	ProgressDialog	progressDialog;

	private void showWaitDialog() {
		progressDialog = ProgressDialog.show(this, "", "Načítám...", true);
	}

	private void hideWaitDialog() {
		progressDialog.dismiss();
	}
}
