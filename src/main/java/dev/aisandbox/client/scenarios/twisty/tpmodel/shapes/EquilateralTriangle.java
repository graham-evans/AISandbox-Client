package dev.aisandbox.client.scenarios.twisty.tpmodel.shapes;

import java.awt.Polygon;

public class EquilateralTriangle implements CellShape {

  @Override
  public Polygon getPolygon(int locationX, int locationY, int rotation, int scale) {
    Polygon poly = new Polygon();
    // add points
    for (double ang = 90.0; ang < 360.0; ang += 120.0) {
      double a2 = Math.toRadians(ang + rotation);
      poly.addPoint(
          (int) (locationX + scale * Math.sin(a2)), (int) (locationY + scale * Math.cos(a2)));
    }
    return poly;
  }
}
