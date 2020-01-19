package dev.aisandbox.client.scenarios.mine.agent;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.AgentException;
import dev.aisandbox.client.scenarios.ServerRequest;
import dev.aisandbox.client.scenarios.mine.api.MineHunterRequest;
import dev.aisandbox.client.scenarios.mine.api.MineHunterResponse;
import dev.aisandbox.client.scenarios.mine.api.Move;

import java.util.Random;

public class MineTestAgent extends Agent {

    Random rand = new Random();

    @Override
    public <T> T postRequest(ServerRequest req, Class<T> responseType) throws AgentException {
        MineHunterRequest r = (MineHunterRequest) req;
        MineHunterResponse response = new MineHunterResponse();
        Move m = new Move();
                m.setX(rand.nextInt(r.getBoard()[0].length()));
                m.setY(rand.nextInt(r.getBoard().length));
                m.setFlag(rand.nextBoolean());
        response.setMoves(new Move[]{m});
        return responseType.cast(response);
    }
}
