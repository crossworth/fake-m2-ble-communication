package org.andengine.util.adt.map;

import android.util.SparseArray;

public class Library<T> {
    protected final SparseArray<T> mItems;

    public Library() {
        this.mItems = new SparseArray();
    }

    public Library(int pInitialCapacity) {
        this.mItems = new SparseArray(pInitialCapacity);
    }

    public T get(int pID) {
        return this.mItems.get(pID);
    }

    public void put(int pID, T pItem) {
        T item = this.mItems.get(pID);
        if (item == null) {
            this.mItems.put(pID, pItem);
            return;
        }
        throw new IllegalArgumentException("ID: '" + pID + "' is already associated with item: '" + item.toString() + "'.");
    }

    public void remove(int pID) {
        this.mItems.remove(pID);
    }

    public void clear() {
        this.mItems.clear();
    }
}
