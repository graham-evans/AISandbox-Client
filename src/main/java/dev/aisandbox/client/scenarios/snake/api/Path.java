package dev.aisandbox.client.scenarios.snake.api;

import lombok.Data;

@Data
public class Path {

  private int length;
  private Location[] steps;

}
