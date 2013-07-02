package com.example.ifinance2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;


import com.agimind.widget.SlideHolder;

public class GraphActivity extends Activity {
	private SlideHolder mSlideHolder;

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
		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);
		mSlideHolder.setDirection(SlideHolder.DIRECTION_RIGHT);
		
	}

	public void swipeMenu(View v) {
		mSlideHolder.open();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}

}
