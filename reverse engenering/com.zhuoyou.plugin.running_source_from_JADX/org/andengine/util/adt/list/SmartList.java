package org.andengine.util.adt.list;

import java.util.ArrayList;
import java.util.List;
import org.andengine.util.IMatcher;
import org.andengine.util.call.ParameterCallable;

public class SmartList<T> extends ArrayList<T> {
    private static final long serialVersionUID = 8655669528273139819L;

    public SmartList(int pCapacity) {
        super(pCapacity);
    }

    public void addFirst(T pItem) {
        add(0, pItem);
    }

    public void addLast(T pItem) {
        add(size(), pItem);
    }

    public T getFirst() throws IndexOutOfBoundsException {
        return get(0);
    }

    public T getLast() throws IndexOutOfBoundsException {
        return get(size() - 1);
    }

    public T get(IMatcher<T> pMatcher) {
        int size = size();
        for (int i = 0; i < size; i++) {
            T item = get(i);
            if (pMatcher.matches(item)) {
                return item;
            }
        }
        return null;
    }

    public T removeFirst() throws IndexOutOfBoundsException {
        return remove(0);
    }

    public T removeLast() throws IndexOutOfBoundsException {
        return remove(size() - 1);
    }

    public boolean remove(T pItem, ParameterCallable<T> pParameterCallable) {
        boolean removed = remove(pItem);
        if (removed) {
            pParameterCallable.call(pItem);
        }
        return removed;
    }

    public T remove(IMatcher<T> pMatcher) {
        for (int i = 0; i < size(); i++) {
            if (pMatcher.matches(get(i))) {
                return remove(i);
            }
        }
        return null;
    }

    public T remove(IMatcher<T> pMatcher, ParameterCallable<T> pParameterCallable) {
        for (int i = size() - 1; i >= 0; i--) {
            if (pMatcher.matches(get(i))) {
                T removed = remove(i);
                pParameterCallable.call(removed);
                return removed;
            }
        }
        return null;
    }

    public boolean removeAll(IMatcher<T> pMatcher) {
        boolean result = false;
        for (int i = size() - 1; i >= 0; i--) {
            if (pMatcher.matches(get(i))) {
                remove(i);
                result = true;
            }
        }
        return result;
    }

    public boolean removeAll(IMatcher<T> pMatcher, ParameterCallable<T> pParameterCallable) {
        boolean result = false;
        for (int i = size() - 1; i >= 0; i--) {
            if (pMatcher.matches(get(i))) {
                pParameterCallable.call(remove(i));
                result = true;
            }
        }
        return result;
    }

    public void clear(ParameterCallable<T> pParameterCallable) {
        for (int i = size() - 1; i >= 0; i--) {
            pParameterCallable.call(remove(i));
        }
    }

    public int indexOf(IMatcher<T> pMatcher) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (pMatcher.matches(get(i))) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(IMatcher<T> pMatcher) {
        for (int i = size() - 1; i >= 0; i--) {
            if (pMatcher.matches(get(i))) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<T> query(IMatcher<T> pMatcher) {
        return (ArrayList) query(pMatcher, new ArrayList());
    }

    public <L extends List<T>> L query(IMatcher<T> pMatcher, L pResult) {
        int size = size();
        for (int i = 0; i < size; i++) {
            T item = get(i);
            if (pMatcher.matches(item)) {
                pResult.add(item);
            }
        }
        return pResult;
    }

    public <S extends T> ArrayList<S> queryForSubclass(IMatcher<T> pMatcher) {
        return (ArrayList) queryForSubclass(pMatcher, new ArrayList());
    }

    public <L extends List<S>, S extends T> L queryForSubclass(IMatcher<T> pMatcher, L pResult) {
        int size = size();
        for (int i = 0; i < size; i++) {
            T item = get(i);
            if (pMatcher.matches(item)) {
                pResult.add(item);
            }
        }
        return pResult;
    }

    public void call(ParameterCallable<T> pParameterCallable) {
        for (int i = size() - 1; i >= 0; i--) {
            pParameterCallable.call(get(i));
        }
    }

    public void call(IMatcher<T> pMatcher, ParameterCallable<T> pParameterCallable) {
        for (int i = size() - 1; i >= 0; i--) {
            T item = get(i);
            if (pMatcher.matches(item)) {
                pParameterCallable.call(item);
            }
        }
    }
}
