package pl.kerpson.license.utilites.validation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;

public abstract class LicenseValidation<STATUS_RESPONSE, TYPE> implements Operation<OperationResult<TYPE>> {

  protected static final Gson GSON = new GsonBuilder()
      .setPrettyPrinting()
      .disableHtmlEscaping()
      .create();

  protected abstract HttpBuilder prepareRequest();

  protected abstract JsonObject prepareData();

  protected abstract LicenseResult getStatusFromResponse(STATUS_RESPONSE response);

  public enum Status {

    OK,
    UNKNOWN,
    MAX_IP_IN_USE,
    BAD_IP_ADDRESS,
    BLACKLISTED_IP,
    BLACKLISTED_HWID,
    PRODUCT_NOT_FOUND,
    PRODUCT_NOT_MATCH,
    LICENSE_NOT_FOUND,
    MAX_MACHINES_IN_USE,
    LICENSE_NOT_ASSIGNED,
    SECRET_KEY_NOT_FOUND
  }
}
