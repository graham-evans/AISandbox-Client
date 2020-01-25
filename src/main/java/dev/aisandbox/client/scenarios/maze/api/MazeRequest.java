package dev.aisandbox.client.scenarios.maze.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aisandbox.client.scenarios.ServerRequest;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * MazeRequest class.
 *
 * @author gde
 * @version $Id: $Id
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "MazeRequest")
@Data
public class MazeRequest implements ServerRequest {

  private Config config = new Config();

  private History history;

  private Position currentPosition;
}
