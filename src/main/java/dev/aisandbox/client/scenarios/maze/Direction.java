package dev.aisandbox.client.scenarios.maze;

/**
 * Direction class.
 *
 * @author gde
 * @version $Id: $Id
 */
public enum Direction {
  NORTH,
  SOUTH,
  EAST,
  WEST;

  /**
   * opposite.
   *
   * @return a {@link dev.aisandbox.client.scenarios.maze.Direction} object.
   */
  public Direction opposite() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case EAST:
        return WEST;
      case SOUTH:
        return NORTH;
      case WEST:
        return EAST;
      default:
        return null;
    }
  }
}
