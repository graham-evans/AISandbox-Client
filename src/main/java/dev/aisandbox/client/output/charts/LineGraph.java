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

public class LineGraph {

    @Getter
    private final TreeMap<Integer, Double> storage = new TreeMap<>();

    @Getter
    @Setter
    private int memorySize = 50;

    @Setter
    private String title = "Title";

    @Setter
    private String XAxisTitle = null;

    @Setter
    private String YAxisTitle = null;

    private int XValue = 0;

    public void addValue(Double v) {
        XValue++;
        storage.put(XValue,v);
        while (storage.size()>memorySize) {
            storage.remove(storage.firstKey());
        }
    }

    public BufferedImage getGraph(int width, int height) {
        XYSeries series1 = new XYSeries("Results");
        storage.forEach((key,value)->series1.add(key,value));
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        // create the chart
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                XAxisTitle, // x axis label
                YAxisTitle, // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
        );
        return chart.createBufferedImage(width, height,null);
    }

}
