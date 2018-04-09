package org.andengine.util.adt.pool;

import java.util.ArrayList;
import java.util.Collections;
import org.andengine.util.debug.Debug;

public abstract class GenericPool<T> {
    private final int mAvailableItemCountMaximum;
    private final ArrayList<T> mAvailableItems;
    private final int mGrowth;
    private int mUnrecycledItemCount;

    protected abstract T onAllocatePoolItem();

    public GenericPool() {
        this(0);
    }

    public GenericPool(int pInitialSize) {
        this(pInitialSize, 1);
    }

    public GenericPool(int pInitialSize, int pGrowth) {
        this(pInitialSize, pGrowth, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public GenericPool(int pInitialSize, int pGrowth, int pAvailableItemsMaximum) {
        if (pGrowth <= 0) {
            throw new IllegalArgumentException("pGrowth must be greater than 0!");
        } else if (pAvailableItemsMaximum < 0) {
            throw new IllegalArgumentException("pAvailableItemsMaximum must be at least 0!");
        } else {
            this.mGrowth = pGrowth;
            this.mAvailableItemCountMaximum = pAvailableItemsMaximum;
            this.mAvailableItems = new ArrayList(pInitialSize);
            if (pInitialSize > 0) {
                batchAllocatePoolItems(pInitialSize);
            }
        }
    }

    public synchronized int getUnrecycledItemCount() {
        return this.mUnrecycledItemCount;
    }

    public synchronized int getAvailableItemCount() {
        return this.mAvailableItems.size();
    }

    public int getAvailableItemCountMaximum() {
        return this.mAvailableItemCountMaximum;
    }

    protected void onHandleRecycleItem(T t) {
    }

    protected T onHandleAllocatePoolItem() {
        return onAllocatePoolItem();
    }

    protected void onHandleObtainItem(T t) {
    }

    public synchronized void batchAllocatePoolItems(int pCount) {
        ArrayList<T> availableItems = this.mAvailableItems;
        int allocationCount = this.mAvailableItemCountMaximum - availableItems.size();
        if (pCount < allocationCount) {
            allocationCount = pCount;
        }
        for (int i = allocationCount - 1; i >= 0; i--) {
            availableItems.add(onHandleAllocatePoolItem());
        }
    }

    public synchronized T obtainPoolItem() {
        T item;
        if (this.mAvailableItems.size() > 0) {
            item = this.mAvailableItems.remove(this.mAvailableItems.size() - 1);
        } else if (this.mGrowth == 1 || this.mAvailableItemCountMaximum == 0) {
            item = onHandleAllocatePoolItem();
        } else {
            batchAllocatePoolItems(this.mGrowth);
            item = this.mAvailableItems.remove(this.mAvailableItems.size() - 1);
        }
        onHandleObtainItem(item);
        this.mUnrecycledItemCount++;
        return item;
    }

    public synchronized void recyclePoolItem(T pItem) {
        if (pItem == null) {
            throw new IllegalArgumentException("Cannot recycle null item!");
        }
        onHandleRecycleItem(pItem);
        if (this.mAvailableItems.size() < this.mAvailableItemCountMaximum) {
            this.mAvailableItems.add(pItem);
        }
        this.mUnrecycledItemCount--;
        if (this.mUnrecycledItemCount < 0) {
            Debug.m4588e("More items recycled than obtained!");
        }
    }

    public synchronized void shufflePoolItems() {
        Collections.shuffle(this.mAvailableItems);
    }
}
