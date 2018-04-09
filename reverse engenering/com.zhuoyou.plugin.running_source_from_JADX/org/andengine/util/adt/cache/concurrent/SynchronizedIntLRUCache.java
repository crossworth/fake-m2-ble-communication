package org.andengine.util.adt.cache.concurrent;

import org.andengine.util.adt.cache.IntLRUCache;

public class SynchronizedIntLRUCache<V> extends IntLRUCache<V> {
    public SynchronizedIntLRUCache(int pCapacity) {
        super(pCapacity);
    }

    public synchronized int getSize() {
        return super.getSize();
    }

    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    public synchronized V put(int pKey, V pValue) {
        return super.put(pKey, pValue);
    }

    public synchronized V get(int pKey) {
        return super.get(pKey);
    }

    public synchronized void clear() {
        super.clear();
    }
}
