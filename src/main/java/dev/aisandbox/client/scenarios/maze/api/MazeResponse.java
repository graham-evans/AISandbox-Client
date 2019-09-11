package dev.aisandbox.client.scenarios.maze.api;

import dev.aisandbox.client.scenarios.ServerResponse;
import lombok.Data;

/**
 * @author gde
 */

@Data
public class MazeResponse implements ServerResponse {
    String move;
}
