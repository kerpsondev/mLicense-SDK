package pl.kerpson.license.utilites.modules.product.operation;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.exception.ProductUpdateException;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.product.basic.Product;

public class ProductUpdateOperation implements Operation<OperationResult<Boolean>> {

  private final String url;
  private final MSecrets secrets;
  private final Product product;

  public ProductUpdateOperation(String url, MSecrets secrets, Product product) {
    this.url = url;
    this.secrets = secrets;
    this.product = product;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.post()
        .url(this.url)
        .body(this.product.buildJsonForUpdate())
        .bearer(this.secrets.getToken());
  }

  @Override
  public OperationResult<Boolean> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      if (response.statusCode() == 401) {
        return new OperationResult<>(false, new ProductUpdateException("You don't have permission to create product."));
      }

      if (response.statusCode() == 409) {
        return new OperationResult<>(false, new ProductUpdateException("Product with this name already exists."));
      }

      if (response.statusCode() == 423) {
        return new OperationResult<>(false, new ProductUpdateException("You don't have any plan assigned or you have reached the limit of licenses."));
      }

      return new OperationResult<>(true, null);
    } catch (Exception exception) {
      return new OperationResult<>(false, exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<Boolean>> completeAsync() {
    return prepareRequest().async().thenApply(response -> {
      if (response.statusCode() == 401) {
        return new OperationResult<>(false, new ProductUpdateException("You don't have permission to create product."));
      }

      if (response.statusCode() == 409) {
        return new OperationResult<>(false, new ProductUpdateException("Product with this name already exists."));
      }

      if (response.statusCode() == 423) {
        return new OperationResult<>(false, new ProductUpdateException("You don't have any plan assigned or you have reached the limit of licenses."));
      }

      return new OperationResult<>(true, null);
    }).exceptionally(exception -> new OperationResult<>(false, exception));
  }
}
