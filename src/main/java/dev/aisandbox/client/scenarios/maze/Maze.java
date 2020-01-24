package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.scenarios.maze.api.Config;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

/**
 * Maze class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class Maze {

  private static final Logger LOG = java.util.logging.Logger.getLogger(Maze.class.getName());

  @Getter private final String boardID = UUID.randomUUID().toString();

  @Getter private final int width;

  @Getter private final int height;

  @Getter
  private static final Direction[] DIRECTIONS =
      new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

  @Getter private final Cell[][] cellArray;

  @Getter private final List<Cell> cellList = new ArrayList<>();

  @Getter @Setter private Cell startCell = null;

  @Getter @Setter private Cell endCell = null;

  @Getter @Setter private int zoomLevel = 1;

  /**
   * Constructor for Maze.
   *
   * @param width a int.
   * @param height a int.
   */
  public Maze(int width, int height) {
    LOG.log(
        Level.INFO,
        "Generated maze {0} with dimensions {1}x{2}",
        new Object[] {boardID, width, height});
    this.width = width;
    this.height = height;
    cellArray = new Cell[width][height];
    prepareGrid();
    joinGrid();
  }

  /**
   * getConfig.
   *
   * @return a {@link dev.aisandbox.client.scenarios.maze.api.Config} object.
   */
  public Config getConfig() {
    Config c = new Config();
    c.setBoardID(boardID);
    c.setWidth(width);
    c.setHeight(height);
    return c;
  }

  /** prepareGrid. */
  protected void prepareGrid() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Cell c = new Cell(x, y);
        cellArray[x][y] = c;
        cellList.add(c);
      }
    }
  }

  /** joinGrid. */
  protected void joinGrid() {
    for (Cell c : cellList) {
      if (c.getPositionY() > 0) {
        c.linkBi(Direction.NORTH, cellArray[c.getPositionX()][c.getPositionY() - 1]);
      }
      if (c.getPositionX() > 0) {
        c.linkBi(Direction.WEST, cellArray[c.getPositionX() - 1][c.getPositionY()]);
      }
    }
  }
}
