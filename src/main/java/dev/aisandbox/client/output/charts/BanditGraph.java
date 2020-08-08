package dev.aisandbox.client.output.charts;

import dev.aisandbox.client.scenarios.bandit.model.Bandit;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.List;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class BanditGraph extends BaseAWTGraph implements OutputGraph {

  public BanditGraph(int width, int height) {
    graphWidth = width;
    graphHeight = height;
    this.setTitle("Bandits");
    this.setXaxisHeader("Bandit #");
    this.setYaxisHeader("Mean / Std");
  }

  @Setter List<Bandit> bandits;

  @Override
  public BufferedImage getImage() {
    // work out minimum and maximum values
    Pair<Double, Double> bounds = getBanditBounds();
    lowestX = 0.0;
    highestX = bandits.size();
    lowestY = bounds.getLeft();
    highestY = bounds.getRight();
    // reset the graph
    resetGraph();
    // draw the extra lines
    graphics2D.setColor(Color.RED);
    for (int i = 0; i < bandits.size(); i++) {
      Bandit b = bandits.get(i);
      int xStart = (int) (leftMargin + (i - lowestX) * horizontalScale);
      int xEnd = (int) (leftMargin + (i + 1 - lowestX) * horizontalScale);
      int xMid = (xStart + xEnd) / 2;
      int yMean = (int) (graphHeight - bottomMargin - (b.getMean() - lowestY) * verticalScale);
      int yStd = (int) (b.getStd() * verticalScale);
      Polygon diamond = new Polygon();
      diamond.addPoint(xStart, yMean);
      diamond.addPoint(xMid, yMean - yStd);
      diamond.addPoint(xEnd, yMean);
      diamond.addPoint(xMid, yMean + yStd);
      diamond.addPoint(xStart, yMean);
      graphics2D.fillPolygon(diamond);
    }
    // output finished drawing
    return image;
  }

  private Pair<Double, Double> getBanditBounds() {
    double low = 0.0;
    double high = 0.0;
    for (Bandit bandit : bandits) {
      double l = bandit.getMean() - bandit.getStd();
      double h = bandit.getMean() + bandit.getStd();
      low = Math.min(low, l);
      high = Math.max(high, h);
    }
    return new ImmutablePair<>(low, high);
  }
}
