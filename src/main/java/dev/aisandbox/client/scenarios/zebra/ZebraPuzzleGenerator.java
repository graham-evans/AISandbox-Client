package dev.aisandbox.client.scenarios.zebra;

import dev.aisandbox.client.scenarios.zebra.characteristics.*;
import lombok.Getter;
import org.chocosolver.solver.ICause;
import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.*;
import org.chocosolver.solver.variables.delta.IDelta;
import org.chocosolver.solver.variables.delta.IIntDeltaMonitor;
import org.chocosolver.solver.variables.events.IEventType;
import org.chocosolver.solver.variables.view.IView;
import org.chocosolver.util.iterators.DisposableRangeIterator;
import org.chocosolver.util.iterators.DisposableValueIterator;
import org.chocosolver.util.iterators.EvtScheduler;
import org.chocosolver.util.objects.setDataStructures.iterable.IntIterableSet;
import org.jcodec.common.DictionaryCompressor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.search.limits.SolutionCounter;

import java.util.*;
import java.util.logging.Logger;

@Component
public class ZebraPuzzleGenerator {

    private static final Logger LOG = Logger.getLogger(ZebraPuzzleGenerator.class.getName());

    @Getter
    private static final List<Characteristic> characteristicList = new ArrayList<>();

    public ZebraPuzzleGenerator() {
        characteristicList.add(new FrontDoorCharacteristic());
        characteristicList.add(new CurtainColourCharacteristic());
        characteristicList.add(new DrivesCharacteristic());
        characteristicList.add(new FlowersCharacteristic());
        characteristicList.add(new NationalityCharacteristic());
        characteristicList.add(new HolidayCharacteristic());
        characteristicList.add(new LikesToDrinkCharacteristic());
        characteristicList.add(new LikesToEatCharacteristic());
        characteristicList.add(new PetCharacteristic());
        characteristicList.add(new SportCharacteristic());
        characteristicList.add(new StarSignCharacteristic());
    }

    public ZebraPuzzle generatePuzzle() {
        Random rand = new Random();
        LOG.info("Generating new puzzle");
        // generate puzzle with a solution
        ZebraPuzzle puzzle = new ZebraPuzzle(characteristicList, rand);
        // generate the model
        Model model = new Model();
        IntVar places[][] = new IntVar[ZebraPuzzle.PUZZLE_HOUSES][ZebraPuzzle.PUZZLE_CHARACTERISTICS];
        for (int i = 0; i < ZebraPuzzle.PUZZLE_CHARACTERISTICS; i++) {
            List<IntVar> row = new ArrayList<>();
            for (int j = 0; j < ZebraPuzzle.PUZZLE_HOUSES; j++) {
                places[j][i] = model.intVar("#" + (j + 1) + "-" + characteristicList.get(i).getClass().getSimpleName(), 0, ZebraPuzzle.PUZZLE_HOUSES - 1);
                row.add(places[j][i]);
            }
            model.allDifferent(row.toArray(new IntVar[]{})).post();
        }
        // generate all possible clues
        List<Clue> allClues = new ArrayList<>();
        allClues.addAll(livingClues(puzzle, model, places));
        allClues.addAll(livingNegativeClues(puzzle, model, places));
        // randomize clues
        Collections.shuffle(allClues, rand);
        // work out how many clues are needed for a unique solution
        int count = 0;
        boolean found = false;
        while (!found) {
            // add clue
            Clue c = allClues.get(count);
            // enable the clue
            c.postClue();
            LOG.info("Posting clue '" + c.getClueString() + "'");
            // add it to the output
            puzzle.getClues().add(c);
            // test there is only 1 solution

            List<Solution> solutions = model.getSolver().findAllSolutions(new SolutionCounter(model, 2));
            if (solutions.size() == 1) {
                LOG.info("Found unique solution");
                found = true;
            } else if (solutions.size() == 0) {
                LOG.severe("No solutions !!!");
                found = true;
            } else {
                LOG.info("Multiple solutions");
                for (Solution s : solutions) {
                    LOG.info(">" + s.toString());
                }
                count++;
                LOG.info("No unique solution, adding another clue");
                model.getSolver().reset();
            }
        }
        return puzzle;
    }

    /**
     * Generate clues of the form "The person living at number # xxxxxxx"
     *
     * @return list of clues that can be used
     */
    private List<Clue> livingClues(ZebraPuzzle puzzle, Model model, IntVar[][] places) {
        LOG.info("Adding living clues");
        List<Clue> clues = new ArrayList<>();
        for (int i = 0; i < ZebraPuzzle.PUZZLE_CHARACTERISTICS; i++) {
            LOG.info("Adding living clues for " + characteristicList.get(i).getClass().getName());
            for (int j = 0; j < ZebraPuzzle.PUZZLE_HOUSES; j++) {
                Clue c = new Clue();
                c.setClueString("The person living at number " + (j + 1) + " " + characteristicList.get(i).getDescription(puzzle.getSolution()[j][i]) + ".");
                c.getConstraintList().add(model.arithm(places[j][i], "=", puzzle.getSolution()[j][i]));
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
    private List<Clue> livingNegativeClues(ZebraPuzzle puzzle, Model model, IntVar[][] places) {
        LOG.info("Adding negative living clues");
        List<Clue> clues = new ArrayList<>();
        for (int i = 0; i < ZebraPuzzle.PUZZLE_CHARACTERISTICS; i++) {
            LOG.info("Adding negative living clues for " + characteristicList.get(i).getClass().getName());
            for (int j = 0; j < ZebraPuzzle.PUZZLE_HOUSES; j++) {
                for (int k = 0; k < ZebraPuzzle.PUZZLE_HOUSES; k++) {
                    if (puzzle.getSolution()[j][i] != k) {
                        Clue c = new Clue();
                        c.setClueString("The person living at number " + (j + 1) + " " + characteristicList.get(i).getNegativeDescription(k) + ".");
                        c.getConstraintList().add(model.arithm(places[j][i], "!=", puzzle.getSolution()[j][k]));
                        clues.add(c);
                    }
                }
            }
        }
        return clues;
    }

}
