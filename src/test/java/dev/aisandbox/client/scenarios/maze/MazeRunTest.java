package dev.aisandbox.client.scenarios.maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import dev.aisandbox.client.ApplicationModel;
import dev.aisandbox.client.SimulationRunThread;
import dev.aisandbox.client.cli.PropertiesParser;
import dev.aisandbox.client.fx.FakeGameRunController;
import dev.aisandbox.client.scenarios.maze.agent.MazeTestAgent;
import dev.aisandbox.launcher.AISandboxCLI;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AISandboxCLI.class})
@SpringBootTest
@Slf4j
public class MazeRunTest {

  @Autowired PropertiesParser parser;
  @Autowired ApplicationModel model;

  @Before
  public void resetDir() {
    // check that the directory 'target/test-run/maze1' is deleted
    File fdir = new File("target/run/maze1");
    if (fdir.isDirectory()) {
      log.info("Deleting {}", fdir.getAbsolutePath());
      deleteDirectory(fdir);
    }
    fdir.mkdirs();
  }

  private boolean deleteDirectory(File directoryToBeDeleted) {
    File[] allContents = directoryToBeDeleted.listFiles();
    if (allContents != null) {
      for (File file : allContents) {
        deleteDirectory(file);
      }
    }
    return directoryToBeDeleted.delete();
  }

  @Test(timeout = 100000)
  public void runFullMazeTest() throws Exception {
    log.info("Running simulation manually");
    assertNotNull("Null parser", parser);
    assertNotNull("Null application model", model);
    // generate the default model and update with CLI (and XML) options
    parser.parseConfiguration(model, "src/test/resources/config/maze.properties");
    // test config has been loaded
    assertNotNull("Scenario doesn't exist", model.getScenario());
    assertTrue("Incorrect Scenario", model.getScenario() instanceof MazeScenario);
    MazeScenario ms = (MazeScenario) model.getScenario();
    assertEquals("Maze type wrong", MazeType.SIDEWINDER, ms.getMazeType().getValue());
    assertEquals("Maze size wrong", MazeSize.MEDIUM, ms.getMazeSize().getValue());
    assertTrue("Not limiting runtime", model.getLimitRuntime().get());
    assertEquals("Not limiting runtime to 10 steps", 10, model.getMaxStepCount().get());
    // manually attach test agent
    model.getAgentList().clear();
    model.getAgentList().add(new MazeTestAgent());
    assertTrue("Model not ready", model.getValid().get());
    // setup a fake UI
    model.initialiseRuntime(new FakeGameRunController(model, null));
    // run the model
    SimulationRunThread thread;
    thread = new SimulationRunThread(model, model.getMaxStepCount().get());

    thread.start();
    thread.join();
    model.resetRuntime();
    // we should now have a single directory inside maze1
    log.info("Testing output");
    File dest = new File("target/run/maze1");
    log.info("Checking directory {}", dest.getAbsolutePath());
    assertTrue("Target directory does not exist", dest.isDirectory());
    assertEquals("Number of subdirs", 1, dest.listFiles().length);
    File job = dest.listFiles()[0];
    log.info("Looking at directory {} for images", job.getAbsolutePath());
    // we should have 10 images in this directory
    assertEquals("Number of images", 10, job.listFiles().length);
  }
}
