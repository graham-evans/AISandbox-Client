package dev.aisandbox.client.scenarios.mine.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aisandbox.client.scenarios.ServerRequest;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "MineRequest")
public class MineHunterRequest implements ServerRequest {
  private LastMove lastMove;
  private String boardID;
  private String[] board;
  private int flagsRemaining;
}
