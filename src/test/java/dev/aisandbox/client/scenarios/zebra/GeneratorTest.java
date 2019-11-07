package dev.aisandbox.client.scenarios.zebra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeneratorTest {

    private static final Logger LOG = Logger.getLogger(GeneratorTest.class.getName());

    @Autowired
    ZebraPuzzleGenerator gen;

    @Test
    public void generateTest() {
        Random rand = new Random();
        ZebraPuzzle puzzle = gen.generatePuzzle(rand);
        assertNotNull(puzzle);
        assertNotNull(puzzle.getClues());
        LOG.info("Clue list:");
        for (Clue c : puzzle.getClues()) {
            LOG.info(c.getClueString());
        }
        assertTrue(true);
    }
}
