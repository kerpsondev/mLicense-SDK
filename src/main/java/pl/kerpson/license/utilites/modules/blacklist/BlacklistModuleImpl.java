package pl.kerpson.license.utilites.modules.blacklist;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.url.MURL;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.blacklist.basic.Blacklist;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistCreateOperation;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistDeleteOperation;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistGetAllOperation;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistGetOperation;
import pl.kerpson.license.utilites.modules.blacklist.operation.BlacklistUpdateOperation;

class BlacklistModuleImpl implements BlacklistModule {

  private final MSecrets secrets;

  protected BlacklistModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> create(Blacklist blacklist) {
    return new BlacklistCreateOperation(MURL.blacklists().create(), this.secrets, blacklist);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(Blacklist blacklist) {
    return new BlacklistUpdateOperation(MURL.blacklists().update(), this.secrets, blacklist);
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(int id) {
    return new BlacklistDeleteOperation(MURL.blacklists().delete(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Blacklist>> get(int id) {
    return new BlacklistGetOperation(MURL.blacklists().getId(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<List<Blacklist>>> getAll() {
    return new BlacklistGetAllOperation(MURL.blacklists().getAll(), this.secrets);
  }
}
