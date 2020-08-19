package dev.aisandbox.client.scenarios.mine;

public enum MineSize {
  SMALL("Small (8x6 10 Mines)"),
  MEDIUM("Medium (16x16 40 Mines)"),
  LARGE("Large (24x24 99 Mines)"),
  MEGA("Mega (40x40 150 Mines)");

  private String name;

  MineSize(String text) {
    this.name = text;
  }

  @Override
  public String toString() {
    return name;
  }
}
