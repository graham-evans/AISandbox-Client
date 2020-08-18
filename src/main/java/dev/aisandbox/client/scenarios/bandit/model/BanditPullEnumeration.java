package dev.aisandbox.client.scenarios.bandit.model;

import lombok.Getter;

public enum BanditPullEnumeration {
  ONE_HUNDRED(100),
  FIVE_HUNDRED(500),
  ONE_THOUSAND(1000),
  TWO_THOUSAND(2000),
  FIVE_THOUSAND(5000);

  @Getter private final int number;

  BanditPullEnumeration(int number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return Integer.toString(number);
  }
}
