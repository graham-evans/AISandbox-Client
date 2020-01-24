package dev.aisandbox.client.scenarios.zebra;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.chocosolver.solver.constraints.Constraint;

public class Clue {
  private static final Logger LOG = Logger.getLogger(Clue.class.getName());
  @Getter @Setter private String clueString = "Unset";
  @Getter private List<Constraint> constraintList = new ArrayList<>();

  protected void postClue() {
    for (Constraint c : constraintList) {
      LOG.log(Level.INFO, "posting clue {0}", new Object[] {c});

      c.post();
    }
  }
}
