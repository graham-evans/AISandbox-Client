package dev.aisandbox.client.scenarios.zebra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.search.limits.SolutionCounter;
import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Component
public class ZebraPuzzleGenerator {

    private static final Logger LOG = Logger.getLogger(ZebraPuzzleGenerator.class.getName());

    @Autowired
    private List<Characteristic> characteristicList;

    public ZebraPuzzle generatePuzzle() {
        Random rand = new Random();
        LOG.info("Generating new puzzle");
        // generate puzzle with a solution
        ZebraPuzzle puzzle = new ZebraPuzzle(rand);
        Model model = new Model();

        // generate all possible clues
        List<Clue> allClues = new ArrayList<>();
        allClues.addAll(livingClues(puzzle));
        allClues.addAll(livingNegativeClues(puzzle));
        // randomize clues
        Collections.shuffle(allClues, rand);
        // work out how many clues are needed for a unique solution

        // copy all clues
        puzzle.getClues().addAll(allClues);
        return puzzle;
    }

    private boolean checkUnique(List<Clue> cluelist) {
        return true;
    }

    /**
     * Generate clues of the form "The person living at number # xxxxxxx"
     *
     * @return list of clues that can be used
     */
    private List<Clue> livingClues(ZebraPuzzle puzzle) {
        LOG.info("Adding living clues");
        List<Clue> clues = new ArrayList<>();
        for (int i = 0; i < ZebraPuzzle.PUZZLE_HEIGHT; i++) {
            LOG.info("Adding living clues for " + characteristicList.get(i).getClass().getName());
            for (int j = 0; j < ZebraPuzzle.PUZZLE_WIDTH; j++) {
                Clue c = new Clue();
                c.setClueString("The person living at number " + (j + 1) + " " + characteristicList.get(i).getDescription(puzzle.getSolution()[i][j]) + ".");
                clues.add(c);
            }
        }
        return clues;
    }

    /**
     * Generate clues of the form "The person living at number # doesn't xxxxxxx"
     *
     * @return list of clues that can be used
     */
    private List<Clue> livingNegativeClues(ZebraPuzzle puzzle) {
        LOG.info("Adding nagative living clues");
        List<Clue> clues = new ArrayList<>();
        for (int i = 0; i < ZebraPuzzle.PUZZLE_HEIGHT; i++) {
            LOG.info("Adding negative living clues for " + characteristicList.get(i).getClass().getName());
            for (int j = 0; j < ZebraPuzzle.PUZZLE_WIDTH; j++) {
                for (int k = 0; k < ZebraPuzzle.PUZZLE_WIDTH; k++) {
                    if (k != puzzle.getSolution()[i][j]) {
                        Clue c = new Clue();
                        c.setClueString("The person living at number " + (j + 1) + " " + characteristicList.get(i).getNegativeDescription(k) + ".");
                        clues.add(c);
                    }
                }
            }
        }
        return clues;
    }

}
