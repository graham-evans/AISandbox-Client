package dev.aisandbox.client.scenarios;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "testRequest")
@Data
public class TestRequest implements ServerRequest {

  private String name;
}
