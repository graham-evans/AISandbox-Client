package dev.aisandbox.client.scenarios.zebra.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * PropertyDefinition
 */

@Data
public class PropertyDefinition {
  private String propertyName = null;
  private List<String> propertyValues = new ArrayList<>();
}
