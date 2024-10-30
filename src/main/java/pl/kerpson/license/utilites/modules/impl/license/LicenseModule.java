package pl.kerpson.license.utilites.modules.impl.license;

import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.modules.Module;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.impl.license.basic.License;

public interface LicenseModule extends Module<License> {

  static LicenseModule buildModule(MSecrets secrets) {
    return new LicenseModuleImpl(secrets);
  }

  Operation<OperationResult<License>> get(String key);
}
