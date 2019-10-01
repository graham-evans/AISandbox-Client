package dev.aisandbox.client.scenarios.maze.api;

import dev.aisandbox.client.scenarios.ServerResponse;
import lombok.Data;

/**
 * <p>MazeResponse class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */

@Data
public class MazeResponse implements ServerResponse {
    String move;
}
