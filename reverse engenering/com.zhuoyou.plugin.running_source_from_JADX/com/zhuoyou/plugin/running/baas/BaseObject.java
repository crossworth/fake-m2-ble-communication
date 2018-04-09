package com.zhuoyou.plugin.running.baas;

import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiCondition;
import com.droi.sdk.core.DroiObject;
import com.droi.sdk.core.DroiQuery;
import com.droi.sdk.core.DroiQuery.Builder;
import com.droi.sdk.core.DroiQueryCallback;
import java.util.List;

public abstract class BaseObject<T extends BaseObject> extends DroiObject implements Copyable<T>, BaasEquals<T> {
    public void saveOrUpdateInBackground(DroiCondition cond, final DroiCallback<Boolean> callBack) {
        Builder.newBuilder().query(getClass()).where(cond).build().runQueryInBackground(new DroiQueryCallback<T>() {
            public void result(List<T> list, DroiError droiError) {
                if (droiError.isOk()) {
                    if (list.size() > 0) {
                        ((BaseObject) list.get(0)).copy(this);
                        ((BaseObject) list.get(0)).saveInBackground(callBack);
                        return;
                    }
                    BaseObject.this.saveInBackground(callBack);
                } else if (callBack != null) {
                    callBack.result(Boolean.valueOf(false), droiError);
                }
            }
        });
    }

    public DroiError saveOrUpdate(DroiCondition cond) {
        DroiQuery query = Builder.newBuilder().query(getClass()).where(cond).build();
        DroiError error = new DroiError();
        List<T> list = query.runQuery(error);
        if (!error.isOk()) {
            return error;
        }
        if (list.size() <= 0) {
            return save();
        }
        ((BaseObject) list.get(0)).copy(this);
        return ((BaseObject) list.get(0)).save();
    }

    public void deleteInBackground(DroiCondition cond, final DroiCallback<Boolean> callBack) {
        Builder.newBuilder().query(getClass()).where(cond).build().runQueryInBackground(new DroiQueryCallback<T>() {
            public void result(List<T> list, DroiError droiError) {
                if (droiError.isOk() && list.size() > 0) {
                    DroiObject.deleteAllInBackground(list, callBack);
                } else if (callBack != null) {
                    callBack.result(Boolean.valueOf(false), droiError);
                }
            }
        });
    }

    public DroiError delete(DroiCondition cond) {
        DroiQuery query = Builder.newBuilder().query(getClass()).where(cond).build();
        DroiError error = new DroiError();
        List<T> list = query.runQuery(error);
        if (!error.isOk() || list.size() <= 0) {
            return error;
        }
        return DroiObject.deleteAll(list);
    }
}
