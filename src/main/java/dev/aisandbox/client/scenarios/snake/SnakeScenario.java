package dev.aisandbox.client.scenarios.snake;

import dev.aisandbox.client.parameters.EnumerationParameter;
import dev.aisandbox.client.parameters.LimitedEnumerationParameter;
import dev.aisandbox.client.scenarios.BaseScenario;
import dev.aisandbox.client.scenarios.Scenario;
import dev.aisandbox.client.scenarios.ScenarioParameter;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.ScenarioType;
import dev.aisandbox.client.sprite.SpriteLoader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SnakeScenario extends BaseScenario implements Scenario {

  private final SpriteLoader spriteLoader;
  private EnumerationParameter<ArenaType> arenaType =
      new EnumerationParameter<>(
          "snake.arena", ArenaType.CLASSIC, "Arena", "Select size and type of arena to play in");
  private EnumerationParameter<TailType> tailType =
      new EnumerationParameter<>("snake.tail", TailType.GROWING, "Tail", "Select the snakes tail");
  private LimitedEnumerationParameter<TilesetType> tileset =
      new LimitedEnumerationParameter<>(
          "snake.tileset", TilesetType.BASIC_SNAKE, "Tileset", "Select the graphics to use", false);

  /** Constructor for the Snake games. */
  @Autowired
  public SnakeScenario(SpriteLoader spriteLoader) {
    super(
        "snake",
        "Snake Game",
        ScenarioType.INTERMEDIATE,
        "Single or multi-player version of the grid based classic.",
        "Single or multi-player version of the grid based classic.",
        "/dev/aisandbox/client/scenarios/snake/sample.png",
        1,
        1, // TODO - Change to 4
        "https://aisandbox.dev/#",
        "https://files.aisandbox.dev/#");
    tileset.setAllowedValue(TilesetType.BASIC_SNAKE, true);
    tileset.setAllowedValue(TilesetType.BASIC_BIKE, true);
    this.spriteLoader = spriteLoader;
  }

  @Override
  public ScenarioParameter[] getParameterArray() {
    return new ScenarioParameter[] {arenaType, tailType, tileset};
  }

  @Override
  public ScenarioRuntime getRuntime() {
    // load sprites
    BufferedImage[][] sprites = new BufferedImage[0][];
    try {
      sprites =
          spriteLoader.loadSpriteGridFromResources(
              "/dev/aisandbox/client/scenarios/snake/tileset1.png", 32, 32);
    } catch (IOException e) {
      log.error("Error loading sprites", e);
    }

    SnakeRuntime runtime = new SnakeRuntime(sprites, ArenaType.CLASSIC, TailType.GROWING);
    return runtime;
  }
}
