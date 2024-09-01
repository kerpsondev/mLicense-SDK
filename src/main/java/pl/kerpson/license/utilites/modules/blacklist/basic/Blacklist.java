package pl.kerpson.license.utilites.modules.blacklist.basic;

import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.modules.blacklist.basic.BlacklistImpl.BuilderImpl;

public interface Blacklist {

  static Builder createBlacklist() {
    return new BuilderImpl();
  }

  long getId();

  @NotNull String getValue();

  int getRequests();

  @NotNull String getCreatedAt();

  @NotNull Type getType();

  long getAssignedUserId();

  String buildJsonForUpdate();

  String buildJsonForCreate();

  enum Type {

    IP,
    HWID
  }

  interface Builder {

    Builder setId(long id);

    Builder setValue(@NotNull String value);

    Builder setType(@NotNull Type type);

    Blacklist build();
  }
}
