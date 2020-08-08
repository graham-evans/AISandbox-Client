package dev.aisandbox.client.output.charts;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * Graph for PMF's.
 *
 * <p>As well as drawing the graph, the class will also provide mean average and standard deviation
 * values.
 */
@Slf4j
public class FrequencyMassDistributionGraph extends BaseAWTGraph implements OutputGraph {

  @Getter Frequency frequencyTable = new Frequency();
  SummaryStatistics statistics = new SummaryStatistics();
  Integer minX;
  Integer maxY;

  public FrequencyMassDistributionGraph() {}

  /**
   * Add an integer value.
   *
   * @param value the number (score) to record.
   */
  public void addValue(int value) {
    frequencyTable.addValue(value);
    statistics.addValue((double) value);
  }

  /**
   * Get the total of values recorded.
   *
   * @return The number of values added.
   */
  public long getTotal() {
    return frequencyTable.getSumFreq();
  }

  /**
   * Get the (unique) number of values.
   *
   * <p>For example if the numbers [1,2,2,1,3,2] are added, these are three unique values.
   *
   * @return a int.
   */
  public int getUniqueValues() {
    return frequencyTable.getUniqueCount();
  }

  /**
   * Get the standard deviation of the distribution.
   *
   * @return The sd value.
   */
  public double getStandardDeviation() {
    return statistics.getStandardDeviation();
  }

  /**
   * Get the mean average of the values.
   *
   * @return the mean average.
   */
  public double getMean() {
    return statistics.getMean();
  }

  @Override
  public BufferedImage getImage() {
    // work out max and min
    lowestY = 0.0;
    highestY = 0.0;
    lowestX = statistics.getMin();
    highestX = statistics.getMax();
    // calc highest Y
    Iterator i = frequencyTable.valuesIterator();
    while (i.hasNext()) {
      long x = (long) i.next();
      double y = frequencyTable.getPct(x);
      if (y > highestY) {
        highestY = y;
      }
    }
    // reset the graph (this redraws the background and scales the axis)
    super.resetGraph();
    // draw the graph
    graphics2D.setColor(Color.RED);
    // cycle through the values
    i = frequencyTable.valuesIterator();
    while (i.hasNext()) {
      long x = (long) i.next();
      double fr = frequencyTable.getPct(x);
      int px = (int) (leftMargin + (x - lowestX) * horizontalScale);
      int py1 = graphHeight - bottomMargin;
      int py2 = (int) (graphHeight - bottomMargin - (fr - lowestY) * verticalScale);
      graphics2D.drawLine(px, py1, px, py2);
    }
    return image;
  }
}
