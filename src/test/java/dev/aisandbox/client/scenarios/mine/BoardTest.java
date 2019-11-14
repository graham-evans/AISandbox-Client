package dev.aisandbox.client.scenarios.mine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;

public class BoardTest {
    @Test
    public void formattingTest() {
        Board b = new Board(2,2);
        b.getCell(0,0).setMine(true);
        b.getCell(0,0).setFlagged(true);

        b.getCell(1,0).setNeighbours(1);
        b.getCell(1,0).setCovered(false);
        
        b.getCell(0,1).setNeighbours(1);
        
        b.getCell(1,1).setNeighbours(2);

        assertEquals("Row 0","F1",b.getRowToString(0));
        assertEquals("Row 1","##",b.getRowToString(1));
    }

    @Test
    public void singleCellBoardTest() {
        Board b = new Board(1,1);
        Random rand = new Random();
        b.placeMines(rand, 1);
        assertTrue(b.getCell(0, 0).isMine());
    }

    @Test
    public void calculateNeighboursTest() {
        Board b = new Board(2,1);
        b.getCell(0, 0).setMine(true);
        b.countNeighbours();
        assertEquals(1, b.getCell(1,0).getNeighbours());
    }

}