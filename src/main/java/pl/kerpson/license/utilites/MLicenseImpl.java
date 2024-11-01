package pl.kerpson.license.utilites;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.exception.ModuleDisabledException;
import pl.kerpson.license.utilites.modules.Module;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.impl.addon.AddonModule;
import pl.kerpson.license.utilites.modules.impl.blacklist.BlacklistModule;
import pl.kerpson.license.utilites.modules.impl.license.LicenseModule;
import pl.kerpson.license.utilites.modules.impl.product.ProductModule;
import pl.kerpson.license.utilites.validation.BasicLicenseValidation;
import pl.kerpson.license.utilites.validation.LicenseResult;

class MLicenseImpl implements MLicense {

  private final Map<Class<? extends Module<?>>, Module<?>> modules;

  private final MSecrets secrets;

  protected static MLicenseImpl build(MSecrets secrets) {
    return new MLicenseImpl(secrets);
  }

  private MLicenseImpl(MSecrets secrets) {
    this.secrets = secrets;

    this.modules = ImmutableMap.of(
        AddonModule.class, AddonModule.buildModule(this.secrets),
        LicenseModule.class, LicenseModule.buildModule(this.secrets),
        ProductModule.class, ProductModule.buildModule(this.secrets),
        BlacklistModule.class, BlacklistModule.buildModule(this.secrets)
    );
  }

  @Override
  public OperationResult<LicenseResult> checkLicense(@NotNull String key, @NotNull String product, @NotNull String version) throws ModuleDisabledException {
    if (this.secrets.getSecret().isEmpty()) {
      throw new ModuleDisabledException("Validation module is not enabled! Fill secret key from your account");
    }

    BasicLicenseValidation basicLicenseValidation = new BasicLicenseValidation(
        key,
        product,
        version,
        this.secrets
    );

    return basicLicenseValidation.complete();
  }

  @Override
  public CompletableFuture<OperationResult<LicenseResult>> checkLicenseAsync(@NotNull String key, @NotNull String product, @NotNull String version) throws ModuleDisabledException {
    if (this.secrets.getSecret().isEmpty()) {
      throw new ModuleDisabledException("Validation module is not enabled! Fill secret key from your account");
    }

    BasicLicenseValidation basicLicenseValidation = new BasicLicenseValidation(
        key,
        product,
        version,
        this.secrets
    );

    return basicLicenseValidation.completeAsync();
  }

  @Override
  public <T> T getModule(@NotNull Class<T> clazz) throws ModuleDisabledException {
    if (this.secrets.getApiKey().isEmpty()) {
      throw new ModuleDisabledException(String.format("Module %s is not enabled! Fill api key from your account", clazz.getName()));
    }

    return (T) modules.get(clazz);
  }
}
