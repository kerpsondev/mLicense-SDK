package pl.kerpson.license.utilites.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.function.Function;

public final class JsonUtil {

  private JsonUtil() {}

  public static <T> T getValue(String name, T fallback, Function<JsonElement, T> function, JsonObject jsonObject) {
    JsonElement element = jsonObject.get(name);
    if (element.isJsonNull()) {
      return fallback;
    }

    return function.apply(element);
  }

}
