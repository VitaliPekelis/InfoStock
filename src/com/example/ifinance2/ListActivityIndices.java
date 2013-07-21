package com.example.ifinance2;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListActivityIndices extends ListActivity {
	private ProgressDialog  mProDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		showCusomActionBar();
		String urlMarketIndiceCSVFull = "http://www.tase.co.il/_layouts/Tase/ManagementPages/Export.aspx?sn=none&enumTblType=IndexChanges&Columns=noneColumns&Titles=noneTitles&TblId=0&action=1&GroupId=4&day=1&GridId=13&CurGuid=%7B88D94BDD-ADB5-42FB-BA3D-35EBD9259DE9%7D&ExportType=3";
		new DownloadDataMarketIndiceCSV(ListActivityIndices.this).execute(urlMarketIndiceCSVFull);
		mProDialog=new ProgressDialog(ListActivityIndices.this);
		mProDialog.setMessage("Loading... Please wait");
		mProDialog.show();
	}
	
	private void showCusomActionBar() {
		ActionBar ab=getActionBar();
	    Drawable d = getResources().getDrawable(android.R.drawable.title_bar_tall);
		ab.setCustomView(R.layout.header_view);
		ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM );
		ab.setBackgroundDrawable(d);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {       //  called when an item in the list is selected
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		
		MarketIndice mi = (MarketIndice) this.getListAdapter().getItem(position);  //get MarketIndice object
	    String name =    mi.getmNameIndice();
	    String lastIndex=mi.getmLastIndex();
	    double changeRate=mi.getmChangeRate();
	    String idI =mi.getmId();
	    String date = mi.getmDateRefresh();
	    
	    
	    Intent intent=new Intent(this,ChoiceActivity.class);
	    intent.putExtra("nName", name);
	    intent.putExtra("nLastInd", lastIndex);
	    intent.putExtra("nChangeR", changeRate);
	    intent.putExtra("nIdI", idI);
	    intent.putExtra("date", date);
	    startActivity(intent);
	}
	
	public ProgressDialog getmProDialog() {
		return mProDialog;
	}



	public void setmProDialog(ProgressDialog mProDialog) {
		this.mProDialog = mProDialog;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = getMenuInflater();
		    inflater.inflate(R.menu.list_activity_indices, menu);
		    return true;
	}
	
}


	
