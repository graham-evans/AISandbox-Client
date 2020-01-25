package dev.aisandbox.client;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

public class AgentMockTool {

  public static MockRestServiceServer createMockServer(Agent agent) {
    // setup mock server
    RestTemplate template = agent.getRestTemplate();
    MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
    // workaround - recreate the buffering request factory
    final ClientHttpRequestFactory requestFactory =
        (ClientHttpRequestFactory) ReflectionTestUtils.getField(template, "requestFactory");
    template.setRequestFactory(new BufferingClientHttpRequestFactory(requestFactory));
    // return mock server
    return server;
  }
}
