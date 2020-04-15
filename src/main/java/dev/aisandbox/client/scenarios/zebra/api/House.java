package dev.aisandbox.client.scenarios.zebra.api;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * House class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
public class House {
  private int housenumber;
  private List<HouseCharacteristics> characteristics = new ArrayList<>();
}
