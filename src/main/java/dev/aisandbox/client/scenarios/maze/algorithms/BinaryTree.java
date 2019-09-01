package dev.aisandbox.client.scenarios.maze.algorithms;

import dev.aisandbox.client.scenarios.maze.Cell;
import dev.aisandbox.client.scenarios.maze.Direction;
import dev.aisandbox.client.scenarios.maze.Maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinaryTree implements MazeAlgorithm {

    Random rand = new Random(System.currentTimeMillis());

    @Override
    public String getName() {
        return "Binary Tree (Biased)";
    }

    @Override
    public void apply(Maze maze) {
        for (Cell c : maze.getCellList()) {
            List<Cell> targets = new ArrayList<>();
            if (c.getNeighbours().get(Direction.EAST) != null) {
                targets.add(c.getNeighbours().get(Direction.EAST));
            }
            if (c.getNeighbours().get(Direction.NORTH) != null) {
                targets.add(c.getNeighbours().get(Direction.NORTH));
            }
            if (!targets.isEmpty()) {
                c.addPath(targets.get(rand.nextInt(targets.size())));
            }
        }
    }
}
