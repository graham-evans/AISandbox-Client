package dev.aisandbox.client.cli.vo;

import lombok.Data;
import dev.aisandbox.client.scenarios.maze.*;
import dev.aisandbox.client.scenarios.mine.*;
/**
 * The Config class is used to serialise and deserialise the options that get held in the <i>RuntimeModel</i>.
 * <p>
 * NOTE: a seperate class is used so we dont accidentialy access the internal properties of the <i>RuntimeModel</i>.
 */

@Data
public class Config {

    private ScenarioEnum scenario = ScenarioEnum.UNDEFINED;
    private Long scenarioSalt = null;
    private MazeType mazeType = MazeType.BINARYTREE;
    private MazeSize mazeSize = MazeSize.MEDIUM;
    private SizeEnum mineBoardSize = SizeEnum.MEDIUM;

}