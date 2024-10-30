package pl.kerpson.license.utilites.modules.impl.product;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.url.MURL;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.operations.CreateOperation;
import pl.kerpson.license.utilites.modules.operations.DeleteOperation;
import pl.kerpson.license.utilites.modules.operations.MultiGetOperation;
import pl.kerpson.license.utilites.modules.operations.SingleGetOperation;
import pl.kerpson.license.utilites.modules.operations.UpdateOperation;
import pl.kerpson.license.utilites.modules.impl.product.basic.Product;
import pl.kerpson.license.utilites.modules.impl.product.basic.ProductReader;

class ProductModuleImpl implements ProductModule {

  private final MSecrets secrets;

  protected ProductModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(long id) {
    return new DeleteOperation(MURL.products().delete(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Boolean>> create(Product product) {
    return new CreateOperation(MURL.products().create(), this.secrets, product);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(Product product) {
    return new UpdateOperation(MURL.products().update(), this.secrets, product);
  }

  @Override
  public Operation<OperationResult<Product>> get(long id) {
    return new SingleGetOperation<>(MURL.products().getId(id), this.secrets, ProductReader::readProduct);
  }

  @Override
  public Operation<OperationResult<Product>> get(String name) {
    return new SingleGetOperation<>(MURL.products().getName(name), this.secrets, ProductReader::readProduct);
  }

  @Override
  public Operation<OperationResult<List<Product>>> getAll() {
    return new MultiGetOperation<>(MURL.products().getAll(), this.secrets, ProductReader::readProducts);
  }
}
