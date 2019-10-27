package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.AgentException;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.scenarios.maze.api.History;
import dev.aisandbox.client.scenarios.maze.api.MazeRequest;
import dev.aisandbox.client.scenarios.maze.api.MazeResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>MazeRunner class.</p>
 *
 * @author gde
 * @version $Id: $Id
 */
@RequiredArgsConstructor
public class MazeRunner extends Thread {

    private static final Logger LOG = Logger.getLogger(MazeRunner.class.getName());
    private static final double REWARD_STEP = -1.0;
    private static final double REWARD_HIT_WALL = -1000.0;
    private static final double REWARD_GOAL = +1000.0;
    private final Agent agent;
    private final Maze maze;
    private final FrameOutput output;
    private final GameRunController controller;
    private final BufferedImage background;
    private static final int ORIGIN_X = (1920-1000)/2;
    private static final int ORIGIN_Y = (1080-750)/2;
    @Getter
    private boolean running = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        // setup data structures
        History lastMove = null;
        Cell currentCell = maze.getStartCell();
        // setup agent
        agent.setupAgent();
        // load graphics
        BufferedImage logo;
        try {
            logo = ImageIO.read(MazeRunner.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
        } catch (IOException e) {
            LOG.log(Level.SEVERE,"Error loading logo",e);
            logo = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
        }
        Font myFont = new Font("Sans-Serif", Font.PLAIN, 28);

        // main game loop
        running = true;
        long stepCount = 0;
        long stepToFinish = 0;
        while (running) {
            // keep timings
            Map<String, Double> timings = new TreeMap<>();
            long timer = System.currentTimeMillis();
            // work out postRequest
            MazeRequest request = new MazeRequest();
            // populate the config
            request.setConfig(maze.getConfig());
            if (lastMove != null) {
                request.setHistory(lastMove);
            }
            request.setCurrentPosition(currentCell.getPosition());
            timings.put("Setup", (double) (System.currentTimeMillis() - timer));
            // send and get response
            try {
                timer = System.currentTimeMillis();
                MazeResponse response = agent.postRequest(request, MazeResponse.class);
                LOG.log(Level.INFO, "Recieved response from server - {0}", response);
                timings.put("Network", (double) (System.currentTimeMillis() - timer));
                timer = System.currentTimeMillis();
                lastMove = new History();
                lastMove.setLastPosition(currentCell.getPosition());
                lastMove.setAction(response.getMove());
                // move the agent
                Direction direction = Direction.valueOf(response.getMove().toUpperCase());
                if (currentCell.getPaths().contains(direction)) {
                    // move to the new cell
                    currentCell = currentCell.getNeighbours().get(direction);
                    lastMove.setReward(REWARD_STEP);
                } else {
                    // hit wall
                    lastMove.setReward(REWARD_HIT_WALL);
                }
                // special case - have we found the end?
                if (currentCell.equals(maze.getEndCell())) {
                    lastMove.setReward(REWARD_GOAL);
                    currentCell = maze.getStartCell();
                    controller.addReward(stepToFinish);
                    stepToFinish=0;
                }
                LOG.log(Level.INFO, "Moved to {0}", new Object[]{currentCell});
                lastMove.setNewPosition(currentCell.getPosition());
                timings.put("Simulation", (double) (System.currentTimeMillis() - timer));
                timer = System.currentTimeMillis();
                // redraw the map
                BufferedImage image = OutputTools.getWhiteScreen();
                Graphics2D g = image.createGraphics();
                g.setFont(myFont);
                // maze
                g.drawImage(background, ORIGIN_X, ORIGIN_Y,1000,750, null);
                // player
                g.setColor(Color.yellow);
                g.fillOval(currentCell.getPositionX() * MazeRenderer.SCALE*maze.getZoomLevel() + ORIGIN_X, ORIGIN_Y+currentCell.getPositionY() * MazeRenderer.SCALE*maze.getZoomLevel() , MazeRenderer.SCALE* maze.getZoomLevel(), MazeRenderer.SCALE *maze.getZoomLevel());
                // logo
                g.drawImage(logo,(1920-90)/2,20,null);
                // state
                g.setColor(Color.BLACK);
                g.drawString("Step : "+stepCount,ORIGIN_X,1080-100);
                // update UI
                controller.updateBoardImage(image);
                // output frame
                output.addFrame(image);
                timings.put("Graphics", (double) (System.currentTimeMillis() - timer));
                controller.addResponseTimings(timings);
            } catch (AgentException ae) {
                controller.showAgentError(agent.getTarget(), ae);
                running = false;
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error running", ex);
                running = false;
            }
            stepCount++;
            stepToFinish++;
        }
        try {
            output.close();
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error closing output", e);
        }
        LOG.info("Finished run thread");
        controller.resetStartButton();
    }

    /**
     * <p>stopSimulation.</p>
     */
    public void stopSimulation() {
        running = false;
        try {
            this.join();
        } catch (InterruptedException e) {
            LOG.log(Level.WARNING, "Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
    }
}
