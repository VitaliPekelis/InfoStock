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
	public GraphicalView getView(Context context,LinkedList<Date> mDates, LinkedList<Double> mValues,String nameIndic) {
		String title = nameIndic;
		
		int colors = Color.RED ;
		PointStyle styles = PointStyle.CIRCLE ;
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		setChartSettings(renderer,   //		renderer the renderer to set the properties to
				nameIndic,   //		title the chart title
				"תאריך",              //		xTitle the title for the X axis
				"מדד",                 //		yTitle the title for the Y axis
				Collections.min(mValues)-10,                                // the minimum value on the Y
				Collections.max(mValues)+10,                              // the maximum value on the Y axis
				Color.GRAY,                                              //axesColor the axes color
				Color.LTGRAY);                                          //labelsColor the labels color
		
		renderer.setShowGrid(true);
		renderer.setGridColor(Color.GREEN);
		renderer.setXRoundedLabels(true);
		renderer.setXLabelsAlign(Align.RIGHT);
	    renderer.setYLabelsAlign(Align.RIGHT);
		
		

		return ChartFactory.getTimeChartView(context,
				buildDateDataset(title, mDates, mValues), renderer, "dd/MM/yy");
	}

}