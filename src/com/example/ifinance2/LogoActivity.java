package com.example.ifinance2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class LogoActivity extends Activity {

	protected boolean active = true;
	protected int splashTime = 1000;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo);
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (active && (waited < splashTime)) {
						sleep(100);
						if (active) {
							waited += 100;
						}
					}
				} catch (InterruptedException e) {
					// do nothing
				} 
					finally {
					Intent intent = new Intent(LogoActivity.this, FirstActivity.class);
					startActivity(intent);
				finish();
					
				}
			}

		};
		splashTread.start();
	}

	@Override
	/*public void onStop() {

		Intent intent = new Intent(MainActivity.this, MainMenu.class);
		//MainActivity.this.startActivity(intent); 
		startActivity(intent);
finish();
		//MainActivity.this.finish();

	}*/

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	

}
