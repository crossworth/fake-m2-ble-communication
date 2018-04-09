package org.andengine.util.adt.cache.concurrent;

import org.andengine.util.adt.cache.LRUCache;

public class SynchronizedLRUCache<K, V> extends LRUCache<K, V> {
    public SynchronizedLRUCache(int pCapacity) {
        super(pCapacity);
    }

    public synchronized int getSize() {
        return super.getSize();
    }

    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    public synchronized V put(K pKey, V pValue) {
        return super.put(pKey, pValue);
    }

    public synchronized V get(K pKey) {
        return super.get(pKey);
    }

    public synchronized void clear() {
        super.clear();
    }
}
