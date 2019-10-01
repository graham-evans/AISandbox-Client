/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.aisandbox.client.scenarios.maze.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * <p>Position class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    int x;
    int y;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
