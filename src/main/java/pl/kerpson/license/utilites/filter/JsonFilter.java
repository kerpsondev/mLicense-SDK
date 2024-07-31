package pl.kerpson.license.utilites.filter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public class JsonFilter {

  private final Predicate<JsonObject> filter;

  public static JsonFilter createFilter(Predicate<JsonObject> filter) {
    return new JsonFilter(filter);
  }

  private JsonFilter(Predicate<JsonObject> filter) {
    this.filter = filter;
  }

  public <T> List<T> applyToCollection(JsonArray jsonArray, Function<JsonObject, T> function) {
    List<T> list = new ArrayList<>();
    for (JsonElement element : jsonArray.asList()) {
      JsonObject jsonObject = element.getAsJsonObject();
      if (!this.filter.test(jsonObject)) {
        continue;
      }

      list.add(function.apply(jsonObject));
    }

    return list;
  }

  public <T> @Nullable T applySingle(JsonArray jsonArray, Function<JsonObject, T> function) {
    for (JsonElement element : jsonArray.asList()) {
      JsonObject jsonObject = element.getAsJsonObject();
      if (!this.filter.test(jsonObject)) {
        continue;
      }

      return function.apply(jsonObject);
    }

    return null;
  }
}
