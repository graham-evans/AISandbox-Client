package dev.aisandbox.client;

import dev.aisandbox.client.scenarios.TestRequest;
import dev.aisandbox.client.scenarios.TestResponse;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class AgentTest {

  /*  @Test
    public void testRestJSON() {
        Agent a = new Agent();
        // setup REST / JSON
        a.getEnableXML().set(false);
        RestTemplate restTemplate = a.setupAgent();
        // setup mock server
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost:8080/test"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Content-Type","application/json;charset=UTF-8"))
                .andRespond(withSuccess("{}",MediaType.APPLICATION_JSON));
        a.postRequest(new TestRequest());
        server.verify();
    }

   */

    /*   @Test
       public void testRestXML() {
           Agent a = new Agent();
           // setup REST / XML
           a.getEnableXML().set(true);
           RestTemplate restTemplate = a.setupAgent();
           // setup mock server
           MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
           // setup expectations
           server.expect(
                   requestTo("http://localhost:8080/test"))
                   .andExpect(method(HttpMethod.POST))
                   .andExpect(header("Content-Type","application/XML;charset=UTF-8"))
                   .andRespond(withSuccess("<TestResponse></TestResponse>",MediaType.APPLICATION_XML));
           a.postRequest(new TestRequest());
           server.verify();
       }*/
    @Test
    public void testGetJSON() {
        Agent a = new Agent();
        a.getTarget().set("http://localhost/getJSON");
        a.getEnableXML().set(false);
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/getJSON"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"number\":\"4\"}", MediaType.APPLICATION_JSON));
        TestResponse r = a.getRequest("", TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 4, r.getNumber());
    }

    @Test
    public void testGetXML() {
        Agent a = new Agent();
        a.getTarget().set("http://localhost/getXML");
        a.getEnableXML().set(true);
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/getXML"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testResponse><number>5</number></testResponse>", MediaType.APPLICATION_XML));
        TestResponse r = a.getRequest("", TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 5, r.getNumber());
    }

    @Test
    public void testPostJSON() {
        Agent a = new Agent();
        a.getTarget().set("http://localhost/postJSON");
        a.getEnableXML().set(false);
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/postJSON"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("name").value("Betty"))
                .andRespond(withSuccess("{\"number\":\"4\"}", MediaType.APPLICATION_JSON));
        // run request
        TestRequest req = new TestRequest();
        req.setName("Betty");
        TestResponse r = a.postRequest(req, TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 4, r.getNumber());
    }

    @Test
    public void testPostXML() throws Exception {
        Agent a = new Agent();
        a.getTarget().set("http://localhost/postXML");
        a.getEnableXML().set(true);
        a.setupAgent();
        // setup mock server
        RestTemplate template = a.getRestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(template).build();
        // setup expectations
        server.expect(
                requestTo("http://localhost/postXML"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/testRequest/name").string("Fred"))
                .andRespond(withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><testResponse><number>4</number></testResponse>", MediaType.APPLICATION_XML));
        // run request
        TestRequest req = new TestRequest();
        req.setName("Fred");
        TestResponse r = a.postRequest(req, TestResponse.class);
        server.verify();
        assertEquals("Answer=4", 4, r.getNumber());
    }


}
