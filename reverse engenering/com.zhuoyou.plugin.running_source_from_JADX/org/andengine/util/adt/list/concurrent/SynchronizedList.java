package org.andengine.util.adt.list.concurrent;

import org.andengine.util.adt.list.IList;

public class SynchronizedList<T> implements IList<T> {
    protected final IList<T> mList;

    public SynchronizedList(IList<T> mList) {
        this.mList = mList;
    }

    public synchronized boolean isEmpty() {
        return this.mList.isEmpty();
    }

    public synchronized T get(int pIndex) throws IndexOutOfBoundsException {
        return this.mList.get(pIndex);
    }

    public synchronized void set(int pIndex, T pItem) throws IndexOutOfBoundsException {
        this.mList.set(pIndex, pItem);
    }

    public synchronized int indexOf(T pItem) {
        return this.mList.indexOf(pItem);
    }

    public synchronized void add(T pItem) {
        this.mList.add(pItem);
    }

    public synchronized void add(int pIndex, T pItem) throws IndexOutOfBoundsException {
        this.mList.add(pIndex, pItem);
    }

    public synchronized T removeFirst() {
        return this.mList.removeFirst();
    }

    public synchronized T removeLast() {
        return this.mList.removeLast();
    }

    public synchronized boolean remove(T pItem) {
        return this.mList.remove((Object) pItem);
    }

    public synchronized T remove(int pIndex) throws IndexOutOfBoundsException {
        return this.mList.remove(pIndex);
    }

    public synchronized int size() {
        return this.mList.size();
    }

    public synchronized void clear() {
        this.mList.clear();
    }
}
