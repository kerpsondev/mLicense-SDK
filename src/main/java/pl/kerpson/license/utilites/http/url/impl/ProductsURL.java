package pl.kerpson.license.utilites.http.url.impl;

import pl.kerpson.license.utilites.http.url.MURL;

public class ProductsURL extends MURL {

  private final static String PRODUCTS_ALL_URL = "https://api.mlicense.net/api/v1/products/all";
  private final static String PRODUCTS_CREATE_URL = "https://api.mlicense.net/api/v1/products";

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
    return String.format("https://api.mlicense.net/api/v1/products/id?id=%s", id);
  }

  public String getName(String name) {
    return String.format("https://api.mlicense.net/api/v1/products/name?name=%s", name);
  }

  @Override
  public String update() {
    return PRODUCTS_CREATE_URL;
  }

  @Override
  public String delete(long id) {
    return String.format("https://api.mlicense.net/api/v1/products?id=%s", id);
  }
}
