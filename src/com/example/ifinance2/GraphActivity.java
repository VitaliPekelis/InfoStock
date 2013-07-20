package com.example.ifinance2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.achartengine.GraphicalView;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.agimind.widget.SlideHolder;

public class GraphActivity extends Activity {
	
	//activity  
	
	private ProgressDialog mProDialog;
	private boolean        mIsUpdate=false;
	
	//slideMenu
	
	private SlideHolder mSlideHolder;
	private Spinner     mSpinner;
	
	//graph
	
	private String             mNameIndice;
	private String             mIdIndice = null;
	private String             mIsDollar = "IsDollar=False";
	private String             mFrequency = "intFrequency1=0";
	private String             mPeriod= "intPeriod=1";
	private LinkedList<Date>   mDate_Y;
	private LinkedList<Double> mIndex_X;
	private GraphicalView      gview;
	Graph graph = new Graph();

	/*
	 * toggleView can actually be any view you want. 
	 * 
	 * Note, when menu opens our textView will become invisible, so it quite
	 * pointless to assign toggle-event to it. In real app consider using UP
	 * button instead. In our case toggle() can be replaced with open().
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);

		toGetIntentFromChoiceActivity();
		initSliderMenu();
		startDownloadDataToGraph();
		
	}

	private void startDownloadDataToGraph() {
		new DownloadGrafIndices(this).execute();
		mProDialog = new ProgressDialog(GraphActivity.this);
		mProDialog.setMessage("Loading... Please wait");
		mProDialog.show();
		
	}

	private void toGetIntentFromChoiceActivity() {
		Intent i = getIntent();
		mIdIndice = i.getExtras().getString("idIndice");
		mNameIndice=i.getExtras().getString("nameIndice");
	}

	

	private void initSliderMenu() {
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
		mSlideHolder.setDirection(SlideHolder.DIRECTION_RIGHT);
		mSpinner = (Spinner) findViewById(R.id.sp_period);
		List<String> list = new ArrayList<String>();
		list.add("יום מסחר אחרון");
		list.add("חודש אחרון");
		list.add("3 חודשים");
		list.add("6 חודשים");
		list.add("שנה");
		list.add("3 שנים");
		list.add("5 שנים");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpinner.setAdapter(dataAdapter);
		mSpinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		
	}

	private void biuldgraph() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
		gview = graph.getView(this,mDate_Y,mIndex_X,mNameIndice);
		
		if(mIsUpdate){
		layout.removeAllViews();
		}
		layout.addView(gview);
		
	}

	public void onToggleClicked(View view) {
		// Is the toggle on?
		boolean on = ((ToggleButton) view).isChecked();
		if (on) {
			mIsDollar = "IsDollar=True";
		} else {
			mIsDollar = "IsDollar=False";
		}

	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.rb_daily:
			if (checked)
				mFrequency = "intFrequency1=0";
			break;
		case R.id.rb_weekly:
			if (checked)
				mFrequency = "intFrequency1=1";
			break;
		case R.id.rb_monthly:
			if (checked)
				mFrequency = "intFrequency1=2";
			break;
		case R.id.rb_annual:
			if (checked)
				mFrequency = "intFrequency1=3";
			break;
		}
	}
	
	public void updateGraph(){
		mIsUpdate=true;
		startDownloadDataToGraph();
	}

	// /************************************************************************************************

	public class CustomOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			RadioButton rb = (RadioButton) findViewById(R.id.rb_annual);
			RadioButton rb1 = (RadioButton) findViewById(R.id.rb_monthly);
			RadioButton rb2 = (RadioButton) findViewById(R.id.rb_weekly);

			switch ((int) id) {
			case 1:
				mPeriod = "intPeriod=1";// חודש אחרון
				rb2.setEnabled(true);
				rb1.setEnabled(false);
				rb.setEnabled(false);
				break;
			case 2:
				mPeriod = "intPeriod=2";// 3 חודשים
				rb2.setEnabled(true);
				rb1.setEnabled(false);
				rb.setEnabled(false);
				break;
			case 3:
				mPeriod = "intPeriod=3";// 6 חודשים
				rb2.setEnabled(true);
				rb1.setEnabled(true);
				rb.setEnabled(false);
				break;
			case 4:
				mPeriod = "intPeriod=4";// שנה
				rb2.setEnabled(true);
				rb1.setEnabled(true);
				rb.setEnabled(false);
				break;
			case 5:
				mPeriod = "intPeriod=6";// 3 שנים
				rb2.setEnabled(true);
				rb1.setEnabled(true);
				rb.setEnabled(true);
				break;
			case 6:
				mPeriod = "intPeriod=7";// 5 שנים
				rb2.setEnabled(true);
				rb1.setEnabled(true);
				rb.setEnabled(true);
				break;

//			default:
//				mPeriod = "intPeriod=0";// יום מסחר אחרון
//				rb2.setEnabled(false);
//				rb1.setEnabled(false);
//				rb.setEnabled(false);
//				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	}// end of CustomOnItemSelectedListener

	// /************************************************************************************************
	public class DownloadGrafIndices extends AsyncTask<Void, Void, Void> {
		private Context mContext;
		private LinkedList<String> mData;

		public DownloadGrafIndices(Context context) {
			super();
			this.mContext = context;
		}

		@Override
		protected Void doInBackground(Void... params) {

			HttpClient client = new DefaultHttpClient();
			// "http://www.tase.co.il/Heb/MarketData/Indices/MarketCap/Pages/IndexGraph.aspx?Action=2&addTab=&IndexId=137&intPeriod=2&intFrequency1=0&strFromDate=17@04@2013&strToDate=17@07@2013&IsYield=False&IsDollar=False";
			String uri = "http://www.tase.co.il/Heb/MarketData/Indices/MarketCap/Pages/IndexGraph.aspx?"
					+ mIdIndice
					+ "&"
					+ mPeriod
					+ "&"
					+ mFrequency
					+ "&strFromDate=01@04@2013&strToDate=17@07@2013&IsYield=False&"
					+ mIsDollar + "";

			HttpGet request = new HttpGet(uri);
			String userAgentExplorer10_6 = "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0";
			request.setHeader("User-Agent", userAgentExplorer10_6);

			ConnectivityManager connMgr = (ConnectivityManager) mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				try {
					HttpResponse response = client.execute(request);
					Header[] mCookies = response.getHeaders("Set-Cookie");
					HttpEntity entity = response.getEntity();

					String idIndesForUri = mIdIndice.substring(mIdIndice
							.indexOf("IndexId=") + 8);
					uri = "http://www.tase.co.il/_layouts/Tase/ManagementPages/Export.aspx?sn="
							+ idIndesForUri
							+ "dsGrafh&Columns="
							+ idIndesForUri
							+ "AddColColumnsGrafhExcel&Titles="
							+ idIndesForUri
							+ "AddColTitlesGrafhExcel&ExportType=3";
					// "http://www.tase.co.il/_layouts/Tase/ManagementPages/Export.aspx?sn=137dsGrafh&Columns=137AddColColumnsGrafhExcel&Titles=137AddColTitlesGrafhExcel&ExportType=3";
					URI u = null;
					try {
						u = new URI(uri);
					} catch (URISyntaxException e) {
						System.out.println("rong syntax uri Vitali !!!");
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
						mData = new LinkedList<String>();
					while ((line = reader.readLine()) != null) {
						mData.add(line);

					}

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				Toast.makeText(mContext, "An Error has Occurred ",
						Toast.LENGTH_LONG).show();
				// display error
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			String CSV_SEPARATOR = ",";

			mDate_Y = new LinkedList<Date>();
			mIndex_X = new LinkedList<Double>();
			mData.removeFirst();

			for (String string : mData) {
				if (string != "") {
					String[] stringArray = string.split(CSV_SEPARATOR);
					mDate_Y.add(convertToDate(stringArray[0]));
					mIndex_X.add(Double.parseDouble(stringArray[1]));
				}
			}

			if (mProDialog.isShowing()) {
				mProDialog.dismiss();
			}
			
			super.onPostExecute(result);
			biuldgraph();
			
			
			
		}

		private Date convertToDate(String str) {

			SimpleDateFormat format;
			Date date = null;
			try {
				if (mPeriod.equals("intPeriod=0")) {
					format = new SimpleDateFormat("HH:mm:ss '-' dd/MM/yy",Locale.getDefault());
				} else {
					format = new SimpleDateFormat("dd/MM/yy",Locale.getDefault());
				}
				date = format.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		}

	}// end of class DownloadGrafIndices

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_settings1)
			mSlideHolder.toggle();
		if(item.getItemId()== R.id.action_updateGraph)
			updateGraph();
		return true;
	}

}
