package pl.kerpson.license.utilites.modules.blacklist.operation;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.exception.ProductCreateException;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.blacklist.basic.Blacklist;

public class BlacklistUpdateOperation implements Operation<OperationResult<Boolean>> {

  private final String url;
  private final MSecrets secrets;
  private final Blacklist blacklist;

  public BlacklistUpdateOperation(String url, MSecrets secrets, Blacklist blacklist) {
    this.url = url;
    this.secrets = secrets;
    this.blacklist = blacklist;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.put()
        .url(this.url)
        .body(this.blacklist.buildJsonForUpdate())
        .bearer(this.secrets.getToken());
  }

  @Override
  public OperationResult<Boolean> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      if (response.statusCode() == 401) {
        return new OperationResult<>(false, new ProductCreateException("You don't have permission to create blacklist."));
      }

      if (response.statusCode() == 409) {
        return new OperationResult<>(false, new ProductCreateException("Blacklist with this value and type already exists."));
      }

      if (response.statusCode() == 423) {
        return new OperationResult<>(false, new ProductCreateException("You don't have any plan assigned or you have reached the limit of licenses."));
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
        return new OperationResult<>(false, new ProductCreateException("You don't have permission to create blacklist."));
      }

      if (response.statusCode() == 409) {
        return new OperationResult<>(false, new ProductCreateException("Blacklist with this value and type already exists."));
      }

      if (response.statusCode() == 423) {
        return new OperationResult<>(false, new ProductCreateException("You don't have any plan assigned or you have reached the limit of licenses."));
      }

      return new OperationResult<>(true, null);
    }).exceptionally(exception -> new OperationResult<>(false, exception));
  }
}
