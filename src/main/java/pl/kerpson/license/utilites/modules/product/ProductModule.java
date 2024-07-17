package pl.kerpson.license.utilites.modules.product;

import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.modules.Module;
import pl.kerpson.license.utilites.modules.product.basic.Product;

public interface ProductModule extends Module<Product> {

  static ProductModule buildModule(MSecrets secrets) {
    return new ProductModuleImpl(secrets);
  }
}
