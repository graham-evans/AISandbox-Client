package dev.aisandbox.client.scenarios.twisty;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public interface TwistyPuzzle {
  public String getPuzzleName();

  public void resetPuzzle();

  public void resetPuzzle(String state);

  public Map<Character, Color> getColourMap();

  public String getState();

  public List<String> getMoveList();

  public int applyMove(String move) throws NotExistentMoveException;

  public boolean isSolved();

  public BufferedImage getStateImage();
}
