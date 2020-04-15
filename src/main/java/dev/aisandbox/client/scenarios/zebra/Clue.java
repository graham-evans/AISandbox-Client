package dev.aisandbox.client.scenarios.zebra;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.chocosolver.solver.constraints.Constraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clue class.
 *
 * @author gde
 * @version $Id: $Id
 */
public class Clue {
  private static final Logger LOG = LoggerFactory.getLogger(Clue.class.getName());
  @Getter @Setter private String clueString = "Unset";
  @Getter private List<Constraint> constraintList = new ArrayList<>();

  /** postClue. */
  protected void postClue() {
    for (Constraint c : constraintList) {
      LOG.info("posting clue {}", c);

      c.post();
    }
  }
}
