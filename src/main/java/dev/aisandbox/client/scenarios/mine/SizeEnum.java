package dev.aisandbox.client.scenarios.mine;

public enum SizeEnum {
    SMALL {
        @Override
        public String toString() {
            return "Small (8x6)";
        }
    }, MEDIUM {
        @Override
        public String toString() {
            return "Medium (8x6)";
        }
    }, LARGE {
        @Override
        public String toString() {
            return "Large (8x6)";
        }
    }, MEGA {
        @Override
        public String toString() {
            return "Mega (8x6)";
        }
    };
}
