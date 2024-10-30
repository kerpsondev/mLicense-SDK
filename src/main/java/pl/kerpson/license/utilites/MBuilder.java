package pl.kerpson.license.utilites;

import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.exception.MLicenseBuilderException;
import pl.kerpson.license.utilites.logger.LoggerProvider;

public interface MBuilder {

  MBuilder secret(@NotNull String secret);

  MBuilder apiKey(@NotNull String apiKey);

  MBuilder responseKey(@NotNull String responseKey);

  MBuilder fileKey(@NotNull String fileKey);

  MBuilder logger(@NotNull LoggerProvider logger);

  MLicense produce() throws MLicenseBuilderException;
}
