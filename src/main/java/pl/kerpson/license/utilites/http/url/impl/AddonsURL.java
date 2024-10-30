package pl.kerpson.license.utilites.http.url.impl;

import pl.kerpson.license.utilites.http.url.MURL;

public class AddonsURL extends MURL {

  private final static String PRODUCTS_ALL_URL = "https://api.mlicense.net/api/v1/addons/all";
  private final static String PRODUCTS_CREATE_URL = "https://api.mlicense.net/api/v1/addons";

  @Override
  public String create() {
    return PRODUCTS_CREATE_URL;
  }

  @Override
  public String getAll() {
    return PRODUCTS_ALL_URL;
  }

  @Override
  public String getId(long id) {
    return String.format("https://api.mlicense.net/api/v1/addons/id?id=%s", id);
  }

  public String getName(String name) {
    return String.format("https://api.mlicense.net/api/v1/addons/name?name=%s", name);
  }

  @Override
  public String update() {
    return PRODUCTS_CREATE_URL;
  }

  @Override
  public String delete(long id) {
    return String.format("https://api.mlicense.net/api/v1/addons?id=%s", id);
  }
}
