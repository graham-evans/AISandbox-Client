package dev.aisandbox.client.scenarios.zebra;

import lombok.Getter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class ZebraPuzzle {

    private static final Logger LOG = Logger.getLogger(ZebraPuzzle.class.getName());

    // the number of characteristics to track
    public static final int PUZZLE_CHARACTERISTICS = 11;
    // the number of houses
    public static final int PUZZLE_HOUSES = 10;

    @Getter
    private int[][] solution = new int[PUZZLE_HOUSES][PUZZLE_CHARACTERISTICS];

    @Getter
    private List<Clue> clues = new ArrayList<>();

    @Getter
    private final List<Characteristic> characteristics;

    /**
     * create a new puzzle, using the supplied random number generator
     *
     * @param rand
     */
    public ZebraPuzzle(List<Characteristic> characteristics, Random rand) {
        this.characteristics = characteristics;
        LOG.info("Creating random puzzle");
        // initialise the solution
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < PUZZLE_HOUSES; i++) {
            row.add(i);
        }
        for (int j = 0; j < PUZZLE_CHARACTERISTICS; j++) {
            // randomise the row
            Collections.shuffle(row, rand);
            LOG.info("Puzzle characteristic " + j + " = " + row.toString());
            // copy contents to solution
            for (int i = 0; i < PUZZLE_HOUSES; i++) {
                solution[i][j] = row.get(i);
            }
        }
    }


}