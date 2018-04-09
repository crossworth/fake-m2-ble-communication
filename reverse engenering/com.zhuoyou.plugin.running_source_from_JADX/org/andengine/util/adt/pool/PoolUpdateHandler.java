package org.andengine.util.adt.pool;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.util.adt.list.ShiftList;
import org.andengine.util.adt.queue.IQueue;
import org.andengine.util.adt.queue.concurrent.SynchronizedQueue;

public abstract class PoolUpdateHandler<T extends PoolItem> implements IUpdateHandler {
    private final Pool<T> mPool;
    private final IQueue<T> mScheduledPoolItemQueue;

    class C21611 extends Pool<T> {
        C21611() {
        }

        protected T onAllocatePoolItem() {
            return PoolUpdateHandler.this.onAllocatePoolItem();
        }
    }

    protected abstract T onAllocatePoolItem();

    protected abstract void onHandlePoolItem(T t);

    public PoolUpdateHandler() {
        this.mScheduledPoolItemQueue = new SynchronizedQueue(new ShiftList());
        this.mPool = new C21611();
    }

    public PoolUpdateHandler(int pInitialPoolSize) {
        this.mScheduledPoolItemQueue = new SynchronizedQueue(new ShiftList());
        this.mPool = new Pool<T>(pInitialPoolSize) {
            protected T onAllocatePoolItem() {
                return PoolUpdateHandler.this.onAllocatePoolItem();
            }
        };
    }

    public PoolUpdateHandler(int pInitialPoolSize, int pGrowth) {
        this.mScheduledPoolItemQueue = new SynchronizedQueue(new ShiftList());
        this.mPool = new Pool<T>(pInitialPoolSize, pGrowth) {
            protected T onAllocatePoolItem() {
                return PoolUpdateHandler.this.onAllocatePoolItem();
            }
        };
    }

    public PoolUpdateHandler(int pInitialPoolSize, int pGrowth, int pAvailableItemCountMaximum) {
        this.mScheduledPoolItemQueue = new SynchronizedQueue(new ShiftList());
        this.mPool = new Pool<T>(pInitialPoolSize, pGrowth, pAvailableItemCountMaximum) {
            protected T onAllocatePoolItem() {
                return PoolUpdateHandler.this.onAllocatePoolItem();
            }
        };
    }

    public void onUpdate(float pSecondsElapsed) {
        IQueue<T> scheduledPoolItemQueue = this.mScheduledPoolItemQueue;
        Pool<T> pool = this.mPool;
        while (true) {
            PoolItem item = (PoolItem) scheduledPoolItemQueue.poll();
            if (item != null) {
                onHandlePoolItem(item);
                pool.recyclePoolItem(item);
            } else {
                return;
            }
        }
    }

    public void reset() {
        IQueue<T> scheduledPoolItemQueue = this.mScheduledPoolItemQueue;
        Pool<T> pool = this.mPool;
        while (true) {
            PoolItem item = (PoolItem) scheduledPoolItemQueue.poll();
            if (item != null) {
                pool.recyclePoolItem(item);
            } else {
                return;
            }
        }
    }

    public T obtainPoolItem() {
        return (PoolItem) this.mPool.obtainPoolItem();
    }

    public void postPoolItem(T pPoolItem) {
        if (pPoolItem == null) {
            throw new IllegalArgumentException("PoolItem already recycled!");
        } else if (this.mPool.ownsPoolItem(pPoolItem)) {
            this.mScheduledPoolItemQueue.enter(pPoolItem);
        } else {
            throw new IllegalArgumentException("PoolItem from another pool!");
        }
    }
}
