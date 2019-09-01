package dev.aisandbox.client.scenarios.maze.algorithms;

import dev.aisandbox.client.scenarios.maze.Cell;
import dev.aisandbox.client.scenarios.maze.Direction;
import dev.aisandbox.client.scenarios.maze.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sidewinder implements MazeAlgorithm {

    Random rand = new Random(System.currentTimeMillis());

    @Override
    public String getName() {
        return "Sidewinder (Biased)";
    }

    @Override
    public void apply(Maze maze) {
        // special case, join the top row
        for (int x = 0; x < maze.getWidth() - 1; x++) {
            maze.getCellArray()[x][0].addPath(Direction.EAST);
        }
        for (int y = 1; y < maze.getHeight(); y++) {
            List<Cell> group = new ArrayList<>();
            for (int x = 0; x < maze.getWidth(); x++) {
                // get cell
                Cell c = maze.getCellArray()[x][y];
                // add cell to group
                if (!group.isEmpty()) {
                    c.addPath(Direction.WEST);
                }
                group.add(c);
                if ((x == maze.getWidth() - 1) || rand.nextBoolean()) {
                    // link upwards
                    Cell c2 = group.get(rand.nextInt(group.size()));
                    c2.addPath(Direction.NORTH);
                    group.clear();
                }
            }
        }
    }
}
