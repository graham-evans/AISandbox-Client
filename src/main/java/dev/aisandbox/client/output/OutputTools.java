package dev.aisandbox.client.output;

import java.awt.*;
import java.awt.image.BufferedImage;

public class OutputTools {
    public static final int VIDEO_WIDTH = 1920;
    public static final int VIDEO_HEIGHT = 1080;

    public static BufferedImage getBlankScreen() {
        return new BufferedImage(VIDEO_WIDTH, VIDEO_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    public static BufferedImage getWhiteScreen() {
        return getColouredScreen(Color.WHITE);
    }

    public static BufferedImage getBlackScreen() {
        return getColouredScreen(Color.BLACK);
    }

    public static BufferedImage getColouredScreen(Color color) {
        BufferedImage image = new BufferedImage(VIDEO_WIDTH, VIDEO_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, VIDEO_WIDTH, VIDEO_WIDTH);
        return image;
    }


}
