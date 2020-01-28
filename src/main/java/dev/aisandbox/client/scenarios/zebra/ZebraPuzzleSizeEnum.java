package dev.aisandbox.client.scenarios.zebra;

public enum ZebraPuzzleSizeEnum {
  SMALL {
    @Override
    public String toString() {
      return "Small (6 houses, 5 characteristics)";
    }
  },
  MEDIUM {
    @Override
    public String toString() {
      return "Medium (8 houses, 7 characteristics)";
    }
  },
  LARGE {
    @Override
    public String toString() {
      return "Large (10 houses, 11 characteristics)";
    }
  };
}
