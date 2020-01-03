package dev.aisandbox.client.scenarios.zebra.api;

import lombok.Data;

import java.util.List;

@Data
public class PropertyDefinition {
  private String propertyName = null;
  private List<String> propertyValues = null;
}
