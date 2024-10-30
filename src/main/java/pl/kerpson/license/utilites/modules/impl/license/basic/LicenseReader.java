package pl.kerpson.license.utilites.modules.impl.license.basic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pl.kerpson.license.utilites.modules.impl.license.basic.License.AddressInfo.Request;
import pl.kerpson.license.utilites.modules.impl.license.basic.License.MachineInfo;
import pl.kerpson.license.utilites.modules.impl.license.basic.LicenseImpl.AddressInfoImpl;
import pl.kerpson.license.utilites.modules.impl.license.basic.LicenseImpl.ClientInfoImpl;
import pl.kerpson.license.utilites.modules.impl.license.basic.LicenseImpl.DurationInfoImpl;
import pl.kerpson.license.utilites.modules.impl.license.basic.LicenseImpl.MachineInfoImpl;
import pl.kerpson.license.utilites.util.JsonUtil;

public final class LicenseReader {

  private LicenseReader() {}

  public static List<License> readLicenses(HttpResponse<String> response) {
    Gson gson = new Gson();
    List<License> licenses = new ArrayList<>();
    JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);

    for (JsonElement element : jsonArray.asList()) {
      JsonObject jsonObject = element.getAsJsonObject();
      licenses.add(LicenseReader.readLicense(jsonObject));
    }

    return licenses;
  }

  public static License readLicense(HttpResponse<String> response) {
    Gson gson = new Gson();
    JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
    return LicenseReader.readLicense(jsonObject);
  }

  public static License readLicense(JsonObject jsonObject) {
    return new LicenseImpl(
        jsonObject.get("id").getAsLong(),
        jsonObject.get("key").getAsString(),
        jsonObject.get("productId").getAsInt(),
        JsonUtil.getValue("description", null, JsonElement::getAsString, jsonObject),
        LicenseReader.readAddons(jsonObject),
        jsonObject.get("assignedUserId").getAsLong(),
        LicenseReader.readClientInfo(jsonObject),
        LicenseReader.readAddressInfo(jsonObject),
        LicenseReader.readMachineInfo(jsonObject),
        LicenseReader.readDurationInfo(jsonObject)
    );
  }

  private static List<Long> readAddons(JsonObject jsonObject) {
    JsonArray jsonArray = jsonObject.getAsJsonArray("addons");
    if (jsonArray.isJsonNull() || jsonArray.isEmpty()) {
      return new ArrayList<>();
    }

    return jsonArray.asList()
        .stream()
        .map(JsonElement::getAsLong)
        .collect(Collectors.toList());
  }

  private static License.ClientInfo readClientInfo(JsonObject jsonObject) {
    JsonObject clientObject = jsonObject.getAsJsonObject("client");
    return new ClientInfoImpl(
        clientObject.get("name").getAsString(),
        JsonUtil.getValue("email", null, JsonElement::getAsString, clientObject),
        JsonUtil.getValue("discordId", 0L, JsonElement::getAsLong, clientObject)
    );
  }

  private static License.AddressInfo readAddressInfo(JsonObject jsonObject) {
    JsonObject addressObject = jsonObject.getAsJsonObject("address");
    List<License.AddressInfo.Request> requests = new ArrayList<>();
    JsonArray requestsArray = addressObject.getAsJsonArray("requests");

    for (JsonElement jsonElement : requestsArray.asList()) {
      JsonObject requestObject = jsonElement.getAsJsonObject();
      requests.add(new Request(
          requestObject.get("date").getAsString(),
          requestObject.get("address").getAsString()
      ));
    }

    return new AddressInfoImpl(
        addressObject.get("limit").getAsInt(),
        addressObject.get("duration").getAsLong(),
        JsonUtil.getValue("assignedTo", null, JsonElement::getAsString, addressObject),
        JsonUtil.getValue("lastAddress", null, JsonElement::getAsString, addressObject),
        requests
    );
  }

  private static License.MachineInfo readMachineInfo(JsonObject jsonObject) {
    JsonObject machineObject = jsonObject.getAsJsonObject("machine");
    List<License.MachineInfo.Request> requests = new ArrayList<>();
    JsonArray requestsArray = machineObject.getAsJsonArray("requests");

    for (JsonElement jsonElement : requestsArray.asList()) {
      JsonObject requestObject = jsonElement.getAsJsonObject();
      requests.add(new MachineInfo.Request(
          requestObject.get("date").getAsString(),
          requestObject.get("hwid").getAsString()
      ));
    }

    return new MachineInfoImpl(
        machineObject.get("limit").getAsInt(),
        machineObject.get("duration").getAsLong(),
        JsonUtil.getValue("assignedTo", null, JsonElement::getAsString, machineObject),
        JsonUtil.getValue("lastMachine", null, JsonElement::getAsString, machineObject),
        requests
    );
  }

  private static License.DurationInfo readDurationInfo(JsonObject jsonObject) {
    JsonObject durationObject = jsonObject.getAsJsonObject("duration");

    return new DurationInfoImpl(
        durationObject.get("duration").getAsLong(),
        durationObject.get("createdAt").getAsString(),
        JsonUtil.getValue("lastRequest", null, JsonElement::getAsString, durationObject),
        durationObject.get("deleteAfterExpire").getAsBoolean(),
        durationObject.get("active").getAsBoolean(),
        JsonUtil.getValue("expiresAt", null, JsonElement::getAsString, durationObject)
    );
  }
}
