package dev.aisandbox.client.scenarios.maze;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import dev.aisandbox.client.ApplicationModel;
import dev.aisandbox.client.SimulationRunThread;
import dev.aisandbox.client.cli.PropertiesParser;
import dev.aisandbox.client.fx.FakeGameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.PNGOutputWriter;
import dev.aisandbox.client.scenarios.maze.agent.MazeTestAgent;
import dev.aisandbox.launcher.AISandboxCLI;
import java.io.File;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AISandboxCLI.class})
@SpringBootTest
@Slf4j
public class MazeRunTest {

  @Autowired PropertiesParser parser;

  @Autowired ApplicationModel model;

  @Autowired ApplicationContext context;

  @Before
  public void resetDir() {
    // check that the directory 'target/test-run/maze1' is deleted
    File fdir = new File("target/run/maze1");
    if (fdir.isDirectory()) {
      log.info("Deleting {}", fdir.getAbsolutePath());
      deleteDirectory(fdir);
    }
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

    System.out.println("Running simulation manually");
    SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxCLI.class);
    builder.headless(true);
    ConfigurableApplicationContext context = builder.run();
    // generate the default model and update with CLI (and XML) options
    parser.parseConfiguration(model, "src/test/resources/config/maze.properties");
    // test config has been loaded
    assertNotNull("Scenario doesn't exist", model.getScenario());
    assertTrue("Incorrect Scenario", model.getScenario() instanceof MazeScenario);
    // manually attach test agent
    model.getAgentList().clear();
    model.getAgentList().add(new MazeTestAgent());
    assertTrue("Model not ready", model.getValid().get());
    // setup a fake UI
    model.initialiseRuntime(new FakeGameRunController(context, model, null));
    // run the model
    SimulationRunThread thread;
    if (model.getLimitRuntime().get()) {
      thread = new SimulationRunThread(model, model.getMaxStepCount().get());
    } else {
      thread = new SimulationRunThread(model);
    }
    thread.start();
    thread.join();
    model.resetRuntime();
    // we should now have a single directory inside maze1
    File dest = new File("target/run/maze1");
    assertTrue("Target directory exists", dest.isDirectory());
    assertEquals("Number of subdirs", 1, dest.listFiles().length);
    File job = dest.listFiles()[0];
    // we should have 10 images in this directory
    assertEquals("Number of images", 10, job.listFiles().length);
  }
}
