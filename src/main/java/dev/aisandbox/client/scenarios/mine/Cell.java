package dev.aisandbox.client.scenarios.mine;

import lombok.Data;

/**
 * A single cell (square) on the board, includes rules for how it should be displayed # - Covered F
 * - Covered + flagged X - uncovered mine = GAME OVER f - missflagged empty square = GAME OVER 1-8 -
 * uncovered + number of mines surrounding . - uncovered + no mines surrounding
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
public class Cell {

  private boolean mine;

  // is this cell still covered
  private boolean covered = true;

  // has this cell been flagged
  private boolean flagged;

  // the number of neighbours that are mines
  private int neighbours;

  /**
   * getPlayerView.
   *
   * @return a char.
   */
  public char getPlayerView() {
    if (covered) {
      if (flagged) {
        if (mine) {
          return 'F';
        } else {
          return 'f';
        }
      } else {
        return '#';
      }
    } else {
      if (mine) {
        return 'X';
      } else if (neighbours == 0) {
        return '.';
      } else {
        return Integer.toString(neighbours).charAt(0);
      }
    }
  }
}
