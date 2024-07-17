package pl.kerpson.license.utilites.modules.license.basic;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.kerpson.license.utilites.exception.LicenseCreateException;

class LicenseImpl implements License {

  private final int id;
  private final String key;
  private final int productId;
  private final String description;
  private final int assignedUserId;
  private final ClientInfo clientInfo;
  private final AddressInfo addressInfo;
  private final MachineInfo machineInfo;
  private final DurationInfo durationInfo;

  protected LicenseImpl(
      int id,
      @NotNull String key,
      int productId,
      @Nullable String description,
      int assignedUserId,
      @NotNull ClientInfo clientInfo,
      @NotNull AddressInfo addressInfo,
      @NotNull MachineInfo machineInfo,
      @NotNull DurationInfo durationInfo
  ) {
    this.id = id;
    this.key = key;
    this.productId = productId;
    this.description = description;
    this.assignedUserId = assignedUserId;
    this.clientInfo = clientInfo;
    this.addressInfo = addressInfo;
    this.machineInfo = machineInfo;
    this.durationInfo = durationInfo;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public @NotNull String getKey() {
    return key;
  }

  @Override
  public int getProductId() {
    return productId;
  }

  @Override
  public @Nullable String getDescription() {
    return description;
  }

  @Override
  public int getAssignedUserId() {
    return assignedUserId;
  }

  @Override
  public @NotNull ClientInfo getClient() {
    return clientInfo;
  }

  @Override
  public @NotNull AddressInfo getAddressInfo() {
    return addressInfo;
  }

  @Override
  public @NotNull MachineInfo getMachineInfo() {
    return machineInfo;
  }

  @Override
  public @NotNull DurationInfo getDurationInfo() {
    return durationInfo;
  }

  @Override
  public String buildJsonForUpdate() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("id", this.id);
    jsonObject.addProperty("key", this.key);
    jsonObject.addProperty("productId", this.productId);
    jsonObject.addProperty("description", this.description);
    jsonObject.addProperty("clientName", this.clientInfo.getName());
    jsonObject.addProperty("email", this.clientInfo.getEmail());
    jsonObject.addProperty("discordId", this.clientInfo.getDiscordId());
    jsonObject.addProperty("addressLimit", this.addressInfo.getLimit());
    jsonObject.addProperty("addressDuration", this.addressInfo.getDuration());
    jsonObject.addProperty("assignedAddress", this.addressInfo.getAssignedAddress());
    jsonObject.addProperty("machineLimit", this.machineInfo.getLimit());
    jsonObject.addProperty("machineDuration", this.machineInfo.getDuration());
    jsonObject.addProperty("assignedMachine", this.machineInfo.getAssignedMachine());
    jsonObject.addProperty("duration", this.durationInfo.getDuration());
    jsonObject.addProperty("deleteAfterExpire", this.durationInfo.isDeleteAfterExpire());

    return jsonObject.toString();
  }

  @Override
  public String buildJsonForCreate() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("key", this.key);
    jsonObject.addProperty("productId", this.productId);
    jsonObject.addProperty("description", this.description);
    jsonObject.addProperty("clientName", this.clientInfo.getName());
    jsonObject.addProperty("email", this.clientInfo.getEmail());
    jsonObject.addProperty("discordId", this.clientInfo.getDiscordId());
    jsonObject.addProperty("addressLimit", this.addressInfo.getLimit());
    jsonObject.addProperty("addressDuration", this.addressInfo.getDuration());
    jsonObject.addProperty("assignedAddress", this.addressInfo.getAssignedAddress());
    jsonObject.addProperty("machineLimit", this.machineInfo.getLimit());
    jsonObject.addProperty("machineDuration", this.machineInfo.getDuration());
    jsonObject.addProperty("assignedMachine", this.machineInfo.getAssignedMachine());
    jsonObject.addProperty("duration", this.durationInfo.getDuration());
    jsonObject.addProperty("deleteAfterExpire", this.durationInfo.isDeleteAfterExpire());

    return jsonObject.toString();
  }

  static class ClientInfoImpl implements ClientInfo {

    private final String name;
    private final String email;
    private final long discordId;

    protected ClientInfoImpl(
        @NotNull String name,
        @Nullable String email,
        long discordId
    ) {
      this.name = name;
      this.email = email;
      this.discordId = discordId;
    }

    @Override
    public @NotNull String getName() {
      return name;
    }

    @Override
    public @Nullable String getEmail() {
      return email;
    }

    @Override
    public long getDiscordId() {
      return discordId;
    }
  }

  static class AddressInfoImpl implements License.AddressInfo {

    private final int limit;
    private final long duration;
    private final String assignedAddress;
    private final String lastAddress;
    private final List<Request> requests;

    protected AddressInfoImpl(
        int limit,
        long duration,
        @Nullable String assignedAddress,
        @Nullable String lastAddress,
        @NotNull List<Request> requests
    ) {
      this.limit = limit;
      this.duration = duration;
      this.assignedAddress = assignedAddress;
      this.lastAddress = lastAddress;
      this.requests = requests;
    }

    @Override
    public int getLimit() {
      return limit;
    }

    @Override
    public long getDuration() {
      return duration;
    }

    @Override
    public @Nullable String getAssignedAddress() {
      return assignedAddress;
    }

    @Override
    public @Nullable String getLastAddress() {
      return lastAddress;
    }

    @Override
    public @NotNull List<Request> getRequests() {
      return requests;
    }
  }

  static class MachineInfoImpl implements License.MachineInfo {

    private final int limit;
    private final long duration;
    private final String assignedMachine;
    private final String lastMachine;
    private final List<MachineInfo.Request> requests;

    protected MachineInfoImpl(
        int limit,
        long duration,
        @Nullable String assignedMachine,
        @Nullable String lastMachine,
        @NotNull List<Request> requests
    ) {
      this.limit = limit;
      this.duration = duration;
      this.assignedMachine = assignedMachine;
      this.lastMachine = lastMachine;
      this.requests = requests;
    }

    @Override
    public int getLimit() {
      return limit;
    }

    @Override
    public long getDuration() {
      return duration;
    }

    @Override
    public @Nullable String getAssignedMachine() {
      return assignedMachine;
    }

    @Override
    public @Nullable String getLastMachine() {
      return lastMachine;
    }

    @Override
    public @NotNull List<Request> getRequests() {
      return requests;
    }
  }

  static class DurationInfoImpl implements License.DurationInfo {

    private final long duration;
    private final String createdAt;
    private final String lastRequest;
    private final boolean deleteAfterExpire;
    private final boolean active;
    private final String expiresAt;

    protected DurationInfoImpl(
        long duration,
        @NotNull String createdAt,
        @Nullable String lastRequest,
        boolean deleteAfterExpire,
        boolean active,
        @Nullable String expiresAt
    ) {
      this.duration = duration;
      this.createdAt = createdAt;
      this.lastRequest = lastRequest;
      this.deleteAfterExpire = deleteAfterExpire;
      this.active = active;
      this.expiresAt = expiresAt;
    }

    @Override
    public long getDuration() {
      return duration;
    }

    @Override
    public @NotNull String getCreatedAt() {
      return createdAt;
    }

    @Override
    public @Nullable String getLastRequest() {
      return lastRequest;
    }

    @Override
    public boolean isDeleteAfterExpire() {
      return deleteAfterExpire;
    }

    @Override
    public boolean isActive() {
      return active;
    }

    @Override
    public @Nullable String getExpiresAt() {
      return expiresAt;
    }
  }

  static class BuilderImpl implements Builder {

    private int id;
    private int productId;
    private String key;
    private String description;
    private ClientInfo clientInfo;
    private AddressInfo addressInfo;
    private MachineInfo machineInfo;
    private DurationInfo durationInfo;

    protected BuilderImpl() {}

    @Override
    public Builder setId(int id) {
      this.id = id;
      return this;
    }

    @Override
    public Builder setProductId(int productId) {
      this.productId = productId;
      return this;
    }

    @Override
    public Builder setKey(@NotNull String key) {
      this.key = key;
      return this;
    }

    @Override
    public Builder setDescription(@Nullable String description) {
      this.description = description;
      return this;
    }

    @Override
    public Builder setClientInfo(@NotNull String name, @Nullable String email, long discordId) {
      this.clientInfo = new ClientInfoImpl(name, email, discordId);
      return this;
    }

    @Override
    public Builder setAddressInfo(int limit, long duration, @Nullable String assignedTo) {
      this.addressInfo = new AddressInfoImpl(limit, duration, assignedTo, null, List.of());
      return this;
    }

    @Override
    public Builder setMachineInfo(int limit, long duration, @Nullable String assignedTo) {
      this.machineInfo = new MachineInfoImpl(limit, duration, assignedTo, null, List.of());
      return this;
    }

    @Override
    public Builder setDurationInfo(long duration, boolean deleteAfterExpire) {
      this.durationInfo = new DurationInfoImpl(
          duration,
          StringUtils.EMPTY,
          null,
          deleteAfterExpire,
          false,
          null
      );

      return this;
    }

    @Override
    public License build() throws LicenseCreateException {
      if (Objects.isNull(this.clientInfo)) {
        throw new LicenseCreateException("Client is null!");
      }

      if (Objects.isNull(this.addressInfo)) {
        throw new LicenseCreateException("Address is null!");
      }

      if (Objects.isNull(this.machineInfo)) {
        throw new LicenseCreateException("Machine is null!");
      }

      if (Objects.isNull(this.durationInfo)) {
        throw new LicenseCreateException("Duration is null!");
      }

      return new LicenseImpl(
          this.id,
          this.key,
          this.productId,
          this.description,
          0,
          this.clientInfo,
          this.addressInfo,
          this.machineInfo,
          this.durationInfo
      );
    }
  }
}
