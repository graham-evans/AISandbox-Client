package dev.aisandbox.client.scenarios.mine;

import dev.aisandbox.client.AISandboxClient;
import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.cli.CLIParser;
import dev.aisandbox.client.fx.FakeGameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.PNGOutputWriter;
import dev.aisandbox.client.scenarios.mine.agent.MineTestAgent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MineRunTest {
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

    @Test(timeout=10000) // shouldn't take more than 10 seconds
    public void runFullMineTest() throws Exception {
        System.out.println("Running simulation manualy");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxClient.class);
        builder.headless(true);
        ConfigurableApplicationContext context = builder.run();
        // generate the default model and update with CLI (and XML) options
        CLIParser cli = context.getBean(CLIParser.class);
        RuntimeModel model = cli.parseCommandLine(context.getBean(RuntimeModel.class), new String[]{
                "-config","src/test/resources/config/mine.properties"
        });
        // test config has been loaded
        assertNotNull("Scenario doesn't exist",model.getScenario());
        assertTrue("Incorrect Scenario",model.getScenario() instanceof MineHunterScenario);
        // manualy attach test agent
        model.getAgentList().add(new MineTestAgent());
        assertTrue("Model not ready",model.getValid().get());
        // manualy run the model
        FrameOutput out = new PNGOutputWriter();
        File dest = new File("target/test-run/mine1");
        out.open(dest);
        model.getScenario().startSimulation(model.getAgentList(), new FakeGameRunController(), out, 10l);
        model.getScenario().joinSimulation();
        // we should now have a single directory inside maze1
        assertEquals("Number of subdirectories in target",1,dest.listFiles().length);
        File dest2 = dest.listFiles()[0];
        // we should have 10 images in this directory
        assertTrue("Too few images",10<=dest2.listFiles().length);
    }
}
