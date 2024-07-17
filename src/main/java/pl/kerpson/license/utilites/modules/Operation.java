package pl.kerpson.license.utilites.modules;

import java.util.concurrent.CompletableFuture;

public interface Operation<T> {

  T complete();

  CompletableFuture<T> completeAsync();
}
