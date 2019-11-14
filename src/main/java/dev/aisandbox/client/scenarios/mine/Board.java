package dev.aisandbox.client.scenarios.mine;

import java.util.Random;

public class Board {

    private final int width;
    private final int height;

    private final Cell[][] grid;

    private GameState state = GameState.PLAYING;

    public Board(int width,int height) {
        this.width = width;
        this.height = height;
        grid = new Cell[width][height];
        for (int x=0;x<width;x++) {
            for (int y=0;y<height;y++) {
                grid[x][y] = new Cell();
            }
        }
    }

    public void placeMines(Random rand,int count) {
        // check we dont have more mines than cells
        count = Math.min(count,width*height);
        // place the mines randomly
        while (count>0) {
            Cell c = grid[rand.nextInt(width)][rand.nextInt(height)];
            if (!c.isMine()) {
                c.setMine(true);
                count--;
            }
        }
    }

    protected Cell getCell(int x,int y) {
        return grid[x][y];
    }

    public String getRowToString(int y) {
        StringBuilder sb = new StringBuilder();
        for(int x=0;x<width;x++) {
            sb.append(grid[x][y].getPlayerView());
        }
        return sb.toString();
    }

    private int getMineBounds(int x,int y) {
        try {
            return grid[x][y].isMine() ? 1 : 0;
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public void countNeighbours() {
        for (int x=0;x<width;x++) {
            for (int y=0;y<height;y++) {
                int count=0;
                for (int dx=-1;dx<2;dx++) {
                    for (int dy=-1;dy<2;dy++) {
                        if ((dx!=0)||(dy!=0)) {
                            count+= getMineBounds(x+dx, y+dy);
                        }
                    }
                }
                grid[x][y].setNeighbours(count);
            }
        }
    }

    public void placeFlag(int x,int y) {

    }

    public void uncover(int x,int y) {

    }

}