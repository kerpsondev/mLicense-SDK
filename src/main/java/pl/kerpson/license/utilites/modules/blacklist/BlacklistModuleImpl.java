package pl.kerpson.license.utilites.modules.blacklist;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.blacklist.basic.Blacklist;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistCreateOperation;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistDeleteOperation;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistGetAllOperation;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistGetOperation;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistUpdateOperation;

class BlacklistModuleImpl implements BlacklistModule {

  private final static String BLACKLISTS_ALL_URL = "https://api.mlicense.net/api/v1/blacklists/all";
  private final static String BLACKLISTS_CREATE_URL = "https://api.mlicense.net/api/v1/blacklists";
  private final static String BLACKLISTS_ID_URL = "https://api.mlicense.net/api/v1/blacklists/id?id=%s";
  private final static String BLACKLISTS_DELETE_URL = "https://api.mlicense.net/api/v1/blacklists?id=%s";

  private final MSecrets secrets;

  protected BlacklistModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> create(Blacklist blacklist) {
    return new BlacklistCreateOperation(BLACKLISTS_CREATE_URL, this.secrets, blacklist);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(Blacklist blacklist) {
    return new BlacklistUpdateOperation(BLACKLISTS_CREATE_URL, this.secrets, blacklist);
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(int id) {
    return new BlacklistDeleteOperation(String.format(BLACKLISTS_DELETE_URL, id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Blacklist>> get(int id) {
    return new BlacklistGetOperation(String.format(BLACKLISTS_ID_URL, id), this.secrets);
  }

  @Override
  public Operation<OperationResult<List<Blacklist>>> getAll() {
    return new BlacklistGetAllOperation(BLACKLISTS_ALL_URL, this.secrets);
  }
}
