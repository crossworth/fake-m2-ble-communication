package org.andengine.util.call;

public interface Callable<T> {
    T call() throws Exception;
}
