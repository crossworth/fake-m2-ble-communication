package org.andengine.util.adt.list;

import java.util.ArrayList;
import java.util.List;
import org.andengine.util.adt.queue.IQueue;
import org.andengine.util.adt.queue.concurrent.SynchronizedQueue;
import org.andengine.util.math.MathUtils;

public final class ListUtils {
    public static final <T> IQueue<T> synchronizedQueue(IQueue<T> pQueue) {
        return new SynchronizedQueue(pQueue);
    }

    public static final <T> T random(List<T> pList) {
        return pList.get(MathUtils.random(0, pList.size() - 1));
    }

    public static final <T> ArrayList<? extends T> toList(T pItem) {
        ArrayList<T> out = new ArrayList();
        out.add(pItem);
        return out;
    }

    public static final <T> ArrayList<? extends T> toList(T... pItems) {
        ArrayList<T> out = new ArrayList();
        for (Object add : pItems) {
            out.add(add);
        }
        return out;
    }

    public static <T> void swap(List<T> pItems, int pIndexA, int pIndexB) {
        T tmp = pItems.get(pIndexA);
        pItems.set(pIndexA, pItems.get(pIndexB));
        pItems.set(pIndexB, tmp);
    }

    public static <T> void swap(IList<T> pItems, int pIndexA, int pIndexB) {
        T tmp = pItems.get(pIndexA);
        pItems.set(pIndexA, pItems.get(pIndexB));
        pItems.set(pIndexB, tmp);
    }

    public static final int encodeInsertionIndex(int pIndex) {
        return (-pIndex) - 1;
    }
}
