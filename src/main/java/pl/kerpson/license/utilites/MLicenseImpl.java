package pl.kerpson.license.utilites;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.exception.ModuleDisabledException;
import pl.kerpson.license.utilites.modules.Module;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.blacklist.BlacklistModule;
import pl.kerpson.license.utilites.modules.license.LicenseModule;
import pl.kerpson.license.utilites.modules.product.ProductModule;
import pl.kerpson.license.utilites.validation.LicenseValidation;

class MLicenseImpl implements MLicense {

  private final Map<Class<? extends Module<?>>, Module<?>> modules;

  private final MSecrets secrets;

  protected static MLicenseImpl build(MSecrets secrets) {
    return new MLicenseImpl(secrets);
  }

  private MLicenseImpl(MSecrets secrets) {
    this.secrets = secrets;

    this.modules = ImmutableMap.of(
        LicenseModule.class, LicenseModule.buildModule(this.secrets),
        ProductModule.class, ProductModule.buildModule(this.secrets),
        BlacklistModule.class, BlacklistModule.buildModule(this.secrets)
    );
  }

  @Override
  public OperationResult<Boolean> checkLicense(String key, String product, String version) {
    LicenseValidation licenseValidation = new LicenseValidation(
        key,
        product,
        version,
        this.secrets
    );

    return licenseValidation.complete();
  }

  @Override
  public CompletableFuture<OperationResult<Boolean>> checkLicenseAsync(String key, String product, String version) {
    LicenseValidation licenseValidation = new LicenseValidation(
        key,
        product,
        version,
        this.secrets
    );

    return licenseValidation.completeAsync();
  }

  @Override
  public <T> T getModule(Class<T> clazz) throws ModuleDisabledException {
    if (this.secrets.getToken().isEmpty()) {
      throw new ModuleDisabledException("Module is disabled because you don't fill token.");
    }

    return (T) modules.get(clazz);
  }
}
