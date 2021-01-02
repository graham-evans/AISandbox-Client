package dev.aisandbox.client.scenarios.twisty.tpmodel.shapes;

import java.awt.Polygon;

/** A Unit square */
public class Square implements CellShape {

  @Override
  public Polygon getPolygon(int locationX, int locationY, int rotation, int scale) {
    Polygon poly = new Polygon();
    // length between center and corner
    double len = Math.sqrt(2.0 * Math.pow(scale, 2.0));
    // add points
    for (double ang = 45.0; ang < 360.0; ang += 90.0) {
      double a2 = Math.toRadians(ang + rotation);
      poly.addPoint((int) (locationX + len * Math.sin(a2)), (int) (locationY + len * Math.cos(a2)));
    }
    return poly;
  }
}
