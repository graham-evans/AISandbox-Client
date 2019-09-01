package dev.aisandbox.client.scenarios.maze;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aisandbox.client.scenarios.ServerRequest;
import dev.aisandbox.client.scenarios.maze.api.Config;
import dev.aisandbox.client.scenarios.maze.api.History;
import dev.aisandbox.client.scenarios.maze.api.Position;
import lombok.Data;

/**
 * @author gde
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MazeRequest implements ServerRequest {

    private Config config = new Config();
    private History history;
    private Position currentPosition;

}
