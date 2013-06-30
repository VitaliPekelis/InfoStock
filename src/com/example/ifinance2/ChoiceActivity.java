package com.example.ifinance2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class ChoiceActivity extends Activity {
private String idIndice=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);
		Intent i=getIntent();
		
		String name = i.getExtras().getString("nName"); 
		String lastIndex = i.getExtras().getString("nLastInd");
		String changeRate = i.getExtras().getString("nChangeR");
		String idI = i.getExtras().getString("nIdI");
		
		TextView tv=(TextView) findViewById(R.id.tv_nameIndice1);
		tv.setText(name);
		TextView tv2=(TextView) findViewById(R.id.tv_LastIndexIndice1);
		tv2.setText(lastIndex);
		TextView tv3=(TextView) findViewById(R.id.tv_ChangeIndice1);
		tv3.setText(changeRate);
		idIndice=idI;
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choice, menu);
		return true;
	}

}
