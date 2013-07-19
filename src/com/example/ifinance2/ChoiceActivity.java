package com.example.ifinance2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ChoiceActivity extends Activity {
private String idIndice=null;
private String mNameInd=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choice);
		Intent i=getIntent();
		
		mNameInd = i.getExtras().getString("nName"); 
		String lastIndex = i.getExtras().getString("nLastInd");
		double changeRate = i.getExtras().getDouble("nChangeR");
		String idI = i.getExtras().getString("nIdI");
		String date =i.getExtras().getString("date");
		
		TextView tv=(TextView) findViewById(R.id.tv_nameIndice1);
		tv.setText(mNameInd);
		TextView tv2=(TextView) findViewById(R.id.tv_LastIndexIndice1);
		tv2.setText(lastIndex);
		TextView tv3=(TextView) findViewById(R.id.tv_ChangeIndice1);
		ImageView iv=(ImageView)findViewById(R.id.iv_iconChange1);
		if(changeRate<=0){
			tv3.setText(""+(changeRate));
			tv3.setTextColor(Color.RED);
			iv.setImageResource(R.drawable.arrow_downe);
		}else{
			tv3.setText(""+(changeRate)+" %");
			tv3.setTextColor(Color.GREEN);
			iv.setImageResource(R.drawable.arrow_up);
		}
		
		TextView tv4=(TextView) findViewById(R.id.tv_date1);
		tv4.setText(date);
		
		idIndice=idI;
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choice, menu);
		return true;
	}
	
	public void goToGraph(View v){
		Intent intent=new Intent(this,GraphActivity.class);
		intent.putExtra("idIndice", idIndice);
		intent.putExtra("nameIndice", mNameInd);
		startActivity(intent);
		
	}

}
