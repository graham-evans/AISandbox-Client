package dev.aisandbox.client.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.output.OutputFormat;
import dev.aisandbox.client.scenarios.maze.MazeScenario;
import dev.aisandbox.client.scenarios.maze.MazeSize;
import dev.aisandbox.client.scenarios.maze.MazeType;
import dev.aisandbox.client.scenarios.mine.MineHunterScenario;
import dev.aisandbox.client.scenarios.mine.SizeEnum;
import dev.aisandbox.launcher.AISandboxCLI;
import java.util.Properties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AISandboxCLI.class})
@SpringBootTest
public class PropertiesParserTest {

  @Autowired PropertiesParser parser;

  @Test
  public void readMine1Test() {
    Properties props = new Properties();
    props.setProperty("scenario", "mine");
    props.setProperty("salt", "123456");
    props.setProperty("size", "small");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertTrue("incorrect scenario", model.getScenario() instanceof MineHunterScenario);
    MineHunterScenario mine = (MineHunterScenario) model.getScenario();
    assertEquals("Incorrect salt", 123456, (long) mine.getScenarioSalt());
    assertEquals("Incorrect board size", SizeEnum.SMALL, mine.getMineHunterBoardSize());
  }

  @Test
  public void readMine2Test() {
    Properties props = new Properties();
    props.setProperty("scenario", "mine");
    props.setProperty("salt", "234567");
    props.setProperty("size", "medium");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertTrue("incorrect scenario", model.getScenario() instanceof MineHunterScenario);
    MineHunterScenario mine = (MineHunterScenario) model.getScenario();
    assertEquals("Incorrect salt", 234567, (long) mine.getScenarioSalt());
    assertEquals("Incorrect board size", SizeEnum.MEDIUM, mine.getMineHunterBoardSize());
  }

  @Test
  public void readMine3Test() {
    Properties props = new Properties();
    props.setProperty("scenario", "mine");
    props.setProperty("salt", "654321");
    props.setProperty("size", "large");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertTrue("incorrect scenario", model.getScenario() instanceof MineHunterScenario);
    MineHunterScenario mine = (MineHunterScenario) model.getScenario();
    assertEquals("Incorrect salt", 654321, (long) mine.getScenarioSalt());
    assertEquals("Incorrect board size", SizeEnum.LARGE, mine.getMineHunterBoardSize());
  }

  @Test
  public void readMaze1Test() {
    Properties props = new Properties();
    props.setProperty("scenario", "maze");
    props.setProperty("salt", "654321");
    props.setProperty("size", "small");
    props.setProperty("type", "binarytree");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertTrue("incorrect scenario", model.getScenario() instanceof MazeScenario);
    MazeScenario maze = (MazeScenario) model.getScenario();
    assertEquals("Incorrect salt", 654321, (long) maze.getScenarioSalt());
    assertEquals("Incorrect board size", MazeSize.SMALL, maze.getMazeSize());
    assertEquals("Inncorrect maze type", MazeType.BINARYTREE, maze.getMazeType());
  }

  @Test
  public void readMaze2Test() {
    Properties props = new Properties();
    props.setProperty("scenario", "maze");
    props.setProperty("salt", "654321");
    props.setProperty("size", "medium");
    props.setProperty("type", "sidewinder");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertTrue("incorrect scenario", model.getScenario() instanceof MazeScenario);
    MazeScenario maze = (MazeScenario) model.getScenario();
    assertEquals("Incorrect salt", 654321, (long) maze.getScenarioSalt());
    assertEquals("Incorrect board size", MazeSize.MEDIUM, maze.getMazeSize());
    assertEquals("Inncorrect maze type", MazeType.SIDEWINDER, maze.getMazeType());
  }

  @Test
  public void readMaze3Test() {
    Properties props = new Properties();
    props.setProperty("scenario", "maze");
    props.setProperty("salt", "654321");
    props.setProperty("size", "large");
    props.setProperty("type", "recursivebacktracker");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertTrue("incorrect scenario", model.getScenario() instanceof MazeScenario);
    MazeScenario maze = (MazeScenario) model.getScenario();
    assertEquals("Incorrect salt", 654321, (long) maze.getScenarioSalt());
    assertEquals("Incorrect board size", MazeSize.LARGE, maze.getMazeSize());
    assertEquals("Inncorrect maze type", MazeType.RECURSIVEBACKTRACKER, maze.getMazeType());
  }

  @Test
  public void readMaze4Test() {
    Properties props = new Properties();
    props.setProperty("scenario", "maze");
    props.setProperty("salt", "654321");
    props.setProperty("size", "large");
    props.setProperty("type", "braided");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertTrue("incorrect scenario", model.getScenario() instanceof MazeScenario);
    MazeScenario maze = (MazeScenario) model.getScenario();
    assertEquals("Incorrect salt", 654321, (long) maze.getScenarioSalt());
    assertEquals("Incorrect board size", MazeSize.LARGE, maze.getMazeSize());
    assertEquals("Inncorrect maze type", MazeType.BRAIDED, maze.getMazeType());
  }

  @Test
  public void readLimitTest() {
    Properties props = new Properties();
    props.setProperty("steps", "100");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertTrue("Step limit not set", model.getLimitRuntime().get());
    assertEquals("Step limit incorrect", 100, model.getMaxStepCount().get());
  }

  @Test
  public void readNoLimitTest() {
    Properties props = new Properties();
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertFalse("Step limit set", model.getLimitRuntime().get());
  }

  @Test
  public void readOutputImageTest() {
    Properties props = new Properties();
    props.setProperty("output", "png");
    props.setProperty("outputDir", "test");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertEquals("Wrong output type", OutputFormat.PNG, model.getOutputFormat());
    assertEquals("Wrong output Directory", "test", model.getOutputDirectory().toString());
  }

  @Test
  public void readNoOutputTest() {
    Properties props = new Properties();
    props.setProperty("output", "none");
    props.setProperty("outputDir", "test");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertEquals("Wrong output type", OutputFormat.NONE, model.getOutputFormat());
    assertEquals("Wrong output Directory", "test", model.getOutputDirectory().toString());
  }

  @Test
  public void readOutputVideoTest() {
    Properties props = new Properties();
    props.setProperty("output", "mp4");
    props.setProperty("outputDir", "test");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertEquals("Wrong output type", OutputFormat.MP4, model.getOutputFormat());
    assertEquals("Wrong output Directory", "test", model.getOutputDirectory().toString());
  }

  @Test
  public void agent1Test() {
    Properties props = new Properties();
    props.setProperty("agents", "1");
    props.setProperty("agent1URL", "http://www.test.com/");
    props.setProperty("agent1Lang", "XML");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertEquals("Wrong number of agents configured", 1, model.getAgentList().size());
    Agent agent1 = model.getAgentList().get(0);
    assertEquals("Agent 1 wrong URL", "http://www.test.com/", agent1.getTarget());
    assertTrue("Agent 1 not using XML", agent1.isEnableXML());
  }

  @Test
  public void agent2Test() {
    Properties props = new Properties();
    props.setProperty("agents", "2");
    props.setProperty("agent1URL", "http://www.test.com/");
    props.setProperty("agent1Lang", "JSON");
    props.setProperty("agent2URL", "http://www.test2.com/");
    props.setProperty("agent2Lang", "XML");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertEquals("Wrong number of agents configured", 2, model.getAgentList().size());
    Agent agent1 = model.getAgentList().get(0);
    Agent agent2 = model.getAgentList().get(1);
    assertEquals("Agent 1 wrong URL", "http://www.test.com/", agent1.getTarget());
    assertFalse("Agent 1 not using JSON", agent1.isEnableXML());
    assertEquals("Agent 2 wrong URL", "http://www.test2.com/", agent2.getTarget());
    assertTrue("Agent 2 not using XML", agent2.isEnableXML());
  }

  @Test
  public void authKeyTest() {
    Properties props = new Properties();
    props.setProperty("agents", "1");
    props.setProperty("agent1URL", "http://www.test.com/");
    props.setProperty("agent1Lang", "JSON");
    props.setProperty("agent1HeaderName", "key");
    props.setProperty("agent1HeaderValue", "value");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertEquals("Wrong number of agents configured", 1, model.getAgentList().size());
    Agent agent1 = model.getAgentList().get(0);
    assertTrue("Header auth not enabled", agent1.isApiKey());
    assertEquals("Header key incorrect", "key", agent1.getApiKeyHeader());
    assertEquals("Header value incorrect", "value", agent1.getApiKeyValue());
  }

  @Test
  public void basicAuthTest() {
    Properties props = new Properties();
    props.setProperty("agents", "1");
    props.setProperty("agent1URL", "http://www.test.com/");
    props.setProperty("agent1Lang", "JSON");
    props.setProperty("agent1Username", "key");
    props.setProperty("agent1Password", "value");
    RuntimeModel model = parser.parseConfiguration(new RuntimeModel(), props);
    assertEquals("Wrong number of agents configured", 1, model.getAgentList().size());
    Agent agent1 = model.getAgentList().get(0);
    assertTrue("Basic auth not enabled", agent1.isBasicAuth());
    assertEquals("Basic auth key incorrect", "key", agent1.getBasicAuthUsername());
    assertEquals("Basic auth value incorrect", "value", agent1.getBasicAuthPassword());
  }
}
