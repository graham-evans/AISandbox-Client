package dev.aisandbox.client.scenarios.zebra;

import lombok.Getter;
import lombok.Setter;
import org.chocosolver.solver.constraints.Constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Clue {
    @Getter
    @Setter
    private String clueString = "Unset";

    @Getter
    private List<Constraint> constraintList = new ArrayList<>();

    private static final Logger LOG = Logger.getLogger(Clue.class.getName());

    protected void postClue() {
        for (Constraint c : constraintList) {
            LOG.info("posting clue " + c.toString());

            c.post();
        }
    }
}
