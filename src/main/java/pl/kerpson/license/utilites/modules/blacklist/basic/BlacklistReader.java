package pl.kerpson.license.utilites.modules.blacklist.basic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import pl.kerpson.license.utilites.modules.blacklist.basic.Blacklist.Type;

public final class BlacklistReader {

  private BlacklistReader() {}

  public static List<Blacklist> readBlacklists(HttpResponse<String> response) {
    Gson gson = new Gson();
    List<Blacklist> blacklists = new ArrayList<>();
    JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);

    for (JsonElement element : jsonArray.asList()) {
      JsonObject jsonObject = element.getAsJsonObject();
      blacklists.add(BlacklistReader.readBlacklist(jsonObject));
    }

    return blacklists;
  }

  public static Blacklist readBlacklist(HttpResponse<String> response) {
    Gson gson = new Gson();
    JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
    return readBlacklist(jsonObject);
  }

  public static Blacklist readBlacklist(JsonObject jsonObject) {
    return new BlacklistImpl(
        jsonObject.get("id").getAsInt(),
        jsonObject.get("value").getAsString(),
        jsonObject.get("requests").getAsInt(),
        jsonObject.get("createdAt").getAsString(),
        Type.valueOf(jsonObject.get("type").getAsString().toUpperCase()),
        jsonObject.get("assignedUserId").getAsInt()
    );
  }
}
