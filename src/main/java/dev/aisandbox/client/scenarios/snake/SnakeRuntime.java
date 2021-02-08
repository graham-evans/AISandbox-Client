package dev.aisandbox.client.scenarios.snake;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.RuntimeResponse;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.SimulationException;
import dev.aisandbox.client.scenarios.snake.api.Location;
import dev.aisandbox.client.scenarios.snake.api.SnakeDirection;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class SnakeRuntime implements ScenarioRuntime {

  private final BufferedImage[][] sprites;
  private final ArenaType arenaType;
  private final TailType tailType;
  private List<Snake> snakes = new ArrayList<>();

  @Getter(AccessLevel.PROTECTED)
  private Board board;

  private Location[] startPositions =
      new Location[] {
        new Location(5, 5), new Location(15, 15), new Location(5, 15), new Location(15, 5)
      };

  @Override
  public void setAgents(List<Agent> agents) {
    // remove any old agents
    snakes.clear();
    // convert agents to snakes
    for (int i = 0; i < agents.size(); i++) {
      log.info("Setting up agent {}", i);
      Agent agent = agents.get(i);
      Snake snake = new Snake(agent);
      snake.setColour(i);
      snakes.add(snake);
    }
  }

  private void resetBoard() {
    // start with new board
    board = new Board(arenaType, sprites);
    // setup snakes
    for (int i = 0; i < snakes.size(); i++) {
      Snake snake = snakes.get(i);
      // move snakes to the starting point
      snake.getTail().clear();
      snake.getTail().add(startPositions[i]);
      board.addSnakeHead(snake.getTail().get(0), i, SnakeDirection.NORTH);
    }
  }

  @Override
  public void initialise() {
    resetBoard();
  }

  @Override
  public RuntimeResponse advance() throws AgentException, SimulationException {
    ProfileStep profileStep = new ProfileStep();
    for (Snake snake : snakes) {
      // update the next move the response
      snake.updateNextAction(board);
    }
    profileStep.addStep("Network");
    // things that were dying are now dead
    for (Snake snake : snakes) {
      if (snake.getState() == SnakeState.DYING) {
        snake.setState(SnakeState.DEAD);
        snake.setMaxTailLength(0);
      }
    }
    // Special case - check for head to head collisions
    // move and update snakes
    for (Snake s1 : snakes) {
      for (Snake s2 : snakes) {
        if ((s1 != s2)
            && (s1.getState() == SnakeState.ALIVE)
            && (s2.getState() == SnakeState.ALIVE)
            && (s1.getNextTarget().equals(s2.getNextTarget()))) {
          // s1 and s2 are trying to move into the same square
        }
      }
    }
    // move the snakes
    for (Snake s : snakes) {
      if (s.getState() == SnakeState.ALIVE) {
        Location target = s.getNextTarget();
        if (board.isSolid(target)) {
          // hit something - DIE !
        } else {
          // move
          s.getTail().add(0, target);
          // paint the head
          board.addSnakeHead(target, s.getColour(), s.getNextAction());
          // repaint the second space
          if (s.getTail().size()==2) {
            // the second space is also the back
            Location tailEnd = s.getTail().get(1);
            SnakeDirection taildir = DirectionUtility.findDirection(tailEnd, target);
            switch (taildir) {
              case NORTH:
                board.paintSnake(tailEnd, Board.SPRITE_TAIL_NORTH, s.getColour());
                break;
              case EAST:
                board.paintSnake(tailEnd, Board.SPRITE_TAIL_EAST, s.getColour());
                break;
              case SOUTH:
                board.paintSnake(tailEnd, Board.SPRITE_TAIL_SOUTH, s.getColour());
                break;
              case WEST:
                board.paintSnake(tailEnd, Board.SPRITE_TAIL_WEST, s.getColour());
            }
          } else if (s.getTail().size()>2) {
            // draw the second step
            Location tailMid = s.getTail().get(1);
            Location tailNext = s.getTail().get(2);
            SnakeDirection upstream = DirectionUtility.findDirection(tailMid,target);
            SnakeDirection downstream = DirectionUtility.findDirection(tailMid,tailNext);

          }
        }
      }
    }
    // remove tails
    if (tailType != TailType.INFINITE) {
      for (Snake s : snakes) {
        if (s.getTail().size() > s.getMaxTailLength()) {
          // remove the and of the tail
          Location t = s.getTail().remove(s.getTail().size() - 1);
          board.removeSnake(t);
          if (s.getTail().size() > 1) {
            // redraw the new end of the tail
            Location tailEnd = s.getTail().get(s.getTail().size() - 1);
            Location tailmid = s.getTail().get(s.getTail().size() - 2);
            SnakeDirection taildir = DirectionUtility.findDirection(tailEnd, tailmid);
            switch (taildir) {
              case NORTH:
                board.paintSnake(tailEnd, Board.SPRITE_TAIL_NORTH, s.getColour());
                break;
              case EAST:
                board.paintSnake(tailEnd, Board.SPRITE_TAIL_EAST, s.getColour());
                break;
              case SOUTH:
                board.paintSnake(tailEnd, Board.SPRITE_TAIL_SOUTH, s.getColour());
                break;
              case WEST:
                board.paintSnake(tailEnd, Board.SPRITE_TAIL_WEST, s.getColour());
            }
          }
        }
      }
    }
    profileStep.addStep("Simulation");
    // draw the screen
    BufferedImage image = OutputTools.getWhiteScreen();
    Graphics2D graphics2D = image.createGraphics();
    graphics2D.drawImage(board.getScreen(), 100, 100, null);
    profileStep.addStep("Graphics");
    return new RuntimeResponse(profileStep, image);
  }

  private int getIndex(SnakeDirection direction) {
    switch (direction) {
      case NORTH:
        return 2;
      case EAST:
        return 3;
      case SOUTH:
        return 4;
      case WEST:
        return 5;
    }
    return 0;
  }

  @Override
  public void writeStatistics(File statisticsOutputFile) {}
}
