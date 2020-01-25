package dev.aisandbox.client.scenarios.zebra.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ZebraRequestHistory {
  private String puzzleID;
  private Solution solution;
  private int score;
}
