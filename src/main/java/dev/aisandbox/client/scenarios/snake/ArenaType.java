package dev.aisandbox.client.scenarios.snake;

public enum ArenaType {
  CLASSIC(25, 25, "Classic Square (25x25)"),
  RANDOM_WALLS(30, 30, "Random Walls (30x30)");

  private final int width;
  private final int height;
  private final String name;

  ArenaType(int width, int height, String name) {
    this.width = width;
    this.height = height;
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
