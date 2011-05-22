package cz.festomat.client;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

class ProgramFetcher{
	private String url;
	//program in html:
    private String program;    
    
    public String getProgram(String url){
    	return "<html>" +
    			"<body>" +
    			"<style type=\"text/css\">"+
    			"body {color: white; }"  +		
    			"</style>" +
    			"<table>" +
    			"<tr><td><b>Cas</b></td><td><b>Aktivita</b></td></tr>" +
    			"<tr><td>Rano</td><td>chlast</td></tr>" +
    			"<tr><td>Vecer</td><td>chlast</td></tr>" + 
    			"</table>" +
    			"</body>" +
    			"</html>";
    }
	
}

public class Program extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TextView textview = new TextView(this);
        //textview.setText("This is the Program tab");
        setContentView(R.layout.program);
        
        WebView programWebView = (WebView) findViewById(R.id.program);
        //programWebView.getSettings().setJavaScriptEnabled(true);
        
        programWebView.setBackgroundColor(0);
        String summary = new ProgramFetcher().getProgram("neconeco");
        programWebView.loadData(summary, "text/html", "utf-8");
        
        //programWebView.loadUrl("http://www.google.com");
    }
}