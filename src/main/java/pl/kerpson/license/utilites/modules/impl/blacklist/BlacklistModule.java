package pl.kerpson.license.utilites.modules.impl.blacklist;

import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.modules.Module;
import pl.kerpson.license.utilites.modules.impl.blacklist.basic.Blacklist;

public interface BlacklistModule extends Module<Blacklist> {

  static BlacklistModule buildModule(MSecrets secrets) {
    return new BlacklistModuleImpl(secrets);
  }
}
