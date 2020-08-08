package dev.aisandbox.client.scenarios;

import java.util.Comparator;

public class ScenarioComparator implements Comparator<Scenario> {

  @Override
  public int compare(Scenario o1, Scenario o2) {
    if (o1.getGroup().equals(o2.getGroup())) {
      return o1.getId().compareTo(o2.getId());
    } else {
      return o1.getGroup().ordinal() - o2.getGroup().ordinal();
    }
  }
}
