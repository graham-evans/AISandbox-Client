package dev.aisandbox.client.scenarios.maze.agent;

import dev.aisandbox.client.agent.Agent;
import dev.aisandbox.client.agent.AgentException;
import dev.aisandbox.client.scenarios.ServerRequest;
import dev.aisandbox.client.scenarios.maze.api.MazeRequest;
import dev.aisandbox.client.scenarios.maze.api.MazeResponse;
import java.util.Random;

public class MazeTestAgent extends Agent {

  Random rand = new Random();

  @Override
  public <T> T postRequest(ServerRequest req, Class<T> responseType) throws AgentException {
    MazeRequest r = (MazeRequest) req;
    MazeResponse response = new MazeResponse();
    response.setMove(
        r.getConfig().getValidMoves()[rand.nextInt(r.getConfig().getValidMoves().length)]);
    return responseType.cast(response);
  }
}
