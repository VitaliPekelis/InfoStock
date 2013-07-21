package com.example.ifinance2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;

/**
 * Sales growth demo chart.
 */
public class Graph extends AbstractDemoChart {
	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "Sales growth";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "The sales growth across several years (time chart)";
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the built intent
	 */
	public GraphicalView getView(Context context,LinkedList<Date> mDates, LinkedList<Double> mValues,String nameIndic,String mPeriod) {
		String title = nameIndic;
		
		int colors = Color.RED ;
		PointStyle styles = PointStyle.CIRCLE ;
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		
		
		setChartSettings(renderer,   //		renderer the renderer to set the properties to
				nameIndic,   //		title the chart title
				"תאריך\\שעה ",              //		xTitle the title for the X axis
				"מדד",                 //		yTitle the title for the Y axis
				Collections.min(mValues)-10,                                // the minimum value on the Y
				Collections.max(mValues)+10,                              // the maximum value on the Y axis
				Color.BLACK,                                              //axesColor the axes color
				Color.GREEN);                                          //labelsColor the labels color
		
		renderer.setShowGrid(true);
		renderer.setGridColor(Color.LTGRAY);
		renderer.setXRoundedLabels(true);
		renderer.setXLabelsAlign(Align.CENTER);
	    renderer.setYLabelsAlign(Align.RIGHT);
	    renderer.setMarginsColor(Color.BLACK);
	    
	    renderer.setShowGridY(true);
	    renderer.setShowGridX(true);
	    
	    
	    renderer.setXLabelsColor(Color.WHITE);
	    renderer.setYLabelsColor(0, Color.WHITE);
	    
	    
	    
        if(mPeriod.equals("intPeriod=0")){
        	return ChartFactory.getTimeChartView(context,
    				buildDateDataset(title, mDates, mValues), renderer, "hh:mm:ss");
         }
		return ChartFactory.getTimeChartView(context,
				buildDateDataset(title, mDates, mValues), renderer, "dd/MM/yy");
	}
}