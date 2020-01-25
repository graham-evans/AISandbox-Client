package dev.aisandbox.client.scenarios.mine.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aisandbox.client.scenarios.ServerResponse;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "MineResponse")
public class MineHunterResponse implements ServerResponse {
  Move[] moves;
}
