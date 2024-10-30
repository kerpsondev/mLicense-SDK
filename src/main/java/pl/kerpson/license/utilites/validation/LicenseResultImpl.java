package pl.kerpson.license.utilites.validation;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.validation.LicenseValidation.Status;

class LicenseResultImpl implements LicenseResult {

  private final boolean valid;
  private final JsonObject object;

  LicenseResultImpl(boolean valid, JsonObject object) {
    this.valid = valid;
    this.object = object;
  }

  @Override
  public boolean isValid() {
    return valid;
  }

  @Override
  public @NotNull Status getStatus() {
    if (this.object == null || !this.object.has("code")) {
      return Status.UNKNOWN;
    }

    return Status.valueOf(this.object.get("code").getAsString());
  }

  @Override
  public @NotNull String getStatusMessage() {
    if (this.object == null || !this.object.has("message")) {
      return "UNKNOWN";
    }

    if (this.getStatus() == Status.OK) {
      return "OK";
    }

    return object.get("message").getAsString();
  }
}
