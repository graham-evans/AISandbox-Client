package dev.aisandbox.client.scenarios.mine;

import lombok.Data;

@Data
public class Cell {

    private boolean mine;

    public boolean isMine() { return mine;}
    public void setMine(boolean m) { mine=m;}

    // is this cell still covered
    private boolean covered = true;

    public boolean isCovered() { return covered;}
    public void setCovered(boolean c) { covered = c; }

    // has this cell been flagged
    private boolean flagged;

    public boolean isFlagged() { return flagged;}
    public void setFlagged(boolean f) { flagged = f;}

    // the number of neighbours that are mines
    private int neighbours;

    public int getNeighbours() { return neighbours; }
    public void setNeighbours(int n) { neighbours=n;}
    
    public char getPlayerView() {
        if (covered) {
            if (flagged) {
                return 'F';
            } else {
                return '#';
            }
        } else {
            if (mine) {
                return 'X';
            } else if (neighbours==0) {
                return '.';
            } else {
                return Integer.toString(neighbours).charAt(0);
            }
        }
    }

}