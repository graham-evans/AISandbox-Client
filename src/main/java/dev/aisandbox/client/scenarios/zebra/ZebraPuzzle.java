package dev.aisandbox.client.scenarios.zebra;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class ZebraPuzzle {

    private static final Logger LOG = Logger.getLogger(ZebraPuzzle.class.getName());

    public static final int PUZZLE_WIDTH = 10;
    public static final int PUZZLE_HEIGHT = 2;

    @Getter
    private int[][] solution = new int[PUZZLE_HEIGHT][PUZZLE_WIDTH];

    @Getter
    private List<Clue> clues = new ArrayList<>();

    /**
     * create a new puzzle, using the supplied random number generator
     *
     * @param rand
     */
    public ZebraPuzzle(Random rand) {
        LOG.info("Creating random puzzle");
        // initialise the solution
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < PUZZLE_WIDTH; i++) {
            row.add(i);
        }
        for (int j = 0; j < PUZZLE_HEIGHT; j++) {
            // randomise the row
            Collections.shuffle(row, rand);
            LOG.info("Puzzle row " + j + " = " + row.toString());
            // copy contents to solution
            for (int i = 0; i < PUZZLE_WIDTH; i++) {
                solution[j][i] = row.get(i);
            }
        }
    }


}