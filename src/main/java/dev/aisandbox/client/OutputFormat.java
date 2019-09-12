package dev.aisandbox.client;

public enum OutputFormat {
    NONE{
        @Override
        public String toString() {
            return "No output";
        }
    },
    MP4{
        @Override
        public String toString() {
            return "MP4 Video (slow)";
        }
    },
    PNG{
        @Override
        public String toString() {
            return "PNG Images";
        }
    }
    ;
}
