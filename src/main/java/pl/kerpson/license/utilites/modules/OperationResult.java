package pl.kerpson.license.utilites.modules;

import org.jetbrains.annotations.Nullable;

public class OperationResult<T> {

  private final T result;
  private final Throwable throwable;

  public OperationResult(@Nullable T result, @Nullable Throwable throwable) {
    this.result = result;
    this.throwable = throwable;
  }

  public T getResult() {
    return result;
  }

  public @Nullable Throwable getThrowable() {
    return throwable;
  }
}
