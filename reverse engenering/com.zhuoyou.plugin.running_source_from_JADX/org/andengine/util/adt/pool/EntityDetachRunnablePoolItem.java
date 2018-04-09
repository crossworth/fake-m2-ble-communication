package org.andengine.util.adt.pool;

import org.andengine.entity.IEntity;
import org.andengine.util.call.Callback;

public class EntityDetachRunnablePoolItem extends RunnablePoolItem {
    protected Callback<IEntity> mCallback;
    protected IEntity mEntity;

    public void setEntity(IEntity pEntity) {
        this.mEntity = pEntity;
    }

    public void setCallback(Callback<IEntity> pCallback) {
        this.mCallback = pCallback;
    }

    public void run() {
        this.mEntity.detachSelf();
        if (this.mCallback != null) {
            this.mCallback.onCallback(this.mEntity);
        }
    }
}
