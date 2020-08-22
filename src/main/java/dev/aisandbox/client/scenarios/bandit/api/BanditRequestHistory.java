package dev.aisandbox.client.scenarios.bandit.api;

import lombok.Data;

/** BanditRequestHistory */
@Data
public class BanditRequestHistory {
  private String sessionID;
  private int chosenBandit;
  private double reward;
}
