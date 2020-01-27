package dev.aisandbox.client.scenarios.zebra.api;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Solution {
  private List<House> house = new ArrayList<>();
}
