package dev.aisandbox.client.scenarios.twisty.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Piece {
    int defaultColourIndex;
    int currentColourIndex;
    List<Point> points = new ArrayList<>();
}