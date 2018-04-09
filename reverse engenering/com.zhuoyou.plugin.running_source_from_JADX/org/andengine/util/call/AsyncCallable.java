package org.andengine.util.call;

public interface AsyncCallable<T> {
    void call(Callback<T> callback, Callback<Exception> callback2);
}
