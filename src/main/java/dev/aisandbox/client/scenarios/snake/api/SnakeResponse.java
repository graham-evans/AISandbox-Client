package dev.aisandbox.client.scenarios.snake.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "SnakeResponse")
public class SnakeResponse {
  private SnakeDirection move;
}
