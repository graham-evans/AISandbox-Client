package dev.aisandbox.client.scenarios.snake;

public enum TailType {
  INFINITE("Infinite tail"),
  GROWING("Growing with food");

  private final String name;

  TailType(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}
