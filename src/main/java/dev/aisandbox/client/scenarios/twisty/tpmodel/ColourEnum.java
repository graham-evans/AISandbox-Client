package dev.aisandbox.client.scenarios.twisty.tpmodel;

import java.awt.Color;
import lombok.Getter;

public enum ColourEnum {
  WHITE("FFFFFF", 'W'),
  ORANGE("FFA600", 'O'),
  GREEN("068E00", 'G'),
  RED("8E0000", 'R'),
  BLUE("02008E", 'B'),
  YELLOW("F7FF00", 'Y'),
  OLIVE("9FD82C", 'L'),
  PINK("D12CD8", 'P'),
  CYAN("2CD1D8", 'C'),
  GREY("B8B8B8", 'E'),
  IVORY("D2D399", 'I');

  @Getter private final String hex;
  @Getter private final Color awtColour;
  @Getter private final char character;

  ColourEnum(String hex, char character) {
    this.hex = hex;
    this.character = character;
    awtColour =
        new Color(
            Integer.valueOf(hex.substring(0, 2), 16),
            Integer.valueOf(hex.substring(2, 4), 16),
            Integer.valueOf(hex.substring(4, 6), 16));
  }
}
