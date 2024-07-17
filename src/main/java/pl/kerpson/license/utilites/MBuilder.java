package pl.kerpson.license.utilites;

import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.exception.MLicenseBuilderException;
import pl.kerpson.license.utilites.logger.LoggerProvider;

public interface MBuilder {

  MBuilder key(@NotNull String key);

  MBuilder token(@NotNull String token);

  MBuilder token(@NotNull String email, @NotNull String password);

  MBuilder logger(@NotNull LoggerProvider logger);

  MLicense produce() throws MLicenseBuilderException;
}
