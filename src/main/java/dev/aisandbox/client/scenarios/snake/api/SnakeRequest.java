package dev.aisandbox.client.scenarios.snake.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aisandbox.client.scenarios.ServerRequest;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "SnakeRequest")
public class SnakeRequest implements ServerRequest {

  private String gameID;

  private int boardWidth;
  private int boardHeight;

  private String[] board;

  private Path player;

  private List<Path> opponents=new ArrayList<>();

}
