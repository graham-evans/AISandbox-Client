package dev.aisandbox.client.agent;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import dev.aisandbox.client.scenarios.TestRequest;
import dev.aisandbox.client.scenarios.TestResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

public class AgentLoggerTest {

  private static final Logger LOG = LoggerFactory.getLogger(AgentLoggerTest.class.getName());

  private static final String JSON_TEXT = "{\"number\":\"4\"}";

  @Test
  public void testLogger() throws AgentException {
    Agent a = new Agent();
    a.setTarget("http://localhost/postJSON");
    a.setEnableXML(false);
    a.setupAgent();
    // setup mock server
    MockRestServiceServer server = AgentMockTool.createMockServer(a);
    // setup expectations
    server
        .expect(requestTo("http://localhost/postJSON"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("name").value("Betty"))
        .andRespond(withSuccess("{\"number\":\"4\"}", MediaType.APPLICATION_JSON));
    // run request
    TestRequest req = new TestRequest();
    req.setName("Betty");
    TestResponse r = a.postRequest(req, TestResponse.class);
    server.verify();
    assertEquals("Answer=4", 4, r.getNumber());
    // check the correct response was recieved
    AgentResponseLogger l = a.getResponseLogger();
    assertEquals("Status code", 200, l.lastHTTPCode);
    assertEquals("Message contents", JSON_TEXT, l.lastResponse.replaceAll("\\s", ""));
  }
}
