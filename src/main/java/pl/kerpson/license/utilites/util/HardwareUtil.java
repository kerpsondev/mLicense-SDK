package pl.kerpson.license.utilites.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public final class HardwareUtil {

  private static final String UNKNOWN = "unknown";
  private static final String OS = System.getProperty("os.name").toLowerCase();

  private HardwareUtil() {}

  public static String getHardwareId() {
    try {
      if (isWindows()) {
        return getWindowsIdentifier();
      } else if (isMac()) {
        return getMacOsIdentifier();
      } else if (isLinux()) {
        return getLinuxMacAddress();
      } else {
        return UNKNOWN;
      }
    } catch (Exception exception) {
      return UNKNOWN;
    }
  }

  private static boolean isWindows() {
    return (OS.contains("win"));
  }

  private static boolean isMac() {
    return (OS.contains("mac"));
  }

  private static boolean isLinux() {
    return (OS.contains("inux"));
  }

  private static String getLinuxMacAddress() throws FileNotFoundException, NoSuchAlgorithmException {
    File machineId = new File("/var/lib/dbus/machine-id");
    if (!machineId.exists()) {
      machineId = new File("/etc/machine-id");
    }
    if (!machineId.exists()) {
      return UNKNOWN;
    }

    try (Scanner scanner = new Scanner(machineId)) {
      String id = scanner.useDelimiter("\\A").next();
      return hexStringify(sha256Hash(id.getBytes()));
    }
  }

  private static String getMacOsIdentifier() throws SocketException, NoSuchAlgorithmException {
    NetworkInterface networkInterface = NetworkInterface.getByName("en0");
    byte[] hardwareAddress = networkInterface.getHardwareAddress();
    return hexStringify(sha256Hash(hardwareAddress));
  }

  private static String getWindowsIdentifier() throws IOException, NoSuchAlgorithmException {
    Runtime runtime = Runtime.getRuntime();
    Process process = runtime.exec(new String[]{"wmic", "csproduct", "get", "UUID"});

    String result = null;
    try (InputStream ignored = process.getInputStream()) {
      Scanner sc = new Scanner(process.getInputStream());
      while (sc.hasNext()) {
        String next = sc.next();
        if (next.contains("UUID")) {
          result = sc.next().trim();
          break;
        }
      }
    }

    return result == null ? UNKNOWN : hexStringify(sha256Hash(result.getBytes()));
  }

  private static byte[] sha256Hash(byte[] data) throws NoSuchAlgorithmException {
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    return messageDigest.digest(data);
  }

  private static  String hexStringify(byte[] data) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte singleByte : data) {
      stringBuilder.append(Integer.toString((singleByte & 0xff) + 0x100, 16).substring(1));
    }

    return stringBuilder.toString();
  }
}
