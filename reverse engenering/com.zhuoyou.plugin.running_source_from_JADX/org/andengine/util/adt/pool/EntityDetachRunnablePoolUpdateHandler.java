package org.andengine.util.adt.pool;

import org.andengine.entity.IEntity;
import org.andengine.util.call.Callback;

public class EntityDetachRunnablePoolUpdateHandler extends RunnablePoolUpdateHandler<EntityDetachRunnablePoolItem> {
    public EntityDetachRunnablePoolUpdateHandler(int pInitialPoolSize) {
        super(pInitialPoolSize);
    }

    public EntityDetachRunnablePoolUpdateHandler(int pInitialPoolSize, int pGrowth) {
        super(pInitialPoolSize, pGrowth);
    }

    public EntityDetachRunnablePoolUpdateHandler(int pInitialPoolSize, int pGrowth, int pAvailableItemCountMaximum) {
        super(pInitialPoolSize, pGrowth, pAvailableItemCountMaximum);
    }

    protected EntityDetachRunnablePoolItem onAllocatePoolItem() {
        return new EntityDetachRunnablePoolItem();
    }

    public void scheduleDetach(IEntity pEntity) {
        scheduleDetach(pEntity, null);
    }

    public void scheduleDetach(IEntity pEntity, Callback<IEntity> pCallback) {
        EntityDetachRunnablePoolItem entityDetachRunnablePoolItem = (EntityDetachRunnablePoolItem) obtainPoolItem();
        entityDetachRunnablePoolItem.setEntity(pEntity);
        entityDetachRunnablePoolItem.setCallback(pCallback);
        postPoolItem(entityDetachRunnablePoolItem);
    }
}
