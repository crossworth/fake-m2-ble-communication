package org.andengine.util.adt.queue.concurrent;

import org.andengine.util.adt.queue.IQueue;

public class SynchronizedQueue<T> implements IQueue<T> {
    private final IQueue<T> mQueue;

    public SynchronizedQueue(IQueue<T> pQueue) {
        this.mQueue = pQueue;
    }

    public synchronized boolean isEmpty() {
        return this.mQueue.isEmpty();
    }

    public synchronized T get(int pIndex) throws IndexOutOfBoundsException {
        return this.mQueue.get(pIndex);
    }

    public synchronized void set(int pIndex, T pItem) throws IndexOutOfBoundsException {
        this.mQueue.set(pIndex, pItem);
    }

    public synchronized int indexOf(T pItem) {
        return this.mQueue.indexOf(pItem);
    }

    public synchronized void add(T pItem) {
        this.mQueue.add(pItem);
    }

    public synchronized void add(int pIndex, T pItem) throws IndexOutOfBoundsException {
        this.mQueue.add(pIndex, pItem);
    }

    public synchronized T peek() {
        return this.mQueue.peek();
    }

    public synchronized T poll() {
        return this.mQueue.poll();
    }

    public synchronized void enter(T pItem) {
        this.mQueue.enter(pItem);
    }

    public synchronized void enter(int pIndex, T pItem) throws IndexOutOfBoundsException {
        this.mQueue.enter(pIndex, pItem);
    }

    public synchronized T removeFirst() {
        return this.mQueue.removeFirst();
    }

    public synchronized T removeLast() {
        return this.mQueue.removeLast();
    }

    public synchronized boolean remove(T pItem) {
        return this.mQueue.remove((Object) pItem);
    }

    public synchronized T remove(int pIndex) throws IndexOutOfBoundsException {
        return this.mQueue.remove(pIndex);
    }

    public synchronized int size() {
        return this.mQueue.size();
    }

    public synchronized void clear() {
        this.mQueue.clear();
    }
}
