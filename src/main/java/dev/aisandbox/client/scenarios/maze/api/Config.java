package dev.aisandbox.client.scenarios.maze.api;

import lombok.Data;

/**
 * <p>Config class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
public class Config {
    private String boardID;
    private String[] validMoves = {"North", "South", "East", "West"};
    private int width;
    private int height;
}
