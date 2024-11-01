package pl.kerpson.license.utilites.validation;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import pl.kerpson.license.utilites.MLicense;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.util.HardwareUtil;
import pl.kerpson.license.utilites.util.ValidationUtil;

public class BasicLicenseValidation extends LicenseValidation<HttpResponse<String>, LicenseResult> {

  private final String key;
  private final String product;
  private final String version;
  private final MSecrets secrets;

  public BasicLicenseValidation(
      String key,
      String product,
      String version,
      MSecrets secrets
  ) {
    this.key = key;
    this.product = product;
    this.version = version;
    this.secrets = secrets;
  }

  @Override
  protected HttpBuilder prepareRequest() {
    return HttpBuilder.post()
        .url(MLicense.VALIDATION_URL)
        .authorization(this.secrets.getSecret())
        .body(GSON.toJson(this.prepareData()));
  }

  @Override
  public OperationResult<LicenseResult> complete() {
    try {
      HttpResponse<String> httpResponse = this.prepareRequest().sync();
      LicenseResult result = this.getStatusFromResponse(httpResponse);
      return new OperationResult<>(result, null);
    } catch (InterruptedException | IOException exception) {
      return new OperationResult<>(null, exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<LicenseResult>> completeAsync() {
    return prepareRequest().async().thenApply(httpResponse -> {
      LicenseResult result = this.getStatusFromResponse(httpResponse);
      return new OperationResult<>(result, null);
    });
  }

  @Override
  protected JsonObject prepareData() {
    String hardwareId = HardwareUtil.getHardwareId();
    JsonObject request = new JsonObject();
    request.addProperty("key", this.key);
    request.addProperty("product", this.product);
    request.addProperty("version", this.version);
    request.addProperty("hardwareId", hardwareId);
    return request;
  }

  @Override
  protected LicenseResult getStatusFromResponse(HttpResponse<String> response) {
    String body = response.body();
    int statusCode = response.statusCode();

    JsonObject responseObject;
    try {
      responseObject = GSON.fromJson(body, JsonObject.class);
    } catch (Exception exception) {
      return new LicenseResultImpl(false, null);
    }

    if (statusCode != 200) {
      return new LicenseResultImpl(false, responseObject);
    }

    String decodedHash = new String(Base64.getDecoder().decode(responseObject.get("hash").getAsString()));
    boolean isValid = ValidationUtil.validHash(statusCode, decodedHash, this.key, this.secrets.getSecret());

    return new LicenseResultImpl(isValid, responseObject);
  }
}
