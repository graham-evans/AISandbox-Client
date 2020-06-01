package dev.aisandbox.client.agent;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import dev.aisandbox.client.scenarios.TestRequest;
import dev.aisandbox.client.scenarios.TestResponse;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;

public class AgentHTTPErrorTest {
  @Test(expected = AgentFileNotFoundException.class)
  public void test404Error() throws AgentException {
    Agent a = new Agent();
    a.setTarget("http://localhost/xxxx");
    a.setEnableXML(false);
    a.setupAgent();
    // setup mock server
    MockRestServiceServer server = AgentMockTool.createMockServer(a);
    // setup expectations
    server
        .expect(requestTo("http://localhost/xxxx"))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withStatus(HttpStatus.NOT_FOUND));
    // run request
    TestRequest req = new TestRequest();
    req.setName("Betty");
    TestResponse r = a.postRequest(req, TestResponse.class);
    server.verify();
  }
}
