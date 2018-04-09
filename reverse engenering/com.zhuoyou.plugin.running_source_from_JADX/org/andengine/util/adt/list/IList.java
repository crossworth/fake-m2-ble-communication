package org.andengine.util.adt.list;

public interface IList<T> {
    void add(int i, T t) throws IndexOutOfBoundsException;

    void add(T t);

    void clear();

    T get(int i) throws IndexOutOfBoundsException;

    int indexOf(T t);

    boolean isEmpty();

    T remove(int i) throws IndexOutOfBoundsException;

    boolean remove(T t);

    T removeFirst();

    T removeLast();

    void set(int i, T t) throws IndexOutOfBoundsException;

    int size();
}
