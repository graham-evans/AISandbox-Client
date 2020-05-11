package dev.aisandbox.client.agent;

import dev.aisandbox.client.scenarios.ServerRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
 *
 * @author gde
 * @version $Id: $Id
 */
@Slf4j
public class Agent {

  // TODO - this class uses RestTemplate it should be migrated to WebClient for more control over
  // timeouts

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
    log.info("Setting up agent to use {}", enableXML ? "XML" : "JSON");
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
    log.info("Setting target to {}", url);
    this.target = url;
    try {
      new URL(url);
      validProperty.set(true);
    } catch (MalformedURLException e) {
      log.warn("Invalid URL passed", e);
      validProperty.set(false);
    }
  }

  /**
   * Perform a POST request against the current target.
   *
   * @param req the request data, a instance of {@link
   *     dev.aisandbox.client.scenarios.ServerRequest}.
   * @param responseType The class of a {@link dev.aisandbox.client.scenarios.ServerResponse} object
   *     to return
   * @param <T> The object type to be returned.
   * @return the JSON or XML response deserialised into the specified class.
   * @throws dev.aisandbox.client.agent.AgentException if any.
   */
  public <T> T postRequest(ServerRequest req, Class<T> responseType) throws AgentException {
    try {
      // request entity is created with request headers
      HttpEntity<ServerRequest> requestEntity = new HttpEntity<>(req, restHeaders);
      ResponseEntity response =
          restTemplate.exchange(target, HttpMethod.POST, requestEntity, responseType);
      switch (response.getStatusCode()) {
        case RESET_CONTENT:
          throw new AgentResetException("Reset content request");
        default:
          // convert
          return responseType.cast(response.getBody());
      }
    } catch (ResourceAccessException re) {
      log.error("Error talking to remote resource", re);
      throw new AgentConnectionException("Error accessing remote resource");
    } catch (RestClientException me) {
      // get the response from the Agent logger
      log.error(RESPONSE_PARSE_ERROR, me);
      log.error(
          "Last code {} response {}", responseLogger.lastHTTPCode, responseLogger.lastResponse);
      throw new AgentParserException(
          "Error converting response", responseLogger.lastHTTPCode, responseLogger.lastResponse);
    }
  }
}
