package dev.aisandbox.client.scenarios.zebra.api;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * Solution class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
public class Solution {
  private List<House> house = new ArrayList<>();
}
