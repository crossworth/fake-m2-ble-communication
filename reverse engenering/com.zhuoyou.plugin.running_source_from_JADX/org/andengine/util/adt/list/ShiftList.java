package org.andengine.util.adt.list;

import java.util.Arrays;
import org.andengine.util.adt.queue.IQueue;

public class ShiftList<T> implements IQueue<T> {
    private static final int CAPACITY_INITIAL_DEFAULT = 1;
    private static final int INDEX_INVALID = -1;
    protected int mHead;
    protected Object[] mItems;
    protected int mTail;

    public ShiftList() {
        this(1);
    }

    public ShiftList(int pInitialCapacity) {
        this.mItems = new Object[pInitialCapacity];
    }

    public boolean isEmpty() {
        return this.mHead == this.mTail;
    }

    public T get(int pIndex) throws ArrayIndexOutOfBoundsException {
        return this.mItems[this.mHead + pIndex];
    }

    public void set(int pIndex, T pItem) throws IndexOutOfBoundsException {
        this.mItems[this.mHead + pIndex] = pItem;
    }

    public int indexOf(T pItem) {
        int i;
        if (pItem == null) {
            for (i = this.mHead; i < this.mTail; i++) {
                if (this.mItems[i] == null) {
                    return i - this.mHead;
                }
            }
        } else {
            for (i = this.mHead; i < this.mTail; i++) {
                if (pItem.equals(this.mItems[i])) {
                    return i - this.mHead;
                }
            }
        }
        return -1;
    }

    public T peek() {
        if (this.mHead == this.mTail) {
            return null;
        }
        return this.mItems[this.mHead];
    }

    public T poll() {
        if (this.mHead == this.mTail) {
            return null;
        }
        T item = this.mItems[this.mHead];
        this.mItems[this.mHead] = null;
        this.mHead++;
        if (this.mHead != this.mTail) {
            return item;
        }
        this.mHead = 0;
        this.mTail = 0;
        return item;
    }

    public void enter(T pItem) {
        ensureShiftableRight();
        this.mItems[this.mTail] = pItem;
        this.mTail++;
    }

    public void enter(int pIndex, T pItem) throws ArrayIndexOutOfBoundsException {
        int size = this.mTail - this.mHead;
        if (pIndex < (size >> 1)) {
            enterShiftingLeft(pIndex, pItem);
        } else {
            enterShiftingRight(pIndex, pItem, size);
        }
    }

    private void enterShiftingRight(int pIndex, T pItem, int size) {
        ensureShiftableRight();
        int shiftAmount = size - pIndex;
        if (shiftAmount == 0) {
            this.mItems[this.mTail] = pItem;
        } else {
            int internalIndex = this.mHead + pIndex;
            System.arraycopy(this.mItems, internalIndex, this.mItems, internalIndex + 1, shiftAmount);
            this.mItems[internalIndex] = pItem;
        }
        this.mTail++;
    }

    private void enterShiftingLeft(int pIndex, T pItem) {
        ensureShiftableLeft();
        this.mHead--;
        if (pIndex == 0) {
            this.mItems[this.mHead] = pItem;
            return;
        }
        System.arraycopy(this.mItems, this.mHead + 1, this.mItems, this.mHead, pIndex);
        this.mItems[this.mHead + pIndex] = pItem;
    }

    public void add(T pItem) {
        enter(pItem);
    }

    public void add(int pIndex, T pItem) throws ArrayIndexOutOfBoundsException {
        enter(pIndex, pItem);
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

    public T remove(int pIndex) throws ArrayIndexOutOfBoundsException {
        int internalIndex = this.mHead + pIndex;
        T removed = this.mItems[internalIndex];
        int size = this.mTail - this.mHead;
        if (pIndex < (size >> 1)) {
            if (internalIndex > this.mHead) {
                System.arraycopy(this.mItems, this.mHead, this.mItems, this.mHead + 1, pIndex);
            }
            this.mItems[this.mHead] = null;
            this.mHead++;
        } else {
            int shiftAmount = (size - pIndex) - 1;
            if (shiftAmount > 0) {
                System.arraycopy(this.mItems, internalIndex + 1, this.mItems, internalIndex, shiftAmount);
            }
            this.mTail--;
            this.mItems[this.mTail] = null;
        }
        return removed;
    }

    public int size() {
        return this.mTail - this.mHead;
    }

    public void clear() {
        Arrays.fill(this.mItems, this.mHead, this.mTail, null);
        this.mHead = 0;
        this.mTail = 0;
    }

    public void shift() {
        int size = this.mTail - this.mHead;
        if (size == 0) {
            this.mHead = 0;
            this.mTail = 0;
            return;
        }
        System.arraycopy(this.mItems, this.mHead, this.mItems, 0, size);
        int start = Math.max(size, this.mHead);
        int end = Math.max(start, this.mTail);
        if (start < end) {
            Arrays.fill(this.mItems, start, end, null);
        }
        this.mHead = 0;
        this.mTail = size;
    }

    private void ensureShiftableRight() {
        int currentCapacity = this.mItems.length;
        if (this.mTail != currentCapacity) {
            return;
        }
        if (this.mTail - this.mHead != currentCapacity) {
            shift();
            return;
        }
        Object[] newItems = new Object[(((currentCapacity * 3) >> 1) + 1)];
        System.arraycopy(this.mItems, 0, newItems, 0, currentCapacity);
        this.mItems = newItems;
    }

    private void ensureShiftableLeft() {
        if (this.mHead == 0) {
            int size = this.mTail - this.mHead;
            int currentCapacity = this.mItems.length;
            if (size >= currentCapacity) {
                Object[] newItems = new Object[(((currentCapacity * 3) >> 1) + 1)];
                System.arraycopy(this.mItems, 0, newItems, 1, currentCapacity);
                this.mItems = newItems;
                this.mHead++;
                this.mTail++;
            } else if (size == 0) {
                this.mHead = 1;
                this.mTail = 1;
            } else {
                System.arraycopy(this.mItems, this.mHead, this.mItems, this.mHead + 1, size);
                this.mItems[this.mHead] = null;
                this.mHead++;
                this.mTail++;
            }
        }
    }
}
