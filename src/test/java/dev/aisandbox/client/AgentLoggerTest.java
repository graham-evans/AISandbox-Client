package dev.aisandbox.client;

import dev.aisandbox.client.scenarios.TestResponse;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class AgentLoggerTest {

    private static final Logger LOG = LoggerFactory.getLogger(AgentLoggerTest.class.getName());

    private static final String JSON_TEXT = "{\"number\":\"4\"}";

    @Test
    public void testGetJSON() throws AgentException {
        Agent a = new Agent();
        a.setTarget("http://localhost/getJSON");
        a.setEnableXML(false);
        a.setupAgent();
        // setup mock server
        MockRestServiceServer server = AgentMockTool.createMockServer(a);

        // setup expectations
        server.expect(
                requestTo("http://localhost/getJSON"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(JSON_TEXT, MediaType.APPLICATION_JSON));
        TestResponse r = a.getRequest("", TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 4, r.getNumber());
        // check the correct response was recieved
        AgentResponseLogger l = a.getResponseLogger();
        assertEquals("Status code",200,l.lastHTTPCode);
        assertEquals("Message contents",JSON_TEXT,l.lastResponse.replaceAll("\\s",""));
    }

}
