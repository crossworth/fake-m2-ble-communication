package org.andengine.util.adt.array;

import java.lang.reflect.Array;
import java.util.List;
import org.andengine.util.math.MathUtils;

public final class ArrayUtils {
    public static final byte random(byte[] pArray) {
        return pArray[MathUtils.random(0, pArray.length - 1)];
    }

    public static final short random(short[] pArray) {
        return pArray[MathUtils.random(0, pArray.length - 1)];
    }

    public static final int random(int[] pArray) {
        return pArray[MathUtils.random(0, pArray.length - 1)];
    }

    public static final long random(long[] pArray) {
        return pArray[MathUtils.random(0, pArray.length - 1)];
    }

    public static final float random(float[] pArray) {
        return pArray[MathUtils.random(0, pArray.length - 1)];
    }

    public static final double random(double[] pArray) {
        return pArray[MathUtils.random(0, pArray.length - 1)];
    }

    public static final <T> T random(T[] pArray) {
        return pArray[MathUtils.random(0, pArray.length - 1)];
    }

    public static final void reverse(byte[] pArray) {
        if (pArray != null) {
            int j = pArray.length - 1;
            for (int i = 0; j > i; i++) {
                byte tmp = pArray[j];
                pArray[j] = pArray[i];
                pArray[i] = tmp;
                j--;
            }
        }
    }

    public static final void reverse(short[] pArray) {
        if (pArray != null) {
            int j = pArray.length - 1;
            for (int i = 0; j > i; i++) {
                short tmp = pArray[j];
                pArray[j] = pArray[i];
                pArray[i] = tmp;
                j--;
            }
        }
    }

    public static final void reverse(int[] pArray) {
        if (pArray != null) {
            int j = pArray.length - 1;
            for (int i = 0; j > i; i++) {
                int tmp = pArray[j];
                pArray[j] = pArray[i];
                pArray[i] = tmp;
                j--;
            }
        }
    }

    public static final void reverse(long[] pArray) {
        if (pArray != null) {
            int j = pArray.length - 1;
            for (int i = 0; j > i; i++) {
                long tmp = pArray[j];
                pArray[j] = pArray[i];
                pArray[i] = tmp;
                j--;
            }
        }
    }

    public static final void reverse(float[] pArray) {
        if (pArray != null) {
            int j = pArray.length - 1;
            for (int i = 0; j > i; i++) {
                float tmp = pArray[j];
                pArray[j] = pArray[i];
                pArray[i] = tmp;
                j--;
            }
        }
    }

    public static final void reverse(double[] pArray) {
        if (pArray != null) {
            int j = pArray.length - 1;
            for (int i = 0; j > i; i++) {
                double tmp = pArray[j];
                pArray[j] = pArray[i];
                pArray[i] = tmp;
                j--;
            }
        }
    }

    public static final void reverse(Object[] pArray) {
        if (pArray != null) {
            int j = pArray.length - 1;
            for (int i = 0; j > i; i++) {
                Object tmp = pArray[j];
                pArray[j] = pArray[i];
                pArray[i] = tmp;
                j--;
            }
        }
    }

    public static final boolean equals(byte[] pArrayA, int pOffsetA, byte[] pArrayB, int pOffsetB, int pLength) {
        int lastIndexA = pOffsetA + pLength;
        if (lastIndexA > pArrayA.length) {
            throw new ArrayIndexOutOfBoundsException(pArrayA.length);
        } else if (pOffsetB + pLength > pArrayB.length) {
            throw new ArrayIndexOutOfBoundsException(pArrayB.length);
        } else {
            int a = pOffsetA;
            int b = pOffsetB;
            while (a < lastIndexA) {
                if (pArrayA[a] != pArrayB[b]) {
                    return false;
                }
                a++;
                b++;
            }
            return true;
        }
    }

    public static final byte[] toByteArray(List<Byte> pItems) {
        byte[] out = new byte[pItems.size()];
        for (int i = out.length - 1; i >= 0; i--) {
            out[i] = ((Byte) pItems.get(i)).byteValue();
        }
        return out;
    }

    public static final char[] toCharArray(List<Character> pItems) {
        char[] out = new char[pItems.size()];
        for (int i = out.length - 1; i >= 0; i--) {
            out[i] = ((Character) pItems.get(i)).charValue();
        }
        return out;
    }

    public static final short[] toShortArray(List<Short> pItems) {
        short[] out = new short[pItems.size()];
        for (int i = out.length - 1; i >= 0; i--) {
            out[i] = ((Short) pItems.get(i)).shortValue();
        }
        return out;
    }

    public static final int[] toIntArray(List<Integer> pItems) {
        int[] out = new int[pItems.size()];
        for (int i = out.length - 1; i >= 0; i--) {
            out[i] = ((Integer) pItems.get(i)).intValue();
        }
        return out;
    }

    public static final long[] toLongArray(List<Long> pItems) {
        long[] out = new long[pItems.size()];
        for (int i = out.length - 1; i >= 0; i--) {
            out[i] = ((Long) pItems.get(i)).longValue();
        }
        return out;
    }

    public static final float[] toFloatArray(List<Float> pItems) {
        float[] out = new float[pItems.size()];
        for (int i = out.length - 1; i >= 0; i--) {
            out[i] = ((Float) pItems.get(i)).floatValue();
        }
        return out;
    }

    public static final double[] toDoubleArray(List<Double> pItems) {
        double[] out = new double[pItems.size()];
        for (int i = out.length - 1; i >= 0; i--) {
            out[i] = ((Double) pItems.get(i)).doubleValue();
        }
        return out;
    }

    public static final boolean contains(byte[] pItems, byte pItem) {
        for (int i = pItems.length - 1; i >= 0; i--) {
            if (pItems[i] == pItem) {
                return true;
            }
        }
        return false;
    }

    public static final boolean contains(char[] pItems, char pItem) {
        for (int i = pItems.length - 1; i >= 0; i--) {
            if (pItems[i] == pItem) {
                return true;
            }
        }
        return false;
    }

    public static final boolean contains(short[] pItems, short pItem) {
        for (int i = pItems.length - 1; i >= 0; i--) {
            if (pItems[i] == pItem) {
                return true;
            }
        }
        return false;
    }

    public static final boolean contains(int[] pItems, int pItem) {
        for (int i = pItems.length - 1; i >= 0; i--) {
            if (pItems[i] == pItem) {
                return true;
            }
        }
        return false;
    }

    public static final boolean contains(long[] pItems, long pItem) {
        for (int i = pItems.length - 1; i >= 0; i--) {
            if (pItems[i] == pItem) {
                return true;
            }
        }
        return false;
    }

    public static final boolean contains(float[] pItems, float pItem) {
        for (int i = pItems.length - 1; i >= 0; i--) {
            if (pItems[i] == pItem) {
                return true;
            }
        }
        return false;
    }

    public static final boolean contains(double[] pItems, double pItem) {
        for (int i = pItems.length - 1; i >= 0; i--) {
            if (pItems[i] == pItem) {
                return true;
            }
        }
        return false;
    }

    public static <T> T[] join(Class<T> pClass, T[]... pArrays) {
        if (pArrays == null) {
            return null;
        }
        if (arrayCount == 0) {
            return null;
        }
        if (arrayCount == 1) {
            return pArrays[0];
        }
        int resultLength = 0;
        for (int i = pArrays.length - 1; i >= 0; i--) {
            T[] array = pArrays[i];
            if (array != null && array.length > 0) {
                resultLength += array.length;
            }
        }
        if (resultLength == 0) {
            return null;
        }
        Object[] result = (Object[]) ((Object[]) Array.newInstance(pClass.getComponentType(), resultLength));
        int offset = 0;
        for (T[] array2 : pArrays) {
            if (array2 != null && array2.length > 0) {
                System.arraycopy(array2, 0, result, offset, array2.length);
                offset += array2.length;
            }
        }
        return result;
    }

    public static int idealByteArraySize(int pSize) {
        for (int i = 4; i < 32; i++) {
            if (pSize <= (1 << i) - 12) {
                return (1 << i) - 12;
            }
        }
        return pSize;
    }

    public static int idealBooleanArraySize(int pSize) {
        return idealByteArraySize(pSize);
    }

    public static int idealShortArraySize(int pSize) {
        return idealByteArraySize(pSize << 1) >> 1;
    }

    public static int idealCharArraySize(int pSize) {
        return idealByteArraySize(pSize << 1) >> 1;
    }

    public static int idealIntArraySize(int pSize) {
        return idealByteArraySize(pSize << 2) >> 2;
    }

    public static int idealFloatArraySize(int pSize) {
        return idealByteArraySize(pSize << 2) >> 2;
    }

    public static int idealDoubleArraySize(int pSize) {
        return idealByteArraySize(pSize << 3) >> 3;
    }

    public static int idealLongArraySize(int pSize) {
        return idealByteArraySize(pSize << 3) >> 3;
    }

    public static int idealObjectArraySize(int pSize) {
        return idealByteArraySize(pSize << 2) >> 2;
    }
}
