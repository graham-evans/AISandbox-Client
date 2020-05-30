package dev.aisandbox.client.scenarios.twisty;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.agent.AgentParserException;
import dev.aisandbox.client.agent.AgentResetException;
import dev.aisandbox.client.output.charts.BaseChart;
import dev.aisandbox.client.output.charts.FrequencyMassDistributionGraph;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.RuntimeResponse;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.SimulationException;
import dev.aisandbox.client.scenarios.maze.MazeRunner;
import dev.aisandbox.client.scenarios.twisty.api.TwistyRequest;
import dev.aisandbox.client.scenarios.twisty.api.TwistyRequestHistory;
import dev.aisandbox.client.scenarios.twisty.api.TwistyResponse;
import dev.aisandbox.client.scenarios.twisty.puzzles.CubePuzzle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwistyRuntime implements ScenarioRuntime {

  // agents
  private Agent agent = null;
  // puzzle elements
  @Setter Random random = new Random();
  @Setter TwistyPuzzle puzzle;
  @Setter boolean startSolved;
  private static final int SCRAMBLE_MOVES = 200;
  String savedState;
  List<String> actions = new ArrayList<>();
  int moves;
  TwistyRequestHistory history = null;
  // UI elements
  private BufferedImage logo;
  private List<String> moveHistory = new ArrayList<>();
  private static final int HISTORY_WIDTH = 9;
  private static final int HISTORY_HEIGHT = 5;
  private static final int MOVE_HISTORY_MAX = HISTORY_WIDTH * HISTORY_HEIGHT;
  private FrequencyMassDistributionGraph frequencyGraph = new FrequencyMassDistributionGraph();

  public TwistyRuntime() {
    try {
      // load logo
      logo =
          ImageIO.read(MazeRunner.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
    } catch (IOException e) {
      log.error("Error loading logo", e);
      logo = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
    }
    // setup graph
    frequencyGraph.setTitle("# Moves to solve");
    frequencyGraph.setXaxisHeader("# Moves");
    frequencyGraph.setYaxisHeader("Frequency");
    frequencyGraph.setGraphWidth(HISTORY_WIDTH * CubePuzzle.MOVE_ICON_WIDTH);
    frequencyGraph.setGraphHeight(350);
  }

  @Override
  public void setAgents(List<Agent> agents) {
    agent = agents.get(0);
    if (agents.size() > 1) {
      log.error("Passed multiple agents when only one will be used.");
    }
  }

  @Override
  public void initialise() {
    if (!startSolved) {
      scramblePuzzle();
    }
    savedState = puzzle.getState();
    moves = 0;
  }

  @Override
  public RuntimeResponse advance() throws AgentException, SimulationException {
    ProfileStep profileStep = new ProfileStep();
    List<BufferedImage> frames = new ArrayList<>();
    if (actions.isEmpty()) {
      try {
        // get next set of actions
        TwistyRequest request = new TwistyRequest();
        request.setPuzzleType(puzzle.getPuzzleName());
        request.setMoves(puzzle.getMoveList());
        request.setState(puzzle.getState());
        request.setHistory(history);
        log.info("Requesting new actions from state {}", puzzle.getState());
        TwistyResponse response = agent.postRequest(request, TwistyResponse.class);
        actions.addAll(Arrays.asList(response.getMove().trim().split(" ")));
        log.info("Action list now '{}'", actions);
        profileStep.addStep("Network");
      } catch (AgentResetException r) {
        log.info("Received reset puzzle from user");
        profileStep.addStep("Network");
        // clear history
        moves = 0;
        moveHistory.clear();
        // reset puzzle
        puzzle.resetPuzzle(savedState);
        // reset moves
        profileStep.addStep("Puzzle Setup");
      }
    }
    // perform actions
    if (!actions.isEmpty()) {
      String action = actions.remove(0);
      try {
        history = new TwistyRequestHistory();
        history.setStartState(puzzle.getState());
        history.setMoves(action);
        log.info("Applying move '{}'", action);
        moves += puzzle.applyMove(action);
        moveHistory.add(action);
        while (moveHistory.size() > MOVE_HISTORY_MAX) {
          moveHistory.remove(0);
        }
        history.setEndState(puzzle.getState());
        history.setSuccess(puzzle.isSolved());
        log.info("State now {}", puzzle.getState());
        profileStep.addStep("Simulation");
      } catch (NotExistentMoveException e) {
        log.warn("Client used non existent move '{}'", action);
        throw new AgentParserException("Move doesn't exist '" + action + "'");
      }
    }
    // check if the last move solved the puzzle - and we're looking for a solve
    if (puzzle.isSolved() && !startSolved) {
      // register solve
      frequencyGraph.addValue(moves);
      frequencyGraph.resetGraph();
      // draw the solved image
      frames.add(renderPuzzle());
      profileStep.addStep("Graphics");
      // reset the puzzle
      log.info("Puzzle solved, resetting");
      // clear history
      moves = 0;
      moveHistory.clear();
      // scramble
      scramblePuzzle();
      // this is the new saved puzzle
      savedState = puzzle.getState();
      // draw new state
      frames.add(renderPuzzle());
      profileStep.addStep("Puzzle Setup");
    } else {
      // draw the puzzle as normal
      frames.add(renderPuzzle());
      profileStep.addStep("Graphics");
    }
    return new RuntimeResponse(profileStep, frames);
  }

  @Override
  public String getStatistics() {
    return null;
  }

  private void scramblePuzzle() {
    for (int i = 0; i < SCRAMBLE_MOVES; i++) {
      try {
        String randomMove = puzzle.getMoveList().get(random.nextInt(puzzle.getMoveList().size()));
        puzzle.applyMove(randomMove);
      } catch (NotExistentMoveException e) {
        log.error("Non existent move when trying a move defined in the class", e);
      }
    }
  }

  private BufferedImage renderPuzzle() {
    BufferedImage image = puzzle.getStateImage();
    Graphics2D g = image.createGraphics();
    g.setFont(new Font("Helvetica", Font.PLAIN, 18));
    g.setRenderingHint(
        RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    // add logo
    g.drawImage(logo, 100, 50, null);
    // draw history
    for (int i = 0; i < moveHistory.size(); i++) {
      BufferedImage moveImage = puzzle.getMoveImage(moveHistory.get(i));
      if (moveImage != null) {
        g.drawImage(
            moveImage,
            (i % HISTORY_WIDTH) * CubePuzzle.MOVE_ICON_WIDTH + 1350,
            (i / HISTORY_WIDTH) * CubePuzzle.MOVE_ICON_HEIGHT + 550,
            null);
      }
    }
    g.setColor(Color.BLACK);
    if (frequencyGraph.getImage() != null) {
      g.drawImage(frequencyGraph.getImage(), 1350, 100, null);
      g.drawString(
          "Mean : " + BaseChart.toSignificantDigitString(frequencyGraph.getMean(), 5), 1400, 480);
      g.drawString(
          "\u03C3\u00B2 : "
              + BaseChart.toSignificantDigitString(frequencyGraph.getStandardDeviation(), 5),
          1400,
          480 + 24);
    }
    return image;
  }
}
