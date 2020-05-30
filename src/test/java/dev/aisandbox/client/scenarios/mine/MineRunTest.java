package dev.aisandbox.client.scenarios.mine;

import dev.aisandbox.client.ApplicationModel;
import dev.aisandbox.client.cli.PropertiesParser;
import dev.aisandbox.launcher.AISandboxCLI;
import java.io.File;
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
public class MineRunTest {

  @Autowired PropertiesParser parser;

  @Autowired ApplicationModel model;

  @Before
  public void resetDir() {
    // check that the directory 'target/test-run/mine1' is deleted
    File fdir = new File("target/test-run/mine1");
    if (fdir.isDirectory()) {
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

  @Test(timeout = 100000) // shouldn't take more than 100 seconds
  public void runFullMineTest() throws Exception {
    //    System.out.println("Running simulation manualy");
    //    SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxCLI.class);
    //    builder.headless(true);
    //    ConfigurableApplicationContext context = builder.run();
    //    // generate the default model and update with CLI (and XML) options
    //    parser.parseConfiguration(model, "src/test/resources/config/mine.properties");
    //    // test config has been loaded
    //    assertNotNull("Scenario doesn't exist", model.getScenario());
    //    assertTrue("Incorrect Scenario", model.getScenario() instanceof MineHunterScenario);
    //    // manualy attach test agent
    //    model.getAgentList().clear();
    //    model.getAgentList().add(new MineTestAgent());
    //    assertTrue("Model not ready", model.getValid().get());
    //    // manualy run the model
    //    FrameOutput out = new PNGOutputWriter();
    //    File dest = new File("target/test-run/mine1");
    //    out.open(dest);
    //    model
    //        .getScenario()
    //        .startSimulation(model.getAgentList(), new FakeGameRunController(), out, 10l);
    //    model.getScenario().joinSimulation();
    //    // we should now have a single directory inside maze1
    //    assertEquals("Number of subdirectories in target", 1, dest.listFiles().length);
    //    File dest2 = dest.listFiles()[0];
    //    // we should have 10 images in this directory
    //    assertTrue("Too few images", 10 <= dest2.listFiles().length);
  }
}
