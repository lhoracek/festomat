package cz.festomat.client.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DataSourceImpl implements IDataSource {

	RestClient rc;
	
	public DataSourceImpl() {
		rc = new RestClient();
	}
	
	@Override
	public Map<String, String> getAllFestivalls() {
		Map<String, String> map =
			(Map<String, String>)rc.getData("list",
					new TypeToken<Map<String, String>>(){}.getType());
		return map;
	}

	@Override
	public FestivalBean getFestivalById(String id) {
		FestivalBean fb = (FestivalBean)rc.getData(id,
				new TypeToken<FestivalBean>(){}.getType());
		return fb;
	}

	@Override
	public List<CommentBean> getAllComments(String festivalId) {
		List<CommentBean> list =
			(List<CommentBean>)rc.getData(festivalId + "/comments",
					new TypeToken<List<CommentBean>>(){}.getType());
		if(list == null){
			list = Collections.EMPTY_LIST;
		}
		return list;
	}

	@Override
	public void sendComment(String festivalId, CommentBean comment) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(RestClient.BASEURL + festivalId + "/comments/new");
		
		try {
			Gson gson = new Gson();
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("comment", gson.toJson(comment)));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
	}

	
	
}
