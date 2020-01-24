package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.sprite.SpriteLoader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MazeRenderer {

  private static final Logger LOG = Logger.getLogger(MazeRenderer.class.getName());

  public static final int SCALE = 25;

  @Autowired SpriteLoader spriteLoader;

  // load sprites the first time they are needed
  List<BufferedImage> sprites = null;

  public BufferedImage renderMaze(Maze maze) {
    LOG.log(Level.INFO, "Rendering maze {0}", maze.getBoardID());
    if (sprites == null) {
      sprites = spriteLoader.loadSprites("/dev/aisandbox/client/scenarios/maze/bridge.png", 25, 25);
    }
    BufferedImage image =
        new BufferedImage(
            maze.getWidth() * SCALE, maze.getHeight() * SCALE, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();
    for (Cell c : maze.getCellList()) {
      // work out which sprite to load
      int icon = 0;
      if (!c.isPath(Direction.NORTH)) {
        icon += 1;
      }
      if (!c.isPath(Direction.EAST)) {
        icon += 2;
      }
      if (!c.isPath(Direction.SOUTH)) {
        icon += 4;
      }
      if (!c.isPath(Direction.WEST)) {
        icon += 8;
      }
      g.drawImage(sprites.get(icon), c.getPositionX() * SCALE, c.getPositionY() * SCALE, null);
    }
    Cell start = maze.getStartCell();
    if (start != null) {
      g.drawImage(
          sprites.get(17), start.getPositionX() * SCALE, start.getPositionY() * SCALE, null);
    }
    Cell finish = maze.getEndCell();
    if (finish != null) {
      g.drawImage(
          sprites.get(18), finish.getPositionX() * SCALE, finish.getPositionY() * SCALE, null);
    }
    return image;
  }
}
