package pl.kerpson.license.utilites;

import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.exception.ModuleDisabledException;
import pl.kerpson.license.utilites.modules.OperationResult;

public interface MLicense {

  String VALIDATION_URL = "https://valid.mlicense.net/api/v1/validation";

  static MBuilder builder() {
    return new MBuilderImpl();
  }

  OperationResult<Boolean> checkLicense(String key, String product, String version);

  CompletableFuture<OperationResult<Boolean>> checkLicenseAsync(String key, String product, String version);

  <T> T getModule(Class<T> clazz) throws ModuleDisabledException;
}
