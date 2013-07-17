package com.example.ifinance2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

public class DownloadDataMarketIndiceCSV extends AsyncTask<String, Void, Void> {
	private String                  mAsOfTheDate;
	private ArrayList<MarketIndice> mArrayIndices;
	private String[]                mNamesIndices;
	private LinkedList<String>      mData;
	private ListActivity            mListActivity;

	public DownloadDataMarketIndiceCSV(ListActivity list) {
		this.mListActivity = (ListActivityIndices) list;
		
	}

	@Override
	protected void onPreExecute() {
		mData = new LinkedList<String>();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... params) {
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(params[0]);
		String userAgentExplorer10_6 = "Mozilla/5.0 (compatible; MSIE 10.6; Windows NT 6.1; Trident/5.0; InfoPath.2; SLCC1; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET CLR 2.0.50727) 3gpp-gba UNTRUSTED/1.0";
		request.setHeader("User-Agent", userAgentExplorer10_6);
		
		ConnectivityManager connMgr = (ConnectivityManager) mListActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
		try {
			HttpResponse response = client.execute(request);
			
			HttpEntity entity = response.getEntity();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(), Charset.forName("windows-1255")));

			String line = "";

			while ((line = reader.readLine()) != null) {
			
				mData.add(line);

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Have a problem !!!");
		}
		
		} else {
			Toast.makeText(mListActivity, "An Error has Occurred ",
					Toast.LENGTH_LONG).show();
			// display error
		}

		return null;
	}


	protected void onPostExecute(Void result) {
		String CSV_SEPARATOR = ",";
		LinkedList<String[]> mDataIndices;

		mAsOfTheDate = mData.get(1);
		mData.remove(0);
		mData.remove(0);
		mData.remove(0);
		mData.remove(0);
		mDataIndices = new LinkedList<String[]>();
		mArrayIndices = new ArrayList<MarketIndice>();

		for (String string : mData) {
			String[] stringArray = string.split(CSV_SEPARATOR);
			if (string != "")
				mDataIndices.add(stringArray);
		}

		mNamesIndices = new String[mDataIndices.size()];// Allocate space
														// array string

		String[] arrayString;

		for (int i = 0; i < mDataIndices.size(); i++) {
			MarketIndice tempIndice = new MarketIndice();
			arrayString = mDataIndices.get(i);
			mNamesIndices[i] = arrayString[0];
			tempIndice.setmNameIndice(arrayString[0]);
			tempIndice.setmLastIndex(arrayString[1]);
			tempIndice.setmChangeRate((Double.parseDouble(arrayString[2])));
			tempIndice.setmDateRefresh(mAsOfTheDate);
			mArrayIndices.add(tempIndice);
			
			if(((ListActivityIndices) mListActivity).getmProDialog().isShowing()){
				((ListActivityIndices) mListActivity).getmProDialog().dismiss();
			}
			
	        
			

			// System.out.println(tempIndice.getmNameIndex()+" "+tempIndice.getmLastIndex()
			// +" "+tempIndice.getmDateRefresh()+" "+tempIndice.getmChangeRate()+"%");//test
			// System.out.println(mArrayIndices.get(i).getmNameIndex().toString());//test
		}

		Context c = ApplicationContextProvider.getContext();
		MarketIndiceAdapter indiceAdapter = new MarketIndiceAdapter(c,
				mArrayIndices);
		mListActivity.setListAdapter(indiceAdapter);

		super.onPostExecute(result);
	}

	public String[] getmNamesIndices() {
		return mNamesIndices;
	}

}// end DownloadFilesMarketIndiceCSV

