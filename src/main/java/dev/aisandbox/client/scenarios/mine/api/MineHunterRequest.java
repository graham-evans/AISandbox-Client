package dev.aisandbox.client.scenarios.mine.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlRootElement(name = "MineRequest")
public class MineHunterRequest {
    private LastMove lastMove;
    private String boardID;
    private String[] board;
    private int flagsRemaining;
}
