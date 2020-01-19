package dev.aisandbox.client.fx;

import dev.aisandbox.client.profiler.ProfileStep;

import java.awt.image.BufferedImage;

public class FakeGameRunController extends GameRunController {


    @Override
    public void addProfileStep(ProfileStep step) {
        // IGNORE - Do Nothing
    }

    @Override
    public void updateBoardImage(BufferedImage image) {
        // IGNORE - Do Nothing
    }

    @Override
    public void resetStartButton() {
        // IGNORE - Do Nothing
    }

    @Override
    public void showAgentError(String agentURL, Exception e) {
        // IGNORE - Do Nothing
    }

    @Override
    public void showAgentError(String agentURL, String description, String details) {
        // IGNORE - Do Nothing
    }
}