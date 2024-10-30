package pl.kerpson.license.utilites;

import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.validation.LicenseResult;

public interface MLicense {

  String VALIDATION_URL = "https://valid.mlicense.net/api/v1/validation";

  static MBuilder builder() {
    return new MBuilderImpl();
  }

  OperationResult<LicenseResult> checkLicense(@NotNull String key, @NotNull String product, @NotNull String version);

  CompletableFuture<OperationResult<LicenseResult>> checkLicenseAsync(@NotNull String key, @NotNull String product, @NotNull String version);

  <T> T getModule(@NotNull Class<T> clazz);
}
