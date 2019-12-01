package dev.aisandbox.client.scenarios.mine.api;

import dev.aisandbox.client.Agent;
import dev.aisandbox.client.AgentMockTool;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
                .andExpect(xpath("/MineRequest/lastMove/boardID").string("1233-5678-90abc"))
                .andExpect(xpath("/MineRequest/lastMove/result").string("LOST"))
                .andExpect(xpath("/MineRequest/boardID").string("1234-1234-1234-1234"))
                .andExpect(xpath("/MineRequest/board[1]").string("##1..")) // note XPath indexes are 1-based
                .andExpect(xpath("/MineRequest/board[2]").string("##1.."))
                .andExpect(xpath("/MineRequest/board[3]").string("221.."))
                .andExpect(xpath("/MineRequest/board[4]").string("#1..."))
                .andRespond(withSuccess("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<MineResponse>\n" +
                        "    <moves>\n" +
                        "        <x>1</x>\n" +
                        "        <y>1</y>\n" +
                        "        <flag>true</flag>\n" +
                        "    </moves>\n" +
                        "</MineResponse>", MediaType.APPLICATION_XML));
        // run request

        MineHunterRequest request = new MineHunterRequest();
        LastMove last = new LastMove();
        last.setBoardID("1233-5678-90abc");
        last.setResult("LOST");
        request.setLastMove(last);
        request.setBoardID("1234-1234-1234-1234");
        request.setBoard(new String[] {"##1..","##1..","221..","#1..."});

        MineHunterResponse response = a.postRequest(request,MineHunterResponse.class);
        server.verify();
        assertEquals("Moves = 1", 1, response.getMoves().length);
        assertEquals("Move x",1,response.getMoves()[0].getX());
        assertEquals("Move y",1,response.getMoves()[0].getY());
        assertTrue("flag",response.getMoves()[0].isFlag());
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
                .andExpect(jsonPath("$.lastMove.boardID").value("1233-5678-90abc"))
                .andExpect(jsonPath("$.lastMove.result").value("LOST"))
                .andExpect(jsonPath("$.boardID").value("1234-1234-1234-1234"))
                .andExpect(jsonPath("$.board.[0]").value("##1..")) // note JSON indexes are 0-based
                .andExpect(jsonPath("$.board.[1]").value("##1.."))
                .andExpect(jsonPath("$.board.[2]").value("221.."))
                .andExpect(jsonPath("$.board.[3]").value("#1..."))
                .andRespond(withSuccess("{\n" +
                        "  \"moves\": [\n" +
                        "    {\n" +
                        "      \"x\": 1,\n" +
                        "      \"y\": 1,\n" +
                        "      \"flag\": true\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}", MediaType.APPLICATION_JSON));
        // run request

        MineHunterRequest request = new MineHunterRequest();
        LastMove last = new LastMove();
        last.setBoardID("1233-5678-90abc");
        last.setResult("LOST");
        request.setLastMove(last);
        request.setBoardID("1234-1234-1234-1234");
        request.setBoard(new String[] {"##1..","##1..","221..","#1..."});

        MineHunterResponse response = a.postRequest(request,MineHunterResponse.class);
        server.verify();
        assertEquals("Moves = 1", 1, response.getMoves().length);
        assertEquals("Move x",1,response.getMoves()[0].getX());
        assertEquals("Move y",1,response.getMoves()[0].getY());
        assertTrue("flag",response.getMoves()[0].isFlag());
    }

}
