package dev.aisandbox.client.scenarios.twisty.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Face {
    String faceName;
    List<Piece> pieces = new ArrayList<>();
}