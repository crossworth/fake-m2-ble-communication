package org.andengine.util.adt.list;

public interface IIntList {
    void add(int i);

    void add(int i, int i2) throws ArrayIndexOutOfBoundsException;

    void clear();

    float get(int i) throws ArrayIndexOutOfBoundsException;

    boolean isEmpty();

    float remove(int i) throws ArrayIndexOutOfBoundsException;

    int size();

    int[] toArray();
}
