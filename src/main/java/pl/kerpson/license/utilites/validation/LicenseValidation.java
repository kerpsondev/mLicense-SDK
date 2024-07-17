package pl.kerpson.license.utilites.validation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.Nullable;
import pl.kerpson.license.utilites.MLicense;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.util.HardwareUtil;
import pl.kerpson.license.utilites.util.ValidationUtil;

public class LicenseValidation implements Operation<OperationResult<Boolean>> {

  private final Gson gson;

  private final String key;
  private final String product;
  private final String version;
  private final MSecrets secrets;

  public LicenseValidation(
      String key,
      String product,
      String version,
      MSecrets secrets
  ) {
    this.gson = new Gson();
    this.key = key;
    this.product = product;
    this.version = version;
    this.secrets = secrets;
  }

  private HttpBuilder prepareRequest() {
    return HttpBuilder.post()
        .url(MLicense.VALIDATION_URL)
        .authorization(this.secrets.getKey())
        .body(this.prepareData());
  }

  @Override
  public OperationResult<Boolean> complete() {
    try {
      HttpResponse<String> httpResponse = this.prepareRequest().sync();
      Response response = this.getStatusFromResponse(httpResponse);
      return getResultFromStatus(response);
    } catch (IOException | InterruptedException exception) {
      throw new RuntimeException(exception);
    }
  }

  @Override
  public CompletableFuture<OperationResult<Boolean>> completeAsync() {
    return prepareRequest().async().thenApply(httpResponse -> {
      Response response = this.getStatusFromResponse(httpResponse);
      return getResultFromStatus(response);
    });
  }

  private String prepareData() {
    String hardwareId = HardwareUtil.getHardwareId();
    return String.format("{\"key\":\"%s\",\"product\":\"%s\",\"version\":\"%s\",\"hardwareId\":\"%s\"}", key, product, version, hardwareId);
  }

  private String determineErrorReason(Response status) {
    try {
      return status.getObject().get("message").getAsString();
    } catch (Exception e) {
      return "Unknown error";
    }
  }

  private OperationResult<Boolean> getResultFromStatus(Response status) {
    if (status.isValid()) {
      return new OperationResult<>(true, null);
    }

    String reason = determineErrorReason(status);
    return new OperationResult<>(true, new Exception(reason));
  }

  private Response getStatusFromResponse(HttpResponse<String> response) {
    JsonObject object = this.parse(response.body());
    if (object.has("code")) {
      return new Response(false, object);
    }

    String hash = new String(Base64.getDecoder().decode(object.get("hash").getAsString()));
    byte statusByte = (byte) response.statusCode();

    boolean isValid = ValidationUtil.validateHashComponents(
        statusByte,
        hash,
        this.secrets.getKey(),
        this.key
    );
    
    return new Response(isValid, object);
  }

  private JsonObject parse(String body) {
    return gson.fromJson(body, JsonObject.class);
  }

  public static class Response {

    private final boolean valid;
    private final JsonObject object;

    public Response(boolean valid, JsonObject object) {
      this.valid = valid;
      this.object = object;
    }

    public boolean isValid() {
      return valid;
    }

    public JsonObject getObject() {
      return object;
    }
  }

  public static class Result {

    private final boolean valid;
    private final String errorCode;

    public Result(boolean valid, @Nullable String errorCode) {
      this.valid = valid;
      this.errorCode = errorCode;
    }

    public boolean isValid() {
      return valid;
    }

    public @Nullable String getErrorCode() {
      return errorCode;
    }
  }
}
