package org.andengine.util.adt.list;

public class FloatArrayList implements IFloatList {
    private static final int CAPACITY_INITIAL_DEFAULT = 0;
    private float[] mItems;
    private int mSize;

    public FloatArrayList() {
        this(0);
    }

    public FloatArrayList(int pInitialCapacity) {
        this.mItems = new float[pInitialCapacity];
    }

    public boolean isEmpty() {
        return this.mSize == 0;
    }

    public float get(int pIndex) throws ArrayIndexOutOfBoundsException {
        return this.mItems[pIndex];
    }

    public void add(float pItem) {
        ensureCapacity(this.mSize + 1);
        this.mItems[this.mSize] = pItem;
        this.mSize++;
    }

    public void add(int pIndex, float pItem) throws ArrayIndexOutOfBoundsException {
        ensureCapacity(this.mSize + 1);
        System.arraycopy(this.mItems, pIndex, this.mItems, pIndex + 1, this.mSize - pIndex);
        this.mItems[pIndex] = pItem;
        this.mSize++;
    }

    public float remove(int pIndex) throws ArrayIndexOutOfBoundsException {
        float oldValue = this.mItems[pIndex];
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

    public float[] toArray() {
        float[] array = new float[this.mSize];
        System.arraycopy(this.mItems, 0, array, 0, this.mSize);
        return array;
    }

    private void ensureCapacity(int pCapacity) {
        int currentCapacity = this.mItems.length;
        if (currentCapacity < pCapacity) {
            float[] newItems = new float[(((currentCapacity * 3) >> 1) + 1)];
            System.arraycopy(this.mItems, 0, newItems, 0, currentCapacity);
            this.mItems = newItems;
        }
    }
}
