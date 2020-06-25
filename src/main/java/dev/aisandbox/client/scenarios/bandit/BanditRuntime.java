package dev.aisandbox.client.scenarios.bandit;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.profiler.ProfileStep;
import dev.aisandbox.client.scenarios.RuntimeResponse;
import dev.aisandbox.client.scenarios.ScenarioRuntime;
import dev.aisandbox.client.scenarios.SimulationException;
import dev.aisandbox.client.scenarios.bandit.api.BanditRequest;
import dev.aisandbox.client.scenarios.bandit.api.BanditRequestHistory;
import dev.aisandbox.client.scenarios.bandit.api.BanditResponse;
import dev.aisandbox.client.scenarios.bandit.model.BanditSession;
import java.io.File;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class BanditRuntime implements ScenarioRuntime {

  private Agent agent;
  private final Random rand;
  private final int banditCount;
  private final int pullCount;
  private final double banditMean;
  private final double banditVar;
  private BanditSession currentSession;

  @Override
  public void setAgents(List<Agent> agents) {
    agent = agents.get(0);
    if (agents.size() > 1) {
      log.error("Passed multiple agents when only one will be used.");
    }
  }

  @Override
  public void initialise() {
    currentSession = new BanditSession(rand, banditCount);
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
    log.info("Requesting next pull");
    BanditResponse response = agent.postRequest(request, BanditResponse.class);
    // resolve the response
    history = new BanditRequestHistory();
    history.setSessionID(currentSession.getSessionID());
    history.setPull(response.getArm());
    double reward = currentSession.selectBandit(response.getArm());
    history.setReward(reward);

    return null;
  }

  @Override
  public void writeStatistics(File statisticsOutputFile) {
    // TODO - Implement statistics
  }
}
