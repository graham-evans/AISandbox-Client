package dev.aisandbox.client.output.charts;


import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.image.BufferedImage;
import java.util.TreeMap;

/**
 * Utility class for storing and drawing trend graphs.
 */
public class LineGraph {

    @Getter
    private final TreeMap<Integer, Double> storage = new TreeMap<>();

    @Getter
    @Setter
    private int memorySize = 50;

    @Setter
    private String title = "Title";

    @Setter
    private String xAxisTitle = null;

    @Setter
    private String yAxisTitle = null;

    private int xValue = 0;

    public void addValue(Double v) {
        xValue++;
        storage.put(xValue,v);
        while (storage.size()>memorySize) {
            storage.remove(storage.firstKey());
        }
    }

    public BufferedImage getGraph(int width, int height) {
        XYSeries series1 = new XYSeries("Results");
        storage.forEach(series1::add);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        // create the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                xAxisTitle, // x axis label
                yAxisTitle, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
        );
        return chart.createBufferedImage(width, height,null);
    }

}
