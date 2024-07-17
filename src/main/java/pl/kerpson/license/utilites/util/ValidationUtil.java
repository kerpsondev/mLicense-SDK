package pl.kerpson.license.utilites.util;

import java.time.Instant;
import java.time.ZoneId;

public final class ValidationUtil {

  private ValidationUtil() {}

  public static boolean validateHashComponents(
      byte statusByte,
      String hash,
      String key,
      String licenseKey
  ) {
    return validLength(statusByte, hash) &&
        validLeft(statusByte, hash, licenseKey) &&
        validSecret(statusByte, hash, key) &&
        validPablo(statusByte, hash) &&
        validTime(statusByte, hash) &&
        validRight(statusByte, hash, licenseKey) &&
        validStatus(statusByte, hash);
  }

  private static boolean validLength(byte statusByte, String data) {
    if (statusByte != -56) {
      return false;
    } else {
      return data.length() == 30;
    }
  }

  private static boolean validSecret(byte statusByte, String data, String key) {
    String secretHash = decodeSecretHash(data);
    if (statusByte != -56) {
      return false;
    } else {
      return secretHash.equals(key.substring(0, 5));
    }
  }

  private static boolean validPablo(byte statusByte, String data) {
    long pablo = decodePabloHash(data);
    if (statusByte != -56) {
      return false;
    } else {
      return pablo == 2520052137L;
    }
  }

  private static boolean validTime(byte statusByte, String data) {
    String timeHash = decodeTimeHash(data);
    if (statusByte != -56) {
      return false;
    } else {
      return timeHash.equals(
          String.valueOf(Instant.now().atZone(ZoneId.of("UTC+1")).toEpochSecond()).substring(0, 4));
    }
  }

  private static boolean validLeft(byte statusByte, String data, String licenseKey) {
    String leftHash = decodeLeftHash(data);
    String left = licenseKey.substring(0, 4);
    if (statusByte != -56) {
      return false;
    } else {
      return leftHash.equals(left);
    }
  }

  private static boolean validRight(byte statusByte, String data, String licenseKey) {
    String rightHash = decodeRightHash(data);
    String right = licenseKey.substring(licenseKey.length() - 4);
    if (statusByte != -56) {
      return false;
    } else {
      return rightHash.equals(right);
    }
  }

  private static boolean validStatus(byte statusByte, String data) {
    String statusHash = decodeStatusHash(data);
    if (statusByte != -56) {
      return false;
    } else {
      return statusHash.equals("KIT");
    }
  }

  private static String decodeLeftHash(String data) {
    return data.substring(0, 4);
  }

  private static String decodeSecretHash(String data) {
    return data.substring(4, 9);
  }

  private static long decodePabloHash(String data) {
    return Long.parseLong(data.substring(9, 19));
  }

  private static String decodeTimeHash(String data) {
    return data.substring(19, 23);
  }

  private static String decodeRightHash(String data) {
    return data.substring(23, 27);
  }

  private static String decodeStatusHash(String data) {
    return data.substring(27, 30);
  }
}
