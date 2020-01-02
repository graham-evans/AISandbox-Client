package dev.aisandbox.client.scenarios.zebra.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "ZebraResponse")
public class ZebraResponse {
  private Answer answer = null;
}
