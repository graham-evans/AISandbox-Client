package dev.aisandbox.client.scenarios.twisty;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * TwistyPuzzle interface.
 *
 * @author gde
 * @version $Id: $Id
 */
public interface TwistyPuzzle {
  /**
   * getPuzzleName.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getPuzzleName();

  /** resetPuzzle. */
  public void resetPuzzle();

  /**
   * resetPuzzle.
   *
   * @param state a {@link java.lang.String} object.
   */
  public void resetPuzzle(String state);

  /**
   * getColourMap.
   *
   * @return a {@link java.util.Map} object.
   */
  public Map<Character, Color> getColourMap();

  /**
   * getState.
   *
   * @return a {@link java.lang.String} object.
   */
  public String getState();

  /**
   * getMoveList.
   *
   * @return a {@link java.util.List} object.
   */
  public List<String> getMoveList();

  /**
   * applyMove.
   *
   * @param move a {@link java.lang.String} object.
   * @return a int.
   * @throws dev.aisandbox.client.scenarios.twisty.NotExistentMoveException if any.
   */
  public int applyMove(String move) throws NotExistentMoveException;

  /**
   * isSolved.
   *
   * @return a boolean.
   */
  public boolean isSolved();

  /**
   * getStateImage.
   *
   * @return a {@link java.awt.image.BufferedImage} object.
   */
  public BufferedImage getStateImage();

  /**
   * getMoveImage.
   *
   * @param move a {@link java.lang.String} object.
   * @return a {@link java.awt.image.BufferedImage} object.
   */
  public BufferedImage getMoveImage(String move);

  /**
   * createMoveSpriteSheet.
   *
   * @return a {@link java.awt.image.BufferedImage} object.
   */
  public BufferedImage createMoveSpriteSheet();
}
