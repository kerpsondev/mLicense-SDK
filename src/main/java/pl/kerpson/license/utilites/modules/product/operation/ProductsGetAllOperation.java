package pl.kerpson.license.utilites.modules.product.operation;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.exception.IllegalStatusException;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.product.basic.Product;
import pl.kerpson.license.utilites.modules.product.basic.ProductReader;

public class ProductsGetAllOperation implements Operation<OperationResult<List<Product>>> {

  private final String url;
  private final MSecrets secrets;

  public ProductsGetAllOperation(String url, MSecrets secrets) {
    this.url = url;
    this.secrets = secrets;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.get()
        .url(this.url)
        .bearer(this.secrets.getToken());
  }

  @Override
  public OperationResult<List<Product>> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      if (response.statusCode() == 401) {
        return new OperationResult<>(List.of(), new IllegalStatusException(401, "You don't have permission!"));
      }

      return new OperationResult<>(ProductReader.readProducts(response), null);
    } catch (Exception exception) {
      return new OperationResult<>(List.of(), exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<List<Product>>> completeAsync() {
    return prepareRequest().async()
        .thenApply(response -> {
          if (response.statusCode() == 401) {
            return new OperationResult<List<Product>>(List.of(), new IllegalStatusException(401, "You don't have permission!"));
          }

          return new OperationResult<>(ProductReader.readProducts(response), null);
        })
        .exceptionally(exception -> new OperationResult<>(List.of(), exception));
  }
}
