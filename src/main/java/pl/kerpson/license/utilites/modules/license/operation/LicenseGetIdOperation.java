package pl.kerpson.license.utilites.modules.license.operation;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.exception.IllegalStatusException;
import pl.kerpson.license.utilites.filter.JsonFilter;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.license.basic.License;
import pl.kerpson.license.utilites.modules.license.basic.LicenseReader;

public class LicenseGetIdOperation implements Operation<OperationResult<License>> {

  private final String url;
  private final MSecrets secrets;
  private final int id;

  public LicenseGetIdOperation(String url, MSecrets secrets, int id) {
    this.url = url;
    this.secrets = secrets;
    this.id = id;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.get()
        .url(this.url)
        .bearer(this.secrets.getToken());
  }

  @Override
  public OperationResult<License> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      if (response.statusCode() == 401) {
        return new OperationResult<>(null, new IllegalStatusException(401, "You don't have permission!"));
      }

      JsonFilter jsonFilter = JsonFilter.createFilter(jsonObject -> jsonObject.get("id").getAsInt() == this.id);
      return new OperationResult<>(LicenseReader.readLicense(response, jsonFilter), null);
    } catch (Exception exception) {
      return new OperationResult<>(null, exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<License>> completeAsync() {
    return prepareRequest().async()
        .thenApply(response -> {
          if (response.statusCode() == 401) {
            return new OperationResult<License>(null, new IllegalStatusException(401, "You don't have permission!"));
          }

          JsonFilter jsonFilter = JsonFilter.createFilter(jsonObject -> jsonObject.get("id").getAsInt() == this.id);
          return new OperationResult<>(LicenseReader.readLicense(response, jsonFilter), null);
        })
        .exceptionally(exception -> new OperationResult<>(null, exception));
  }
}
