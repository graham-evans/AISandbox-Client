package dev.aisandbox.client.scenarios.twisty.tpmodel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@XStreamAlias("puzzle")
public class Puzzle {

  public static final int WIDTH = 1280;
  public static final int HEIGHT = 1000;

  @Getter private List<Cell> cells = new ArrayList<>();
  @Getter private List<Move> moves = new ArrayList<>();

  @Getter private Map<String, CompiledMove> compiledMoves = new HashMap<>();

  public BufferedImage getStateImage(String state) {
    BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();

    g.setColor(Color.white);
    g.fillRect(0, 0, WIDTH, HEIGHT);
    // draw polygons
    for (int i = 0; i < cells.size(); i++) {
      Cell c = cells.get(i);
      Polygon poly = c.getPolygon();
      g.setColor(c.getColour().getAwtColour());
      g.fillPolygon(poly);
      g.setColor(Color.DARK_GRAY);
      g.drawPolygon(poly);
    }
    return image;
  }

  private void drawCellConnection(Graphics2D graphics2D, Cell start, Cell end) {
    // get start position
    double startX = start.getLocationX();
    double startY = start.getLocationY();
    // work out unit vector from start to end
    double dx = end.getLocationX() - startX;
    double dy = end.getLocationY() - startY;
    double len = Math.sqrt(dx * dx + dy * dy);
    double ux = dx / len;
    double uy = dy / len;
    // create a unit vector 90 degrees off
    double tx = -uy;
    double ty = ux;
    // find the mid point of the line + 4t
    double midx = startX + dx / 2 + 5 * tx;
    double midy = startY + dy / 2 + 5 * ty;
    // straight line
    // graphics2D.drawLine(start.getLocationX(), start.getLocationY(), end.getLocationX(),
    //    end.getLocationY());
    graphics2D.drawLine(start.getLocationX(), start.getLocationY(), (int) midx, (int) midy);
    graphics2D.drawLine((int) midx, (int) midy, end.getLocationX(), end.getLocationY());
    graphics2D.drawLine(
        (int) midx, (int) midy, (int) (midx - 8 * ux + 3 * tx), (int) (midy - 8 * uy + 3 * ty));
    graphics2D.drawLine(
        (int) midx, (int) midy, (int) (midx - 8 * ux - 3 * tx), (int) (midy - 8 * uy - 3 * ty));
  }

  public Cell findCell(double x, double y) {
    Cell result = null;
    for (Cell c : cells) {
      if (c.getPolygon().contains(x, y)) {
        result = c;
      }
    }
    return result;
  }
}
