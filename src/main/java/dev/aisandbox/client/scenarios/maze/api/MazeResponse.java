package dev.aisandbox.client.scenarios.maze.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aisandbox.client.scenarios.ServerResponse;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>MazeResponse class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "MazeResponse")
@Data
public class MazeResponse implements ServerResponse {
    String move;
}
