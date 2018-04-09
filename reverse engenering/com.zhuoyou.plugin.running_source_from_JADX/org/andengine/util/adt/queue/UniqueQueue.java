package org.andengine.util.adt.queue;

import org.andengine.util.adt.list.UniqueList;

public class UniqueQueue<T extends Comparable<T>> extends UniqueList<T> implements IUniqueQueue<T> {
    public UniqueQueue(IQueue<T> pQueue) {
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
