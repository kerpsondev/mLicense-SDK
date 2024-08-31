package pl.kerpson.license.utilites.modules.product;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.product.basic.Product;
import pl.kerpson.license.utilites.modules.product.operation.ProductCreateOperation;
import pl.kerpson.license.utilites.modules.product.operation.ProductDeleteOperation;
import pl.kerpson.license.utilites.modules.product.operation.ProductGetOperation;
import pl.kerpson.license.utilites.modules.product.operation.ProductUpdateOperation;
import pl.kerpson.license.utilites.modules.product.operation.ProductsGetAllOperation;

class ProductModuleImpl implements ProductModule {

  private final static String PRODUCTS_ALL_URL = "https://api.mlicense.net/api/v1/products/all";
  private final static String PRODUCTS_CREATE_URL = "https://api.mlicense.net/api/v1/products";
  private final static String PRODUCTS_DELETE_URL = "https://api.mlicense.net/api/v1/products?id=%s";
  private final static String PRODUCTS_ID_URL = "https://api.mlicense.net/api/v1/products/id?id=%s";
  private final static String PRODUCTS_NAME_URL = "https://api.mlicense.net/api/v1/products/name?name=%s";

  private final MSecrets secrets;

  protected ProductModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(int id) {
    return new ProductDeleteOperation(String.format(PRODUCTS_DELETE_URL, id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Boolean>> create(Product product) {
    return new ProductCreateOperation(PRODUCTS_CREATE_URL, this.secrets, product);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(Product product) {
    return new ProductUpdateOperation(PRODUCTS_CREATE_URL, this.secrets, product);
  }

  @Override
  public Operation<OperationResult<Product>> get(int id) {
    return new ProductGetOperation(String.format(PRODUCTS_ID_URL, id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Product>> get(String name) {
    return new ProductGetOperation(String.format(PRODUCTS_NAME_URL, name), this.secrets);
  }

  @Override
  public Operation<OperationResult<List<Product>>> getAll() {
    return new ProductsGetAllOperation(PRODUCTS_ALL_URL, this.secrets);
  }
}
