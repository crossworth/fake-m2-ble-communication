package com.droi.sdk;

public interface DroiCallback<T> {
    void result(T t, DroiError droiError);
}
