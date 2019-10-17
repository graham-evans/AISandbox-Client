package dev.aisandbox.client.scenarios.zebra;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class ZebraPuzzle {

    public static final int PUZZLE_WIDTH = 10;
    public static final int PUZZLE_HEIGHT = 2;

    @Autowired
    private List<Characteristic> characteristicList;

    private int[][] solution = new int[PUZZLE_HEIGHT][PUZZLE_WIDTH];

    @Getter
    private List<Clue> clues = new ArrayList<>();

    private static final Logger LOG = Logger.getLogger(ZebraPuzzle.class.getName());

    /**
     * create a new puzzle, using the supplied random number generator
     *
     * @param rand
     */
    public ZebraPuzzle(Random rand) {
        // initialise the solution
        initialiseSolution(rand);
        // generate all possible clues
        clues.addAll(livingClues());
    }

    private void initialiseSolution(Random rand) {
        List<Integer> row = new ArrayList<>();
        for (int i = 0; i < PUZZLE_WIDTH; i++) {
            row.add(i);
        }
        for (int j = 0; j < PUZZLE_HEIGHT; j++) {
            // randomise the row
            Collections.shuffle(row, rand);
            // copy contents to solution
            for (int i = 0; i < PUZZLE_WIDTH; i++) {
                solution[j][i] = row.get(i);
            }
        }
    }

    /**
     * Generate clues of the form "The person living at number # xxxxxxx"
     *
     * @return list of clues that can be used
     */
    private List<Clue> livingClues() {
        List<Clue> clues = new ArrayList<>();
        for (int i = 0; i < PUZZLE_HEIGHT; i++) {
            for (int j = 0; j < PUZZLE_WIDTH; j++) {
                Clue c = new Clue();
                c.setClueString("The person living at number " + (j + 1) + " " + characteristicList.get(i).getDescription(solution[i][j]));
                clues.add(c);
            }
        }
        return clues;
    }


}