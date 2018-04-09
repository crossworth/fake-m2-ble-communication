package org.andengine.util.adt.queue;

import org.andengine.util.adt.list.SortedList;

public class SortedQueue<T extends Comparable<T>> extends SortedList<T> implements ISortedQueue<T> {
    public SortedQueue(IQueue<T> pQueue) {
        super(pQueue);
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return get(0);
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }
        return remove(0);
    }

    public void enter(T pItem) {
        add((Comparable) pItem);
    }

    @Deprecated
    public void enter(int pIndex, T pItem) throws IndexOutOfBoundsException {
        add(pIndex, (Comparable) pItem);
    }
}
