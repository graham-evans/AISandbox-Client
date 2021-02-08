package dev.aisandbox.client.scenarios.snake;

import dev.aisandbox.client.scenarios.snake.api.Location;
import dev.aisandbox.client.scenarios.snake.api.SnakeDirection;

public class DirectionUtility {

  public static SnakeDirection findDirection(Location start, Location finish) {
    int dx = finish.getX() - start.getX();
    int dy = finish.getY() - start.getY();
    if (dx == 0) {
      if (dy == -1) {
        return SnakeDirection.NORTH;
      }
      if (dy == +1) {
        return SnakeDirection.SOUTH;
      }
    } else if ((dx == -1) && (dy == 0)) {
      return SnakeDirection.WEST;
    } else if ((dx == +1) && (dy == 0)) {
      return SnakeDirection.EAST;
    }
    return null;
  }

  public static int iconLookup(SnakeDirection d1,SnakeDirection d2) {
    return 0;
  }

}
