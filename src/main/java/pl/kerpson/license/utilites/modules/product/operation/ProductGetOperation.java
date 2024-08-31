package pl.kerpson.license.utilites.modules.product.operation;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.exception.IllegalStatusException;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.product.basic.Product;
import pl.kerpson.license.utilites.modules.product.basic.ProductReader;

public class ProductGetOperation implements Operation<OperationResult<Product>> {

  private final String url;
  private final MSecrets secrets;

  public ProductGetOperation(String url, MSecrets secrets) {
    this.url = url;
    this.secrets = secrets;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.get()
        .url(this.url)
        .bearer(this.secrets.getToken());
  }

  @Override
  public OperationResult<Product> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      if (response.statusCode() == 401) {
        return new OperationResult<>(null, new IllegalStatusException(401, "You don't have permission!"));
      }

      return new OperationResult<>(ProductReader.readProduct(response), null);
    } catch (Exception exception) {
      return new OperationResult<>(null, exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<Product>> completeAsync() {
    return prepareRequest().async()
        .thenApply(response -> {
          if (response.statusCode() == 401) {
            return new OperationResult<Product>(null, new IllegalStatusException(401, "You don't have permission!"));
          }

          return new OperationResult<>(ProductReader.readProduct(response), null);
        })
        .exceptionally(exception -> new OperationResult<>(null, exception));
  }
}
