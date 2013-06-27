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
	

	public MarketIndiceAdapter(Context context, ArrayList<MarketIndice> list) {
		super();
		this.mContext = context;
		this.mArrayIndices = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayIndices.size();
	}

	@Override
	public Object getItem(int position) {         //return object in "position" on AcivityList
		MarketIndice mi=mArrayIndices.get(position);
		return mi;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {                //build ListActivity
		View rowVeiw;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowVeiw = inflater.inflate(R.layout.indice_israel_list, parent,false);
		} else {
			rowVeiw = convertView;
		}
		
		TextView tvNameI = (TextView) rowVeiw.findViewById(R.id.tv_nameIndice);
		tvNameI.setText(mArrayIndices.get(position).getmNameIndex());

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
	}

	public Context getmContext() {
		return mContext;
	}

	public void printData(String[] data) {// for test
		for (String string : data) {
			System.out.println("Vitali " + string);
		}
	}

}// end class MarketIndiceAdapter
