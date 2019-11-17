package dev.aisandbox.client.scenarios.mine.api;

import dev.aisandbox.client.scenarios.ServerResponse;
import lombok.Data;

@Data
public class MineHunterResponse implements ServerResponse {
    Move[] moves;
}
