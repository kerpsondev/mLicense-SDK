package pl.kerpson.license.utilites.modules.blacklist.operation;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.blacklist.basic.Blacklist;
import pl.kerpson.license.utilites.modules.blacklist.basic.BlacklistReader;
import pl.kerpson.license.utilites.status.StatusCode;
import pl.kerpson.license.utilites.status.StatusParser;

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
      StatusCode statusCode = StatusParser.parse(response);
      if (!statusCode.isOk()) {
        return new OperationResult<>(List.of(), statusCode.getThrowable());
      }

      return new OperationResult<>(BlacklistReader.readBlacklists(response), null);
    } catch (Exception exception) {
      return new OperationResult<>(List.of(), exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<List<Blacklist>>> completeAsync() {
    return prepareRequest().async()
        .thenApply(response -> {
          StatusCode statusCode = StatusParser.parse(response);
          if (!statusCode.isOk()) {
            return new OperationResult<List<Blacklist>>(List.of(), statusCode.getThrowable());
          }

          return new OperationResult<>(BlacklistReader.readBlacklists(response), null);
        })
        .exceptionally(exception -> new OperationResult<>(List.of(), exception));
  }
}
