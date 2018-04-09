package org.andengine.util.adt.queue;

import org.andengine.util.adt.list.CircularList;

public class CircularQueue<T> extends CircularList<T> implements IQueue<T> {
    public CircularQueue(int pInitialCapacity) {
        super(pInitialCapacity);
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
        add(pItem);
    }

    public void enter(int pIndex, T pItem) throws IndexOutOfBoundsException {
        add(pIndex, pItem);
    }
}
