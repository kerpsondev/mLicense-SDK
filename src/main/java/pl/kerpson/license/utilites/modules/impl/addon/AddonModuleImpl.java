package pl.kerpson.license.utilites.modules.impl.addon;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.url.MURL;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.impl.addon.basic.Addon;
import pl.kerpson.license.utilites.modules.impl.addon.basic.AddonReader;
import pl.kerpson.license.utilites.modules.operations.CreateOperation;
import pl.kerpson.license.utilites.modules.operations.DeleteOperation;
import pl.kerpson.license.utilites.modules.operations.MultiGetOperation;
import pl.kerpson.license.utilites.modules.operations.SingleGetOperation;
import pl.kerpson.license.utilites.modules.operations.UpdateOperation;

class AddonModuleImpl implements AddonModule {

  private final MSecrets secrets;

  AddonModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(long id) {
    return new DeleteOperation(MURL.addons().delete(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Boolean>> create(Addon addon) {
    return new CreateOperation(MURL.addons().create(), this.secrets, addon);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(Addon addon) {
    return new UpdateOperation(MURL.addons().update(), this.secrets, addon);
  }

  @Override
  public Operation<OperationResult<Addon>> get(long id) {
    return new SingleGetOperation<>(MURL.addons().getId(id), this.secrets, AddonReader::readAddon);
  }

  @Override
  public Operation<OperationResult<Addon>> get(String name) {
    return new SingleGetOperation<>(MURL.addons().getName(name), this.secrets, AddonReader::readAddon);
  }

  @Override
  public Operation<OperationResult<List<Addon>>> getAll() {
    return new MultiGetOperation<>(MURL.addons().getAll(), this.secrets, AddonReader::readAddons);
  }
}
