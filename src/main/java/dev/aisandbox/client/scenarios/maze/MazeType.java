package dev.aisandbox.client.scenarios.maze;


public enum MazeType {
    BINARYTREE{
        @Override
        public String toString() {
            return "Binary Tree (Biased)";
        }
    },
    SIDEWINDER{
        @Override
        public String toString() {
            return "Sidewinder";
        }
    };


}
