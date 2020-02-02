package dev.aisandbox.client.sprite;

import static org.junit.Assert.assertEquals;

import dev.aisandbox.launcher.AISandboxCLI;
import java.awt.image.BufferedImage;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AISandboxCLI.class})
@SpringBootTest
public class SpriteLoaderTest {

  @Autowired private ApplicationContext appContext;

  @Autowired private SpriteLoader loader;

  @Test
  public void loadMazeTest() {
    List<BufferedImage> images =
        loader.loadSprites("/dev/aisandbox/client/scenarios/maze/bridge.png", 25, 25);
    assertEquals("Wrong number of sprites", 21, images.size());
    for (BufferedImage i : images) {
      assertEquals("Image wrong width", 25, i.getWidth());
      assertEquals("Image wrong Height", 25, i.getHeight());
    }
  }
}
