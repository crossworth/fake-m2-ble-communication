package com.droi.sdk.core;

import com.droi.sdk.DroiError;
import java.util.List;

public interface DroiQueryCallback<T extends DroiObject> {
    void result(List<T> list, DroiError droiError);
}
