package pl.kerpson.license.utilites;

import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.exception.MLicenseBuilderException;
import pl.kerpson.license.utilites.logger.LoggerProvider;
import pl.kerpson.license.utilites.logger.LoggerProvider.LogType;
import pl.kerpson.license.utilites.updater.LicenseUpdater;

class MBuilderImpl implements MBuilder {

  private final MSecrets secrets = new MSecretsImpl();
  private LoggerProvider logger = LoggerProvider.DEFAULT_LOGGER;

  @Override
  public MBuilderImpl secret(@NotNull String secret) {
    this.secrets.setSecret(secret);
    return this;
  }

  @Override
  public MBuilderImpl apiKey(@NotNull String apiKey) {
    this.secrets.setApiKey(apiKey);
    return this;
  }

  @Override
  public MBuilder responseKey(@NotNull String responseKey) {
    this.secrets.setResponseKey(responseKey);
    return this;
  }

  @Override
  public MBuilder fileKey(@NotNull String fileKey) {
    this.secrets.setFileKey(fileKey);
    return this;
  }

  @Override
  public MBuilder logger(@NotNull LoggerProvider logger) {
    this.logger = logger;
    return this;
  }

  @Override
  public MLicense produce() throws MLicenseBuilderException {
    this.logger.log(LogType.INFO, "ˆˆˆˆˆˆˆˆˆˆˆ  ::  ˆˆˆˆˆˆˆˆˆˆˆ");
    this.logger.log(LogType.INFO, "");
    this.logger.log(LogType.INFO, "          mLicense          ");
    this.logger.log(LogType.INFO, "   Unofficial license api   ");
    this.logger.log(LogType.INFO, "");
    this.logger.log(LogType.INFO, String.format("Secret: %s", this.secrets.getSecret().isEmpty() ? "empty" : "provided"));
    this.logger.log(LogType.INFO, String.format("Api key: %s", this.secrets.getApiKey().isEmpty() ? "empty" : "provided"));
    this.logger.log(LogType.INFO, String.format("File key: %s", this.secrets.getFileKey().isEmpty() ? "empty" : "provided"));
    this.logger.log(LogType.INFO, String.format("Response key: %s", this.secrets.getResponseKey().isEmpty() ? "empty" : "provided"));
    this.logger.log(LogType.INFO, "");
    this.logger.log(LogType.INFO, "ˆˆˆˆˆˆˆˆˆˆˆ  ::  ˆˆˆˆˆˆˆˆˆˆˆ");

    LicenseUpdater.checkUpdate().thenAccept(result -> {
      this.logger.log(LogType.INFO, "Check api version...");
      if (result.isLatest()) {
        this.logger.log(LogType.INFO, String.format("You are using latest version! (%s)", result.getLatestVersion()));
      } else {
        this.logger.log(LogType.WARNING, String.format("The new version of mLicense-SDK (%s) is available! Download from https://github.com/kerpsondev/mLicense-SDK", result.getLatestVersion()));
      }
    });

    return MLicenseImpl.build(this.secrets);
  }
}