package pl.kerpson.license.utilites.modules.impl.product.basic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import pl.kerpson.license.utilites.util.JsonUtil;

public final class ProductReader {

  private ProductReader() {}

  public static List<Product> readProducts(HttpResponse<String> response) {
    Gson gson = new Gson();
    List<Product> products = new ArrayList<>();
    JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
    JsonArray jsonArray = jsonObject.getAsJsonArray("products");

    for (JsonElement element : jsonArray.asList()) {
      JsonObject elementJsonObject = element.getAsJsonObject();
      products.add(ProductReader.readProduct(elementJsonObject));
    }

    return products;
  }

  public static Product readProduct(HttpResponse<String> response) {
    Gson gson = new Gson();
    JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
    return readProduct(jsonObject);
  }

  public static Product readProduct(JsonObject jsonObject) {
    return new ProductImpl(
        jsonObject.get("id").getAsLong(),
        jsonObject.get("name").getAsString(),
        jsonObject.get("version").getAsString(),
        jsonObject.get("createdAt").getAsString(),
        jsonObject.get("assignedUserId").getAsLong(),
        JsonUtil.getValue("builtByBitResourceId", 0L, JsonElement::getAsLong, jsonObject)
    );
  }
}
