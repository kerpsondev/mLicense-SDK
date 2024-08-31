package pl.kerpson.license.utilites.status;

import org.jetbrains.annotations.Nullable;

public interface StatusCode {

  boolean isOk();

  @Nullable Throwable getThrowable();
}
