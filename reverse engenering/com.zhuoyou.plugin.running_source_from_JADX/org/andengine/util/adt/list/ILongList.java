package org.andengine.util.adt.list;

public interface ILongList {
    void add(int i, long j) throws ArrayIndexOutOfBoundsException;

    void add(long j);

    void clear();

    float get(int i) throws ArrayIndexOutOfBoundsException;

    boolean isEmpty();

    float remove(int i) throws ArrayIndexOutOfBoundsException;

    int size();

    long[] toArray();
}
