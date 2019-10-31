package dev.aisandbox.client.scenarios.maze;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MazeRendererTest {

    @Autowired
    MazeRenderer renderer;

    @Test
    public void testRender() {
        Maze m = new Maze(10,10);
        assertNotNull("Renderer not initialising",renderer);
        assertNotNull("Cell list not populated",m.getCellList());

        BufferedImage image = renderer.renderMaze(m);

        assertEquals("Image width",250,image.getWidth());
        assertEquals("Image height",250,image.getHeight());
    }

}
