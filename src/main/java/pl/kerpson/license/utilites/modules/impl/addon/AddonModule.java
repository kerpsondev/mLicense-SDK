package pl.kerpson.license.utilites.modules.impl.addon;

import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.modules.Module;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.impl.addon.basic.Addon;

public interface AddonModule extends Module<Addon> {

  static AddonModule buildModule(MSecrets secrets) {
    return new AddonModuleImpl(secrets);
  }

  Operation<OperationResult<Addon>> get(String name);
}
