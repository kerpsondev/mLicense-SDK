package pl.kerpson.license.utilites.modules.impl.addon.basic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pl.kerpson.license.utilites.util.JsonUtil;

public final class AddonReader {

  private AddonReader() {}

  public static List<Addon> readAddons(HttpResponse<String> response) {
    Gson gson = new Gson();
    List<Addon> addons = new ArrayList<>();
    JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
    JsonArray jsonArray = jsonObject.getAsJsonArray("addons");

    for (JsonElement element : jsonArray.asList()) {
      JsonObject elementJsonObject = element.getAsJsonObject();
      addons.add(AddonReader.readAddon(elementJsonObject));
    }

    return addons;
  }

  public static Addon readAddon(HttpResponse<String> response) {
    Gson gson = new Gson();
    JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
    return readAddon(jsonObject);
  }

  public static Addon readAddon(JsonObject jsonObject) {
    return new AddonImpl(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("name").getAsString(),
        jsonObject.get("version").getAsString(),
        jsonObject.get("createdAt").getAsString(),
        JsonUtil.getValue("createdAt", "", JsonElement::getAsString, jsonObject),
        jsonObject.get("assignedUserId").getAsLong(),
        JsonUtil.getValue("builtByBitResourceId", 0L, JsonElement::getAsLong, jsonObject),
        jsonObject.get("binaryFileId").getAsLong(),
        jsonObject.get("priority").getAsInt(),
        AddonReader.readProducts(jsonObject)
    );
  }

  private static List<Integer> readProducts(JsonObject jsonObject) {
    JsonArray jsonArray = jsonObject.getAsJsonArray("products");
    return jsonArray.asList()
        .stream()
        .map(JsonElement::getAsInt)
        .collect(Collectors.toList());
  }
}
