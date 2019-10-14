package dev.aisandbox.client.scenarios.maze;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.fx.GameRunController;
import dev.aisandbox.client.output.FrameOutput;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.scenarios.maze.api.History;
import dev.aisandbox.client.scenarios.maze.api.MazeRequest;
import dev.aisandbox.client.scenarios.maze.api.MazeResponse;
import javafx.application.Platform;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
    private static final double REWARD_STEP = -0.1;
    private static final double REWARD_HIT_WALL = -1.0;
    private static final double REWARD_GOAL = +50.0;
    private final Agent agent;
    private final Maze maze;
    private final FrameOutput output;
    private final GameRunController controller;
    private boolean running = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        // setup data structures
        History lastMove = null;
        Cell currentCell = maze.getStartCell();
        // get background
        BufferedImage background = maze.toImage();
        // setup agent
        agent.setupAgent();
        // setup graph
        controller.addResponseChartCategory("AI");
        controller.addResponseChartCategory("Model");
        controller.addResponseChartCategory("Rendering");
        // main game loop
        running = true;
        long step = 1;
        while (running) {
            // work out postRequest
            MazeRequest request = new MazeRequest();
            // populate the config
            request.setConfig(maze.getConfig());
            if (lastMove != null) {
                request.setHistory(lastMove);
            }
            request.setCurrentPosition(currentCell.getPosition());
            // send and get response
            try {
                MazeResponse response = agent.postRequest(request, MazeResponse.class);
                LOG.log(Level.INFO, "Recieved response from server - {0}", response);
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
                }
                LOG.log(Level.INFO, "Moved to {0}", new Object[]{currentCell});
                lastMove.setNewPosition(currentCell.getPosition());
                // redraw the map
                long startTime = System.currentTimeMillis();
                BufferedImage image = OutputTools.getWhiteScreen();
                Graphics2D g = image.createGraphics();
                g.drawImage(background, 0, 0, null);

                g.setColor(Color.yellow);
                g.fillOval(currentCell.getPositionX() * Maze.SCALE + 1, currentCell.getPositionY() * Maze.SCALE + 1, Maze.SCALE - 2, Maze.SCALE - 2);
                // update UI
                controller.updateBoardImage(image);
                // output frame
                output.addFrame(image);
                long stopTime = System.currentTimeMillis();
                controller.addResponseReading("Rendering", step, (stopTime - startTime));
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Error running", ex);
                running = false;
            }
            step++;
        }
        try {
            output.close();
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error closing output", e);
        }
        LOG.info("Finished run thread");
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
