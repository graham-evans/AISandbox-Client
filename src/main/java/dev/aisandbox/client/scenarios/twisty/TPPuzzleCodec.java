package dev.aisandbox.client.scenarios.twisty;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import dev.aisandbox.client.scenarios.twisty.tpmodel.Cell;
import dev.aisandbox.client.scenarios.twisty.tpmodel.CompiledMove;
import dev.aisandbox.client.scenarios.twisty.tpmodel.Loop;
import dev.aisandbox.client.scenarios.twisty.tpmodel.Move;
import dev.aisandbox.client.scenarios.twisty.tpmodel.Puzzle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TPPuzzleCodec {

  public static XStream getCodec() {
    XStream xstream = new XStream();
    xstream.processAnnotations(Puzzle.class);
    xstream.registerConverter(new BufferedImageConverter());
    xstream.addPermission(NoTypePermission.NONE);
    xstream.allowTypes(
        new Class[] {
          Puzzle.class,
          Cell.class,
          Move.class,
          Loop.class,
          CompiledMove.class,
          HashMap.class,
          Map.class,
          List.class,
          ArrayList.class,
          String.class,
          Integer.class
        });
    return xstream;
  }
}
