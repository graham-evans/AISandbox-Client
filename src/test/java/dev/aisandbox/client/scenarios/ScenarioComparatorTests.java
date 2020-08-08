package dev.aisandbox.client.scenarios;

import static org.junit.Assert.assertTrue;

import dev.aisandbox.client.scenarios.maze.MazeScenario;
import dev.aisandbox.client.scenarios.mine.MineHunterScenario;
import dev.aisandbox.client.scenarios.twisty.TwistyScenario;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ScenarioComparatorTests {

  @Test
  public void groupOrderTest1() {
    List<Scenario> scenarioList = new ArrayList<>();
    scenarioList.add(new MineHunterScenario(null));
    scenarioList.add(new TwistyScenario());
    // sort the list
    scenarioList.sort(new ScenarioComparator());
    assertTrue(
        "Mine Hunter should come first (group order)", scenarioList.get(0).getId().equals("mine"));
  }

  @Test
  public void groupOrderTest2() {
    List<Scenario> scenarioList = new ArrayList<>();
    scenarioList.add(new TwistyScenario());
    scenarioList.add(new MineHunterScenario(null));
    // sort the list
    scenarioList.sort(new ScenarioComparator());
    assertTrue(
        "Mine Hunter should come first (group order)", scenarioList.get(0).getId().equals("mine"));
  }

  @Test
  public void nameOrderTest() {
    List<Scenario> scenarioList = new ArrayList<>();
    scenarioList.add(new MazeScenario(null));
    scenarioList.add(new MineHunterScenario(null));
    // sort the list
    scenarioList.sort(new ScenarioComparator());
    assertTrue("Maze should come first (name order)", scenarioList.get(0).getId().equals("maze"));
  }
}
