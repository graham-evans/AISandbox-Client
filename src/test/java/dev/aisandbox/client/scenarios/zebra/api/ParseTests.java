package dev.aisandbox.client.scenarios.zebra.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.xpath;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.AgentMockTool;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

public class ParseTests {

  @Test
  public void testPostXML() throws Exception {
    Agent a = new Agent();
    a.setTarget("http://localhost/postXML");
    a.setEnableXML(true);
    a.setupAgent();
    // setup mock server
    MockRestServiceServer server = AgentMockTool.createMockServer(a);
    // setup expectations
    server
        .expect(requestTo("http://localhost/postXML"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(content().contentType(MediaType.APPLICATION_XML))
        .andExpect(xpath("/ZebraRequest/history/puzzleID").string("1233-5678-90abc"))
        .andExpect(xpath("/ZebraRequest/puzzleID").string("1234-1234-1234-1234"))
        .andRespond(
            withSuccess(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<ZebraResponse>\n"
                    + "\t<solution>\n"
                    + "\t\t<house>\n"
                    + "\t\t\t<housenumber>1</housenumber>\n"
                    + "\t\t\t<characteristics>\n"
                    + "\t\t\t\t<characteristicNumber>3</characteristicNumber>\n"
                    + "\t\t\t\t<characteristicValue>4</characteristicValue>\n"
                    + "\t\t\t</characteristics>\n"
                    + "\t\t</house>\n"
                    + "\t</solution>\n"
                    + "</ZebraResponse>",
                MediaType.APPLICATION_XML));
    // run request
    ZebraRequestHistory history = new ZebraRequestHistory();
    history.setPuzzleID("1233-5678-90abc");
    House h = new House();
    h.getCharacteristics().add(new HouseCharacteristics(3, 4));
    history.getSolution().getHouse().add(h);
    history.setScore(3400);
    ZebraRequest request = new ZebraRequest();
    request.setHistory(history);
    request.setPuzzleID("1234-1234-1234-1234");
    request.getClues().add("The person who lives at number 4 has a red front door");
    ZebraResponse response = a.postRequest(request, ZebraResponse.class);
    server.verify();
    assertNotNull("No Solution", response.getSolution());
    assertEquals("Wrong number of houses", 1, response.getSolution().getHouse().size());
    House hs = response.getSolution().getHouse().get(0);
    assertEquals("House number", 1, hs.getHousenumber());
    assertEquals("House characteristic size", 1, hs.getCharacteristics().size());
    HouseCharacteristics hc = h.getCharacteristics().get(0);
    assertEquals("Characteristic number", 3, hc.getCharacteristicNumber());
    assertEquals("Characteristic value", 4, hc.getCharacteristicValue());
  }
}
