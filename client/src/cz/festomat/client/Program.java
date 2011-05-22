package cz.festomat.client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

class ProgramFetcher{
	private String url;
	//program in html:
    private String program;    
    
	public String getProgram(String url) {
		String returnHtml = new String();
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				returnHtml = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (IOException e) {
			returnHtml = "<html>" +
					"<body>" +
					"<style type=\"text/css\">"+
					"body {color: white; }"  +		
					"</style>" +
					"<table>" +
					"<tr><td><b>Cas</b></td><td><b>Aktivita</b></td></tr>" +
					"<tr><td>Chyba, nelze nacist data :-/.</td><td>nic</td></tr>" +					 
					"</table>" +
					"</body>" +
					"</html>";
		}
		return returnHtml;
	}

}

public class Program extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.program);
        
        WebView programWebView = (WebView) findViewById(R.id.program);
        //programWebView.getSettings().setJavaScriptEnabled(true);
        
        programWebView.setBackgroundColor(0);
        String summary = new ProgramFetcher().getProgram("http://bl00der.kbx.cz/festomat/festomat.html");
        programWebView.loadData(summary, "text/html", "utf-8");

    }
}