package dev.aisandbox.client.scenarios.snake;

import dev.aisandbox.client.parameters.EnumerationParameter;
import dev.aisandbox.client.parameters.LimitedEnumerationParameter;
import dev.aisandbox.client.scenarios.BaseScenario;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SnakeScenario extends BaseScenario implements Scenario {

  private EnumerationParameter<ArenaType> arenaType =
      new EnumerationParameter<>(
          "snake.arena", ArenaType.CLASSIC, "Arena", "Select size and type of arena to play in");
  private EnumerationParameter<TailType> tailType =
      new EnumerationParameter<>("snake.tail", TailType.GROWING, "Tail", "Select the snakes tail");
  private LimitedEnumerationParameter<TilesetType> tileset =
      new LimitedEnumerationParameter<>(
          "snake.tileset", TilesetType.BASIC_SNAKE, "Tileset", "Select the graphics to use", false);

  /** Constructor for the Snake games. */
  public SnakeScenario() {
    super(
        "snake",
        "Snake Game",
        ScenarioType.INTERMEDIATE,
        "Single or multi-player version of the grid based classic.",
        "Single or multi-player version of the grid based classic.",
        "/dev/aisandbox/client/scenarios/snake/sample.png",
        1,
        4,
        "https://aisandbox.dev/#",
        "https://files.aisandbox.dev/#");
    tileset.setAllowedValue(TilesetType.BASIC_SNAKE, true);
    tileset.setAllowedValue(TilesetType.BASIC_BIKE, true);
  }

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {arenaType, tailType, tileset};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    return null;
  }
}
