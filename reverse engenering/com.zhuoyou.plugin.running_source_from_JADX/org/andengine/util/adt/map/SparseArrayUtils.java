package org.andengine.util.adt.map;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

public final class SparseArrayUtils {
    public static final String toString(SparseArray<?> pSparseArray) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = pSparseArray.size();
        stringBuilder.append("{");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(pSparseArray.keyAt(i)).append("=").append(pSparseArray.valueAt(i));
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static final String toString(SparseIntArray pSparseIntArray) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = pSparseIntArray.size();
        stringBuilder.append("{");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(pSparseIntArray.keyAt(i)).append("=").append(pSparseIntArray.valueAt(i));
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static final String toString(SparseBooleanArray pSparseBooleanArray) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = pSparseBooleanArray.size();
        stringBuilder.append("{");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(pSparseBooleanArray.keyAt(i)).append("=").append(pSparseBooleanArray.valueAt(i));
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static final String toString(LongSparseArray<?> pLongSparseArray) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = pLongSparseArray.size();
        stringBuilder.append("{");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(pLongSparseArray.keyAt(i)).append("=").append(pLongSparseArray.valueAt(i));
            if (i < size - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
