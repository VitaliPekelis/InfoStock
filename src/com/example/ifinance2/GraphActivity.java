package com.example.ifinance2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
	private ProgressDialog     mProDialog;
	private SlideHolder        mSlideHolder;
	private String             mPeriod;
	private Spinner            mSpinner;
	private String             mIdIndice = null;
	private String             mIsDollar = null;
	private String             mFrequency = null;
	private LinkedList<String> mData_Y;
	private LinkedList<String> mIndex_X;

	/*
	 * toggleView can actually be any view you want. Here, for simplicity, we're
	 * using TextView, but you can easily replace it with button.
	 * 
	 * Note, when menu opens our textView will become invisible, so it quite
	 * pointless to assign toggle-event to it. In real app consider using UP
	 * button instead. In our case toggle() can be replaced with open().
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);

		toGetIntent();
		setParametersToBuildUri();
		new DownloadGrafIndices(this).execute();
		
		mProDialog=new ProgressDialog(GraphActivity.this);
		mProDialog.setMessage("Loading... Please wait");
		mProDialog.show();
		
		initSlider();
		addItemsOnSpinner();
		biuldgraph();

	}

	private void toGetIntent() {
		Intent i = getIntent();
		mIdIndice = i.getExtras().getString("idIndice");
	}

	private void setParametersToBuildUri() {
		mIsDollar = "IsDollar=False";
		mFrequency = "intFrequency1=0";
		mPeriod = "intPeriod=0";
	}

	private void initSlider() {
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
		mSlideHolder.setDirection(SlideHolder.DIRECTION_RIGHT);
	}

	private void addItemsOnSpinner() {
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
		Graph graph = new Graph();
		GraphicalView gview = graph.getView(this);
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph);
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

			default:
				mPeriod = "intPeriod=0";// יום מסחר אחרון
				rb2.setEnabled(false);
				rb1.setEnabled(false);
				rb.setEnabled(false);
				break;
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

		// public void myOnClick(){
		// this.execute();
		// }

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

			mData_Y = new LinkedList<String>();
			mIndex_X = new LinkedList<String>();
			mData.removeFirst();

			for (String string : mData) {
				if (string != "") {
					String[] stringArray = string.split(CSV_SEPARATOR);
					mData_Y.add(stringArray[0]);
					mIndex_X.add(stringArray[1]);
				}
			}

			System.out.println("mData_Y "+mData_Y);// test
			System.out.println("mIndex_X "+mIndex_X);// test
			
			if(mProDialog.isShowing()){
				mProDialog.dismiss();
			}
			super.onPostExecute(result);
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
		return true;
	}

}
