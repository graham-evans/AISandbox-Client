package dev.aisandbox.client.scenarios.zebra.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HouseCharacteristics class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseCharacteristics {
  private int characteristicNumber;
  private int characteristicValue;
}
