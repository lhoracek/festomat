package cz.festomat.client.tabs;

import cz.festomat.client.R;
import cz.festomat.client.data.DataSource;
import cz.festomat.client.data.IDataSource;
import cz.festomat.client.data.beans.FestivalBean;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

class ProgramFetcher{
	private String festivalId;
	//program in html:
    private String descriptionHtml;
    private String title;
    
    private String programHeader = "<html>" +
	"<body>" +
	"<style type=\"text/css\">"+
	"body {color: white; }"  +		
	"</style>";
    private String programFooter = "</body>" +
	"</html>"; 
    
    public ProgramFetcher(String festivalId) {
    	IDataSource ds = DataSource.getInstance();
		FestivalBean festivalBean = ds.getFestivalById(festivalId);
		festivalBean.getName();
		this.descriptionHtml = festivalBean.getDescription();
		this.title = festivalBean.getName();    	    	
	}
    
	public String getDescriptionHtml() {		
		return programHeader + this.descriptionHtml + programFooter;
	}
	
	public String getTitle(){
		return this.title;
	}

}

public class Program extends Activity {
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.program);
        
        Bundle festivalBund = getIntent().getExtras();
         
        String festivalId = festivalBund.getString("festivalId");
        //String festivalId = "ASFHGF1";
        
        
        WebView programWebView = (WebView) findViewById(R.id.program);
        //programWebView.getSettings().setJavaScriptEnabled(true);
        
        programWebView.setBackgroundColor(0);
        ProgramFetcher pf = new ProgramFetcher(festivalId);
        String summary = pf.getDescriptionHtml();
        String title = pf.getTitle();
        this.setTitle(title);
        programWebView.loadData(summary, "text/html", "utf-8");

    }
}