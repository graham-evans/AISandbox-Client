package dev.aisandbox.client.scenarios.zebra;

public enum ZebraPuzzleDifficultyEnum {
  EASY {
    @Override
    public String toString() {
      return "Easy (6 houses, 5 characteristics)";
    }
  },
  MEDIUM {
    @Override
    public String toString() {
      return "Medium (8 houses, 7 characteristics)";
    }
  },
  HARD {
    @Override
    public String toString() {
      return "Hard (10 houses, 11 characteristics)";
    }
  };
}
