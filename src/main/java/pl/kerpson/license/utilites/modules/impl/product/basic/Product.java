package pl.kerpson.license.utilites.modules.impl.product.basic;

import org.jetbrains.annotations.NotNull;
import pl.kerpson.license.utilites.modules.ModuleObject;
import pl.kerpson.license.utilites.modules.impl.product.basic.ProductImpl.BuilderImpl;

public interface Product extends ModuleObject {

  static Builder createProduct() {
    return new BuilderImpl();
  }

  long getId();

  @NotNull String getName();

  @NotNull String getVersion();

  @NotNull String createdAt();

  long getAssignedUserId();

  long getBuiltByBitResourceId();

  interface Builder {

    Builder setId(long id);

    Builder setName(@NotNull String name);

    Builder setVersion(@NotNull String version);

    Builder setBuiltByBitResourceId(long builtByBitResourceId);

    Product build();
  }
}
