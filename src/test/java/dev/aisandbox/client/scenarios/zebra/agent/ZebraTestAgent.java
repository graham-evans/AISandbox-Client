package dev.aisandbox.client.scenarios.zebra.agent;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.AgentException;
import dev.aisandbox.client.scenarios.ServerRequest;
import dev.aisandbox.client.scenarios.zebra.api.House;
import dev.aisandbox.client.scenarios.zebra.api.HouseCharacteristics;
import dev.aisandbox.client.scenarios.zebra.api.ZebraRequest;
import dev.aisandbox.client.scenarios.zebra.api.ZebraRequestCharacteristics;
import dev.aisandbox.client.scenarios.zebra.api.ZebraRequestEntries;
import dev.aisandbox.client.scenarios.zebra.api.ZebraResponse;
import java.util.List;
import java.util.Random;

public class ZebraTestAgent extends Agent {

  Random rand = new Random();

  @Override
  public <T> T postRequest(ServerRequest req, Class<T> responseType) throws AgentException {
    ZebraRequest r = (ZebraRequest) req;
    ZebraResponse response = new ZebraResponse();
    for (int houseNo = 1; houseNo <= r.getNumberOfHouses(); houseNo++) {
      House house = new House();
      house.setHousenumber(houseNo);
      for (ZebraRequestCharacteristics characteristic : r.getCharacteristics()) {
        // pick a random answer (could be duplicate)
        List<ZebraRequestEntries> entries = characteristic.getEntries();
        house
            .getCharacteristics()
            .add(
                new HouseCharacteristics(
                    characteristic.getCharacteristicNumber(),
                    entries.get(rand.nextInt(entries.size())).getCharacteristicValue()));
      }
      response.getSolution().getHouse().add(house);
    }
    return responseType.cast(response);
  }
}
