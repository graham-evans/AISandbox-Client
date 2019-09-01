package dev.aisandbox.client.scenarios.maze;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    IN,
    OUT,
    CLOCKWISE,
    ANTICLOCKWISE;

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
