package pl.kerpson.license.utilites.modules.license.operation;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.license.basic.License;
import pl.kerpson.license.utilites.modules.license.basic.LicenseReader;
import pl.kerpson.license.utilites.status.StatusCode;
import pl.kerpson.license.utilites.status.StatusParser;

public class LicenseGetAllOperation implements Operation<OperationResult<List<License>>> {

  private final String url;
  private final MSecrets secrets;

  public LicenseGetAllOperation(String url, MSecrets secrets) {
    this.url = url;
    this.secrets = secrets;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.get()
        .url(this.url)
        .bearer(this.secrets.getToken());
  }

  @Override
  public OperationResult<List<License>> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      StatusCode statusCode = StatusParser.parse(response);
      if (!statusCode.isOk()) {
        return new OperationResult<>(List.of(), statusCode.getThrowable());
      }

      return new OperationResult<>(LicenseReader.readLicenses(response), null);
    } catch (Exception exception) {
      return new OperationResult<>(List.of(), exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<List<License>>> completeAsync() {
    return prepareRequest().async()
        .thenApply(response -> {
          StatusCode statusCode = StatusParser.parse(response);
          if (!statusCode.isOk()) {
            return new OperationResult<List<License>>(List.of(), statusCode.getThrowable());
          }

          return new OperationResult<>(LicenseReader.readLicenses(response), null);
        })
        .exceptionally(exception -> new OperationResult<>(List.of(), exception));
  }
}
