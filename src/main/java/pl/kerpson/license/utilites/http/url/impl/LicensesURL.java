package pl.kerpson.license.utilites.http.url.impl;

import pl.kerpson.license.utilites.http.url.MURL;

public class LicensesURL extends MURL {

  private final static String LICENSES_CREATE_URL = "https://api.mlicense.net/api/v1/licenses";
  private final static String LICENSES_ALL_URL = "https://api.mlicense.net/api/v1/licenses/all";

  @Override
  public String create() {
    return LICENSES_CREATE_URL;
  }

  @Override
  public String getAll() {
    return LICENSES_ALL_URL;
  }

  @Override
  public String getId(long id) {
    return String.format("https://api.mlicense.net/api/v1/licenses/id?id=%s", id);
  }

  public String getKey(String key) {
    return String.format("https://api.mlicense.net/api/v1/licenses/key?key=%s", key);
  }

  @Override
  public String update() {
    return LICENSES_CREATE_URL;
  }

  @Override
  public String delete(long id) {
    return String.format("https://api.mlicense.net/api/v1/licenses?id=%s", id);
  }
}
