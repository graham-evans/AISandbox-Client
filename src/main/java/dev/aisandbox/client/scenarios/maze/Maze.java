package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.scenarios.maze.api.Config;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Maze {

    private static final Logger LOG = java.util.logging.Logger.getLogger(Maze.class.getName());

    public static final int SCALE = 25;

    @Getter
    private final String boardID = UUID.randomUUID().toString();

    @Getter
    private final int width;

    @Getter
    private final int height;

    @Getter
    Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    @Getter
    private final Cell[][] cellArray;

    @Getter
    private final List<Cell> cellList = new ArrayList<>();

    @Getter
    @Setter
    private Cell startCell = null;

    @Getter
    @Setter
    private Cell endCell = null;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        cellArray = new Cell[width][height];
        prepareGrid();
        joinGrid();
    }

    public Config getConfig() {
        Config c = new Config();
        c.setBoardID(boardID);
        c.setWidth(width);
        c.setHeight(height);
        return c;
    }

    protected void prepareGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell c = new Cell(x, y);
                cellArray[x][y] = c;
                cellList.add(c);
            }
        }
    }

    protected void joinGrid() {
        for (Cell c : cellList) {
            if (c.getPositionY() > 0) {
                c.linkBi(Direction.NORTH, cellArray[c.getPositionX()][c.getPositionY() - 1]);
            }
            if (c.getPositionX() > 0) {
                c.linkBi(Direction.WEST, cellArray[c.getPositionX() - 1][c.getPositionY()]);
            }
        }
    }

    public BufferedImage toImage(boolean colourize) {
        BufferedImage image = new BufferedImage(width * SCALE, height * SCALE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width * SCALE, height * SCALE);

        for (Cell c : cellList) {
            int x = c.getPositionX() * SCALE;
            int y = c.getPositionY() * SCALE;
            if (colourize) {
                g.setColor(new Color(c.getValue(), 0.0f, 0.0f));
                g.fillRect(x, y, x + SCALE, y + SCALE);
            }
            g.setColor(Color.DARK_GRAY);
            if (!c.getPaths().contains(Direction.NORTH)) {
                g.drawLine(x, y, x + SCALE - 1, y);
            }
            if (!c.getPaths().contains(Direction.WEST)) {
                g.drawLine(x, y, x, y + SCALE - 1);
            }
            if (!c.getPaths().contains(Direction.EAST)) {
                g.drawLine(x + SCALE - 1, y, x + SCALE - 1, y + SCALE - 1);
            }
            if (!c.getPaths().contains(Direction.SOUTH)) {
                g.drawLine(x, y + SCALE - 1, x + SCALE - 1, y + SCALE - 1);
            }
        }
        return image;
    }

    public BufferedImage toImage() {
        return toImage(false);
    }

    public void toImage(File f, boolean colourize) {
        BufferedImage i = toImage(colourize);
        try {
            ImageIO.write(i, "PNG", f);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error writing image file", e);
        }
    }

    public void toImage(File f) {
        toImage(f, false);
    }


}
