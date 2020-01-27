package dev.aisandbox.client.scenarios.zebra;

import dev.aisandbox.client.scenarios.zebra.vo.Characteristic;
import dev.aisandbox.client.scenarios.zebra.vo.CharacteristicObject;
import dev.aisandbox.client.scenarios.zebra.vo.Template;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.AccessLevel;
import lombok.Getter;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.search.limits.SolutionCounter;
import org.chocosolver.solver.variables.IntVar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class representing an entire Zebra puzzle.
 *
 * <p>This consists of three seperate parts
 */
public class ZebraPuzzle {

  private static final Logger LOG = LoggerFactory.getLogger(ZebraPuzzle.class.getName());

  private final Random rand;
  private final int characteristicCount;
  private final int houses;

  private final Template template;

  public ZebraPuzzle(Random rand, int characteristicCount, int houses) {
    this.rand = rand;
    this.characteristicCount = characteristicCount;
    this.houses = houses;
    template = CharacteristicGenerator.createTemplate(rand, characteristicCount, houses);
  }

  /*
  The solution is stored in a two dimensional array solution[characteristic][housenumber-1]
  the result is the number of the characteristic that this house has
   */
  private int[][] solution;

  public void generateSolution() {
    solution = new int[characteristicCount][houses];
    // generate a list of houses
    List<Integer> houseNumbers = new ArrayList<>();
    for (int i = 0; i < houses; i++) {
      houseNumbers.add(i);
    }
    // distribute the characteristics
    for (int c = 0; c < characteristicCount; c++) {
      Collections.shuffle(houseNumbers, rand);
      for (int i = 0; i < houses; i++) {
        solution[c][i] = houseNumbers.get(i);
      }
    }
  }

  public CharacteristicObject getCharacteristicObject(int characteristicNumber, int houseNumber) {
    return template
        .getCharacteristics()
        .get(characteristicNumber)
        .getInstances()
        .get(solution[characteristicNumber][houseNumber]);
  }

  @Getter private List<Clue> clueList = new ArrayList<>();

  @Getter(AccessLevel.PROTECTED)
  private List<Clue> rawClueList = new ArrayList<>();

  public void generateClues() {
    // generate the model
    Model model = new Model();
    // populate the model
    IntVar[][] proposedSolution = new IntVar[characteristicCount][houses];
    for (int cnum = 0; cnum < characteristicCount; cnum++) {
      List<IntVar> row = new ArrayList<>();
      Characteristic characteristic = template.getCharacteristics().get(cnum);
      for (int hnum = 0; hnum < houses; hnum++) {
        proposedSolution[cnum][hnum] =
            model.intVar(characteristic.getName() + " @ " + (hnum), 0, houses - 1);
        row.add(proposedSolution[cnum][hnum]);
      }
      model.allDifferent(row.toArray(new IntVar[] {})).post();
    }
    // generate all possible clues
    rawClueList.addAll(livingClues(model, proposedSolution));
    rawClueList.addAll(livingNegativeClues(model, proposedSolution));
    // randomize clues
    Collections.shuffle(rawClueList, rand);
    // work out how many clues are needed for a unique solution
    int count = 0;
    boolean found = false;
    while (!found) {
      // add clue
      Clue c = rawClueList.get(count);
      // enable the clue
      c.postClue();
      LOG.info("Posting clue '{}''", c.getClueString());
      // add it to the output
      clueList.add(c);
      // test there is only 1 solution
      List<Solution> solutions = model.getSolver().findAllSolutions(new SolutionCounter(model, 2));
      if (solutions.size() == 1) {
        LOG.info("Found unique solution");
        found = true;
      } else if (solutions.isEmpty()) {
        LOG.error("No solutions !!!");
        found = true;
      } else {
        //                LOG.info("Multiple solutions");
        //                for (Solution s : solutions) {
        //                    LOG.log(Level.INFO, "> {0}", new Object[]{s});
        //                }
        count++;
        LOG.info("No unique solution, adding another clue");
        model.getSolver().reset();
      }
    }
  }

  /**
   * Generate clues of the form "The person living at number # xxxxxxx"
   *
   * @return list of clues that can be used
   */
  private List<Clue> livingClues(Model model, IntVar[][] proposedSolution) {
    LOG.info("Adding living clues");
    List<Clue> clues = new ArrayList<>();
    for (int characteristic = 0; characteristic < characteristicCount; characteristic++) {
      LOG.info(
          "Adding living clues for {}",
          template.getCharacteristics().get(characteristic).getName());
      for (int houseNumber = 0; houseNumber < houses; houseNumber++) {
        Clue c = new Clue();
        c.setClueString(
            "The person living at number "
                + (houseNumber + 1)
                + " "
                + getCharacteristicObject(characteristic, houseNumber).getPositiveDescription()
                + ".");
        c.getConstraintList()
            .add(
                model.arithm(
                    proposedSolution[characteristic][houseNumber],
                    "=",
                    solution[characteristic][houseNumber]));
        clues.add(c);
      }
    }
    return clues;
  }

  /**
   * Generate clues of the form "The person living at number # !xxxxxxx"
   *
   * @return list of clues that can be used
   */
  private List<Clue> livingNegativeClues(Model model, IntVar[][] proposedSolution) {
    LOG.info("Adding living negative clues");
    List<Clue> clues = new ArrayList<>();
    for (int characteristic = 0; characteristic < characteristicCount; characteristic++) {
      LOG.info(
          "Adding living negative clues for {}",
          template.getCharacteristics().get(characteristic).getName());
      for (int houseNumber = 0; houseNumber < houses; houseNumber++) {
        for (int characteristicObjectNumber = 0;
            characteristicObjectNumber < houses;
            characteristicObjectNumber++) {
          if (solution[characteristic][houseNumber] != characteristicObjectNumber) {
            Clue c = new Clue();
            c.setClueString(
                "The person living at number "
                    + (houseNumber + 1)
                    + " "
                    + getCharacteristicObject(characteristic, characteristicObjectNumber)
                        .getNegativeDescription()
                    + ".");
            c.getConstraintList()
                .add(
                    model.arithm(
                        proposedSolution[characteristic][houseNumber],
                        "!=",
                        characteristicObjectNumber));
            clues.add(c);
          }
        }
      }
    }
    return clues;
  }

  /**
   * create a new puzzle, using the supplied random number generator
   *
   * @param rand
   */
  /*   public ZebraPuzzle(List<Characteristic> characteristics, Random rand) {
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
              LOG.log(Level.INFO, "Puzzle characteristic {0} = {1}", new Object[]{j, row});
              // copy contents to solution
              for (int i = 0; i < PUZZLE_HOUSES; i++) {
                  solution[i][j] = row.get(i);
              }
          }
      }
  */

}
