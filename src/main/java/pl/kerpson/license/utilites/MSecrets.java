package pl.kerpson.license.utilites;

import org.jetbrains.annotations.NotNull;

public interface MSecrets {

  @NotNull String getSecret();

  MSecrets setSecret(@NotNull String secret);

  @NotNull String getApiKey();

  MSecrets setApiKey(@NotNull String apikey);

  @NotNull String getResponseKey();

  MSecrets setResponseKey(@NotNull String responseKey);

  @NotNull String getFileKey();

  MSecrets setFileKey(@NotNull String fileKey);
}
