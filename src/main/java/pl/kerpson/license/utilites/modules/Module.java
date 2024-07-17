package pl.kerpson.license.utilites.modules;

import java.util.List;

public interface Module<T> {

  Operation<OperationResult<Boolean>> create(T t);

  Operation<OperationResult<Boolean>> update(T t);

  Operation<OperationResult<Boolean>> delete(int id);

  Operation<OperationResult<T>> get(int id);

  Operation<OperationResult<List<T>>> getAll();
}
