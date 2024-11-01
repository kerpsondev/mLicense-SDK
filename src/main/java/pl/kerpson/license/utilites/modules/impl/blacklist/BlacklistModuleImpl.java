package pl.kerpson.license.utilites.modules.impl.blacklist;

import java.util.List;
import pl.kerpson.license.utilites.MSecrets;
import pl.kerpson.license.utilites.http.url.MURL;
import pl.kerpson.license.utilites.modules.Operation;
import pl.kerpson.license.utilites.modules.OperationResult;
import pl.kerpson.license.utilites.modules.impl.blacklist.basic.Blacklist;
import pl.kerpson.license.utilites.modules.impl.blacklist.basic.BlacklistReader;
import pl.kerpson.license.utilites.modules.operations.CreateOperation;
import pl.kerpson.license.utilites.modules.operations.DeleteOperation;
import pl.kerpson.license.utilites.modules.operations.MultiGetOperation;
import pl.kerpson.license.utilites.modules.operations.SingleGetOperation;
import pl.kerpson.license.utilites.modules.operations.UpdateOperation;

class BlacklistModuleImpl implements BlacklistModule {

  private final MSecrets secrets;

  BlacklistModuleImpl(MSecrets secrets) {
    this.secrets = secrets;
  }

  @Override
  public Operation<OperationResult<Boolean>> create(Blacklist blacklist) {
    return new CreateOperation(MURL.blacklists().create(), this.secrets, blacklist);
  }

  @Override
  public Operation<OperationResult<Boolean>> update(Blacklist blacklist) {
    return new UpdateOperation(MURL.blacklists().update(), this.secrets, blacklist);
  }

  @Override
  public Operation<OperationResult<Boolean>> delete(long id) {
    return new DeleteOperation(MURL.blacklists().delete(id), this.secrets);
  }

  @Override
  public Operation<OperationResult<Blacklist>> get(long id) {
    return new SingleGetOperation<>(MURL.blacklists().getId(id), this.secrets, BlacklistReader::readBlacklist);
  }

  @Override
  public Operation<OperationResult<List<Blacklist>>> getAll() {
    return new MultiGetOperation<>(MURL.blacklists().getAll(), this.secrets, BlacklistReader::readBlacklists);
  }
}
