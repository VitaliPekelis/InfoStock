package com.example.ifinance2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;

public class DownloadGrafIndices extends AsyncTask<Void, Void, Void> {
	private Context mContext;
	
	
	public DownloadGrafIndices(Context context) {
		super();
		this.mContext = context;
		
	}
	
	public void myOnClick(){
		this.execute();
	}

	
	
	@Override
	protected Void doInBackground(Void... params) {

	    HttpClient client = new DefaultHttpClient();
	    String uri= "http://www.tase.co.il/Heb/MarketData/Indices/MarketCap/Pages/IndexGraph.aspx?Action=1&addTab=&IndexId=142&intPeriod=7&intFrequency1=0&strFromDate=25@03@2013&strToDate=25@06@2013&IsYield=False&IsDollar=False";
	   // String uri="http://www.tase.co.il/_layouts/Tase/ManagementPages/Export.aspx?sn=142dsGrafh&Columns=142AddColColumnsGrafhExcel&Titles=142AddColTitlesGrafhExcel&ExportType=3";
	    HttpGet request=new HttpGet(uri);
	    String userAgentExplorer10_6 = "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0";
		request.setHeader("User-Agent", userAgentExplorer10_6);
		
		
	    ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	        
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	    	try {
				HttpResponse response = client.execute(request);
		 		Header[] mCookies=response.getHeaders("Set-Cookie");
				HttpEntity entity = response.getEntity();
				
				
				uri="http://www.tase.co.il/_layouts/Tase/ManagementPages/Export.aspx?sn=142dsGrafh&Columns=142AddColColumnsGrafhExcel&Titles=142AddColTitlesGrafhExcel&ExportType=3";
				URI u = null;
				try {
					u = new URI(uri);
				} catch (URISyntaxException e) {
					System.out.println("rong syntax uri !!!");
					e.printStackTrace();
				}
				
				request.setURI(u);
				
				request.setHeaders(mCookies);
				request.addHeader("User-Agent", userAgentExplorer10_6);
				
				
				HttpResponse respon = client.execute(request);
				HttpEntity entity2 = respon.getEntity();
				
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(entity2.getContent(),
								Charset.forName("windows-1255")));
				String line = "";
				
				
				while ((line = reader.readLine()) != null) {
					
					System.out.println(line);//test
				}
				
				
			
				
			//	System.out.println(mCookies[0].toString());//test
				
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } else {
	        // display error
	    }
		return null;
	}

}
