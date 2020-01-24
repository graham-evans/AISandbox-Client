package dev.aisandbox.client.scenarios.maze.api;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

public class POJOTests {

  @Test
  public void PositionTest() {
    assertPojoMethodsFor(Position.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
  }

  @Test
  public void ConfigTest() {
    assertPojoMethodsFor(Config.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
  }

  @Test
  public void HistoryTest() {
    assertPojoMethodsFor(History.class).testing(Method.GETTER, Method.SETTER).areWellImplemented();
  }

  @Test
  public void MazeRequest() {
    assertPojoMethodsFor(MazeRequest.class)
        .testing(Method.GETTER, Method.SETTER)
        .areWellImplemented();
  }

  @Test
  public void MazeResponse() {
    assertPojoMethodsFor(MazeResponse.class)
        .testing(Method.GETTER, Method.SETTER)
        .areWellImplemented();
  }
}
