package pl.kerpson.license.utilites.exception;

public class IllegalStatusException extends Exception {

  private final int statusCode;

  public IllegalStatusException(int statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
