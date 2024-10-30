package pl.kerpson.license.utilites.validation;

import org.jetbrains.annotations.NotNull;

public interface LicenseResult {

  boolean isValid();

  @NotNull LicenseValidation.Status getStatus();

  @NotNull String getStatusMessage();
}
