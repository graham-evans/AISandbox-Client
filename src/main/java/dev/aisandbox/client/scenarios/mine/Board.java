package dev.aisandbox.client.scenarios.mine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Object to hold the mine-hunter state. */
public class Board {

  private static final Logger LOG = LoggerFactory.getLogger(Board.class.getName());

  @Getter private final int width;
  @Getter private final int height;

  private final Cell[][] grid;

  @Getter private GameState state = GameState.INIT;

  @Getter private String boardID = UUID.randomUUID().toString();

  @Getter private int unfoundMines = 0;

  /**
   * Constructor based on a predefined width and height.
   *
   * @param width the width of the board (in squares).
   * @param height the height of the board (in squares).
   */
  public Board(int width, int height) {
    this.width = width;
    this.height = height;
    grid = new Cell[width][height];
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        grid[x][y] = new Cell();
      }
    }
  }

  /**
   * Place mines randomly on the board.
   *
   * @param rand a {@link java.util.Random} random number generator.
   * @param count the number of mines to place;
   */
  public void placeMines(Random rand, int count) {
    // check we dont have more mines than cells
    count = Math.min(count, width * height);
    // place the mines randomly
    while (count > 0) {
      Cell c = grid[rand.nextInt(width)][rand.nextInt(height)];
      if (!c.isMine()) {
        c.setMine(true);
        count--;
        unfoundMines++;
      }
    }
  }

  /**
   * Get the cell at <i>(X,Y)</i>.
   *
   * @param x Cell horizontal position.
   * @param y Cell vertical position.
   * @return The requested {@link dev.aisandbox.client.scenarios.mine.Cell} Cell Object.
   */
  protected Cell getCell(int x, int y) {
    return grid[x][y];
  }

  /**
   * Convert a row of cells as a string.
   *
   * @param y the row number (starts at zero).
   * @return a {@link java.lang.String} showing the state of the cells.
   */
  public String getRowToString(int y) {
    StringBuilder sb = new StringBuilder();
    for (int x = 0; x < width; x++) {
      sb.append(grid[x][y].getPlayerView());
    }
    return sb.toString();
  }

  /**
   * Convert the board into an array of strings.
   *
   * @return an array of {@link java.lang.String} Strings, one for each row.
   */
  public String[] getBoardToString() {
    String[] result = new String[grid[0].length];
    for (int y = 0; y < height; y++) {
      result[y] = getRowToString(y);
    }
    return result;
  }

  /**
   * look at the cell @ x,y and return 1 if it is mined. If it doesn't have a mine or is lies
   * outside the grid, return 0.
   *
   * @param x the X position.
   * @param y The Y position.
   * @return 0 or 1
   */
  private int getMineBounds(int x, int y) {
    try {
      return grid[x][y].isMine() ? 1 : 0;
    } catch (IndexOutOfBoundsException e) {
      return 0;
    }
  }

  /**
   * Count the number of neighbours a cell has.
   *
   * <p>This will be 8 for a cell inside the grid, 5 for a cell on the edge, and 3 for a cell in a
   * corner.
   *
   * @param x The X position.
   * @param y The Y position.
   * @return The number of neighbours the cell has.
   */
  private int countNeighbours(int x, int y) {
    int count = 0;
    for (int dx = -1; dx < 2; dx++) {
      for (int dy = -1; dy < 2; dy++) {
        if ((dx != 0) || (dy != 0)) {
          count += getMineBounds(x + dx, y + dy);
        }
      }
    }
    return count;
  }

  /**
   * Count the number of neighbours each cell has.
   *
   * <p>The result is stored in the cell object.
   */
  public void countNeighbours() {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        grid[x][y].setNeighbours(countNeighbours(x, y));
      }
    }
    state = GameState.PLAYING;
  }

  /**
   * Place a flag at position <i>(X,Y)</i>.
   *
   * <p>Placing a flag incorrectly will trigger the "game lost" flag.
   *
   * <p>Placing a flag on a tile that has already been uncovered, or already has a flag, will make
   * no change.
   *
   * @param x The X position.
   * @param y The Y position.
   * @return true is the state of the board has changed.
   */
  public boolean placeFlag(int x, int y) {
    LOG.info("Placing flag @ {},{}", x, y);
    Cell c = grid[x][y];
    boolean change = false;
    if (c.isFlagged()) {
      LOG.info("Flagging an already flagged cell - ignoring");
    } else if (!c.isCovered()) {
      LOG.info("Flagging an uncovered tile - ignoring");
    } else if (c.isMine()) {
      LOG.info("Correctly found a mine");
      c.setFlagged(true);
      unfoundMines--;
      change = true;
    } else {
      LOG.info("Incorrectly marked a mine");
      c.setFlagged(true);
      state = GameState.LOST;
      change = true;
    }
    if (unfoundMines == 0) {
      state = GameState.WON;
    }
    return change;
  }

  /**
   * Uncover the tile at <i>(X,Y)</i>.
   *
   * <p>Uncovering a time that is already uncovered will have no effect.
   *
   * <p>Uncovering a mine will trigger the "game lost" flag.
   *
   * <p>Uncovering a tile with no neighboring bombs will trigger neighboring tiles to be uncovered.
   *
   * @param x The X position.
   * @param y The Y position.
   * @return if the board has changed.
   */
  public boolean uncover(int x, int y) {
    Cell c = grid[x][y];
    boolean change = false;
    if (c.isFlagged() || (!c.isCovered())) {
      LOG.warn("trying to uncover an used cell - ignoring");
    } else {
      c.setCovered(false);
      change = true;
      if (c.isMine()) {
        LOG.info("Bad move");
        state = GameState.LOST;
      } else if (c.getNeighbours() == 0) {
        floodFill(x, y);
      }
    }
    return change;
  }

  private void floodFill(int x, int y) {
    Set<CellLocation> visited = new HashSet<>();
    List<CellLocation> stack = new ArrayList<>();
    stack.add(new CellLocation(x, y));
    while (!stack.isEmpty()) {
      // take the next sell from the stack
      CellLocation currentCellLocation = stack.remove(0);
      Cell currentCell = grid[currentCellLocation.getX()][currentCellLocation.getY()];
      // add it to the visited pile
      visited.add(currentCellLocation);
      // uncover it
      currentCell.setCovered(false);
      // look at neighbours
      if (currentCell.getNeighbours() == 0) {
        // place neighbours on the stack
        List<CellLocation> neighbours = getNeighbours(currentCellLocation);
        for (CellLocation c : neighbours) {
          if ((!visited.contains(c)) && (!stack.contains(c))) {
            stack.add(c);
          }
        }
      }
    }
  }

  private List<CellLocation> getNeighbours(CellLocation c) {
    List<CellLocation> result = new ArrayList<>();
    for (int dx = -1; dx < 2; dx++) {
      int x = c.getX() + dx;
      if ((x >= 0) && (x < width)) {
        for (int dy = -1; dy < 2; dy++) {
          int y = c.getY() + dy;
          if ((y >= 0) && (y < height)) {
            result.add(new CellLocation(x, y));
          }
        }
      }
    }
    return result;
  }
}
