package dev.aisandbox.client.scenarios.zebra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorTest {

    private static final Logger LOG = Logger.getLogger(GeneratorTest.class.getName());

    @Test
    public void generateTest() {
        Random rand = new Random();
        ZebraPuzzle puzzle = new ZebraPuzzle(rand);
        for (Clue c : puzzle.getClues()) {
            LOG.info(c.getClueString());
        }
        assertTrue(true);
    }
}
