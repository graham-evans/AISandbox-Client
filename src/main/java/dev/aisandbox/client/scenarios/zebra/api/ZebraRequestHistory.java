package dev.aisandbox.client.scenarios.zebra.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ZebraRequestHistory {
  private String puzzleID = null;
  private List<HouseAnswer> answer = new ArrayList<HouseAnswer>();
  private Integer score = null;

}
