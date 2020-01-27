package dev.aisandbox.client.scenarios.twisty.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Move {
  private int cost;
  private List<MoveChain> chains = new ArrayList<>();
}
