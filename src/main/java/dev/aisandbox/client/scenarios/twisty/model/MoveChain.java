package dev.aisandbox.client.scenarios.twisty.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MoveChain {
    private List<String> pieceIDs = new ArrayList<>();
}