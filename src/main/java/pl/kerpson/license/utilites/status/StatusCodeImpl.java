package pl.kerpson.license.utilites.status;

import org.jetbrains.annotations.Nullable;

class StatusCodeImpl implements StatusCode {

  private final boolean ok;
  private final Throwable throwable;

  public StatusCodeImpl(boolean ok, @Nullable Throwable throwable) {
    this.ok = ok;
    this.throwable = throwable;
  }

  @Override
  public boolean isOk() {
    return ok;
  }

  @Override
  public @Nullable Throwable getThrowable() {
    return throwable;
  }
}
