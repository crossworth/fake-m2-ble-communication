package org.andengine.engine.handler;

import org.andengine.entity.IEntity;

public abstract class BaseEntityUpdateHandler implements IUpdateHandler {
    private IEntity mEntity;

    protected abstract void onUpdate(float f, IEntity iEntity);

    public BaseEntityUpdateHandler(IEntity pEntity) {
        this.mEntity = pEntity;
    }

    public IEntity getEntity() {
        return this.mEntity;
    }

    public void setEntity(IEntity pEntity) {
        this.mEntity = pEntity;
    }

    public final void onUpdate(float pSecondsElapsed) {
        onUpdate(pSecondsElapsed, this.mEntity);
    }

    public void reset() {
    }
}
