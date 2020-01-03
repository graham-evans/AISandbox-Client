package dev.aisandbox.client.scenarios.zebra.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "ZebraResponse")
public class ZebraResponse {
  private List<HouseAnswer> answer = new ArrayList<HouseAnswer>();
}
