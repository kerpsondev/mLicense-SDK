package pl.kerpson.license.utilites.modules.impl.blacklist.basic;

import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.modules.ModuleObject;
import pl.kerpson.license.utilites.modules.impl.blacklist.basic.BlacklistImpl.BuilderImpl;

public interface Blacklist extends ModuleObject {

  static Builder createBlacklist() {
    return new BuilderImpl();
  }

  long getId();

  @NotNull String getValue();

  int getRequests();

  @NotNull String getCreatedAt();

  @NotNull Type getType();

  long getAssignedUserId();

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
