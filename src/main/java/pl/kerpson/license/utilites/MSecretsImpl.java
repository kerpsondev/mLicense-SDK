package pl.kerpson.license.utilites;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.kerpson.license.utilites.http.HttpBuilder;
import pl.kerpson.license.utilites.logger.LoggerProvider;
import pl.kerpson.license.utilites.logger.LoggerProvider.LogType;

class MSecretsImpl implements MSecrets {

  private String key = StringUtils.EMPTY;
  private String token = StringUtils.EMPTY;

  private LoginData loginData;

  @Override
  public @NotNull String getKey() {
    return key;
  }

  @Override
  public MSecrets setKey(@NotNull String key) {
    this.key = key;
    return this;
  }

  @Override
  public @NotNull String getToken() {
    return token;
  }

  @Override
  public MSecrets setToken(@NotNull String token) {
    this.token = token;
    return this;
  }

  @Override
  public MSecrets setToken(@NotNull String email, @NotNull String password) {
    this.loginData = new LoginData(email, password);
    return this;
  }

  @Override
  public @Nullable LoginData getLoginData() {
    return loginData;
  }

  @Override
  public void parseTokenByLoginData(LoggerProvider logger) {
    try {
      JsonObject requestBody = new JsonObject();
      requestBody.addProperty("email", this.loginData.getEmail());
      requestBody.addProperty("password", this.loginData.getPassword());

      HttpResponse<String> response = HttpBuilder.post()
          .url("https://api.mlicense.net/api/v1/auth/authenticate")
          .body(requestBody.toString())
          .sync();

      JsonElement responseElement = JsonParser.parseString(response.body());
      if (responseElement.isJsonNull()) {
        logger.log(LogType.WARNING, "Invalid email or password! JWT Token is empty.");
        return;
      }

      JsonObject responseData = responseElement.getAsJsonObject();
      this.token = responseData.get("jwt").getAsString();
    } catch (Exception exception) {
      logger.log(LogType.ERROR, "Error while sending http request!", exception);
    }
  }
}
