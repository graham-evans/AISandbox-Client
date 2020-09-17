package dev.aisandbox.client.scenarios.twisty;

/**
 * PuzzleType class.
 *
 * @author gde
 * @version $Id: $Id
 */
public enum PuzzleType {
  CUBE3 {
    @Override
    public String toString() {
      return "Cube 3x3x3";
    }
  },
  CUBE2 {
    @Override
    public String toString() {
      return "Cube 2x2x2";
    }
  },
  CUBE4 {
    @Override
    public String toString() {
      return "Cube 4x4x4";
    }
  },
  CUBE5 {
    @Override
    public String toString() {
      return "Cube 5x5x5";
    }
  },
  CUBE6 {
    @Override
    public String toString() {
      return "Cube 6x6x6";
    }
  },
  CUBE7 {
    @Override
    public String toString() {
      return "Cube 7x7x7";
    }
  },
  CUBE8 {
    @Override
    public String toString() {
      return "Cube 8x8x8";
    }
  },
  CUBE9 {
    @Override
    public String toString() {
      return "Cube 9x9x9";
    }
  },
  CUBE10 {
    @Override
    public String toString() {
      return "Cube 10x10x10";
    }
  },
  PYRAMID3 {
    @Override
    public String toString() {
      return "Pyramid 3";
    }
  }
}
