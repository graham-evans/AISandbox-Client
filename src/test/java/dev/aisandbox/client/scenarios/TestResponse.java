package dev.aisandbox.client.scenarios;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "testResponse")
@Data
public class TestResponse implements ServerResponse {
    private int number;
}
