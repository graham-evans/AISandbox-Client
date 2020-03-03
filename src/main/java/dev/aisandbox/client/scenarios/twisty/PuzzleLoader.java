package dev.aisandbox.client.scenarios.twisty;

import dev.aisandbox.client.scenarios.twisty.model.TwistyPuzzle;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PuzzleLoader {

  public static TwistyPuzzle loadPuzzle(PuzzleType puzzleType) {
    try {
      JAXBContext context = JAXBContext.newInstance(TwistyPuzzle.class);
      Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
      return (TwistyPuzzle)
          jaxbUnmarshaller.unmarshal(
              PuzzleLoader.class.getResourceAsStream(getResourse(puzzleType)));
    } catch (JAXBException e) {
      log.error("Error marshalling puzzle", e);
      return null;
    }
  }

  private static String getResourse(PuzzleType puzzleType) {
    switch (puzzleType) {
      case CUBE2:
        return "/dev/aisandbox/client/scenarios/twisty/cube2.tps";
      case CUBE3:
        return "/dev/aisandbox/client/scenarios/twisty/cube3.tps";
      case CUBE4:
        return "/dev/aisandbox/client/scenarios/twisty/cube4.tps";
      case CUBE5:
        return "/dev/aisandbox/client/scenarios/twisty/cube5.tps";
      case CUBE6:
        return "/dev/aisandbox/client/scenarios/twisty/cube6.tps";
      case CUBE7:
        return "/dev/aisandbox/client/scenarios/twisty/cube7.tps";
      case CUBE8:
        return "/dev/aisandbox/client/scenarios/twisty/cube8.tps";
      case CUBE9:
        return "/dev/aisandbox/client/scenarios/twisty/cube9.tps";
      case CUBE10:
        return "/dev/aisandbox/client/scenarios/twisty/cube10.tps";
      default:
        log.error("Unknown puzzle type");
        return null;
    }
  }
}
