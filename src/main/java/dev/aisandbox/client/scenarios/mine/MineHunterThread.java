package dev.aisandbox.client.scenarios.mine;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.scenarios.maze.MazeRunner;
import dev.aisandbox.client.scenarios.mine.api.LastMove;
import dev.aisandbox.client.sprite.SpriteLoader;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MineHunterThread extends Thread {

    private static final Logger LOG = Logger.getLogger(MineHunterThread.class.getName());

    private final Agent agent;
    private final FrameOutput output;
    private final GameRunController controller;
    private final Random random;

    private Board board;

    private BufferedImage logo;

    private List<BufferedImage> sprites;

    public MineHunterThread(Agent agent, FrameOutput output, GameRunController controller, Random random,SpriteLoader loader) {
        this.agent = agent;
        this.output = output;
        this.controller = controller;
        this.random = random;
        // load images
        LOG.info("Loading sprites");
        try {
            logo = ImageIO.read(MazeRunner.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
        } catch (IOException e) {
            LOG.log(Level.SEVERE,"Error loading logo",e);
            logo = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
        }
        sprites = loader.loadSprites("/mine/grid.png",40,40);
    }

    @Getter
    @Setter
    private boolean running = false;

    @Override
    public void run() {
        getNewBoard();
        running = true;
        LastMove last = null;
        while (running) {
            // draw image
            controller.updateBoardImage(createLevelImage());
            // send a request
            running=false;
        }
        LOG.info("Finished run thread");
        controller.resetStartButton();
    }

    private void getNewBoard() {
        // create a board
        LOG.info("Initialising board");
        board = new Board(9,9);
        board.placeMines(random,10);
        board.countNeighbours();
    }

    private BufferedImage createLevelImage() {
        BufferedImage image = OutputTools.getWhiteScreen();
        Graphics2D g = image.createGraphics();
        // add logo
        g.drawImage(logo,100,50,null);
        for (int x=0;x<board.getWidth();x++) {
            for (int y=0;y<board.getHeight();y++) {
                Cell c = board.getCell(x,y);
                switch (c.getPlayerView()) {
                    case '#' : g.drawImage(sprites.get(11),100+x*40,200+y*40,null);
                    break;
                    case 'F' : g.drawImage(sprites.get(12),100+x*40,200+y*40,null);
                        break;
                    case 'f' : g.drawImage(sprites.get(13),100+x*40,200+y*40,null);
                        break;
                    case 'X' : g.drawImage(sprites.get(10),100+x*40,200+y*40,null);
                        break;
                    case '.' : g.drawImage(sprites.get(0),100+x*40,200+y*40,null);
                        break;
                    case '1' : g.drawImage(sprites.get(1),100+x*40,200+y*40,null);
                        break;
                    case '2' : g.drawImage(sprites.get(2),100+x*40,200+y*40,null);
                        break;
                    case '3' : g.drawImage(sprites.get(3),100+x*40,200+y*40,null);
                        break;
                    case '4' : g.drawImage(sprites.get(4),100+x*40,200+y*40,null);
                        break;
                    case '5' : g.drawImage(sprites.get(5),100+x*40,200+y*40,null);
                        break;
                    case '6' : g.drawImage(sprites.get(6),100+x*40,200+y*40,null);
                        break;
                    case '7' : g.drawImage(sprites.get(7),100+x*40,200+y*40,null);
                        break;
                    case '8' : g.drawImage(sprites.get(8),100+x*40,200+y*40,null);
                        break;
                    case '9' : g.drawImage(sprites.get(9),100+x*40,200+y*40,null);
                        break;
                    default:
                        LOG.warning("Unexpected char");
                }
            }
        }
        g.setColor(Color.BLACK);
        g.drawString("Mines Remaining : "+board.getUnfoundMines(),1000,100);
        return image;
    }

    protected void stopSimulation() {

    }

}
