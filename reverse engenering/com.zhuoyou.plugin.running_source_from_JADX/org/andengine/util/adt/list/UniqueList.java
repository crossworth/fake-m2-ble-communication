package org.andengine.util.adt.list;

public class UniqueList<T extends Comparable<T>> implements ISortedList<T> {
    private final IList<T> mList;

    public UniqueList(IList<T> pList) {
        this.mList = pList;
    }

    public boolean isEmpty() {
        return this.mList.isEmpty();
    }

    public T get(int pIndex) throws IndexOutOfBoundsException {
        return (Comparable) this.mList.get(pIndex);
    }

    @Deprecated
    public void set(int pIndex, T pItem) throws IndexOutOfBoundsException {
        this.mList.set(pIndex, pItem);
    }

    public int indexOf(T pItem) {
        return this.mList.indexOf(pItem);
    }

    @Deprecated
    public void add(int pIndex, T pItem) {
        if (indexOf((Comparable) pItem) < 0) {
            this.mList.add(pItem);
        }
    }

    public void add(T pItem) {
        int index = indexOf((Comparable) pItem);
        if (index < 0) {
            this.mList.add(ListUtils.encodeInsertionIndex(index), pItem);
        }
    }

    public T removeFirst() {
        return (Comparable) this.mList.removeFirst();
    }

    public T removeLast() {
        return (Comparable) this.mList.removeLast();
    }

    public boolean remove(T pItem) {
        return this.mList.remove((Object) pItem);
    }

    public T remove(int pIndex) {
        return (Comparable) this.mList.remove(pIndex);
    }

    public int size() {
        return this.mList.size();
    }

    public void clear() {
        this.mList.clear();
    }
}
