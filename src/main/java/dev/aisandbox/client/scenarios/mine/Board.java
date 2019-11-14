package dev.aisandbox.client.scenarios.mine;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

public class Board {

    private static final Logger LOG = Logger.getLogger(Board.class.getName());

    private final int width;
    private final int height;

    private final Cell[][] grid;

    private GameState state = GameState.INIT;

    private int unfoundMines=0;

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

    public GameState getState() {
        return state;
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
                unfoundMines++;
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
        Cell c = grid[x][y];
        if (c.isFlagged()) {
            LOG.info("Flagging an already flagged cell - ignoring");
        } else if (!c.isCovered()) {
            LOG.info("Flagging an uncovered tile - ignoring");
        } else if (c.isMine()) {
            LOG.info("Correctly found a mine");
            c.setFlagged(true);
            unfoundMines--;
        } else {
            LOG.info("Incorrectly marked a mine");
            c.setFlagged(true);
            state=GameState.LOST;
        }
        if (unfoundMines==0) {
            state=GameState.WON;
        }
    }

    public void uncover(int x,int y) {
        Cell c = grid[x][y];
        if (c.isFlagged()||(!c.isCovered())) {
            LOG.warning("trying to uncover an used cell - ignoring");
        } else {
            c.setCovered(false);
            if (c.isMine()) {
                LOG.info("Bad move");
                state = GameState.LOST;
            } else if (c.getNeighbours()==0) {
  //              floodFill(x,y);
            }
        }
    }

    private void floodFill(int x,int y) {
        Set<CellLocation> visited= new HashSet<>();
        Stack<CellLocation> stack = new Stack<>();
        stack.add(new CellLocation(x,y));
        while (!stack.isEmpty()) {
            CellLocation cl = stack.pop();
            Cell c = grid[cl.getX()][cl.getY()];
            visited.add(cl);
            if (c.getNeighbours()==0) {
                // place neighbours on the stack
                for (int dy=-1;dy<2;dy++) {
                    for (int dx=-1;dx<2;dx++) {
                        if ((dx!=0)||(dy!=0)) {
                            try {
                            Cell c2 = grid[cl.getX()+dx][cl.getY()+y];
                            CellLocation cl2 = new CellLocation(cl.getX()+dx,cl.getY()+y);
                            if ((!visited.contains(cl2))&&(!stack.contains(cl2))) {
                                // new cell
                                c2.setCovered(false);
                                if (c2.getNeighbours()==0) {
                                    stack.add(cl2);
                                }
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            // ignore
                        }
                    }
                    }
                }
            }
            
        }
    }


}