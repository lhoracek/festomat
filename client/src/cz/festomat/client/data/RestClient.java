package cz.festomat.client.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.Gson;

public class RestClient {
	
	public static final String	BASEURL	= "http://festomat.appspot.com/";
	
	private String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
	
	/* This is a test function which will connects to a given
     * rest service and prints it's response to Android Log with
     * labels "Praeda".
     */
    public Object getData(String url, Type cls)
    {
    	Object data = null;
        HttpClient httpclient = new DefaultHttpClient();
 
        // Prepare a request object
		HttpGet httpget = new HttpGet(BASEURL + url);
 
        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            
            if (entity != null) {
            	InputStream instream = entity.getContent();
                String json = convertStreamToString(instream);           
                Gson gson = new Gson();
                data = gson.fromJson(json, cls);
            }
        } catch (Exception e) {
            Log.e("Praeda", e.getMessage());
        }
        return data;
    }
	
}
