package dev.aisandbox.client.scenarios;

import javafx.scene.paint.Color;

public enum ScenarioType {
    INTRODUCTION,
    TEXT;

    private Color typeColour;

    static {
        INTRODUCTION.typeColour = Color.DARKGREEN;
        TEXT.typeColour = Color.BLACK;
    }

    public Color getTypeColour() {
        return typeColour;
    }
}
