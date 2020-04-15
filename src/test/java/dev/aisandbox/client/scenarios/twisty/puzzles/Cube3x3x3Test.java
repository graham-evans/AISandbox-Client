package dev.aisandbox.client.scenarios.twisty.puzzles;

import static org.junit.Assert.*;

import dev.aisandbox.client.scenarios.twisty.NotExistentMoveException;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class Cube3x3x3Test {

  @Test
  public void initialStateTest() {
    Cube3x3x3 cube = new Cube3x3x3();
    assertEquals(
        "Initial state", "WWWWWWWWWOOOOOOOOOGGGGGGGGGRRRRRRRRRBBBBBBBBBYYYYYYYYY", cube.getState());
  }

  @Test
  public void moveNumberTest() {
    Cube3x3x3 cube = new Cube3x3x3();
    assertEquals("Number of moves", 45, cube.getMoveList().size());
  }

  @Test
  public void solvedTestStatic() {
    Cube3x3x3 cube = new Cube3x3x3();
    assertTrue(cube.isSolved());
  }

  @Test
  public void solvedTestMove() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.applyMove("F");
    assertFalse(cube.isSolved());
  }

  @Test
  public void solvedTestMoveBack() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.applyMove("F");
    cube.applyMove("F'");
    assertTrue("Cube state=" + cube.getState(), cube.isSolved());
  }

  // <editor-fold desc="Front Moves">
  @Test
  public void testMoveF() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    int cost = cube.applyMove("F");
    assertEquals("Cost", 1, cost);
    assertEquals(
        "State", "abcdefroljkTmnUpqVyvszwtAxugCDhFGiIJKLMNOPQRSHEBWXYZ12", cube.getState());
  }

  @Test
  public void testMoveFw() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    int cost = cube.applyMove("Fw");
    assertEquals("Cost", 1, cost);
    assertEquals(
        "State", "abcqnkroljWTmXUpYVyvszwtAxugdDheGifJKLMNOPQRSHEBIFCZ12", cube.getState());
  }

  @Test
  public void testMoveFw2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Fw2"));
    assertEquals("Cost", 1, cube.applyMove("Fw2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveFwinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Fw'"));
    assertEquals("Cost", 1, cube.applyMove("Fw"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveFinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("F"));
    assertEquals("Cost", 1, cube.applyMove("F'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveF2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("F2"));
    assertEquals("Cost", 1, cube.applyMove("F2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }
  // </editor-fold>

  // <editor-fold desc="Left Moves">
  @Test
  public void testMoveL() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    int cost = cube.applyMove("L");
    assertEquals("Cost", 1, cost);
    assertEquals(
        "State", "SbcPefMhipmjqnkrolatudwxgzABCDEFGHIJKLZNOWQRTsUVvXYy12", cube.getState());
  }

  @Test
  public void testMoveLinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("L"));
    assertEquals("Cost", 1, cube.applyMove("L'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveL2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("L2"));
    assertEquals("Cost", 1, cube.applyMove("L2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveLw() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Lw"));
    assertEquals(
        "State", "SRcPOfMLipmjqnkrolabudexghABCDEFGHIJK1ZNXWQUTstVvwYyz2", cube.getState());
  }

  @Test
  public void testMoveLwinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Lw"));
    assertEquals("Cost", 1, cube.applyMove("Lw'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveLw2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Lw2"));
    assertEquals("Cost", 1, cube.applyMove("Lw2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }
  // </editor-fold>

  // <editor-fold desc="Down Moves">
  @Test
  public void testMoveD() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("D"));
    assertEquals(
        "State", "abcdefghijklmnoQRSstuvwxpqrBCDEFGyzAKLMNOPHIJZWT1XU2YV", cube.getState());
  }

  @Test
  public void testMoveDinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("D"));
    assertEquals("Cost", 1, cube.applyMove("D'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveD2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("D2"));
    assertEquals("Cost", 1, cube.applyMove("D2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveDw() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Dw"));
    assertEquals(
        "State", "abcdefghijklNOPQRSstumnopqrBCDvwxyzAKLMEFGHIJZWT1XU2YV", cube.getState());
  }

  @Test
  public void testMoveDwinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Dw"));
    assertEquals("Cost", 1, cube.applyMove("Dw'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveDw2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Dw2"));
    assertEquals("Cost", 1, cube.applyMove("Dw2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }
  // </editor-fold>

  // <editor-fold desc="Up Moves">
  @Test
  public void testMoveU() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("U"));
    assertEquals(
        "State", "gdahebifcstumnopqrBCDvwxyzAKLMEFGHIJjklNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveUinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("U"));
    assertEquals("Cost", 1, cube.applyMove("U'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveU2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("U2"));
    assertEquals("Cost", 1, cube.applyMove("U2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveUw() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Uw"));
    assertEquals(
        "State", "gdahebifcstuvwxpqrBCDEFGyzAKLMNOPHIJjklmnoQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveUwinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Uw"));
    assertEquals("Cost", 1, cube.applyMove("Uw'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveUw2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Uw2"));
    assertEquals("Cost", 1, cube.applyMove("Uw2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }
  // </editor-fold>

  // <editor-fold desc="Right Moves">
  @Test
  public void testMoveR() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("R"));
    assertEquals(
        "State", "abudexghAjklmnopqrstVvwYyz2HEBIFCJGDiLMfOPcRSTUQWXNZ1K", cube.getState());
  }

  @Test
  public void testMoveRinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("R"));
    assertEquals("Cost", 1, cube.applyMove("R'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveR2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("R2"));
    assertEquals("Cost", 1, cube.applyMove("R2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveRw() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Rw"));
    assertEquals(
        "State", "atudwxgzAjklmnopqrsUVvXYy12HEBIFCJGDihMfePcbSTRQWONZLK", cube.getState());
  }

  @Test
  public void testMoveRwinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Rw"));
    assertEquals("Cost", 1, cube.applyMove("Rw'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveRw2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Rw2"));
    assertEquals("Cost", 1, cube.applyMove("Rw2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }
  // </editor-fold>

  // <editor-fold desc="Back Moves">
  @Test
  public void testMoveB() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("B"));
    assertEquals(
        "State", "DGJdefghicklbnoaqrstuvwxyzABC2EF1HIZQNKROLSPMTUVWXYjmp", cube.getState());
  }

  @Test
  public void testMoveBinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("B"));
    assertEquals("Cost", 1, cube.applyMove("B'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveB2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("B2"));
    assertEquals("Cost", 1, cube.applyMove("B2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveBw() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Bw"));
    assertEquals(
        "State", "DGJCFIghicflbeoadrstuvwxyzABY2EX1HWZQNKROLSPMTUVknqjmp", cube.getState());
  }

  @Test
  public void testMoveBwinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Bw"));
    assertEquals("Cost", 1, cube.applyMove("Bw'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testMoveBw2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 1, cube.applyMove("Bw2"));
    assertEquals("Cost", 1, cube.applyMove("Bw2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }
  // </editor-fold>
  // <editor-fold desc="Rotations">
  @Test
  public void testRotateX() throws NotExistentMoveException {
    Cube3x3x3 cube1 = new Cube3x3x3();
    Cube3x3x3 cube2 = new Cube3x3x3();
    cube1.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    cube2.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 0, cube1.applyMove("x"));
    cube2.applyMove("Rw");
    cube2.applyMove("L'");
    assertEquals("State", cube1.getState(), cube2.getState());
  }

  @Test
  public void testRotateY() throws NotExistentMoveException {
    Cube3x3x3 cube1 = new Cube3x3x3();
    Cube3x3x3 cube2 = new Cube3x3x3();
    cube1.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    cube2.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 0, cube1.applyMove("y"));
    cube2.applyMove("Uw");
    cube2.applyMove("D'");
    assertEquals("State", cube1.getState(), cube2.getState());
  }

  @Test
  public void testRotateZ() throws NotExistentMoveException {
    Cube3x3x3 cube1 = new Cube3x3x3();
    Cube3x3x3 cube2 = new Cube3x3x3();
    cube1.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    cube2.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 0, cube1.applyMove("z"));
    cube2.applyMove("Fw");
    cube2.applyMove("B'");
    assertEquals("State", cube1.getState(), cube2.getState());
  }

  @Test
  public void testRotateXinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 0, cube.applyMove("x"));
    assertEquals("Cost", 0, cube.applyMove("x'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testRotateYinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 0, cube.applyMove("y"));
    assertEquals("Cost", 0, cube.applyMove("y'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testRotateZinv() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 0, cube.applyMove("z"));
    assertEquals("Cost", 0, cube.applyMove("z'"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testRotateX2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 0, cube.applyMove("x2"));
    assertEquals("Cost", 0, cube.applyMove("x2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testRotateY2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 0, cube.applyMove("y2"));
    assertEquals("Cost", 0, cube.applyMove("y2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }

  @Test
  public void testRotateZ2() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    cube.resetPuzzle("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12");
    assertEquals("Cost", 0, cube.applyMove("z2"));
    assertEquals("Cost", 0, cube.applyMove("z2"));
    assertEquals(
        "State", "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ12", cube.getState());
  }
  // </editor-fold>

  @Test
  public void testAllMovesImplemented() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    for (String move : cube.getMoveList()) {
      cube.resetPuzzle();
      String source = cube.getState();
      cube.applyMove(move);
      String dest = cube.getState();
      assertFalse("Move " + move + " doesn't change state", source.equals(dest));
    }
  }

  @Test
  public void testAllMovesDifferent() throws NotExistentMoveException {
    Cube3x3x3 cube = new Cube3x3x3();
    Set<String> hash = new HashSet<>();
    for (String move : cube.getMoveList()) {
      cube.resetPuzzle();
      cube.applyMove(move);
      String dest = cube.getState();
      assertFalse("Move " + move + " isn't unique", hash.contains(dest));
      hash.add(dest);
    }
  }
}
