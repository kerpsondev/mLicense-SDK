package pl.kerpson.license.utilites;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.kerpson.license.utilites.logger.LoggerProvider;

public interface MSecrets {

  @NotNull String getKey();

  MSecrets setKey(@NotNull String key);

  @NotNull String getToken();

  MSecrets setToken(@NotNull String token);

  MSecrets setToken(@NotNull String email, @NotNull String password);

  @Nullable LoginData getLoginData();

  void parseTokenByLoginData(LoggerProvider logger);

  class LoginData {

    private final String email;
    private final String password;

    public LoginData(@NotNull String email, @NotNull String password) {
      this.email = email;
      this.password = password;
    }

    public @NotNull String getEmail() {
      return email;
    }

    public @NotNull String getPassword() {
      return password;
    }
  }
}
