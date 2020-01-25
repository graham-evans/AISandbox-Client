package dev.aisandbox.client;

/** Enum for the choice of output format, none/MP4/PNG */
public enum OutputFormat {
  NONE {
    @Override
    public String toString() {
      return "No output";
    }
  },
  MP4 {
    @Override
    public String toString() {
      return "MP4 Video (slow)";
    }
  },
  PNG {
    @Override
    public String toString() {
      return "PNG Images";
    }
  }
}
