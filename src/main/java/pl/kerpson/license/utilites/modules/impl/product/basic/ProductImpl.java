package pl.kerpson.license.utilites.modules.impl.product.basic;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

class ProductImpl implements Product {

  private final long id;
  private final String name;
  private final String version;
  private final String createdAt;
  private final long assignedUserId;
  private final long builtByBitResourceId;

  protected ProductImpl(
      long id,
      @NotNull String name,
      @NotNull String version,
      @NotNull String createdAt,
      long assignedUserId,
      long builtByBitResourceId
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
  public long getBuiltByBitResourceId() {
    return builtByBitResourceId;
  }

  @Override
  public String buildJsonForUpdate() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("id", this.id);
    jsonObject.addProperty("name", this.name);
    jsonObject.addProperty("version", this.version);
    if (this.builtByBitResourceId != 0L) {
      jsonObject.addProperty("builtByBitResourceId", this.builtByBitResourceId);
    }

    return jsonObject.toString();
  }

  @Override
  public String buildJsonForCreate() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("name", this.name);
    jsonObject.addProperty("version", this.version);
    if (this.builtByBitResourceId != 0L) {
      jsonObject.addProperty("builtByBitResourceId", this.builtByBitResourceId);
    }

    return jsonObject.toString();
  }

  static class BuilderImpl implements Builder {

    private long id;
    private String name;
    private String version;
    private long builtByBitResourceId;

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
    public Builder setBuiltByBitResourceId(long builtByBitResourceId) {
      this.builtByBitResourceId = builtByBitResourceId;
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
          builtByBitResourceId
      );
    }
  }
}
