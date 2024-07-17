package pl.kerpson.license.utilites.modules.blacklist.basic;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

class BlacklistImpl implements Blacklist {

  private final int id;
  private final String value;
  private final int requests;
  private final String createdAt;
  private final Type type;
  private final int assignedUserId;

  protected BlacklistImpl(
      int id,
      @NotNull String value,
      int requests,
      @NotNull String createdAt,
      @NotNull Type type,
      int assignedUserId
  ) {
    this.id = id;
    this.value = value;
    this.requests = requests;
    this.createdAt = createdAt;
    this.type = type;
    this.assignedUserId = assignedUserId;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public @NotNull String getValue() {
    return value;
  }

  @Override
  public int getRequests() {
    return requests;
  }

  @Override
  public @NotNull String getCreatedAt() {
    return createdAt;
  }

  @Override
  public @NotNull Type getType() {
    return type;
  }

  @Override
  public int getAssignedUserId() {
    return assignedUserId;
  }

  @Override
  public String buildJsonForUpdate() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("id", this.id);
    jsonObject.addProperty("value", this.value);
    jsonObject.addProperty("type", this.type.name());

    return jsonObject.toString();
  }

  @Override
  public String buildJsonForCreate() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("value", this.value);
    jsonObject.addProperty("type", this.type.name());

    return jsonObject.toString();
  }

  static class BuilderImpl implements Builder {

    private int id;
    private String value;
    private Type type;

    @Override
    public Builder setId(int id) {
      this.id = id;
      return this;
    }

    @Override
    public Builder setValue(@NotNull String value) {
      this.value = value;
      return this;
    }

    @Override
    public Builder setType(@NotNull Type type) {
      this.type = type;
      return this;
    }

    @Override
    public Blacklist build() {
      return new BlacklistImpl(this.id, this.value, 0, StringUtils.EMPTY, this.type, 0);
    }
  }
}
