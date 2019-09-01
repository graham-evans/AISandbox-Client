package dev.aisandbox.client.scenarios.maze.algorithms;

import dev.aisandbox.client.scenarios.maze.Maze;

public interface MazeAlgorithm {

    public String getName();

    public void apply(Maze maze);
}
