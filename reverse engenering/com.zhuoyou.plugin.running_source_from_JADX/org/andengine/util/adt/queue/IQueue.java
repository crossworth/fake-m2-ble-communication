package org.andengine.util.adt.queue;

import org.andengine.util.adt.list.IList;

public interface IQueue<T> extends IList<T> {
    void enter(int i, T t) throws IndexOutOfBoundsException;

    void enter(T t);

    T peek();

    T poll();
}
