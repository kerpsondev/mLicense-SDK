package pl.kerpson.license.utilites.modules.impl.license;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.url.MURL;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.impl.license.basic.License;
import pl.kerpson.license.utilites.modules.impl.license.basic.LicenseReader;
import pl.kerpson.license.utilites.modules.operations.CreateOperation;
import pl.kerpson.license.utilites.modules.operations.DeleteOperation;
import pl.kerpson.license.utilites.modules.operations.MultiGetOperation;
import pl.kerpson.license.utilites.modules.operations.SingleGetOperation;
import pl.kerpson.license.utilites.modules.operations.UpdateOperation;

class LicenseModuleImpl implements LicenseModule {

  private final MSecrets secrets;

  LicenseModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(long id) {
    return new DeleteOperation(MURL.licenses().delete(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Boolean>> create(License license) {
    return new CreateOperation(MURL.licenses().create(), this.secrets, license);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(License license) {
    return new UpdateOperation(MURL.licenses().update(), this.secrets, license);
  }

  @Override
  public Operation<OperationResult<License>> get(String key) {
    return new SingleGetOperation<>(MURL.licenses().getKey(key), this.secrets, LicenseReader::readLicense);
  }

  @Override
  public Operation<OperationResult<License>> get(long id) {
    return new SingleGetOperation<>(MURL.licenses().getId(id), this.secrets, LicenseReader::readLicense);
  }

  @Override
  public Operation<OperationResult<List<License>>> getAll() {
    return new MultiGetOperation<>(MURL.licenses().getAll(), this.secrets, LicenseReader::readLicenses);
  }
}
