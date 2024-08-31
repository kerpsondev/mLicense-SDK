package pl.kerpson.license.utilites.exception;

import org.jetbrains.annotations.NotNull;

public class StatusCodeException extends Exception {

  public StatusCodeException(int statusCode, @NotNull String message) {
    super(String.format("Status code: %s | %s", statusCode, message));
  }
}
