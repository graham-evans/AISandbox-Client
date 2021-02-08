package dev.aisandbox.client.scenarios.snake;

import dev.aisandbox.client.scenarios.snake.api.Location;
import dev.aisandbox.client.scenarios.snake.api.SnakeDirection;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

public class Board {

  /* A grid of bytes representing the number of "players" on a square.
      -1 = wall
      0 = empty
      >0 = snakes (can be >1 on impacts)
  */
  byte[][] grid;
  @Getter int width;
  @Getter int height;

  @Getter BufferedImage screen;
  Graphics2D graphics;

  public static final int SPRITE_SPACE = 0;
  public static final int SPRITE_WALL = 1;

  public static final int SPRITE_HEAD_NORTH = 2;
  public static final int SPRITE_HEAD_EAST = 3;
  public static final int SPRITE_HEAD_SOUTH = 4;
  public static final int SPRITE_HEAD_WEST = 5;

  public static final int SPRITE_TAIL_NORTH = 6;
  public static final int SPRITE_TAIL_EAST = 7;
  public static final int SPRITE_TAIL_SOUTH = 8;
  public static final int SPRITE_TAIL_WEST = 9;

  public static final int DIRECTION_NORTH = 2;
  public static final int DIRECTION_EAST = 3;
  public static final int DIRECTION_SOUTH = 4;
  public static final int DIRECTION_WEST = 5;

  private final BufferedImage[][] sprites;

  public Board(ArenaType arenaType, BufferedImage[][] sprites) {
    // save sprites
    this.sprites = sprites;
    // initialise
    width = 20;
    height = 20;
    grid = new byte[width][height];
    screen = new BufferedImage(width * 32, height * 32, BufferedImage.TYPE_INT_RGB);
    graphics = screen.createGraphics();
    // fill with blanks
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        grid[x][y] = 0;
        graphics.drawImage(sprites[0][0], x * 32, y * 32, null);
      }
    }
    // put wall around the edge
    for (int x = 0; x < width; x++) {
      setWall(Pair.of(x, 0));
      setWall(Pair.of(x, height - 1));
    }
    for (int y = 0; y < height; y++) {
      setWall(Pair.of(0, y));
      setWall(Pair.of(width - 1, y));
    }
  }

  public void addSnakeHead(Location location, int colour, SnakeDirection direction) {
    grid[location.getX()][location.getY()]++;
    int index;
    switch (direction) {
      case NORTH:
        index = 2;
        break;
      case EAST:
        index = 3;
        break;
      case SOUTH:
        index = 4;
        break;
      case WEST:
        index = 5;
        break;
      default:
        index = 2;
    }
    graphics.drawImage(sprites[colour][index], location.getX() * 32, location.getY() * 32, null);
  }

  public void repaintSnake(Location up, Location mid, Location down) {
    if (down == null) {
      // draw the end of the snake
    }
  }

  public void paintSnake(Location location, int index, int colour) {
    graphics.drawImage(sprites[colour][index], location.getX() * 32, location.getY() * 32, null);
  }

  public void removeSnake(Location location) {
    grid[location.getX()][location.getY()]--;
    if (grid[location.getX()][location.getY()] == 0) {
      graphics.drawImage(sprites[0][0], location.getX() * 32, location.getY() * 32, null);
    }
  }

  /**
   * Remove the contents of the square
   *
   * @param location X,Y
   */
  public void clearSquare(Pair<Integer, Integer> location) {
    grid[location.getLeft()][location.getRight()] = 0;
    graphics.drawImage(sprites[0][0], location.getLeft() * 32, location.getRight() * 32, null);
  }

  private void setWall(Pair<Integer, Integer> location) {
    grid[location.getLeft()][location.getRight()] = -1;
    graphics.drawImage(sprites[0][1], location.getLeft() * 32, location.getRight() * 32, null);
  }

  public boolean isSolid(Location location) {
    return grid[location.getX()][location.getY()] != 0;
  }
}
