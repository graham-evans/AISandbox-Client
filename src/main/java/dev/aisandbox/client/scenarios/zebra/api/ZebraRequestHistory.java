package dev.aisandbox.client.scenarios.zebra.api;

import lombok.Data;

/**
 * ZebraRequestHistory class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
public class ZebraRequestHistory {
  private String puzzleID;
  private Solution solution = new Solution();
  private int score;
}
