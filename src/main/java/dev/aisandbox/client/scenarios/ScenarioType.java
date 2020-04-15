package dev.aisandbox.client.scenarios;

import javafx.scene.paint.Color;

/**
 * ScenarioType class.
 *
 * @author gde
 * @version $Id: $Id
 */
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

  /**
   * Getter for the field <code>typeColour</code>.
   *
   * @return a {@link javafx.scene.paint.Color} object.
   */
  public Color getTypeColour() {
    return typeColour;
  }
}
