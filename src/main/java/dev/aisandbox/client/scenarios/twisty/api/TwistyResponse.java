package dev.aisandbox.client.scenarios.twisty.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * TwistyResponse class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "TwistyResponse")
public class TwistyResponse {
  private String move;
}
