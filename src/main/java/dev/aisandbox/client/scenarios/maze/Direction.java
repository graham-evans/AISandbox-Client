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
    WEST,
    IN,
    OUT,
    CLOCKWISE,
    ANTICLOCKWISE;

    /**
     * <p>opposite.</p>
     *
     * @return a {@link dev.aisandbox.client.scenarios.maze.Direction} object.
     */
    public Direction opposite() {
        switch (this) {
            case IN:
                return OUT;
            case OUT:
                return IN;
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
            case CLOCKWISE:
                return ANTICLOCKWISE;
            case ANTICLOCKWISE:
                return CLOCKWISE;
            default:
                return null;
        }
    }
}
