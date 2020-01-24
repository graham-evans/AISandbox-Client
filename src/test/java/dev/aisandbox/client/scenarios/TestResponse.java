package dev.aisandbox.client.scenarios;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "testResponse")
@Data
public class TestResponse implements ServerResponse {
  private int number;
}
