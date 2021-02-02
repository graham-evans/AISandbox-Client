package dev.aisandbox.client.scenarios.snake;

import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.sprite.SpriteLoader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.junit.Test;

public class SetupTest {
  @Test
  public void setupTest() throws IOException {
    // fake agents
    List<Agent> agents = new ArrayList<>();
    agents.add(new Agent());
    agents.add(new Agent());
    // load sprites
    BufferedImage[][] sprites = SpriteLoader.loadSpriteGridFromResources("/dev/aisandbox/client/scenarios/snake/tileset1.png",32,32);
    // generate runtime
    SnakeRuntime runtime = new SnakeRuntime(sprites,ArenaType.CLASSIC,TailType.GROWING);
    runtime.setAgents(agents);
    runtime.initialise();
    // get a view of the current board
    Board board = runtime.getBoard();
    File out = new File("target/test-images/snake/board2.png");
    out.getParentFile().mkdirs();
    ImageIO.write(board.getScreen(),"png",out);
    assertTrue(out.isFile());
  }


}
