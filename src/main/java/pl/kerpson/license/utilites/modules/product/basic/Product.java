package pl.kerpson.license.utilites.modules.product.basic;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.kerpson.license.utilites.modules.product.basic.ProductImpl.BuilderImpl;

public interface Product {

  static Builder createProduct() {
    return new BuilderImpl();
  }

  int getId();

  @NotNull String getName();

  @NotNull String getVersion();

  @NotNull String createdAt();

  int getAssignedUserId();

  @Nullable String getBuiltByBitResourceId();

  String buildJsonForUpdate();

  String buildJsonForCreate();

  interface Builder {

    Builder setId(int id);

    Builder setName(@NotNull String name);

    Builder setVersion(@NotNull String version);

    Product build();
  }
}
