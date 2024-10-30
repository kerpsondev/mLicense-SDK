package pl.kerpson.license.utilites;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

class MSecretsImpl implements MSecrets {

  private String secret = StringUtils.EMPTY;
  private String apiKey = StringUtils.EMPTY;
  private String fileKey = StringUtils.EMPTY;
  private String responseKey = StringUtils.EMPTY;

  @Override
  public @NotNull String getSecret() {
    return secret;
  }

  @Override
  public MSecrets setSecret(@NotNull String secret) {
    this.secret = secret;
    return this;
  }

  @Override
  public @NotNull String getApiKey() {
    return apiKey;
  }

  @Override
  public MSecrets setApiKey(@NotNull String apiKey) {
    this.apiKey = apiKey;
    return this;
  }

  @Override
  public @NotNull String getResponseKey() {
    return responseKey;
  }

  @Override
  public MSecrets setResponseKey(@NotNull String responseKey) {
    this.responseKey = responseKey;
    return this;
  }

  @Override
  public @NotNull String getFileKey() {
    return fileKey;
  }

  @Override
  public MSecrets setFileKey(@NotNull String fileKey) {
    this.fileKey = fileKey;
    return this;
  }
}
