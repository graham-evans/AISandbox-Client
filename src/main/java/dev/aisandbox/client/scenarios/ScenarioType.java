package dev.aisandbox.client.scenarios;

import javafx.scene.paint.Color;

public enum ScenarioType {
  INTRODUCTION,
  TEXT,
  INTERMEDIATE;

  private Color typeColour;

  static {
    INTRODUCTION.typeColour = Color.DARKGREEN;
    TEXT.typeColour = Color.BLACK;
    INTERMEDIATE.typeColour = Color.BLUE;
  }

  public Color getTypeColour() {
    return typeColour;
  }
}
