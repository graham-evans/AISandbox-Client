package dev.aisandbox.client.scenarios.zebra.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.aisandbox.client.scenarios.ServerRequest;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * ZebraRequest class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "ZebraRequest")
public class ZebraRequest implements ServerRequest {
  private ZebraRequestHistory history;
  private String puzzleID;
  private List<String> clues = new ArrayList<>();
  private List<ZebraRequestCharacteristics> characteristics = new ArrayList<>();
  private int numberOfHouses;
}
