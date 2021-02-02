package dev.aisandbox.client.scenarios.snake;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.scenarios.snake.api.Location;
import dev.aisandbox.client.scenarios.snake.api.SnakeDirection;
import dev.aisandbox.client.scenarios.snake.api.SnakeRequest;
import dev.aisandbox.client.scenarios.snake.api.SnakeResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@RequiredArgsConstructor
@Slf4j
public class Snake {

  private final Agent agent;

  @Getter
  @Setter
  private SnakeState state = SnakeState.ALIVE;

  @Getter
  private SnakeDirection nextAction;

  @Getter
  private Location nextTarget;

  @Getter
  @Setter
  private Location headLocation;

  @Getter
  List<Location> tail = new ArrayList<>();
  @Getter
  @Setter
  private int maxTailLength = 1;

  private Random random = new Random();

  public void updateNextAction(Board board) throws AgentException {
    SnakeRequest request = new SnakeRequest();
    SnakeResponse response = agent.postRequest(request, SnakeResponse.class);
    // get the next move
    nextAction = response.getMove();
    // work out the next location
    nextTarget = headLocation.nextStep(nextAction);
  }


}
