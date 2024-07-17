package pl.kerpson.license.utilites.modules.blacklist.operation;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.exception.IllegalStatusException;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.blacklist.basic.Blacklist;
import pl.kerpson.license.utilites.modules.blacklist.basic.BlacklistReader;

public class BlacklistGetAllOperation implements Operation<OperationResult<List<Blacklist>>> {

  private final String url;
  private final MSecrets secrets;

  public BlacklistGetAllOperation(String url, MSecrets secrets) {
    this.url = url;
    this.secrets = secrets;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.get()
        .url(this.url)
        .bearer(this.secrets.getToken());
  }

  @Override
  public OperationResult<List<Blacklist>> complete() {
    try {
      HttpResponse<String> response = this.prepareRequest().sync();
      if (response.statusCode() == 401) {
        return new OperationResult<>(List.of(), new IllegalStatusException(401, "You don't have permission!"));
      }

      return new OperationResult<>(BlacklistReader.readBlacklist(response), null);
    } catch (Exception exception) {
      return new OperationResult<>(List.of(), exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<List<Blacklist>>> completeAsync() {
    return prepareRequest().async()
        .thenApply(response -> {
          if (response.statusCode() == 401) {
            return new OperationResult<List<Blacklist>>(List.of(), new IllegalStatusException(401, "You don't have permission!"));
          }

          return new OperationResult<>(BlacklistReader.readBlacklist(response), null);
        })
        .exceptionally(exception -> new OperationResult<>(List.of(), exception));
  }
}
