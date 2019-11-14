package dev.aisandbox.client.scenarios.mine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CellTest {

    @Test
    public void uncoveredTest() {
        Cell c = new Cell();
        assertEquals('#', c.getPlayerView());
    }

    @Test
    public void flaggedTest() {
        Cell c = new Cell();
        c.setMine(true);
        c.setFlagged(true);
        assertEquals('F', c.getPlayerView());
    }    

    @Test
    public void threeNeighboursTest() {
        Cell c = new Cell();
        c.setCovered(false);
        c.setNeighbours(3);
        assertEquals('3',c.getPlayerView());
    }

    @Test
    public void zeroNeighboursTest() {
        Cell c = new Cell();
        c.setCovered(false);
        c.setNeighbours(0);
        assertEquals('.', c.getPlayerView());
    }
}