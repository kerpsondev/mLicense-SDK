package pl.kerpson.license.utilites;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.exception.MLicenseBuilderException;
import pl.kerpson.license.utilites.logger.LoggerProvider;
import pl.kerpson.license.utilites.logger.LoggerProvider.LogType;
import pl.kerpson.license.utilites.updater.LicenseUpdater;

class MBuilderImpl implements MBuilder {

  private final MSecrets secrets = new MSecretsImpl();
  private LoggerProvider logger = LoggerProvider.DEFAULT_LOGGER;

  @Override
  public MBuilderImpl key(@NotNull String key) {
    this.secrets.setKey(key);
    return this;
  }

  @Override
  public MBuilderImpl token(@NotNull String token) {
    this.secrets.setToken(token);
    return this;
  }

  @Override
  public MBuilderImpl token(@NotNull String email, @NotNull String password) {
    this.secrets.setToken(email, password);
    return this;
  }

  @Override
  public MBuilder logger(@NotNull LoggerProvider logger) {
    this.logger = logger;
    return this;
  }

  @Override
  public MLicense produce() throws MLicenseBuilderException {
    int emptySecrets = 0;
    if (this.secrets.getKey().isEmpty()) {
      this.logger.log(LogType.WARNING, "Key is empty! Validation module will not enabled.");
      emptySecrets++;
    }

    if (Objects.nonNull(this.secrets.getLoginData())) {
      this.secrets.parseTokenByLoginData(this.logger);
    }

    if (this.secrets.getToken().isEmpty()) {
      this.logger.log(LogType.WARNING, "Token is empty! Modules will not be activated.");
      emptySecrets++;
    }

    if (emptySecrets == 2) {
      throw new MLicenseBuilderException("Key and JWT Token is empty!");
    }

    this.logger.log(LogType.INFO, "ˆˆˆˆˆˆˆˆˆˆˆ  ::  ˆˆˆˆˆˆˆˆˆˆˆ");
    this.logger.log(LogType.INFO, "");
    this.logger.log(LogType.INFO, "          mLicense          ");
    this.logger.log(LogType.INFO, "   Unofficial license api   ");
    this.logger.log(LogType.INFO, "");
    this.logger.log(LogType.INFO, String.format("Key: %s", this.secrets.getKey().isEmpty() ? "empty" : "provided"));
    this.logger.log(LogType.INFO, String.format("Token: %s", this.secrets.getToken().isEmpty() ? "empty" : "provided"));
    this.logger.log(LogType.INFO, "");
    this.logger.log(LogType.INFO, "ˆˆˆˆˆˆˆˆˆˆˆ  ::  ˆˆˆˆˆˆˆˆˆˆˆ");

    LicenseUpdater.checkUpdate().thenAccept(result -> {
      this.logger.log(LogType.INFO, "Check api version...");
      if (result.isLatest()) {
        this.logger.log(LogType.INFO, String.format("You are using latest version! (%s)", result.getLatestVersion()));
      } else {
        this.logger.log(LogType.INFO, String.format("The new version of LicenseUtilities (%s) is available! Download from https://github.com/kerpsondev/LicenseUtilities", result.getLatestVersion()));
      }
    });

    return MLicenseImpl.build(this.secrets);
  }
}