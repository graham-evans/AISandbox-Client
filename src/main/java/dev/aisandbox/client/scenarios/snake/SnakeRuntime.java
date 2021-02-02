package dev.aisandbox.client.scenarios.snake;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.profiler.AIProfiler;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.RuntimeResponse;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.SimulationException;
import dev.aisandbox.client.scenarios.snake.api.Location;
import dev.aisandbox.client.scenarios.snake.api.SnakeResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

@RequiredArgsConstructor
public class SnakeRuntime implements ScenarioRuntime {

  private final BufferedImage[][] sprites;
  private final ArenaType arenaType;
  private final TailType tailType;
  private List<Snake> snakes = new ArrayList<>();

  @Getter(AccessLevel.PROTECTED)
  private Board board;
  private Location[] startPositions = new Location[]{new Location(5, 5), new Location(15, 15),
      new Location(5, 15), new Location(15, 5)};

  @Override
  public void setAgents(List<Agent> agents) {
    // remove any old agents
    snakes.clear();
    // convert agents to snakes
    for (Agent agent : agents) {
      Snake snake = new Snake(agent);
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
      snake.setHeadLocation(startPositions[i]);
      board.addSnake(snake.getHeadLocation(), i);
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
    // check for head to head collisions
    // move and update snakes
    for (Snake s1 : snakes) {
      for (Snake s2 : snakes) {
        if ((s1!=s2)&&(s1.getState()==SnakeState.ALIVE)&&(s2.getState()==SnakeState.ALIVE)&&(s1.getNextTarget().equals(s2.getNextTarget()))) {
          // s1 and s2 are trying to move into the same square
        }
      }
    }
    return null;
  }

  @Override
  public void writeStatistics(File statisticsOutputFile) {

  }
}
