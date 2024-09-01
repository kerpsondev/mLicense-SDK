package pl.kerpson.license.utilites.http.url.impl;

import pl.kerpson.license.utilites.http.url.MURL;

public class BlacklistsURL extends MURL {

  private final static String BLACKLISTS_ALL_URL = "https://api.mlicense.net/api/v1/blacklists/all";
  private final static String BLACKLISTS_CREATE_URL = "https://api.mlicense.net/api/v1/blacklists";

  @Override
  public String create() {
    return BLACKLISTS_CREATE_URL;
  }

  @Override
  public String getAll() {
    return BLACKLISTS_ALL_URL;
  }

  @Override
  public String getId(long id) {
    return String.format("https://api.mlicense.net/api/v1/blacklists/id?id=%s", id);
  }

  @Override
  public String update() {
    return BLACKLISTS_CREATE_URL;
  }

  @Override
  public String delete(long id) {
    return String.format("https://api.mlicense.net/api/v1/blacklists?id=%s", id);
  }
}
