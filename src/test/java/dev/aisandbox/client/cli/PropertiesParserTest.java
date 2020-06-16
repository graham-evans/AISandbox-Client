package dev.aisandbox.client.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dev.aisandbox.client.ApplicationModel;
import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.output.OutputFormat;
import dev.aisandbox.client.parameters.LongParameter;
import dev.aisandbox.client.parameters.OptionParameter;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.maze.MazeScenario;
import dev.aisandbox.client.scenarios.mine.MineHunterScenario;
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

  private ScenarioParameter findParameter(String name, ScenarioParameter[] params) {
    ScenarioParameter result = null;
    for (ScenarioParameter s : params) {
      if (name.equals(s.getParameterKey())) {
        result = s;
      }
    }
    return result;
  }

  @Test
  public void readMine1Test() {
    Properties props = new Properties();
    props.setProperty("scenario", "mine");
    props.setProperty("mine.salt", "123456");
    props.setProperty("mine.size", "1");
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
    assertTrue("incorrect scenario", model.getScenario() instanceof MineHunterScenario);
    MineHunterScenario mine = (MineHunterScenario) model.getScenario();
    LongParameter salt =
        (LongParameter) findParameter("mine.salt", model.getScenario().getParameterArray());
    assertEquals("Incorrect salt", 123456, (long) salt.getValue());
    OptionParameter option =
        (OptionParameter) findParameter("mine.size", model.getScenario().getParameterArray());
    assertEquals("Incorrect board size", 1, option.getOptionIndex());
  }

  @Test
  public void readMaze1Test() {
    Properties props = new Properties();
    props.setProperty("scenario", "maze");
    props.setProperty("maze.salt", "654321");
    props.setProperty("maze.size", "1");
    props.setProperty("maze.type", "1");
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
    assertTrue("incorrect scenario", model.getScenario() instanceof MazeScenario);
    MazeScenario maze = (MazeScenario) model.getScenario();
    LongParameter salt =
        (LongParameter) findParameter("maze.salt", model.getScenario().getParameterArray());
    assertEquals("Incorrect salt", 654321, (long) salt.getValue());
    OptionParameter option =
        (OptionParameter) findParameter("maze.size", model.getScenario().getParameterArray());
    assertEquals("Incorrect board size", 1, option.getOptionIndex());
    OptionParameter option2 =
        (OptionParameter) findParameter("maze.type", model.getScenario().getParameterArray());
    assertEquals("Incorrect board type", 1, option2.getOptionIndex());
  }

  @Test
  public void readLimitTest() {
    Properties props = new Properties();
    props.setProperty("steps", "100");
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
    assertTrue("Step limit not set", model.getLimitRuntime().get());
    assertEquals("Step limit incorrect", 100, model.getMaxStepCount().get());
  }

  @Test
  public void readNoLimitTest() {
    Properties props = new Properties();
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
    assertFalse("Step limit set", model.getLimitRuntime().get());
  }

  @Test
  public void readOutputImageTest() {
    Properties props = new Properties();
    props.setProperty("output", "png");
    props.setProperty("outputDir", "test");
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
    assertEquals("Wrong output type", OutputFormat.PNG, model.getOutputFormat());
    assertEquals("Wrong output Directory", "test", model.getOutputDirectory().toString());
  }

  @Test
  public void readNoOutputTest() {
    Properties props = new Properties();
    props.setProperty("output", "none");
    props.setProperty("outputDir", "test");
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
    assertEquals("Wrong output type", OutputFormat.NONE, model.getOutputFormat());
    assertEquals("Wrong output Directory", "test", model.getOutputDirectory().toString());
  }

  @Test
  public void readOutputVideoTest() {
    Properties props = new Properties();
    props.setProperty("output", "mp4");
    props.setProperty("outputDir", "test");
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
    assertEquals("Wrong output type", OutputFormat.MP4, model.getOutputFormat());
    assertEquals("Wrong output Directory", "test", model.getOutputDirectory().toString());
  }

  @Test
  public void agent1Test() {
    Properties props = new Properties();
    props.setProperty("agents", "1");
    props.setProperty("agent1URL", "http://www.test.com/");
    props.setProperty("agent1Lang", "XML");
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
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
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
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
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
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
    ApplicationModel model = parser.parseConfiguration(new ApplicationModel(), props);
    assertEquals("Wrong number of agents configured", 1, model.getAgentList().size());
    Agent agent1 = model.getAgentList().get(0);
    assertTrue("Basic auth not enabled", agent1.isBasicAuth());
    assertEquals("Basic auth key incorrect", "key", agent1.getBasicAuthUsername());
    assertEquals("Basic auth value incorrect", "value", agent1.getBasicAuthPassword());
  }
}
