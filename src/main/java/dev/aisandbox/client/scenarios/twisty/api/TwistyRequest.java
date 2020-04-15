package dev.aisandbox.client.scenarios.twisty.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aisandbox.client.scenarios.ServerRequest;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/** TwistyRequest */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "TwistyRequest")
public class TwistyRequest implements ServerRequest {
  private TwistyRequestHistory history = null;
  private String puzzleType = null;
  private List<String> moves = new ArrayList<>();
  private String state = null;
}
