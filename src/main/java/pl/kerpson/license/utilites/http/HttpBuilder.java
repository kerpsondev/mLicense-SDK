package pl.kerpson.license.utilites.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

public class HttpBuilder {

  private final HttpClient client = HttpClient.newHttpClient();

  private final EndpointType endpointType;

  private String url;
  private AuthorizationType authorizationType;
  private String authorizationKey;

  private String body;

  public static HttpBuilder post() {
    return new HttpBuilder(EndpointType.POST);
  }

  public static HttpBuilder put() {
    return new HttpBuilder(EndpointType.PUT);
  }

  public static HttpBuilder delete() {
    return new HttpBuilder(EndpointType.DELETE);
  }

  public static HttpBuilder get() {
    return new HttpBuilder(EndpointType.GET);
  }

  private HttpBuilder(@NotNull EndpointType endpointType) {
    this.endpointType = endpointType;
  }

  public HttpBuilder url(@NotNull String url) {
    this.url = url;
    return this;
  }

  public HttpBuilder authorization(@NotNull String authorizationKey) {
    this.authorizationType = AuthorizationType.AUTHORIZATION;
    this.authorizationKey = authorizationKey;
    return this;
  }

  public HttpBuilder apiKey(@NotNull String authorizationKey) {
    this.authorizationType = AuthorizationType.API_KEY;
    this.authorizationKey = authorizationKey;
    return this;
  }

  public HttpBuilder body(@NotNull String body) {
    this.body = body;
    return this;
  }

  private HttpRequest buildRequest() {
    HttpRequest.Builder builder = HttpRequest.newBuilder()
        .uri(URI.create(this.url))
        .header("Content-Type", "application/json");

    if (Objects.nonNull(this.authorizationType)) {
      if (this.authorizationType == AuthorizationType.AUTHORIZATION) {
        builder.header("Authorization", this.authorizationKey);
      } else {
        builder.header("X-API-KEY", this.authorizationKey);
      }
    }

    this.endpointType.getConsumer().accept(builder, this.body);
    return builder.build();
  }

  public HttpResponse<String> sync() throws IOException, InterruptedException {
    HttpRequest request = this.buildRequest();
    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }

  public HttpResponse<byte[]> syncByte() throws IOException, InterruptedException {
    HttpRequest request = this.buildRequest();
    return client.send(request, HttpResponse.BodyHandlers.ofByteArray());
  }

  public CompletableFuture<HttpResponse<String>> async() {
    HttpRequest request = this.buildRequest();
    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
  }

  public CompletableFuture<HttpResponse<byte[]>> asyncByte() {
    HttpRequest request = this.buildRequest();
    return client.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray());
  }

  enum EndpointType {

    GET((builder, body) -> builder.GET()),
    PUT((builder, body) -> builder.PUT(HttpRequest.BodyPublishers.ofString(body))),
    POST((builder, body) -> builder.POST(HttpRequest.BodyPublishers.ofString(body))),
    DELETE((builder, body) -> builder.DELETE());

    private final BiConsumer<Builder, String> consumer;

    EndpointType(BiConsumer<Builder, String> consumer) {
      this.consumer = consumer;
    }

    public BiConsumer<Builder, String> getConsumer() {
      return consumer;
    }
  }

  enum AuthorizationType {

    API_KEY,
    AUTHORIZATION
  }
}
