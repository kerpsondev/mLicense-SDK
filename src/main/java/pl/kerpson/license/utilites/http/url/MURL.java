package pl.kerpson.license.utilites.http.url;

import pl.kerpson.license.utilites.http.url.impl.AddonsURL;
import pl.kerpson.license.utilites.http.url.impl.BlacklistsURL;
import pl.kerpson.license.utilites.http.url.impl.LicensesURL;
import pl.kerpson.license.utilites.http.url.impl.ProductsURL;

public abstract class MURL {

  private static MURL ADDONS_URL;
  private static MURL LICENSES_URL;
  private static MURL PRODUCTS_URL;
  private static MURL BLACKLISTS_URL;

  static {
    MURL.setupUrls();
  }

  public static AddonsURL addons() {
    return (AddonsURL) ADDONS_URL;
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

  public abstract String getId(long id);

  public abstract String update();

  public abstract String delete(long id);

  private static void setupUrls() {
    ADDONS_URL = new AddonsURL();
    LICENSES_URL = new LicensesURL();
    PRODUCTS_URL = new ProductsURL();
    BLACKLISTS_URL = new BlacklistsURL();
  }
}
