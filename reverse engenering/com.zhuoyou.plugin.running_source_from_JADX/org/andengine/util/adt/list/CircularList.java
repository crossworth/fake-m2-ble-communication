package org.andengine.util.adt.list;

import java.util.Arrays;

public class CircularList<T> implements IList<T> {
    private static final int CAPACITY_INITIAL_DEFAULT = 1;
    private static final int INDEX_INVALID = -1;
    private int mHead;
    private Object[] mItems;
    private int mSize;

    public CircularList() {
        this(1);
    }

    public CircularList(int pInitialCapacity) {
        this.mItems = new Object[pInitialCapacity];
    }

    public boolean isEmpty() {
        return this.mSize == 0;
    }

    public void add(T pItem) {
        ensureCapacity();
        this.mItems[encodeToInternalIndex(this.mSize)] = pItem;
        this.mSize++;
    }

    public T get(int pIndex) throws ArrayIndexOutOfBoundsException {
        return this.mItems[encodeToInternalIndex(pIndex)];
    }

    public void set(int pIndex, T pItem) throws IndexOutOfBoundsException {
        this.mItems[encodeToInternalIndex(pIndex)] = pItem;
    }

    public int indexOf(T pItem) {
        int size = size();
        int i;
        if (pItem == null) {
            for (i = 0; i < size; i++) {
                if (get(i) == null) {
                    return i;
                }
            }
        } else {
            for (i = 0; i < size; i++) {
                if (pItem.equals(get(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void add(int pIndex, T pItem) {
        int internalIndex = encodeToInternalIndex(pIndex);
        ensureCapacity();
        int internalTail = encodeToInternalIndex(this.mSize);
        if (internalIndex != internalTail) {
            if (internalIndex == this.mHead) {
                this.mHead--;
                if (this.mHead == -1) {
                    this.mHead = this.mItems.length - 1;
                }
                internalIndex--;
                if (internalIndex == -1) {
                    internalIndex = this.mItems.length - 1;
                }
            } else if (internalIndex < this.mHead || this.mHead == 0) {
                System.arraycopy(this.mItems, internalIndex, this.mItems, internalIndex + 1, internalTail - internalIndex);
            } else if (internalIndex > internalTail) {
                System.arraycopy(this.mItems, this.mHead, this.mItems, this.mHead - 1, pIndex);
                this.mHead--;
                if (this.mHead == -1) {
                    this.mHead = this.mItems.length - 1;
                }
                internalIndex--;
                if (internalIndex == -1) {
                    internalIndex = this.mItems.length - 1;
                }
            } else if (pIndex < (this.mSize >> 1)) {
                System.arraycopy(this.mItems, this.mHead, this.mItems, this.mHead - 1, pIndex);
                this.mHead--;
                if (this.mHead == -1) {
                    this.mHead = this.mItems.length - 1;
                }
                internalIndex--;
                if (internalIndex == -1) {
                    internalIndex = this.mItems.length - 1;
                }
            } else {
                System.arraycopy(this.mItems, internalIndex, this.mItems, internalIndex + 1, internalTail - internalIndex);
            }
        }
        this.mItems[internalIndex] = pItem;
        this.mSize++;
    }

    public T removeFirst() {
        return remove(0);
    }

    public T removeLast() {
        return remove(size() - 1);
    }

    public boolean remove(T pItem) {
        int index = indexOf(pItem);
        if (index < 0) {
            return false;
        }
        remove(index);
        return true;
    }

    public T remove(int pIndex) {
        int internalIndex = encodeToInternalIndex(pIndex);
        T removed = this.mItems[internalIndex];
        int internalTail = encodeToInternalIndex(this.mSize - 1);
        if (internalIndex == internalTail) {
            this.mItems[internalTail] = null;
        } else if (internalIndex == this.mHead) {
            this.mItems[this.mHead] = null;
            this.mHead++;
            if (this.mHead == this.mItems.length) {
                this.mHead = 0;
            }
        } else if (internalIndex < this.mHead) {
            System.arraycopy(this.mItems, internalIndex + 1, this.mItems, internalIndex, internalTail - internalIndex);
            this.mItems[internalTail] = null;
        } else if (internalIndex > internalTail) {
            System.arraycopy(this.mItems, this.mHead, this.mItems, this.mHead + 1, pIndex);
            this.mItems[this.mHead] = null;
            this.mHead++;
            if (this.mHead == this.mItems.length) {
                this.mHead = 0;
            }
        } else if (pIndex < (this.mSize >> 1)) {
            System.arraycopy(this.mItems, this.mHead, this.mItems, this.mHead + 1, pIndex);
            this.mItems[this.mHead] = null;
            this.mHead++;
            if (this.mHead == this.mItems.length) {
                this.mHead = 0;
            }
        } else {
            System.arraycopy(this.mItems, internalIndex + 1, this.mItems, internalIndex, internalTail - internalIndex);
            this.mItems[internalTail] = null;
        }
        this.mSize--;
        return removed;
    }

    public int size() {
        return this.mSize;
    }

    public void clear() {
        int tail = this.mHead + this.mSize;
        int capacity = this.mItems.length;
        if (tail <= capacity) {
            Arrays.fill(this.mItems, this.mHead, tail, null);
        } else {
            int headToCapacity = capacity - this.mHead;
            Arrays.fill(this.mItems, this.mHead, capacity, null);
            Arrays.fill(this.mItems, 0, this.mSize - headToCapacity, null);
        }
        this.mHead = 0;
        this.mSize = 0;
    }

    private void ensureCapacity() {
        int currentCapacity = this.mItems.length;
        if (this.mSize == currentCapacity) {
            Object[] newItems = new Object[(((currentCapacity * 3) >> 1) + 1)];
            System.arraycopy(this.mItems, this.mHead, newItems, 0, this.mSize - this.mHead);
            System.arraycopy(this.mItems, 0, newItems, this.mSize - this.mHead, this.mHead);
            this.mItems = newItems;
            this.mHead = 0;
        }
    }

    private int encodeToInternalIndex(int pIndex) {
        int internalIndex = this.mHead + pIndex;
        if (internalIndex >= this.mItems.length) {
            return internalIndex - this.mItems.length;
        }
        return internalIndex;
    }
}
