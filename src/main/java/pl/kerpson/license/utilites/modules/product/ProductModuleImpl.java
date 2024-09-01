package pl.kerpson.license.utilites.modules.product;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.url.MURL;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.product.basic.Product;
import pl.kerpson.license.utilites.modules.product.operation.ProductCreateOperation;
import pl.kerpson.license.utilites.modules.product.operation.ProductDeleteOperation;
import pl.kerpson.license.utilites.modules.product.operation.ProductGetOperation;
import pl.kerpson.license.utilites.modules.product.operation.ProductUpdateOperation;
import pl.kerpson.license.utilites.modules.product.operation.ProductsGetAllOperation;

class ProductModuleImpl implements ProductModule {

  private final MSecrets secrets;

  protected ProductModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(long id) {
    return new ProductDeleteOperation(MURL.products().delete(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Boolean>> create(Product product) {
    return new ProductCreateOperation(MURL.products().create(), this.secrets, product);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(Product product) {
    return new ProductUpdateOperation(MURL.products().update(), this.secrets, product);
  }

  @Override
  public Operation<OperationResult<Product>> get(long id) {
    return new ProductGetOperation(MURL.products().getId(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Product>> get(String name) {
    return new ProductGetOperation(MURL.products().getName(name), this.secrets);
  }

  @Override
  public Operation<OperationResult<List<Product>>> getAll() {
    return new ProductsGetAllOperation(MURL.products().getAll(), this.secrets);
  }
}
