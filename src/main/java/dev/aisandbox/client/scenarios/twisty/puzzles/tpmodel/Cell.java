package dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import dev.aisandbox.client.scenarios.twisty.puzzles.tpmodel.shapes.ShapeEnum;
import java.awt.Polygon;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XStreamAlias("cell")
public class Cell {

  @Getter @Setter ShapeEnum shape;
  @Getter @Setter int scale;
  @Getter @Setter int locationX;
  @Getter @Setter int locationY;
  @Getter @Setter int rotation;
  @Getter @Setter ColourEnum colour;

  public Polygon getPolygon() {
    if (shape == null) {
      return null;
    } else {
      return shape.getShape().getPolygon(locationX, locationY, rotation, scale);
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
        .append("shape", shape)
        .append("scale", scale)
        .append("locationX", locationX)
        .append("locationY", locationY)
        .append("rotation", rotation)
        .toString();
  }
}
