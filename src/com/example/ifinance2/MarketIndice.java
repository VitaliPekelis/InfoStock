package com.example.ifinance2;

import java.util.Date;

public class MarketIndice {
	private   String  mLastIndex;
	private   String  mNameIndice;
	private   String  mDateRefresh;
	private   double  mChangeRate;
	private   String  mId;
	
	//constructors
	
	public MarketIndice()
	{
		
	}
	public MarketIndice(String mLastIndex, String mNameIndice, String mDateRefresh,double mChangeRate) 
	{
		super();
		this.mLastIndex = mLastIndex;
		this.mNameIndice = mNameIndice;
		this.mDateRefresh = mDateRefresh;
		this.mChangeRate=mChangeRate;
	}
	public String getmLastIndex() {
		return mLastIndex;
	}
	public void setmLastIndex(String mLastIndex) {
		this.mLastIndex = mLastIndex;
	}
	public String getmNameIndice() {
		return mNameIndice;
	}
	public void setmNameIndice(String mNameIndice) {
		this.mNameIndice = mNameIndice;
	}
	public String getmDateRefresh() {
		return mDateRefresh;
	}
	public void setmDateRefresh(String mDateRefresh) {
		this.mDateRefresh = mDateRefresh;
	}
	public double getmChangeRate() {
		return mChangeRate;
	}
	public void setmChangeRate(double mChangeRate) {
		this.mChangeRate = mChangeRate;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	
	//geters seters
	
	
	
}
