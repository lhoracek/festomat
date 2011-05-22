package cz.festomat.client.data;

import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

public class DataSourceImpl implements IDataSource {

	RestClient rc;
	
	public DataSourceImpl() {
		rc = new RestClient();
	}
	
	public Map<String, String> getAllFestivalls() {
		Map<String, String> map =
			(Map<String, String>)rc.getData("list",
					new TypeToken<Map<String, String>>(){}.getType());
		return map;
	}

	public FestivalBean getFestivalById(String id) {
		FestivalBean fb = (FestivalBean)rc.getData(id,
				new TypeToken<FestivalBean>(){}.getType());
		return fb;
	}

	public List<CommentBean> getAllComments(String festivalId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendComment(String festivalId, CommentBean comment) {
		// TODO Auto-generated method stub
		
	}

	
	
}
