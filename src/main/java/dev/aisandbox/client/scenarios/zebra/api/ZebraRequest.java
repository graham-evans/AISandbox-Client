package dev.aisandbox.client.scenarios.zebra.api;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ZebraRequest {
  private ZebraRequestHistory history;
  private String puzzleID;
  private List<String> clues = new ArrayList<>();
  private List<ZebraRequestCharacteristics> characteristics = new ArrayList<ZebraRequestCharacteristics>();
  private int numberOfHouses;

}
