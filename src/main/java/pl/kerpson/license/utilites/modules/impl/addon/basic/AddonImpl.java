package pl.kerpson.license.utilites.modules.impl.addon.basic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class AddonImpl implements Addon {

  private final long id;
  private final String name;
  private final String version;
  private final String createdAt;
  private final String mainClass;
  private final long assignedUserId;
  private final long builtByBitResourceId;
  private final long binaryFileId;
  private final int priority;
  private final List<Integer> products;

  public AddonImpl(
      long id,
      String name,
      String version,
      String createdAt,
      String mainClass,
      long assignedUserId,
      long builtByBitResourceId,
      long binaryFileId,
      int priority,
      List<Integer> products
  ) {
    this.id = id;
    this.name = name;
    this.version = version;
    this.createdAt = createdAt;
    this.mainClass = mainClass;
    this.assignedUserId = assignedUserId;
    this.builtByBitResourceId = builtByBitResourceId;
    this.binaryFileId = binaryFileId;
    this.priority = priority;
    this.products = products;
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
  public @Nullable String getMainClass() {
    return mainClass;
  }

  @Override
  public long getBinaryFileId() {
    return binaryFileId;
  }

  @Override
  public int getPriority() {
    return priority;
  }

  @Override
  public @NotNull List<Integer> getProducts() {
    return products;
  }

  @Override
  public String buildJsonForUpdate() {
    JsonArray productsArray = new JsonArray();
    this.products.forEach(productsArray::add);

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("id", this.id);
    jsonObject.addProperty("name", this.name);
    jsonObject.addProperty("version", this.version);
    jsonObject.add("products", productsArray);
    jsonObject.addProperty("priority", this.priority);

    if (this.builtByBitResourceId != 0L) {
      jsonObject.addProperty("builtByBitResourceId", this.builtByBitResourceId);
    }

    if (!Objects.isNull(this.mainClass)) {
      jsonObject.addProperty("mainClass", this.mainClass);
    }

    if (this.binaryFileId != 0L) {
      jsonObject.addProperty("binaryFileId", this.binaryFileId);
    }

    return jsonObject.toString();
  }

  @Override
  public String buildJsonForCreate() {
    JsonArray productsArray = new JsonArray();
    this.products.forEach(productsArray::add);

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("name", this.name);
    jsonObject.addProperty("version", this.version);
    jsonObject.add("products", productsArray);
    jsonObject.addProperty("priority", this.priority);

    if (this.builtByBitResourceId != 0L) {
      jsonObject.addProperty("builtByBitResourceId", this.builtByBitResourceId);
    }

    if (!Objects.isNull(this.mainClass)) {
      jsonObject.addProperty("mainClass", this.mainClass);
    }

    if (this.binaryFileId != 0L) {
      jsonObject.addProperty("binaryFileId", this.binaryFileId);
    }

    return jsonObject.toString();
  }

  static class BuilderImpl implements Builder {

    private long id;
    private String name;
    private String version;
    private String mainClass;
    private long builtByBitResourceId;
    private long binaryFileId;
    private int priority;
    private List<Integer> products;

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
    public Builder setProducts(@NotNull List<Integer> products) {
      this.products = products;
      return this;
    }

    @Override
    public Builder setPriority(int priority) {
      this.priority = priority;
      return this;
    }

    @Override
    public Builder setBuiltByBitResourceId(long builtByBitResourceId) {
      this.builtByBitResourceId = builtByBitResourceId;
      return this;
    }

    @Override
    public Builder setMainClass(@Nullable String mainClass) {
      this.mainClass = mainClass;
      return this;
    }

    @Override
    public Builder setBinaryFileId(long binaryFileId) {
      this.binaryFileId = binaryFileId;
      return null;
    }

    @Override
    public Addon build() {
      return new AddonImpl(
          this.id,
          this.name,
          this.version,
          StringUtils.EMPTY,
          this.mainClass,
          0L,
          this.builtByBitResourceId,
          this.binaryFileId,
          this.priority,
          this.products
      );
    }
  }
}
