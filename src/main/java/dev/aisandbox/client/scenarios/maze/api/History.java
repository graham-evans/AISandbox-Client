/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.aisandbox.client.scenarios.maze.api;

import lombok.Data;

/**
 * History class.
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
public class History {

  private Position lastPosition;
  private String action;
  private double reward;
  private Position newPosition;
}
