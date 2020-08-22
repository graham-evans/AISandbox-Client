package dev.aisandbox.client.scenarios.bandit;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.output.OutputTools;
import dev.aisandbox.client.output.charts.AverageRewardGraph;
import dev.aisandbox.client.output.charts.BanditGraph;
import dev.aisandbox.client.output.charts.OptimalActionGraph;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.RuntimeResponse;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.SimulationException;
import dev.aisandbox.client.scenarios.bandit.api.BanditRequest;
import dev.aisandbox.client.scenarios.bandit.api.BanditRequestHistory;
import dev.aisandbox.client.scenarios.bandit.api.BanditResponse;
import dev.aisandbox.client.scenarios.bandit.model.BanditNormalEnumeration;
import dev.aisandbox.client.scenarios.bandit.model.BanditSession;
import dev.aisandbox.client.scenarios.bandit.model.BanditStdEnumeration;
import dev.aisandbox.client.scenarios.bandit.model.BanditUpdateEnumeration;
import dev.aisandbox.client.scenarios.twisty.TwistyRuntime;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BanditRuntime implements ScenarioRuntime {

  private Agent agent;
  private final Random rand;
  private final int banditCount;
  private final int pullCount;
  private final BanditNormalEnumeration normal;
  private final BanditStdEnumeration std;
  private final BanditUpdateEnumeration updateRule;
  private final boolean skipGraphics;
  private BanditSession currentSession;
  private int iteration;
  private BufferedImage logo;
  private AverageRewardGraph averageRewardGraph;
  private OptimalActionGraph optimalActionGraph;
  private BanditGraph banditGraph;

  @Override
  public void setAgents(List<Agent> agents) {
    agent = agents.get(0);
    if (agents.size() > 1) {
      log.error("Passed multiple agents when only one will be used.");
    }
  }

  @Override
  public void initialise() {
    try {
      // load logo
      logo =
          ImageIO.read(
              TwistyRuntime.class.getResourceAsStream("/dev/aisandbox/client/fx/logo1.png"));
    } catch (IOException e) {
      log.error("Error loading logo", e);
      logo = new BufferedImage(0, 0, BufferedImage.TYPE_INT_RGB);
    }
    currentSession = new BanditSession(rand, banditCount, normal, std);
    averageRewardGraph = new AverageRewardGraph(900, 400, pullCount);
    optimalActionGraph = new OptimalActionGraph(pullCount);
    banditGraph = new BanditGraph(800, 400);
    banditGraph.setBandits(currentSession.getBandits());
    iteration = 0;
  }

  BanditRequestHistory history = null;

  @Override
  public RuntimeResponse advance() throws AgentException, SimulationException {
    ProfileStep profileStep = new ProfileStep();
    BanditRequest request = new BanditRequest();
    request.setHistory(history);
    request.setSessionID(currentSession.getSessionID());
    request.setBanditCount(banditCount);
    request.setPullCount(pullCount);
    request.setPull(iteration);
    log.info("Requesting next pull");
    BanditResponse response = agent.postRequest(request, BanditResponse.class);
    profileStep.addStep("Network");
    // resolve the response
    // TODO - check if arm exists (array out of bounds?)
    history = new BanditRequestHistory();
    history.setSessionID(currentSession.getSessionID());
    history.setChosenBandit(response.getArm());
    double reward = currentSession.activateBandit(response.getArm());
    history.setReward(reward);
    // was this the best move?
    boolean best = currentSession.isBestMean(response.getArm());
    // store result
    averageRewardGraph.addReward(iteration, reward);
    optimalActionGraph.addReward(iteration, best ? 100.0 : 0.0);
    // update bandits
    switch (updateRule) {
      case RANDOM:
        currentSession.updateRandom();
        break;
      case EQUALISE:
        currentSession.updateEqualise(response.getArm());
        break;
      case FADE:
        currentSession.updateFade(response.getArm());
        break;
      default: // FIXED
        // no action
    }
    profileStep.addStep("Simulation");
    // draw screen
    BufferedImage image = null;
    if (!skipGraphics || (iteration == 0)) {
      image = OutputTools.getWhiteScreen();
      Graphics2D graphics2D = image.createGraphics();
      // draw logo
      graphics2D.drawImage(logo, 100, 50, null);
      // draw ave reward
      graphics2D.drawImage(averageRewardGraph.getImage(), 100, 200, null);
      graphics2D.drawImage(optimalActionGraph.getGraph(900, 400), 100, 650, null);
      // draw bandits
      graphics2D.drawImage(banditGraph.getImage(), 1000, 200, null);
    }
    profileStep.addStep("Graphics");
    // check for end of run
    iteration++;
    if (iteration == pullCount) {
      // reset run
      iteration = 0;
      currentSession = new BanditSession(rand, banditCount, normal, std);
      banditGraph.setBandits(currentSession.getBandits());
    }
    profileStep.addStep("Simulation");
    return new RuntimeResponse(profileStep, image);
  }

  @Override
  public void writeStatistics(File statisticsOutputFile) {
    try {
      PrintWriter out = new PrintWriter(new FileWriter(statisticsOutputFile));
      out.println("Step,Ave Reward,% Optimal Action");
      for (int i = 0; i < pullCount; i++) {
        out.print(i);
        out.print(",");
        out.print(averageRewardGraph.getAveRewards()[i]);
        out.print(",");
        out.println(optimalActionGraph.getAveRewards()[i]);
      }
      out.close();
    } catch (IOException e) {
      log.warn("Error writing statistics", e);
    }
  }
}
