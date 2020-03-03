package dev.aisandbox.client.scenarios.twisty.model;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Cell {
  List<Position> points = new ArrayList<>();
  char currentColour;
  Character nextColour = null;

  private Polygon polygon = null;

  public Polygon getPolygon() {
    if (polygon != null) {
      return polygon;
    } else {
      polygon = new Polygon();
      for (Position p : points) {
        polygon.addPoint(p.getX(), p.getY());
      }
      return polygon;
    }
  }
}
