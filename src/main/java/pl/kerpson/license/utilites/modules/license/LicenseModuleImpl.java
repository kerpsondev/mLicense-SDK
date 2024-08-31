package pl.kerpson.license.utilites.modules.license;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.license.basic.License;
import pl.kerpson.license.utilites.modules.license.operation.LicenseCreateOperation;
import pl.kerpson.license.utilites.modules.license.operation.LicenseDeleteOperation;
import pl.kerpson.license.utilites.modules.license.operation.LicenseGetAllOperation;
import pl.kerpson.license.utilites.modules.license.operation.LicenseGetOperation;
import pl.kerpson.license.utilites.modules.license.operation.LicenseUpdateOperation;

class LicenseModuleImpl implements LicenseModule {

  private final static String LICENSES_CREATE_URL = "https://api.mlicense.net/api/v1/licenses";
  private final static String LICENSES_ALL_URL = "https://api.mlicense.net/api/v1/licenses/all";
  private final static String LICENSES_ID_URL = "https://api.mlicense.net/api/v1/licenses/id?id=%s";
  private final static String LICENSES_DELETE_URL = "https://api.mlicense.net/api/v1/licenses?id=%s";
  private final static String LICENSES_KEY_URL = "https://api.mlicense.net/api/v1/licenses/key?key=%s";

  private final MSecrets secrets;

  protected LicenseModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(int id) {
    return new LicenseDeleteOperation(String.format(LICENSES_DELETE_URL, id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Boolean>> create(License license) {
    return new LicenseCreateOperation(LICENSES_CREATE_URL, this.secrets, license);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(License license) {
    return new LicenseUpdateOperation(LICENSES_CREATE_URL, this.secrets, license);
  }

  @Override
  public Operation<OperationResult<License>> get(String key) {
    return new LicenseGetOperation(String.format(LICENSES_KEY_URL, key), this.secrets);
  }

  @Override
  public Operation<OperationResult<License>> get(int id) {
    return new LicenseGetOperation(String.format(LICENSES_ID_URL, id), this.secrets);
  }

  @Override
  public Operation<OperationResult<List<License>>> getAll() {
    return new LicenseGetAllOperation(LICENSES_ALL_URL, this.secrets);
  }
}
