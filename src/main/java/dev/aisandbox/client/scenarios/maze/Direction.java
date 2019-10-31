package dev.aisandbox.client.scenarios.maze;

/**
 * <p>Direction class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST
  ;

    /**
     * <p>opposite.</p>
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
