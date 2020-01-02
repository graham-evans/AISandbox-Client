package dev.aisandbox.client.scenarios.zebra.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "ZebraRequest")
public class ZebraRequest {
  private String puzzleID = null;
  private List<String> clues = new ArrayList<>();
  private PropertyDefinition properties = null;
  private List<Integer> houseNumbers = new ArrayList<>();
  private ZebraRequestHistory history = null;
}
