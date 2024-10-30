package pl.kerpson.license.utilites.modules.operations;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.status.StatusCode;
import pl.kerpson.license.utilites.status.StatusParser;

public class MultiGetOperation<T> implements Operation<OperationResult<List<T>>> {

  private final String url;
  private final MSecrets secrets;
  private final Function<HttpResponse<String>, List<T>> function;

  public MultiGetOperation(
      String url,
      MSecrets secrets,
      Function<HttpResponse<String>, List<T>> function
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
  public OperationResult<List<T>> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      StatusCode statusCode = StatusParser.parse(response);
      if (!statusCode.isOk()) {
        return new OperationResult<>(new ArrayList<>(), statusCode.getThrowable());
      }

      return new OperationResult<>(this.function.apply(response), null);
    } catch (Exception exception) {
      return new OperationResult<>(new ArrayList<>(), exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<List<T>>> completeAsync() {
    return prepareRequest().async()
        .thenApply(response -> {
          StatusCode statusCode = StatusParser.parse(response);
          if (!statusCode.isOk()) {
            return new OperationResult<List<T>>(new ArrayList<>(), statusCode.getThrowable());
          }

          return new OperationResult<>(this.function.apply(response), null);
        })
        .exceptionally(exception -> new OperationResult<>(new ArrayList<>(), exception));
  }
}
