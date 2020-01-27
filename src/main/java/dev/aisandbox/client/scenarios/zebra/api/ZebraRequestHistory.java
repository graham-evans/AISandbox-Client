package dev.aisandbox.client.scenarios.zebra.api;

import lombok.Data;

@Data
public class ZebraRequestHistory {
  private String puzzleID;
  private Solution solution;
  private int score;
}
