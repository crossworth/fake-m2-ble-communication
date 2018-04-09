package org.andengine.util.adt.cache;

import java.util.HashMap;
import org.andengine.util.adt.pool.GenericPool;

public class LRUCache<K, V> {
    private final int mCapacity;
    private final LRUCacheQueue<K> mLRUCacheQueue;
    private final GenericPool<LRUCacheValueHolder<K, V>> mLRUCacheValueHolderPool = new C21081();
    private final HashMap<K, LRUCacheValueHolder<K, V>> mMap;
    private int mSize;

    class C21081 extends GenericPool<LRUCacheValueHolder<K, V>> {
        C21081() {
        }

        protected LRUCacheValueHolder<K, V> onAllocatePoolItem() {
            return new LRUCacheValueHolder();
        }

        protected void onHandleRecycleItem(LRUCacheValueHolder<K, V> pLRUCacheValueHolder) {
            pLRUCacheValueHolder.mLRUCacheQueueNode = null;
            pLRUCacheValueHolder.mValue = null;
        }
    }

    static class LRUCacheQueue<K> {
        private LRUCacheQueueNode<K> mHead;
        private final GenericPool<LRUCacheQueueNode<K>> mLRUCacheQueueNodePool = new C21091();
        private LRUCacheQueueNode<K> mTail;

        class C21091 extends GenericPool<LRUCacheQueueNode<K>> {
            C21091() {
            }

            protected LRUCacheQueueNode<K> onAllocatePoolItem() {
                return new LRUCacheQueueNode();
            }

            protected void onHandleRecycleItem(LRUCacheQueueNode<K> pLRUCacheQueueNode) {
                pLRUCacheQueueNode.mKey = null;
                pLRUCacheQueueNode.mPrevious = null;
                pLRUCacheQueueNode.mNext = null;
            }
        }

        LRUCacheQueue() {
        }

        public boolean isEmpty() {
            return this.mHead == null;
        }

        public LRUCacheQueueNode<K> add(K pKey) {
            LRUCacheQueueNode lruCacheQueueNode = (LRUCacheQueueNode) this.mLRUCacheQueueNodePool.obtainPoolItem();
            lruCacheQueueNode.mKey = pKey;
            return add(lruCacheQueueNode);
        }

        private LRUCacheQueueNode<K> add(LRUCacheQueueNode<K> pLRUCacheQueueNode) {
            if (isEmpty()) {
                this.mHead = pLRUCacheQueueNode;
                this.mTail = this.mHead;
            } else {
                this.mTail.mNext = pLRUCacheQueueNode;
                pLRUCacheQueueNode.mPrevious = this.mTail;
                this.mTail = pLRUCacheQueueNode;
            }
            return this.mTail;
        }

        public K poll() {
            LRUCacheQueueNode<K> head = this.mHead;
            K key = this.mHead.mKey;
            if (this.mHead.mNext == null) {
                this.mHead = null;
                this.mTail = null;
            } else {
                this.mHead = this.mHead.mNext;
                this.mHead.mPrevious = null;
            }
            this.mLRUCacheQueueNodePool.recyclePoolItem(head);
            return key;
        }

        public void moveToTail(LRUCacheQueueNode<K> pLRUCacheQueueNode) {
            LRUCacheQueueNode<K> next = pLRUCacheQueueNode.mNext;
            if (next != null) {
                LRUCacheQueueNode<K> previous = pLRUCacheQueueNode.mPrevious;
                next.mPrevious = previous;
                if (previous == null) {
                    this.mHead = next;
                } else {
                    previous.mNext = next;
                }
                this.mTail.mNext = pLRUCacheQueueNode;
                pLRUCacheQueueNode.mPrevious = this.mTail;
                pLRUCacheQueueNode.mNext = null;
                this.mTail = pLRUCacheQueueNode;
            }
        }
    }

    static class LRUCacheQueueNode<K> {
        K mKey;
        LRUCacheQueueNode<K> mNext;
        LRUCacheQueueNode<K> mPrevious;

        LRUCacheQueueNode() {
        }
    }

    static class LRUCacheValueHolder<K, V> {
        LRUCacheQueueNode<K> mLRUCacheQueueNode;
        V mValue;

        LRUCacheValueHolder() {
        }
    }

    public LRUCache(int pCapacity) {
        this.mCapacity = pCapacity;
        this.mMap = new HashMap(pCapacity);
        this.mLRUCacheQueue = new LRUCacheQueue();
    }

    public int getCapacity() {
        return this.mCapacity;
    }

    public int getSize() {
        return this.mSize;
    }

    public boolean isEmpty() {
        return this.mSize == 0;
    }

    public V put(K pKey, V pValue) {
        LRUCacheValueHolder<K, V> existingLRUCacheValueHolder = (LRUCacheValueHolder) this.mMap.get(pKey);
        if (existingLRUCacheValueHolder != null) {
            this.mLRUCacheQueue.moveToTail(existingLRUCacheValueHolder.mLRUCacheQueueNode);
            return existingLRUCacheValueHolder.mValue;
        }
        if (this.mSize >= this.mCapacity) {
            this.mMap.remove(this.mLRUCacheQueue.poll());
            this.mSize--;
        }
        LRUCacheQueueNode<K> lruCacheQueueNode = this.mLRUCacheQueue.add((Object) pKey);
        LRUCacheValueHolder<K, V> lruCacheValueHolder = (LRUCacheValueHolder) this.mLRUCacheValueHolderPool.obtainPoolItem();
        lruCacheValueHolder.mValue = pValue;
        lruCacheValueHolder.mLRUCacheQueueNode = lruCacheQueueNode;
        this.mMap.put(pKey, lruCacheValueHolder);
        this.mSize++;
        return null;
    }

    public V get(K pKey) {
        LRUCacheValueHolder<K, V> lruCacheValueHolder = (LRUCacheValueHolder) this.mMap.get(pKey);
        if (lruCacheValueHolder == null) {
            return null;
        }
        this.mLRUCacheQueue.moveToTail(lruCacheValueHolder.mLRUCacheQueueNode);
        return lruCacheValueHolder.mValue;
    }

    public void clear() {
        while (!this.mLRUCacheQueue.isEmpty()) {
            this.mLRUCacheValueHolderPool.recyclePoolItem((LRUCacheValueHolder) this.mMap.remove(this.mLRUCacheQueue.poll()));
        }
        this.mSize = 0;
    }
}
