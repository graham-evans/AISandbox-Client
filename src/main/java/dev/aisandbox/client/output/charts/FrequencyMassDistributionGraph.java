package dev.aisandbox.client.output.charts;

import java.awt.Color;
import java.util.Iterator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * FrequencyMassDistributionGraph class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class FrequencyMassDistributionGraph extends BaseChart {

  Frequency frequencyTable = new Frequency();
  SummaryStatistics statistics = new SummaryStatistics();
  Integer minX;
  Integer maxY;

  /**
   * addValue.
   *
   * @param value a int.
   */
  public void addValue(int value) {
    frequencyTable.addValue(value);
    statistics.addValue((double) value);
  }

  /**
   * getTotal.
   *
   * @return a long.
   */
  public long getTotal() {
    return frequencyTable.getSumFreq();
  }

  /**
   * getUniqueValues.
   *
   * @return a int.
   */
  public int getUniqueValues() {
    return frequencyTable.getUniqueCount();
  }

  /**
   * getStandardDeviation.
   *
   * @return a double.
   */
  public double getStandardDeviation() {
    return statistics.getStandardDeviation();
  }

  /**
   * getMean.
   *
   * @return a double.
   */
  public double getMean() {
    return statistics.getMean();
  }

  /** {@inheritDoc} */
  @Override
  public void resetGraph() {
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
      if (y > highestY) highestY = y;
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
  }
}
