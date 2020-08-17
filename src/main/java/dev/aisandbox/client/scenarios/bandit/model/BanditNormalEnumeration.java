package dev.aisandbox.client.scenarios.bandit.model;

public enum BanditNormalEnumeration {
  NORMAL_0_1("Normal(0,1)"),
  NORMAL_0_5("Normal(0,5)"),
  UNIFORM_1_1("Uniform -1:1"),
  UNIFORM_0_5("Uniform 0:5");

  private String name;

  BanditNormalEnumeration(String envUrl) {
    this.name = envUrl;
  }

  @Override
  public String toString() {
    return name;
  }
}
