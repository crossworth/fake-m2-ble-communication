package org.andengine.util.adt.pool;

public abstract class RunnablePoolUpdateHandler<T extends RunnablePoolItem> extends PoolUpdateHandler<T> {
    protected abstract T onAllocatePoolItem();

    public RunnablePoolUpdateHandler(int pInitialPoolSize) {
        super(pInitialPoolSize);
    }

    public RunnablePoolUpdateHandler(int pInitialPoolSize, int pGrowth) {
        super(pInitialPoolSize, pGrowth);
    }

    public RunnablePoolUpdateHandler(int pInitialPoolSize, int pGrowth, int pAvailableItemCountMaximum) {
        super(pInitialPoolSize, pGrowth, pAvailableItemCountMaximum);
    }

    protected void onHandlePoolItem(T pRunnablePoolItem) {
        pRunnablePoolItem.run();
    }
}
