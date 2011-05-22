package cz.festomat.client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import cz.festomat.client.data.DataSource;
import cz.festomat.client.data.FestivalBean;
import cz.festomat.client.data.IDataSource;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

class ProgramFetcher{
	private String url;
	//program in html:
    private String program;
    
    private String programHeader = "<html>" +
	"<body>" +
	"<style type=\"text/css\">"+
	"body {color: white; }"  +		
	"</style>";
    private String programFooter = "</body>" +
	"</html>"; 
    
	public String getProgram(String festivalId) {
		String returnHtml = new String();
		
		IDataSource ds = DataSource.getInstance();
		FestivalBean festivalBean = ds.getFestivalById(festivalId);
		returnHtml = festivalBean.getDescription();
		
		/**
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				returnHtml = EntityUtils.toString(httpResponse.getEntity());
			} else {
				returnHtml = 
					"<table>" +
					"<tr><td>Chyba, nelze nacist data :-/.</td></tr>" +					 
					"</table>";
			}
		} catch (IOException e) {
			returnHtml = 
					"<table>" +
					"<tr><td><b>Cas</b></td><td><b>Aktivita</b></td></tr>" +
					"<tr><td>Chyba, nelze nacist data :-/.</td><td>nic</td></tr>" +					 
					"</table>";
					
		}
		*/
		return programHeader + returnHtml + programFooter;
	}

}

public class Program extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.program);
        
        //String festivalId = getIntent().getExtras().getString("festivalId");
        String festivalId = "ASFHGF1";
        
        
        WebView programWebView = (WebView) findViewById(R.id.program);
        //programWebView.getSettings().setJavaScriptEnabled(true);
        
        programWebView.setBackgroundColor(0);
        String summary = new ProgramFetcher().getProgram(festivalId);
        programWebView.loadData(summary, "text/html", "utf-8");

    }
}