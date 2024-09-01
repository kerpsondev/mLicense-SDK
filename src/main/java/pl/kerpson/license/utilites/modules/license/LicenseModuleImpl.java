package pl.kerpson.license.utilites.modules.license;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.url.MURL;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.license.basic.License;
import pl.kerpson.license.utilites.modules.license.operation.LicenseCreateOperation;
import pl.kerpson.license.utilites.modules.license.operation.LicenseDeleteOperation;
import pl.kerpson.license.utilites.modules.license.operation.LicenseGetAllOperation;
import pl.kerpson.license.utilites.modules.license.operation.LicenseGetOperation;
import pl.kerpson.license.utilites.modules.license.operation.LicenseUpdateOperation;

class LicenseModuleImpl implements LicenseModule {

  private final MSecrets secrets;

  protected LicenseModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(long id) {
    return new LicenseDeleteOperation(MURL.licenses().delete(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Boolean>> create(License license) {
    return new LicenseCreateOperation(MURL.licenses().create(), this.secrets, license);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(License license) {
    return new LicenseUpdateOperation(MURL.licenses().update(), this.secrets, license);
  }

  @Override
  public Operation<OperationResult<License>> get(String key) {
    return new LicenseGetOperation(MURL.licenses().getKey(key), this.secrets);
  }

  @Override
  public Operation<OperationResult<License>> get(long id) {
    return new LicenseGetOperation(MURL.licenses().getId(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<List<License>>> getAll() {
    return new LicenseGetAllOperation(MURL.licenses().getAll(), this.secrets);
  }
}
