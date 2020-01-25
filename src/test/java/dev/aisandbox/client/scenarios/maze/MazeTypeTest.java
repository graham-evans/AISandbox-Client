package dev.aisandbox.client.scenarios.maze;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class MazeTypeTest {

  @Test
  public void testUniqueName() {
    Set<String> names = new HashSet<>();
    for (MazeType t : MazeType.values()) {
      assertNotNull("null toString for " + t.name(), t.toString());
      assertFalse(
          "Maze type " + t.name() + " has duplicate toString", names.contains(t.toString()));
      names.add(t.toString());
    }
  }
}
