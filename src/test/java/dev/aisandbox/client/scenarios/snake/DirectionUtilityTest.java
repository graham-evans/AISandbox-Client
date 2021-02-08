package dev.aisandbox.client.scenarios.snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import dev.aisandbox.client.scenarios.snake.api.Location;
import dev.aisandbox.client.scenarios.snake.api.SnakeDirection;
import org.junit.Test;

public class DirectionUtilityTest {

  @Test
  public void voidTestNorth() {
    Location l1 = new Location(4, 5);
    Location l2 = new Location(4, 4);
    SnakeDirection dir = DirectionUtility.findDirection(l1, l2);
    assertEquals(SnakeDirection.NORTH, dir);
  }

  @Test
  public void voidTestEast() {
    Location l1 = new Location(4, 5);
    Location l2 = new Location(5, 5);
    SnakeDirection dir = DirectionUtility.findDirection(l1, l2);
    assertEquals(SnakeDirection.EAST, dir);
  }

  @Test
  public void voidTestSouth() {
    Location l1 = new Location(4, 5);
    Location l2 = new Location(4, 6);
    SnakeDirection dir = DirectionUtility.findDirection(l1, l2);
    assertEquals(SnakeDirection.SOUTH, dir);
  }

  @Test
  public void voidTestWest() {
    Location l1 = new Location(4, 5);
    Location l2 = new Location(3, 5);
    SnakeDirection dir = DirectionUtility.findDirection(l1, l2);
    assertEquals(SnakeDirection.WEST, dir);
  }

  @Test
  public void voidTestDisjoint() {
    Location l1 = new Location(4, 5);
    Location l2 = new Location(8, 2);
    SnakeDirection dir = DirectionUtility.findDirection(l1, l2);
    assertNull(dir);
  }
}
