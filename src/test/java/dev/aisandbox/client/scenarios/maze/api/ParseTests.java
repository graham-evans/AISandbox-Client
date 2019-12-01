package dev.aisandbox.client.scenarios.maze.api;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.AgentMockTool;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.xpath;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

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
        server.expect(
                requestTo("http://localhost/postXML"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(xpath("/MazeRequest/config/boardID").string("board"))
                .andExpect(xpath("/MazeRequest/config/width").string("10"))
                .andExpect(xpath("/MazeRequest/config/height").string("10"))
                .andExpect(xpath("/MazeRequest/history/lastPosition/x").string("0"))
                .andExpect(xpath("/MazeRequest/history/lastPosition/y").string("0"))
                .andExpect(xpath("/MazeRequest/history/action").string("North"))
                .andExpect(xpath("/MazeRequest/history/reward").string("-1.0"))
                .andExpect(xpath("/MazeRequest/history/newPosition/x").string("0"))
                .andExpect(xpath("/MazeRequest/history/newPosition/y").string("0"))
                .andExpect(xpath("/MazeRequest/currentPosition/x").string("0"))
                .andExpect(xpath("/MazeRequest/currentPosition/y").string("0"))
                .andRespond(withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<MazeResponse>\n" +
                        "    <move>North</move>\n" +
                        "</MazeResponse>", MediaType.APPLICATION_XML));
        // run request

        History history = new History();
        history.setLastPosition(new Position(0,0));
        history.setAction("North");
        history.setNewPosition(new Position(0,0));
        history.setReward(-1);

        Config config = new Config();
        config.setBoardID("board");
        config.setHeight(10);
        config.setWidth(10);

        MazeRequest request = new MazeRequest();
        request.setHistory(history);
        request.setConfig(config);
        request.setCurrentPosition(new Position(0,0));

        MazeResponse response = a.postRequest(request,MazeResponse.class);
        server.verify();
        assertEquals("Answer=north", "North", response.getMove());
    }

    @Test
    public void testPostJSON() throws Exception {
        Agent a = new Agent();
        a.setTarget("http://localhost/postJSON");
        a.setEnableXML(false);
        a.setupAgent();
        // setup mock server
        MockRestServiceServer server = AgentMockTool.createMockServer(a);
        // setup expectations
        server.expect(
                requestTo("http://localhost/postJSON"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.config.boardID").value("board"))
                .andExpect(jsonPath("$.config.width").value("10"))
                .andExpect(jsonPath("$.config.height").value("10"))
                .andExpect(jsonPath("$.history.lastPosition.x").value("0"))
                .andExpect(jsonPath("$.history.lastPosition.y").value("0"))
                .andExpect(jsonPath("$.history.action").value("North"))
                .andExpect(jsonPath("$.history.reward").value("-1.0"))
                .andExpect(jsonPath("$.history.newPosition.x").value("0"))
                .andExpect(jsonPath("$.history.newPosition.y").value("0"))
                .andExpect(jsonPath("$.currentPosition.x").value("0"))
                .andExpect(jsonPath("$.currentPosition.y").value("0"))
                .andRespond(withSuccess("{\n" +
                        "  \"move\": \"North\"\n" +
                        "}", MediaType.APPLICATION_JSON));
        // run request

        History history = new History();
        history.setLastPosition(new Position(0,0));
        history.setAction("North");
        history.setNewPosition(new Position(0,0));
        history.setReward(-1);

        Config config = new Config();
        config.setBoardID("board");
        config.setHeight(10);
        config.setWidth(10);

        MazeRequest request = new MazeRequest();
        request.setHistory(history);
        request.setConfig(config);
        request.setCurrentPosition(new Position(0,0));

        MazeResponse response = a.postRequest(request,MazeResponse.class);
        server.verify();
        assertEquals("Answer=north", "North", response.getMove());
    }

}
