package pl.kerpson.license.utilites.status;

import java.net.http.HttpResponse;
import pl.kerpson.license.utilites.exception.StatusCodeException;

public final class StatusParser {

  private StatusParser() {}

  public static StatusCode parse(HttpResponse<String> response) {
    int statusCode = response.statusCode();
    if (statusCode == 200 || statusCode == 201 || statusCode == 204) {
      return new StatusCodeImpl(true, null);
    }

    return new StatusCodeImpl(false, new StatusCodeException(statusCode, response.body()));
  }
}
