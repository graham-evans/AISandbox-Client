package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.output.charts.ForgetfulLineGraph;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.RuntimeResponse;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.SimulationException;
import dev.aisandbox.client.scenarios.maze.api.History;
import dev.aisandbox.client.scenarios.maze.api.MazeRequest;
import dev.aisandbox.client.scenarios.maze.api.MazeResponse;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MazeRuntime implements ScenarioRuntime {

  // agents
  private Agent agent = null;
  // simulation elements
  @Setter Random random = new Random();
  private static final double REWARD_STEP = -1.0;
  private static final double REWARD_HIT_WALL = -1000.0;
  private static final double REWARD_GOAL = +1000.0;
  private Maze maze;
  private BufferedImage background;
  @Setter int mazeSize;
  @Setter int mazeType;
  History lastMove = null;
  Cell currentCell;
  long stepCount = 0;
  long stepToFinish = 0;
  Long fastestSolve = null;
  // UI
  private ForgetfulLineGraph graph;
  private BufferedImage graphCache;
  private static final int GRAPH_WIDTH = 600;
  private static final int GRAPH_HEIGHT = 250;
  private final MazeRenderer mazeRenderer;
  BufferedImage logo;
  Font myFont = new Font("Sans-Serif", Font.PLAIN, 28);

  public MazeRuntime(MazeRenderer mazeRenderer) {
    this.mazeRenderer = mazeRenderer;
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
    log.info("Generating maze");
    switch (mazeSize) {
      case 1:
        maze = new Maze(20, 15);
        maze.setZoomLevel(2);
        break;
      case 2:
        maze = new Maze(40, 30);
        maze.setZoomLevel(1);
        break;
      default: // size=0
        maze = new Maze(8, 6);
        maze.setZoomLevel(5);
    }
    switch (mazeType) {
      case 0:
        MazeUtilities.applyBinaryTree(random, maze);
        break;
      case 1:
        MazeUtilities.applySidewinder(random, maze);
        break;
      case 2:
        MazeUtilities.applyRecursiveBacktracker(random, maze);
        break;
      case 3:
        MazeUtilities.applyRecursiveBacktracker(random, maze);
        MazeUtilities.removeDeadEnds(random, maze);
        break;
      default:
        log.error("Unknown maze type chosen - using binary tree");
        MazeUtilities.applyBinaryTree(random, maze);
    }
    MazeUtilities.findFurthestPoints(maze);
    // render base map
    background = mazeRenderer.renderMaze(maze);
    // place player at start
    currentCell = maze.getStartCell();

    // load graphics
    try {
      logo =
          ImageIO.read(MazeRuntime.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
    } catch (IOException e) {
      log.error("Error loading logo", e);
      logo = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    }
    Font myFont = new Font("Sans-Serif", Font.PLAIN, 28);
    graph = new ForgetfulLineGraph(GRAPH_WIDTH, GRAPH_HEIGHT);
    graph.setTitle("Steps to solve");
    graph.setXaxisHeader("Solution");
    graphCache = graph.getImage();
  }

  @Override
  public RuntimeResponse advance() throws AgentException, SimulationException {
    // keep timings
    ProfileStep profileStep = new ProfileStep();
    // work out postRequest
    MazeRequest request = new MazeRequest();
    // populate the config
    request.setConfig(maze.getConfig());
    if (lastMove != null) {
      request.setHistory(lastMove);
    }
    request.setCurrentPosition(currentCell.getPosition());
    // send and get response

    stepCount++;
    stepToFinish++;
    MazeResponse response = agent.postRequest(request, MazeResponse.class);
    log.info("Recieved response from server - {}", response);
    profileStep.addStep("Network");
    lastMove = new History();
    lastMove.setLastPosition(currentCell.getPosition());
    lastMove.setAction(response.getMove());
    // move the agent
    Direction direction = Direction.valueOf(response.getMove().toUpperCase());
    if (currentCell.getPaths().contains(direction)) {
      // move to the new cell
      currentCell = currentCell.getNeighbours().get(direction);
      lastMove.setReward(REWARD_STEP);
    } else {
      // hit wall
      lastMove.setReward(REWARD_HIT_WALL);
    }
    // special case - have we found the end?
    if (currentCell.equals(maze.getEndCell())) {
      lastMove.setReward(REWARD_GOAL);
      currentCell = maze.getStartCell();
      graph.addValue((double) stepToFinish);
      graphCache = graph.getImage();
      if ((fastestSolve == null) || (fastestSolve > stepToFinish)) {
        fastestSolve = stepToFinish;
      }
      stepToFinish = 0;
    }
    log.info("Moved to {}", currentCell);
    lastMove.setNewPosition(currentCell.getPosition());
    profileStep.addStep("Simulation");
    // redraw the map
    BufferedImage image = OutputTools.getWhiteScreen();
    Graphics2D g = image.createGraphics();
    g.setFont(myFont);
    // maze
    g.drawImage(background, 100, 200, 1000, 750, null);
    // player
    g.setColor(Color.yellow);
    g.fillOval(
        currentCell.getPositionX() * MazeRenderer.SCALE * maze.getZoomLevel() + 100,
        200 + currentCell.getPositionY() * MazeRenderer.SCALE * maze.getZoomLevel(),
        MazeRenderer.SCALE * maze.getZoomLevel(),
        MazeRenderer.SCALE * maze.getZoomLevel());
    // logo
    g.drawImage(logo, 100, 50, null);
    // state
    g.setColor(Color.BLACK);
    g.drawString("Steps : " + stepToFinish, 1200, 500);
    g.drawString("Total Steps : " + stepCount, 1200, 500 + 30);
    g.drawString("Fastest Solve: " + (fastestSolve == null ? "NA" : fastestSolve), 1200, 500 + 60);
    // graph
    g.drawImage(graphCache, 1200, 200, null);
    return new RuntimeResponse(profileStep, image);
  }

  @Override
  public void writeStatistics(File statisticsOutputFile) {
    try {
      PrintWriter out = new PrintWriter(new FileWriter(statisticsOutputFile));
      out.print("Steps,");
      out.println(stepToFinish);
      out.print("Total Steps,");
      out.println(stepCount);
      out.print("Fastest Solve,");
      out.println(fastestSolve == null ? "NA" : fastestSolve);
      out.close();
    } catch (IOException e) {
      log.warn("Error writing stats", e);
    }
  }
}
