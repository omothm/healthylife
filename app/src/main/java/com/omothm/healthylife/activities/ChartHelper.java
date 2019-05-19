package com.omothm.healthylife.activities;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class ChartHelper {

  public static void setupChart(LineChart chart, LineData data, ValueFormatter formatter) {
    // no description text
    chart.getDescription().setEnabled(false);

    chart.setDrawGridBackground(true);

    // enable touch gestures
    chart.setTouchEnabled(true);

    // enable scaling and dragging
    chart.setDragEnabled(true);
    chart.setScaleEnabled(true);

    // if disabled, scaling can be done on x- and y-axis separately
    chart.setPinchZoom(false);

    // set custom chart offsets (automatic offset calculation is hereby disabled)
    chart.setViewPortOffsets(10, 0, 10, 0);

    // add data
    chart.setData(data);

    // Axis options
    XAxis xAxis = chart.getXAxis();
    xAxis.setPosition(XAxisPosition.BOTTOM_INSIDE);
    xAxis.setEnabled(true);
    xAxis.setDrawAxisLine(true);
    xAxis.setDrawGridLines(true);
    xAxis.setDrawLabels(true);
    xAxis.setValueFormatter(formatter);
    YAxis yAxis = chart.getAxisRight();
    yAxis.setPosition(YAxisLabelPosition.INSIDE_CHART);
    yAxis.setEnabled(true);
    yAxis.setDrawAxisLine(true);
    yAxis.setDrawGridLines(true);
    yAxis.setDrawLabels(true);

    // animate calls invalidate()
    chart.animateX(500);
  }
}
