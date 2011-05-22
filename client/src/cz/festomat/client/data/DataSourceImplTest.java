package cz.festomat.client.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class DataSourceImplTest implements IDataSource {

	private Map<String, FestivalBean> festivals;
	private Map<String, String> all;
	
	private List<CommentBean> comments;
	
	public DataSourceImplTest() {
		festivals = new HashMap<String, FestivalBean>();
		FestivalBean fb1 = new FestivalBean("ASFHGF1", "Adresa 1", "Ceske Hrady",
				"50:4:50.251", "14:26:32.166", new Date(2011, 5, 25), new Date(2011, 5, 27), "Festak na hrade.");
		FestivalBean fb2 = new FestivalBean("ASFHGF2", "Adresa 2", "MAJÁLES Kojetín 2011",
				"50:4:50.251", "14:26:32.166", new Date(2011, 6, 25), new Date(2011, 6, 27), "Festak na hrade.");
		FestivalBean fb3 = new FestivalBean("ASFHGF3", "Adresa 3", "FREESTYLE CONTEST 6 - 2011",
				"50:4:50.251", "14:26:32.166", new Date(2011, 7, 25), new Date(2011, 7, 27), "Festak na hrade.");
		
		festivals.put("ASFHGF1", fb1);
		festivals.put("ASFHGF2", fb2);
		festivals.put("ASFHGF3", fb3);
		
		all = new HashMap<String, String>();
		FestivalBean bean;
		for (String key : festivals.keySet()) {
			bean = festivals.get(key);
			all.put(bean.getId(), bean.getName());
		}
		
		comments = new ArrayList<CommentBean>();
		CommentBean cb1 = new CommentBean("@petr", new Date(), "to je paradni festival co..");
		CommentBean cb2 = new CommentBean("@lubos", new Date(), "vidis tu cernovlasku u podia");
		CommentBean cb3 = new CommentBean("@pepa", new Date(), "prosim!");
		
		comments.add(cb1);
		comments.add(cb2);
		comments.add(cb3);
	}
	
	public Map<String, String> getAllFestivalls() {
		return all;
	}

	public FestivalBean getFestivalById(String id) {
		return festivals.get(id);
	}

	public List<CommentBean> getAllComments() {
		return comments;
	}

	public void sendComment(CommentBean comment) {
		// TODO Auto-generated method stub
		Log.i("DATA", "sending comments: " + comment.getText());
	}

}
