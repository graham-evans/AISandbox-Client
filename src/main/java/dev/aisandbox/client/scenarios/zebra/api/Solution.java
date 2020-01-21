package dev.aisandbox.client.scenarios.zebra.api;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Solution {
  private List<House> house = new ArrayList<>();
}
