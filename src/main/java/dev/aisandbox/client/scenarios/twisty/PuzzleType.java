package dev.aisandbox.client.scenarios.twisty;

/**
 * PuzzleType class.
 *
 * @author gde
 * @version $Id: $Id
 */
public enum PuzzleType {
  CUBE3("Cube 3x3x3", "Cube 3x3x3 (OBTM)", "/dev/aisandbox/client/scenarios/twisty/Cube3.tp"),
  CUBE2("Cube 2x2x2", "Cube 2x2x2 (OBTM)", "/dev/aisandbox/client/scenarios/twisty/Cube2.tp"),
  CUBE4("Cube 4x4x4", "Cube 4x4x4 (OBTM)", "/dev/aisandbox/client/scenarios/twisty/Cube4.tp"),
  CUBE5("Cube 5x5x5", "Cube 5x5x5 (OBTM)", "/dev/aisandbox/client/scenarios/twisty/Cube5.tp"),
  CUBE6("Cube 6x6x6", "Cube 6x6x6 (OBTM)", "/dev/aisandbox/client/scenarios/twisty/Cube6.tp"),
  CUBE7("Cube 7x7x7", "Cube 7x7x7 (OBTM)", "/dev/aisandbox/client/scenarios/twisty/Cube7.tp"),
  CUBE8("Cube 8x8x8", "Cube 8x8x8 (OBTM)", "/dev/aisandbox/client/scenarios/twisty/Cube8.tp"),
  CUBE9(
      "Cube 10x10x10", "Cube 10x10x10 (OBTM)", "/dev/aisandbox/client/scenarios/twisty/Cube10.tp"),
  CUBE223(
      "Cuboid 2x2x3",
      "Cuboid 2x2x3 (OBTM)",
      "/dev/aisandbox/client/scenarios/twisty/Cuboid2x2x3.tp"),
  CUBE224(
      "Cuboid 2x2x4",
      "Cuboid 2x2x4 (OBTM)",
      "/dev/aisandbox/client/scenarios/twisty/Cuboid2x2x4.tp"),
  CUBE225(
      "Cuboid 2x2x5",
      "Cuboid 2x2x5 (OBTM)",
      "/dev/aisandbox/client/scenarios/twisty/Cuboid2x2x5.tp"),
  CUBE226(
      "Cuboid 2x2x6",
      "Cuboid 2x2x6 (OBTM)",
      "/dev/aisandbox/client/scenarios/twisty/Cuboid2x2x6.tp"),
  CUBE332(
      "Cuboid 3x3x2",
      "Cuboid 3x3x2 (OBTM)",
      "/dev/aisandbox/client/scenarios/twisty/Cuboid3x3x2.tp"),
  CUBE334(
      "Cuboid 3x3x4",
      "Cuboid 3x3x4 (OBTM)",
      "/dev/aisandbox/client/scenarios/twisty/Cuboid3x3x4.tp"),
  CUBE335(
      "Cuboid 3x3x5",
      "Cuboid 3x3x5 (OBTM)",
      "/dev/aisandbox/client/scenarios/twisty/Cuboid3x3x5.tp"),
  PYRAMID3("Pyramid 3", "Pyramid 3", "/dev/aisandbox/client/scenarios/twisty/Pyramid3.tp");

  PuzzleType(String name, String id, String resource) {
    this.name = name;
    this.id = id;
    this.resource = resource;
  }

  private String name;
  private String id;
  private String resource;

  public String toString() {
    return name;
  }

  public String getID() {
    return id;
  }

  public String getResource() {
    return resource;
  }
}
