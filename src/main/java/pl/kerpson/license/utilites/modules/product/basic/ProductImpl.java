package pl.kerpson.license.utilites.modules.product.basic;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class ProductImpl implements Product {

  private final long id;
  private final String name;
  private final String version;
  private final String createdAt;
  private final long assignedUserId;
  private final String builtByBitResourceId;

  protected ProductImpl(
      long id,
      @NotNull String name,
      @NotNull String version,
      @NotNull String createdAt,
      long assignedUserId,
      @Nullable String builtByBitResourceId
  ) {
    this.id = id;
    this.name = name;
    this.version = version;
    this.createdAt = createdAt;
    this.assignedUserId = assignedUserId;
    this.builtByBitResourceId = builtByBitResourceId;
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public @NotNull String getName() {
    return name;
  }

  @Override
  public @NotNull String getVersion() {
    return version;
  }

  @Override
  public @NotNull String createdAt() {
    return createdAt;
  }

  @Override
  public long getAssignedUserId() {
    return assignedUserId;
  }

  @Override
  public @Nullable String getBuiltByBitResourceId() {
    return builtByBitResourceId;
  }

  @Override
  public String buildJsonForUpdate() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("id", this.id);
    jsonObject.addProperty("name", this.name);
    jsonObject.addProperty("version", this.version);

    return jsonObject.toString();
  }

  @Override
  public String buildJsonForCreate() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("name", this.name);
    jsonObject.addProperty("version", this.version);

    return jsonObject.toString();
  }

  static class BuilderImpl implements Builder {

    private long id;
    private String name;
    private String version;

    @Override
    public Builder setId(long id) {
      this.id = id;
      return this;
    }

    @Override
    public Builder setName(@NotNull String name) {
      this.name = name;
      return this;
    }

    @Override
    public Builder setVersion(@NotNull String version) {
      this.version = version;
      return this;
    }

    @Override
    public Product build() {
      return new ProductImpl(
          this.id,
          this.name,
          this.version,
          StringUtils.EMPTY,
          0,
          null
      );
    }
  }
}
