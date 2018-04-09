package org.andengine.util.adt.list;

public class LongArrayList implements ILongList {
    private static final int CAPACITY_INITIAL_DEFAULT = 0;
    private long[] mItems;
    private int mSize;

    public LongArrayList() {
        this(0);
    }

    public LongArrayList(int pInitialCapacity) {
        this.mItems = new long[pInitialCapacity];
    }

    public boolean isEmpty() {
        return this.mSize == 0;
    }

    public float get(int pIndex) throws ArrayIndexOutOfBoundsException {
        return (float) this.mItems[pIndex];
    }

    public void add(long pItem) {
        ensureCapacity(this.mSize + 1);
        this.mItems[this.mSize] = pItem;
        this.mSize++;
    }

    public void add(int pIndex, long pItem) throws ArrayIndexOutOfBoundsException {
        ensureCapacity(this.mSize + 1);
        System.arraycopy(this.mItems, pIndex, this.mItems, pIndex + 1, this.mSize - pIndex);
        this.mItems[pIndex] = pItem;
        this.mSize++;
    }

    public float remove(int pIndex) throws ArrayIndexOutOfBoundsException {
        float oldValue = (float) this.mItems[pIndex];
        int numMoved = (this.mSize - pIndex) - 1;
        if (numMoved > 0) {
            System.arraycopy(this.mItems, pIndex + 1, this.mItems, pIndex, numMoved);
        }
        this.mSize--;
        return oldValue;
    }

    public int size() {
        return this.mSize;
    }

    public void clear() {
        this.mSize = 0;
    }

    public long[] toArray() {
        long[] array = new long[this.mSize];
        System.arraycopy(this.mItems, 0, array, 0, this.mSize);
        return array;
    }

    private void ensureCapacity(int pCapacity) {
        int currentCapacity = this.mItems.length;
        if (currentCapacity < pCapacity) {
            long[] newItems = new long[(((currentCapacity * 3) >> 1) + 1)];
            System.arraycopy(this.mItems, 0, newItems, 0, currentCapacity);
            this.mItems = newItems;
        }
    }
}
