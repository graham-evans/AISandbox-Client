package dev.aisandbox.client.scenarios.maze;


/**
 * <p>MazeType class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
public enum MazeType {
    BINARYTREE {
        @Override
        public String toString() {
            return "Binary Tree (Biased)";
        }
    },
    SIDEWINDER {
        @Override
        public String toString() {
            return "Sidewinder";
        }
    },
    RECURSIVEBACKTRACKER {
        @Override
        public String toString() {
            return "Recursive Backtracker";
        }
    },
    BRAIDED {
        @Override
        public String toString() {
            return "Braided (includes loops)";
        }
    };


}
