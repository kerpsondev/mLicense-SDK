package pl.kerpson.license.utilites.modules.blacklist.operation;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.exception.LicenseUpdateException;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;

public class BlacklistDeleteOperation implements Operation<OperationResult<Boolean>> {

  private final String url;
  private final MSecrets secrets;

  public BlacklistDeleteOperation(String url, MSecrets secrets) {
    this.url = url;
    this.secrets = secrets;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.delete()
        .url(this.url)
        .bearer(this.secrets.getToken());
  }

  @Override
  public OperationResult<Boolean> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      if (response.statusCode() == 401) {
        return new OperationResult<>(false, new LicenseUpdateException("You don't have permission to create licenses."));
      }

      if (response.statusCode() == 404) {
        return new OperationResult<>(false, new LicenseUpdateException("License not found."));
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
        return new OperationResult<>(false, new LicenseUpdateException("You don't have permission to create licenses."));
      }

      if (response.statusCode() == 404) {
        return new OperationResult<>(false, new LicenseUpdateException("License not found."));
      }

      return new OperationResult<>(true, null);
    }).exceptionally(exception -> new OperationResult<>(false, exception));
  }
}
