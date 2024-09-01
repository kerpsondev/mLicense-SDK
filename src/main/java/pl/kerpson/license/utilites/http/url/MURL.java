package pl.kerpson.license.utilites.http.url;

import pl.kerpson.license.utilites.http.url.impl.BlacklistsURL;
import pl.kerpson.license.utilites.http.url.impl.LicensesURL;
import pl.kerpson.license.utilites.http.url.impl.ProductsURL;

public abstract class MURL {

  private static MURL LICENSES_URL;
  private static MURL PRODUCTS_URL;
  private static MURL BLACKLISTS_URL;

  static {
    MURL.setupUrls();
  }

  public static LicensesURL licenses() {
    return (LicensesURL) LICENSES_URL;
  }

  public static ProductsURL products() {
    return (ProductsURL) PRODUCTS_URL;
  }

  public static BlacklistsURL blacklists() {
    return (BlacklistsURL) BLACKLISTS_URL;
  }

  public abstract String create();

  public abstract String getAll();

  public abstract String getId(int id);

  public abstract String update();

  public abstract String delete(int id);

  private static void setupUrls() {
    LICENSES_URL = new LicensesURL();
    PRODUCTS_URL = new ProductsURL();
    BLACKLISTS_URL = new BlacklistsURL();
  }
}
