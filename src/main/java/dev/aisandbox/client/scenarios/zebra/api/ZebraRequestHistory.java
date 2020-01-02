package dev.aisandbox.client.scenarios.zebra.api;

import lombok.Data;

@Data
public class ZebraRequestHistory {
  private String puzzleID = null;
  private Answer answer = null;
  private Integer score = null;
}
