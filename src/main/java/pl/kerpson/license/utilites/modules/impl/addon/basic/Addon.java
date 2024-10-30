package pl.kerpson.license.utilites.modules.impl.addon.basic;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.kerpson.license.utilites.modules.ModuleObject;
import pl.kerpson.license.utilites.modules.impl.addon.basic.AddonImpl.BuilderImpl;

public interface Addon extends ModuleObject {

  static Builder createAddon() {
    return new BuilderImpl();
  }

  long getId();

  @NotNull String getName();

  @NotNull String getVersion();

  @NotNull String createdAt();

  long getAssignedUserId();

  long getBuiltByBitResourceId();

  @Nullable String getMainClass();

  long getBinaryFileId();

  int getPriority();

  @NotNull List<Integer> getProducts();

  interface Builder {

    Builder setId(long id);

    Builder setName(@NotNull String name);

    Builder setVersion(@NotNull String version);

    Builder setProducts(@NotNull List<Integer> products);

    Builder setPriority(int priority);

    Builder setBuiltByBitResourceId(long builtByBitResourceId);

    Builder setMainClass(@Nullable String mainClass);

    Builder setBinaryFileId(long binaryFileId);

    Addon build();

  }
}
