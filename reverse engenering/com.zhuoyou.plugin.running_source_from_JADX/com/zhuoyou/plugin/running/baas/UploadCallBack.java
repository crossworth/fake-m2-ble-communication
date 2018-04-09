package com.zhuoyou.plugin.running.baas;

import com.droi.sdk.DroiError;
import java.util.List;

public interface UploadCallBack<T extends BaseObject> {
    void result(List<T> list, DroiError droiError);
}
