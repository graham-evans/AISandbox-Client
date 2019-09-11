package dev.aisandbox.client;

import dev.aisandbox.client.scenarios.ServerRequest;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AccessLevel;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import lombok.Setter;
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
    @Setter
    private boolean enableXML = false;
    @Getter
    private String target = "http://localhost:8080/ai";
    @Getter
    @Setter
    private boolean apiKey = false;
    @Getter
    @Setter
    private String apiKeyHeader = "";
    @Getter
    @Setter
    private String apiKeyValue = "";
    @Getter
    @Setter
    private boolean basicAuth = false;
    @Getter
    @Setter
    private String basicAuthUsername = "";
    @Getter
    @Setter
    private String basicAuthPassword = "";
    @Getter
    private final BooleanProperty validProperty = new SimpleBooleanProperty(true);

    public void setupAgent() {
        LOG.log(Level.INFO, "Setting up agent to use {0}", new Object[]{enableXML ? "XML" : "JSON"});
        restHeaders = new HttpHeaders();
        if (enableXML) {
            restHeaders.setContentType(MediaType.APPLICATION_XML);
            restHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        } else {
            restHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
            restHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        }
        // add basic auth
        if (basicAuth) {
            restHeaders.add(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString(
                    (basicAuthUsername + ":" + basicAuthPassword).getBytes())
            );
        }
        // add API key
        if (apiKey) {
            restHeaders.add(apiKeyHeader, apiKeyValue);
        }
        restTemplate = new RestTemplate();
        // TODO replace commented out code with better debugging
/*        restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new LoggingRequestInterceptor());
        restTemplate.setInterceptors(interceptors);*/
    }

    public void setTarget(String url) {
        this.target = url;
        try {
            new URL(url);
            validProperty.set(true);
        } catch (MalformedURLException e) {
            LOG.log(Level.FINE, "Invalid URL passed", e);
            validProperty.set(false);
        }
    }

    public <T> T getRequest(String loc, Class<T> responseType) {
        HttpEntity<ServerRequest> requestEntity = new HttpEntity<>(null, restHeaders);
        ResponseEntity response = restTemplate.exchange(
                target + loc,
                HttpMethod.GET,
                requestEntity,
                responseType
        );
        // convert
        return responseType.cast(response.getBody());
    }

    public <T> T postRequest(ServerRequest req, Class<T> responseType) {
        //request entity is created with request headers
        HttpEntity<ServerRequest> requestEntity = new HttpEntity<>(req, restHeaders);
        ResponseEntity response = restTemplate.exchange(
                target,
                HttpMethod.POST,
                requestEntity,
                responseType
        );
        // convert
        return responseType.cast(response.getBody());
    }


}
