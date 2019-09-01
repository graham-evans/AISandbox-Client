package dev.aisandbox.client.scenarios.maze;

import java.util.*;

public class MazeUtilities {

    public static void findFurthestPoints(Maze maze) {
        applyDijkstra(maze);
        Cell start = getHighestVelueCell(maze);
        maze.setStartCell(start);
        applyDijkstra(maze, start);
        Cell finish = getHighestVelueCell(maze);
        maze.setEndCell(finish);
        normalise(maze);
    }

    /**
     * Apply the Dijkstra algorithm, once, to the maze.
     * This will result in the <i>value</i> of each cell growing from 0 (at a random point)
     *
     * @param maze
     */
    public static void applyDijkstra(Maze maze) {
        Random rand = new Random(System.currentTimeMillis());
        applyDijkstra(maze, maze.getCellList().get(rand.nextInt(maze.getCellList().size())));
    }

    /**
     * Apply the Dijkstra algorithm, once, to the maze.
     * This will result in the <i>value</i> of each cell growing from the starting position
     *
     * @param maze
     */
    public static void applyDijkstra(Maze maze, Cell start) {
        // list of cells that have not been visited
        Set<Cell> unvisitedList = new HashSet<>();
        // the current round of cells
        List<Cell> currentList = new ArrayList<>();
        // the next round of cells
        List<Cell> nextList = new ArrayList<>();
        // add cells to the unvisited list
        unvisitedList.addAll(maze.getCellList());
        // pick our first (random cell)
        currentList.add(start);
        unvisitedList.remove(start);
        int step = 0;
        while (!currentList.isEmpty()) {
            for (Cell base : currentList) {
                // assign a value
                base.setValue(step);
                // add neighbours in next list
                for (Direction d : base.getPaths()) {
                    Cell c2 = base.getNeighbours().get(d);
                    if (unvisitedList.contains(c2)) {
                        // mark this for the next round
                        nextList.add(c2);
                        unvisitedList.remove(c2);
                    }
                }
            }
            // processed all in current list - move on
            currentList = nextList;
            nextList = new ArrayList<>();
            step++;
        }
    }

    public static Cell getHighestVelueCell(Maze maze) {
        Cell result = null;
        float v = Float.MIN_VALUE;
        for (Cell c : maze.getCellList()) {
            if (c.getValue() > v) {
                v = c.getValue();
                result = c;
            }
        }
        return result;
    }

    /**
     * update the values of each cell - distributing them between 0.0 and 1.0
     *
     * @param maze
     */
    public static void normalise(Maze maze) {
        // work our max and min
        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;
        for (Cell c : maze.getCellList()) {
            max = Math.max(c.getValue(), max);
            min = Math.min(c.getValue(), min);
        }
        // scale all values between 0 and 1
        for (Cell c : maze.getCellList()) {
            c.setValue((c.getValue() - min) / (max - min));
        }

    }
}
