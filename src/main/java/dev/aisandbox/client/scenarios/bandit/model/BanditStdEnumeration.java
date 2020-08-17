package dev.aisandbox.client.scenarios.bandit.model;

public enum BanditStdEnumeration {
  ONE("1"),
  FIVE("5"),
  TENTH("1/10");

  private String name;

  BanditStdEnumeration(String envUrl) {
    this.name = envUrl;
  }

  @Override
  public String toString() {
    return name;
  }
}
