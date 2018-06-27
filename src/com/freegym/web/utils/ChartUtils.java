package com.freegym.web.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.AbstractIntervalXYDataset;

public class ChartUtils {

	public static void create(File file, String title, String xTitle, String yTitle, AbstractIntervalXYDataset colls, HttpSession session) throws IOException {
		ChartUtils.create(file, title, xTitle, yTitle, colls, 800, 600, session);
	}

	public static void create(File file, String title, String xTitle, String yTitle, AbstractIntervalXYDataset colls, int width, int height, HttpSession session)
			throws IOException {
		JFreeChart chart = ChartFactory.createXYLineChart(title, xTitle, yTitle, colls, PlotOrientation.VERTICAL, true, true, true);
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));
		chart.getTitle().setPaint(Color.BLACK);
		chart.setBackgroundPaint(new Color(255, 255, 255, 0));
		chart.setAntiAlias(false);
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		XYPlot plot = chart.getXYPlot();
		((XYLineAndShapeRenderer) plot.getRenderer()).setBaseShapesVisible(true);
		plot.getRenderer().setBaseItemLabelsVisible(true);
		NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();// X轴
		domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 14));
		domainAxis.setTickLabelPaint(Color.BLACK);
		domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 14));
		domainAxis.setLabelPaint(Color.BLACK);
		domainAxis.setNumberFormatOverride(new DecimalFormat("#"));
		domainAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		ValueAxis rangeAxis = plot.getRangeAxis();// Y轴
		rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 14));
		rangeAxis.setLabelPaint(Color.BLACK);
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 14));
		ChartUtilities.saveChartAsPNG(file, chart, width, height);
		// .saveChartAsPNG(chart, width, height, session);

	}

}
