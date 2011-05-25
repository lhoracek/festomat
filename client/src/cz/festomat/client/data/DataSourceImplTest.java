package cz.festomat.client.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;
import cz.festomat.client.data.beans.CommentBean;
import cz.festomat.client.data.beans.FestivalBean;
import cz.festomat.client.data.beans.FestivalListBean;

public class DataSourceImplTest implements IDataSource {

	private final Map<String, FestivalBean> festivals;
	private final Map<String, String> all;
	
	private final List<CommentBean> comments;
	
	public DataSourceImplTest() {
		festivals = new HashMap<String, FestivalBean>();
		FestivalBean fb1 = new FestivalBean("ASFHGF1", "Adresa 1", "Ceske Hrady",
				"50:4:50.251", "14:26:32.166", new Date(2011, 5, 25), new Date(2011, 5, 27), "Festak na hrade.");
		FestivalBean fb2 = new FestivalBean("ASFHGF2", "Adresa 2", "MAJ�LES Kojet�n 2011",
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
	
	@Override
	public List<FestivalListBean> getAllFestivalls() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public FestivalBean getFestivalById(String id) {
		return festivals.get(id);
	}

	@Override
	public List<CommentBean> getAllComments(String festivalId) {
		return comments;
	}

	@Override
	public void sendComment(String festivalId, CommentBean comment) {
		// TODO Auto-generated method stub
		Log.i("DATA", "sending comments: " + comment.getText());
	}

}
