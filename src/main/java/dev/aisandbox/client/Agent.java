package dev.aisandbox.client;

import dev.aisandbox.client.scenarios.ServerRequest;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Agent {

    private static final Logger LOG = Logger.getLogger(Agent.class.getName());
    @Getter(AccessLevel.PROTECTED)
    private RestTemplate restTemplate = null;
    private HttpHeaders restHeaders = null;
    @Getter
    private BooleanProperty enableXML = new SimpleBooleanProperty(false);
    @Getter
    private StringProperty target = new SimpleStringProperty("http://localhost:8080/ai");
    @Getter
    private BooleanProperty apiKey = new SimpleBooleanProperty(false);
    @Getter
    private StringProperty apiKeyHeader = new SimpleStringProperty("");
    @Getter
    private StringProperty apiKeyValue = new SimpleStringProperty("");
    @Getter
    private BooleanProperty basicAuth = new SimpleBooleanProperty(false);
    @Getter
    private StringProperty basicAuthUsername = new SimpleStringProperty("");
    @Getter
    private StringProperty basicAuthPassword = new SimpleStringProperty("");

    public void setupAgent() {
        LOG.log(Level.INFO, "Setting up agent to use {}", enableXML.getValue() ? "XML" : "JSON");
        restHeaders = new HttpHeaders();
        if (enableXML.get()) {
            restHeaders.setContentType(MediaType.APPLICATION_XML);
            restHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        } else {
            restHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
            restHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        }

        restTemplate = new RestTemplate();

/*        restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);*/
    }


    public <T> T getRequest(String loc, Class<T> responseType) {
        ResponseEntity response = restTemplate.exchange(
                target.getValue() + loc,
                HttpMethod.GET,
                null,
                responseType
        );
        // convert
        return responseType.cast(response.getBody());
    }

    public <T> T postRequest(ServerRequest req, Class<T> responseType) {
        //request entity is created with request headers
        HttpEntity<ServerRequest> requestEntity = new HttpEntity<>(req, restHeaders);
        ResponseEntity response = restTemplate.exchange(
                target.getValue(),
                HttpMethod.POST,
                requestEntity,
                responseType
        );
        // convert
        return responseType.cast(response.getBody());
    }


}
