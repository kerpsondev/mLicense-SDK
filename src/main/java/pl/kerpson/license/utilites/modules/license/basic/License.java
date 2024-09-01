package pl.kerpson.license.utilites.modules.license.basic;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.kerpson.license.utilites.modules.license.basic.LicenseImpl.BuilderImpl;
import pl.kerpson.license.utilites.modules.license.basic.LicenseImpl.KeyGenerator;

public interface License {

  static Builder createLicense() {
    return new BuilderImpl();
  }

  static String generateKey() {
    return KeyGenerator.generateLicenseKey();
  }

  long getId();

  @NotNull String getKey();

  long getProductId();

  @Nullable String getDescription();

  long getAssignedUserId();

  @NotNull ClientInfo getClient();

  @NotNull AddressInfo getAddressInfo();

  @NotNull MachineInfo getMachineInfo();

  @NotNull DurationInfo getDurationInfo();

  String buildJsonForUpdate();

  String buildJsonForCreate();

  interface ClientInfo {

    @NotNull String getName();

    @Nullable
    String getEmail();

    long getDiscordId();
  }

  interface AddressInfo {

    int getLimit();

    long getDuration();

    @Nullable String getAssignedAddress();

    @Nullable String getLastAddress();

    @NotNull
    List<AddressInfo.Request> getRequests();

    class Request {

      private final String date;
      private final String address;

      public Request(@NotNull String date, @NotNull String address) {
        this.date = date;
        this.address = address;
      }

      public @NotNull String getDate() {
        return date;
      }

      public @NotNull String getAddress() {
        return address;
      }
    }
  }

  interface MachineInfo {

    int getLimit();

    long getDuration();

    @Nullable String getAssignedMachine();

    @Nullable String getLastMachine();

    @NotNull List<MachineInfo.Request> getRequests();

    class Request {

      private final String date;
      private final String hwid;

      public Request(@NotNull String date, @NotNull String hwid) {
        this.date = date;
        this.hwid = hwid;
      }

      public @NotNull String getDate() {
        return date;
      }

      public @NotNull String getHwid() {
        return hwid;
      }
    }
  }

  interface DurationInfo {

    long getDuration();

    @NotNull String getCreatedAt();

    @Nullable String getLastRequest();

    boolean isDeleteAfterExpire();

    boolean isActive();

    @Nullable String getExpiresAt();
  }

  interface Builder {

    Builder setId(long id);

    Builder setProductId(long productId);

    Builder setKey(@NotNull String key);

    Builder setDescription(@Nullable String description);

    Builder setClientInfo(@NotNull String name, @Nullable String email, long discordId);

    Builder setAddressInfo(int limit, long duration, @Nullable String assignedAddress);

    Builder setMachineInfo(int limit, long duration, @Nullable String assignedMachine);

    Builder setDurationInfo(long duration, boolean deleteAfterExpire);

    License build();
  }
}
