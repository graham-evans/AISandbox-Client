package dev.aisandbox.client;

import dev.aisandbox.client.scenarios.ServerRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Represents an external server which the scenario can talk to.
 *
 * <p>Uses the Lombok library to auto generate lots of the getters / setters.
 */
public class Agent {

  private static final Logger LOG = Logger.getLogger(Agent.class.getName());

  @Getter(AccessLevel.PROTECTED)
  private RestTemplate restTemplate = null;

  @Getter private AgentResponseLogger responseLogger = null;
  private HttpHeaders restHeaders = null;
  @Getter @Setter private boolean enableXML = false;
  @Getter private String target = "http://localhost:8080/ai";
  @Getter @Setter private boolean apiKey = false;
  @Getter @Setter private String apiKeyHeader = "";
  @Getter @Setter private String apiKeyValue = "";
  @Getter @Setter private boolean basicAuth = false;
  @Getter @Setter private String basicAuthUsername = "";
  @Getter @Setter private String basicAuthPassword = "";
  @Getter private final BooleanProperty validProperty = new SimpleBooleanProperty(true);

  private static final String RESPONSE_PARSE_ERROR = "Error parsing response";

  /**
   * Setup the agent ready for use.
   *
   * <p>This compiles the HTTP headers based on the supplied settings.
   */
  public void setupAgent() {
    LOG.log(Level.INFO, "Setting up agent to use {0}", new Object[] {enableXML ? "XML" : "JSON"});
    restHeaders = new HttpHeaders();
    if (enableXML) {
      restHeaders.setContentType(MediaType.APPLICATION_XML);
      restHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
    } else {
      restHeaders.setContentType(MediaType.APPLICATION_JSON);
      restHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    }
    // add basic auth
    if (basicAuth) {
      restHeaders.add(
          HttpHeaders.AUTHORIZATION,
          "Basic "
              + Base64.getEncoder()
                  .encodeToString((basicAuthUsername + ":" + basicAuthPassword).getBytes()));
    }
    // add API key
    if (apiKey) {
      restHeaders.add(apiKeyHeader, apiKeyValue);
    }
    // create template
    restTemplate =
        new RestTemplate(
            new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    // add logger
    List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
    if (CollectionUtils.isEmpty(interceptors)) {
      interceptors = new ArrayList<>();
    }
    responseLogger = new AgentResponseLogger();
    interceptors.add(responseLogger);
    restTemplate.setInterceptors(interceptors);
  }

  /**
   * Set the target URL
   *
   * <p>This will also update the {@code valid} property to show if the URL makes sense.
   *
   * @param url The URL in the form of a {@link java.lang.String}.
   */
  public void setTarget(String url) {
    LOG.log(Level.INFO, "Setting target to {0}", new Object[] {url});
    this.target = url;
    try {
      new URL(url);
      validProperty.set(true);
    } catch (MalformedURLException e) {
      LOG.log(Level.WARNING, "Invalid URL passed", e);
      validProperty.set(false);
    }
  }

  /**
   * Perform a GET request against the current target.
   *
   * <p>The <i>target</i> URL is used, appending the contents of <i>params</i> this could include
   * parameters.
   *
   * @param params extra information to append on the target
   * @param responseType The class of a {@link dev.aisandbox.client.scenarios.ServerResponse} object
   *     to return
   * @return the JSON or XML response deserialised into the specified class.
   */
  public <T> T getRequest(String params, Class<T> responseType) throws AgentException {
    try {
      HttpEntity<ServerRequest> requestEntity = new HttpEntity<>(null, restHeaders);
      ResponseEntity response =
          restTemplate.exchange(target + params, HttpMethod.GET, requestEntity, responseType);
      // convert
      return responseType.cast(response.getBody());
    } catch (ResourceAccessException re) {
      LOG.log(Level.SEVERE, "Error talking to remote resource", re);
      throw new AgentConnectionException("Error accessing remote resource");
    } catch (RestClientException me) {
      // get the response from the Agent logger
      LOG.log(Level.SEVERE, RESPONSE_PARSE_ERROR, me);
      LOG.log(
          Level.SEVERE,
          "Last code {0} response {1}",
          new Object[] {responseLogger.lastHTTPCode, responseLogger.lastResponse});
      throw new AgentParserException(
          "Error converting response", responseLogger.lastHTTPCode, responseLogger.lastResponse);
    } catch (Exception e) {
      throw new AgentException("Error talking to server", e);
    }
  }

  /**
   * Perform a POST request against the current target.
   *
   * @param req the request data, a instance of {@link
   *     dev.aisandbox.client.scenarios.ServerRequest}.
   * @param responseType The class of a {@link dev.aisandbox.client.scenarios.ServerResponse} object
   *     to return
   * @return the JSON or XML response deserialised into the specified class.
   */
  public <T> T postRequest(ServerRequest req, Class<T> responseType) throws AgentException {
    try {
      // request entity is created with request headers
      HttpEntity<ServerRequest> requestEntity = new HttpEntity<>(req, restHeaders);
      ResponseEntity response =
          restTemplate.exchange(target, HttpMethod.POST, requestEntity, responseType);
      // convert
      return responseType.cast(response.getBody());
    } catch (ResourceAccessException re) {
      LOG.log(Level.SEVERE, "Error talking to remote resource", re);
      throw new AgentConnectionException("Error accessing remote resource");
    } catch (RestClientException me) {
      // get the response from the Agent logger
      LOG.log(Level.SEVERE, RESPONSE_PARSE_ERROR, me);
      LOG.log(
          Level.SEVERE,
          "Last code {0} response {1}",
          new Object[] {responseLogger.lastHTTPCode, responseLogger.lastResponse});
      throw new AgentParserException(
          "Error converting response", responseLogger.lastHTTPCode, responseLogger.lastResponse);
    } catch (Exception e) {
      throw new AgentException("Error talking to server", e);
    }
  }
}
