package org.andengine.util.adt.list;

public interface IFloatList {
    void add(float f);

    void add(int i, float f) throws ArrayIndexOutOfBoundsException;

    void clear();

    float get(int i) throws ArrayIndexOutOfBoundsException;

    boolean isEmpty();

    float remove(int i) throws ArrayIndexOutOfBoundsException;

    int size();

    float[] toArray();
}
