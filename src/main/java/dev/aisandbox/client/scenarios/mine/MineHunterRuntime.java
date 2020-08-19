package dev.aisandbox.client.scenarios.mine;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.output.charts.SuccessRateGraph;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.RuntimeResponse;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.SimulationException;
import dev.aisandbox.client.scenarios.mine.api.LastMove;
import dev.aisandbox.client.scenarios.mine.api.MineHunterRequest;
import dev.aisandbox.client.scenarios.mine.api.MineHunterResponse;
import dev.aisandbox.client.scenarios.mine.api.Move;
import dev.aisandbox.client.sprite.SpriteLoader;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MineHunterRuntime implements ScenarioRuntime {

  // agents
  private Agent agent = null;
  // puzzle elements
  @Setter Random random = new Random();
  int boardWidth = 9;
  int boardHeight = 9;
  int boardCount = 10;
  private Board board = null;
  // UI elements
  private final SpriteLoader spriteLoader;
  private BufferedImage logo;
  private List<BufferedImage> sprites;
  private SuccessRateGraph winRateGraph = new SuccessRateGraph();
  private BufferedImage winRateGraphImage = null;
  private long boardsWon = 0;
  private long boardsLost = 0;
  private double scale = 1.0;
  Font myFont = new Font("Sans-Serif", Font.PLAIN, 28);
  // API elements
  LastMove last = null;

  public MineHunterRuntime(SpriteLoader spriteLoader) {
    this.spriteLoader = spriteLoader;
  }

  @Override
  public void setAgents(List<Agent> agents) {
    agent = agents.get(0);
    if (agents.size() > 1) {
      log.error("Passed multiple agents when only one will be used.");
    }
  }

  /**
   * Set the size of the board and the number of mines to place.
   *
   * <ul>
   *   <li>0 - 9x9 10
   *   <li>1 - 16x16 40
   *   <li>2 - 24x24 99
   *   <li>3 - 40x40 150
   * </ul>
   *
   * @param size the index of the size option chosen.
   */
  public void setBoardSize(MineSize size) {
    switch (size) {
      case MEDIUM:
        boardWidth = 16;
        boardHeight = 16;
        boardCount = 40;
        break;
      case LARGE:
        boardWidth = 24;
        boardHeight = 24;
        boardCount = 99;
        break;
      case MEGA:
        boardWidth = 40;
        boardHeight = 40;
        boardCount = 150;
        break;
      default: // size=0
        boardWidth = 9;
        boardHeight = 9;
        boardCount = 10;
    }
  }

  @Override
  public void initialise() {
    // load images
    log.info("Loading sprites");
    try {
      logo =
          ImageIO.read(
              MineHunterRuntime.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
    } catch (IOException e) {
      log.error("Error loading logo", e);
      logo = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    }
    sprites = spriteLoader.loadSprites("/dev/aisandbox/client/scenarios/mine/grid.png", 40, 40);
    // create first board
    getNewBoard();
  }

  private void getNewBoard() {
    // create a board
    log.info("Initialising board");
    board = new Board(boardWidth, boardHeight);
    board.placeMines(random, boardCount);
    board.countNeighbours();
    scale = 20.0 / board.getHeight();
    log.info("Scaling board to {}", scale);
    winRateGraphImage = winRateGraph.getGraph(600, 250);
  }

  @Override
  public RuntimeResponse advance() throws AgentException, SimulationException {
    ProfileStep profileStep = new ProfileStep();
    List<BufferedImage> frames = new ArrayList<>();
    // send a request
    MineHunterRequest request = new MineHunterRequest();
    request.setLastMove(last);
    request.setBoardID(board.getBoardID());
    request.setFlagsRemaining(board.getUnfoundMines());
    request.setBoard(board.getBoardToString());
    MineHunterResponse response = agent.postRequest(request, MineHunterResponse.class);
    profileStep.addStep("Network");
    for (Move move : response.getMoves()) {
      boolean change =
          move.isFlag()
              ? board.placeFlag(move.getX(), move.getY())
              : board.uncover(move.getX(), move.getY());
      profileStep.addStep("Simulation");
      // if something has changed, redraw the screen
      if (change) {
        frames.add(createLevelImage());
      }
      profileStep.addStep("Graphics");
      // if the level has ended, dont make any more changes
      if (board.getState() != GameState.PLAYING) {
        break;
      }
    }
    profileStep.addStep("Simulation");
    // record the result of the last move
    last = new LastMove();
    last.setBoardID(board.getBoardID());
    last.setResult(board.getState().name());
    // if the game has finished create a new one
    if (board.getState() != GameState.PLAYING) {
      if (board.getState() == GameState.WON) {
        winRateGraph.addValue(100.0);
        boardsWon++;
      } else {
        winRateGraph.addValue(0.0);
        boardsLost++;
      }

      getNewBoard();
      profileStep.addStep("Setup");
    }
    return new RuntimeResponse(profileStep, frames);
  }

  @Override
  public void writeStatistics(File statisticsOutputFile) {
    try {
      PrintWriter out = new PrintWriter(new FileWriter(statisticsOutputFile));
      out.print("Games won,");
      out.println(boardsWon);
      out.print("Games lost,");
      out.println(boardsLost);
      out.close();
    } catch (IOException e) {
      log.warn("Error writing stats file", e);
    }
  }

  private BufferedImage createLevelImage() {
    BufferedImage image = OutputTools.getWhiteScreen();
    Graphics2D g = image.createGraphics();
    // add logo
    g.drawImage(logo, 100, 50, null);
    // draw graphcs
    g.drawImage(winRateGraphImage, 1200, 200, null);
    g.setColor(Color.BLACK);
    g.setFont(myFont);
    g.drawString("Mines Remaining : " + board.getUnfoundMines(), 1200, 500);
    // transform for drawing the board
    g.translate(100, 200);
    g.scale(scale, scale);
    for (int x = 0; x < board.getWidth(); x++) {
      for (int y = 0; y < board.getHeight(); y++) {
        Cell c = board.getCell(x, y);
        switch (c.getPlayerView()) {
          case '#':
            g.drawImage(sprites.get(11), x * 40, y * 40, null);
            break;
          case 'F':
            g.drawImage(sprites.get(12), x * 40, y * 40, null);
            break;
          case 'f':
            g.drawImage(sprites.get(13), x * 40, y * 40, null);
            break;
          case 'X':
            g.drawImage(sprites.get(10), x * 40, y * 40, null);
            break;
          case '.':
            g.drawImage(sprites.get(0), x * 40, y * 40, null);
            break;
          case '1':
            g.drawImage(sprites.get(1), x * 40, y * 40, null);
            break;
          case '2':
            g.drawImage(sprites.get(2), x * 40, y * 40, null);
            break;
          case '3':
            g.drawImage(sprites.get(3), x * 40, y * 40, null);
            break;
          case '4':
            g.drawImage(sprites.get(4), x * 40, y * 40, null);
            break;
          case '5':
            g.drawImage(sprites.get(5), x * 40, y * 40, null);
            break;
          case '6':
            g.drawImage(sprites.get(6), x * 40, y * 40, null);
            break;
          case '7':
            g.drawImage(sprites.get(7), x * 40, y * 40, null);
            break;
          case '8':
            g.drawImage(sprites.get(8), x * 40, y * 40, null);
            break;
          case '9':
            g.drawImage(sprites.get(9), x * 40, y * 40, null);
            break;
          default:
            log.warn("Unexpected char");
        }
      }
    }
    return image;
  }
}
