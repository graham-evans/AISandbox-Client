package dev.aisandbox.client.scenarios.mine;

public enum SizeEnum {
  SMALL {
    @Override
    public String toString() {
      return "Small (8x6 10 Mines)";
    }
  },
  MEDIUM {
    @Override
    public String toString() {
      return "Medium (16x16 40 Mines)";
    }
  },
  LARGE {
    @Override
    public String toString() {
      return "Large (24x24 99 Mines)";
    }
  },
  MEGA {
    @Override
    public String toString() {
      return "Mega (40x40 150 Mines)";
    }
  };
}
