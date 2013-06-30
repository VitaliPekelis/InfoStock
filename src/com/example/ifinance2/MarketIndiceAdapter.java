package com.example.ifinance2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import au.com.bytecode.opencsv.CSVReader;

public class MarketIndiceAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<MarketIndice> mArrayIndices;
	private LinkedHashMap<String, String> mMapIDs;

	public MarketIndiceAdapter(Context context, ArrayList<MarketIndice> list) {
		super();
		this.mContext = context;
		this.mArrayIndices = list;
		//map.put("one", 1);
		mMapIDs=new LinkedHashMap();
		
		mMapIDs.put("אג\"ח כללי","Action=1&addTab=&IndexId=601");
		mMapIDs.put("אג\"ח לא-ממשלתיות","Action=7&addTab=0&IndexId=603");
		mMapIDs.put("אג\"ח להמרה כללי","Action=7&addTab=3&IndexId=004");
		mMapIDs.put("אג\"ח ממשלתיות","Action=1&addTab=&IndexId=602");
		mMapIDs.put("אופציות כללי","Action=7&addTab=4&IndexId=003");
		mMapIDs.put("יתר מניות והמירים","Action=7&addTab=6&IndexId=161");
		mMapIDs.put("מדדיות לא-ממשלתיות","Action=7&addTab=0&IndexId=606");
		mMapIDs.put("מדדיות ממשלתיות","Action=2&addTab=&IndexId=605");
		mMapIDs.put("מט\"חיות לא-ממשלתיות","Action=2&addTab=&IndexId=605");
		mMapIDs.put("מניות והמירים כללי","Action=7&addTab=0&IndexId=001");
		mMapIDs.put("מק\"מ","Action=4&addTab=&IndexId=800");
		mMapIDs.put("שקליות ממשלתיות","Action=3&addTab=&IndexId=690");
		mMapIDs.put("שקליות ריבית משתנה ממשלתיות","Action=7&addTab=6&IndexId=701");
		mMapIDs.put("שקליות ריבית קבועה ממשלתיות","Action=5&addTab=&IndexId=700");
		mMapIDs.put("ת\"א-ביומד","Action=3&IndexId=167&subDataType=0");
		mMapIDs.put("ת\"א-ביטוח","Action=6&addTab=&IndexId=171");
		mMapIDs.put("ת\"א-טכנולוגיה","Action=2&addTab=&IndexId=169");
		mMapIDs.put("ת\"א-מאגר","Action=6&IndexId=168");
		mMapIDs.put("ת\"א-מעלה","Action=2&addTab=&IndexId=150");
		mMapIDs.put("ת\"א-נפט וגז","Action=4&addTab=&IndexId=170");
		mMapIDs.put("ת\"א-פיננסים","Action=7&addTab=0&IndexId=148");
		mMapIDs.put("ת\"א-תקשורת","Action=5&addTab=&IndexId=172");
		mMapIDs.put("ת\"א - 100","Action=2&IndexId=137");
		mMapIDs.put("ת\"א - 25","Action=1&IndexId=142");
		mMapIDs.put("ת\"א - 75","Action=3&IndexId=143");
		mMapIDs.put("ת\"א בלוטק-50","Action=1&IndexId=145");
		mMapIDs.put("ת\"א בנקים","Action=7&addTab=1&IndexId=164");
		mMapIDs.put("ת\"א יתר-50","Action=4&IndexId=147");
		mMapIDs.put("ת\"א יתר-מאגר","Action=5&IndexId=163");
		mMapIDs.put("ת\"א נדל\"ן-15","Action=7&addTab=2&IndexId=149");
		mMapIDs.put("תל-דיב","Action=1&IndexId=166");
		mMapIDs.put("תל בונד-שקלי","Action=7&addTab=0&IndexId=710");
		mMapIDs.put("תל בונד - תשואות","Action=6&addTab=&IndexId=714");
		mMapIDs.put("תל בונד 20","Action=2&addTab=&IndexId=707");
		mMapIDs.put("תל בונד 40","Action=3&addTab=&IndexId=708");
		mMapIDs.put("תל בונד 60","Action=4&addTab=&IndexId=709");
		mMapIDs.put("תל בונד צמודות","Action=1&addTab=&IndexId=711");
		mMapIDs.put("תל בונד צמודות-בנקים","Action=7&addTab=0&IndexId=713");
		mMapIDs.put("תל בונד צמודות-יתר","Action=5&addTab=&IndexId=712");


				
		System.out.println("Vitali "+mMapIDs.keySet().toString());//test
		
		setIdToIndice(list);
		
	}

	private void setIdToIndice(ArrayList<MarketIndice> list) {
		for (MarketIndice marketIndice : list) {
			String key = marketIndice.getmNameIndice();
			
			String id = null;
			if(mMapIDs.containsKey(key)){
				id=mMapIDs.get(key);
			}else id="-1";
			
			marketIndice.setmId(id);
		}
		
	}
//Implement BaseAdapter
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayIndices.size();
	}

	@Override
	public Object getItem(int position) { // return object in "position" on
											// AcivityList
		MarketIndice mi = mArrayIndices.get(position);
		return mi;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) { // build
																			// ListActivity
		View rowVeiw;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowVeiw = inflater.inflate(R.layout.indice_israel_list, parent,
					false);
		} else {
			rowVeiw = convertView;
		}

		TextView tvNameI = (TextView) rowVeiw.findViewById(R.id.tv_nameIndice);
		tvNameI.setText(mArrayIndices.get(position).getmNameIndice());

		TextView tvIndexI = (TextView) rowVeiw
				.findViewById(R.id.tv_LastIndexIndice);
		tvIndexI.setText(mArrayIndices.get(position).getmLastIndex());

		TextView tvChangeI = (TextView) rowVeiw
				.findViewById(R.id.tv_ChangeIndice);
		tvChangeI.setText(mArrayIndices.get(position).getmChangeRate() + " %");

		ImageView ivIconChange = (ImageView) rowVeiw
				.findViewById(R.id.iv_iconChange);
		TextView tvDateI = (TextView) rowVeiw.findViewById(R.id.tv_date);
		tvDateI.setText(mArrayIndices.get(position).getmDateRefresh());

		return rowVeiw;
	}//finish implement BaseAdapter

	public Context getmContext() {
		return mContext;
	}

	public void printData(String[] data) {// for test
		for (String string : data) {
			System.out.println("Vitali " + string);
		}
	}

}// end class MarketIndiceAdapter
