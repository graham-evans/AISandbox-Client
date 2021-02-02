package dev.aisandbox.client.scenarios.snake;

public enum TilesetType {
  BASIC_SNAKE("Snake tileset"),
  BASIC_BIKE("Bike tileset"),
  EXTENDED_SNAKE("Better snake tileset"),
  EXTENDED_BIKE("Better Bike tileset");

  private final String name;

  TilesetType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
