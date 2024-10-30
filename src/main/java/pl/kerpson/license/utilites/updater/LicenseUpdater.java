package pl.kerpson.license.utilites.updater;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.http.HttpBuilder;

public final class LicenseUpdater {

  private final static String CURRENT_VERSION = "1.0.1";

  private LicenseUpdater() {}

  public static CompletableFuture<Result> checkUpdate() {
    return HttpBuilder.get()
        .url("https://api.github.com/repos/kerpsondev/mLicense-SDK/releases/latest")
        .async()
        .thenApply(response -> {
          Gson gson = new Gson();
          JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
          String latestVersion = jsonObject.get("tag_name").getAsString();
          if (CURRENT_VERSION.equals(latestVersion)) {
            return new Result(true, latestVersion);
          }

          return new Result(false, latestVersion);
        });
  }

  public static class Result {

    private final boolean latest;
    private final String latestVersion;

    public Result(boolean latest, @NotNull String latestVersion) {
      this.latest = latest;
      this.latestVersion = latestVersion;
    }

    public boolean isLatest() {
      return latest;
    }

    public @NotNull String getLatestVersion() {
      return latestVersion;
    }
  }
}
