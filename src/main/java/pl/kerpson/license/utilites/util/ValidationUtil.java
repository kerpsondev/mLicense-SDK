package pl.kerpson.license.utilites.util;

import java.time.Instant;
import java.time.ZoneId;

public final class ValidationUtil {

  private ValidationUtil() {}

  public static boolean validHash(
      int statusCode,
      String hash,
      String key,
      String secretKey
  ) {
    if (statusCode != 200 || hash.length() != 30) {
      return false;
    }

    String left = key.substring(0, 4);
    String secret = secretKey.substring(0, 5);
    String pablo = "2520052137";
    String time = String.valueOf(Instant.now().atZone(ZoneId.of("UTC+1")).toEpochSecond()).substring(0, 4);
    String right = key.substring(key.length() - 4);
    String status = "KIT";

    return hash.startsWith(left) &&
        hash.substring(4, 9).equals(secret) &&
        hash.substring(9, 19).equals(pablo) &&
        hash.substring(19, 23).equals(time) &&
        hash.substring(23, 27).equals(right) &&
        hash.substring(27).equals(status);
  }
}
