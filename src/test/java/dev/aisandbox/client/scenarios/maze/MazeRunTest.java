package dev.aisandbox.client.scenarios.maze;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import dev.aisandbox.client.AISandboxClient;
import dev.aisandbox.client.RuntimeModel;
import dev.aisandbox.client.cli.CLIParser;
import dev.aisandbox.client.fx.FakeGameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.NoOutput;
import dev.aisandbox.client.scenarios.maze.agent.MazeTestAgent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MazeRunTest {

    @Test
    public void runFullMazeTest() {
        System.out.println("Running simulation manualy");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AISandboxClient.class);
        builder.headless(true);
        ConfigurableApplicationContext context = builder.run();
        // generate the default model and update with CLI (and XML) options
        CLIParser cli = context.getBean(CLIParser.class);
        RuntimeModel model = cli.parseCommandLine(context.getBean(RuntimeModel.class), new String[]{
            "-config","src/test/resources/config/maze.properties"
        });
        // test config has been loaded
        assertNotNull("Scenario doesn't exist",model.getScenario());
        assertTrue("Incorrect Scenario",model.getScenario() instanceof MazeScenario);
        // manualy attach test agent
        model.getAgentList().add(new MazeTestAgent());
        assertTrue("Model not ready",model.getValid().get());
        // manualy run the model
        FrameOutput out = new NoOutput();
        model.getScenario().startSimulation(model.getAgentList(), new FakeGameRunController(), out);
    }

}