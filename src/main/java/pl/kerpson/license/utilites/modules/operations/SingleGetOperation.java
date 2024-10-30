package pl.kerpson.license.utilites.modules.operations;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.status.StatusCode;
import pl.kerpson.license.utilites.status.StatusParser;

public class SingleGetOperation<T> implements Operation<OperationResult<T>> {

  private final String url;
  private final MSecrets secrets;
  private final Function<HttpResponse<String>, T> function;

  public SingleGetOperation(
      String url,
      MSecrets secrets,
      Function<HttpResponse<String>, T> function
  ) {
    this.url = url;
    this.secrets = secrets;
    this.function = function;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.get()
        .url(this.url)
        .apiKey(this.secrets.getApiKey());
  }

  @Override
  public OperationResult<T> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      StatusCode statusCode = StatusParser.parse(response);
      if (!statusCode.isOk()) {
        return new OperationResult<>(null, statusCode.getThrowable());
      }

      return new OperationResult<>(this.function.apply(response), null);
    } catch (Exception exception) {
      return new OperationResult<>(null, exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<T>> completeAsync() {
    return prepareRequest().async()
        .thenApply(response -> {
          StatusCode statusCode = StatusParser.parse(response);
          if (!statusCode.isOk()) {
            return new OperationResult<T>(null, statusCode.getThrowable());
          }

          return new OperationResult<>(this.function.apply(response), null);
        })
        .exceptionally(exception -> new OperationResult<>(null, exception));
  }
}
