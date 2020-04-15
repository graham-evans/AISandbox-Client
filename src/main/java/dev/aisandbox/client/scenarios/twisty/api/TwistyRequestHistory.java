package dev.aisandbox.client.scenarios.twisty.api;

import lombok.Data;

/**
 * TwistyRequestHistory class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
public class TwistyRequestHistory {
  private String startState = null;
  private String moves = null;
  private String endState = null;
  private boolean success = false;
}
