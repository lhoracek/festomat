package cz.festomat.client.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataSourceImplTest implements IDataSource {

	private Map<String, FestivalBean> festivalls;
	private Map<String, String> all;
	
	public DataSourceImplTest() {
		festivalls = new HashMap<String, FestivalBean>();
		FestivalBean fb1 = new FestivalBean("ASFHGF1", "Adresa 1", "Ceske Hrady",
				"50:4:50.251", "14:26:32.166", new Date(2011, 5, 25), new Date(2011, 5, 27), "Festak na hrade.");
		FestivalBean fb2 = new FestivalBean("ASFHGF2", "Adresa 2", "MAJÁLES Kojetín 2011",
				"50:4:50.251", "14:26:32.166", new Date(2011, 6, 25), new Date(2011, 6, 27), "Festak na hrade.");
		FestivalBean fb3 = new FestivalBean("ASFHGF3", "Adresa 3", "FREESTYLE CONTEST 6 - 2011",
				"50:4:50.251", "14:26:32.166", new Date(2011, 7, 25), new Date(2011, 7, 27), "Festak na hrade.");
		
		festivalls.put("ASFHGF1", fb1);
		festivalls.put("ASFHGF2", fb2);
		festivalls.put("ASFHGF3", fb3);
		
		all = new HashMap<String, String>();
		FestivalBean bean;
		for (String key : festivalls.keySet()) {
			bean = festivalls.get(key);
			all.put(bean.getId(), bean.getName());
		}
	}
	
	public Map<String, String> getAllFestivalls() {
		return all;
	}

	public FestivalBean getFestivalById(String id) {
		return festivalls.get(id);
	}

}
