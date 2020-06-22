package dev.aisandbox.client.scenarios.bandit.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

/**
 * BanditResponse
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "BanditResponse")
public class BanditResponse   {
  private int arm;
}
