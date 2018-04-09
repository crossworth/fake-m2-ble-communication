package p031u.aly;

import com.umeng.C0915a;
import com.zhuoyou.plugin.bluetooth.data.Util;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/* compiled from: TBaseHelper */
public final class bq {
    private static final Comparator f3718a = new C1487a();

    /* compiled from: TBaseHelper */
    private static class C1487a implements Comparator {
        private C1487a() {
        }

        public int compare(Object obj, Object obj2) {
            if (obj == null && obj2 == null) {
                return 0;
            }
            if (obj == null) {
                return -1;
            }
            if (obj2 == null) {
                return 1;
            }
            if (obj instanceof List) {
                return bq.m3634a((List) obj, (List) obj2);
            }
            if (obj instanceof Set) {
                return bq.m3636a((Set) obj, (Set) obj2);
            }
            if (obj instanceof Map) {
                return bq.m3635a((Map) obj, (Map) obj2);
            }
            if (obj instanceof byte[]) {
                return bq.m3639a((byte[]) obj, (byte[]) obj2);
            }
            return bq.m3630a((Comparable) obj, (Comparable) obj2);
        }
    }

    private bq() {
    }

    public static int m3631a(Object obj, Object obj2) {
        if (obj instanceof Comparable) {
            return bq.m3630a((Comparable) obj, (Comparable) obj2);
        }
        if (obj instanceof List) {
            return bq.m3634a((List) obj, (List) obj2);
        }
        if (obj instanceof Set) {
            return bq.m3636a((Set) obj, (Set) obj2);
        }
        if (obj instanceof Map) {
            return bq.m3635a((Map) obj, (Map) obj2);
        }
        if (obj instanceof byte[]) {
            return bq.m3639a((byte[]) obj, (byte[]) obj2);
        }
        throw new IllegalArgumentException("Cannot compare objects of type " + obj.getClass());
    }

    public static int m3638a(boolean z, boolean z2) {
        return Boolean.valueOf(z).compareTo(Boolean.valueOf(z2));
    }

    public static int m3626a(byte b, byte b2) {
        if (b < b2) {
            return -1;
        }
        if (b2 < b) {
            return 1;
        }
        return 0;
    }

    public static int m3637a(short s, short s2) {
        if (s < s2) {
            return -1;
        }
        if (s2 < s) {
            return 1;
        }
        return 0;
    }

    public static int m3628a(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        if (i2 < i) {
            return 1;
        }
        return 0;
    }

    public static int m3629a(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        if (j2 < j) {
            return 1;
        }
        return 0;
    }

    public static int m3627a(double d, double d2) {
        if (d < d2) {
            return -1;
        }
        if (d2 < d) {
            return 1;
        }
        return 0;
    }

    public static int m3632a(String str, String str2) {
        return str.compareTo(str2);
    }

    public static int m3639a(byte[] bArr, byte[] bArr2) {
        int a = bq.m3628a(bArr.length, bArr2.length);
        if (a != 0) {
            return a;
        }
        for (a = 0; a < bArr.length; a++) {
            int a2 = bq.m3626a(bArr[a], bArr2[a]);
            if (a2 != 0) {
                return a2;
            }
        }
        return 0;
    }

    public static int m3630a(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }

    public static int m3634a(List list, List list2) {
        int a = bq.m3628a(list.size(), list2.size());
        if (a != 0) {
            return a;
        }
        for (a = 0; a < list.size(); a++) {
            int compare = f3718a.compare(list.get(a), list2.get(a));
            if (compare != 0) {
                return compare;
            }
        }
        return 0;
    }

    public static int m3636a(Set set, Set set2) {
        int a = bq.m3628a(set.size(), set2.size());
        if (a != 0) {
            return a;
        }
        SortedSet treeSet = new TreeSet(f3718a);
        treeSet.addAll(set);
        SortedSet treeSet2 = new TreeSet(f3718a);
        treeSet2.addAll(set2);
        Iterator it = treeSet.iterator();
        Iterator it2 = treeSet2.iterator();
        while (it.hasNext() && it2.hasNext()) {
            a = f3718a.compare(it.next(), it2.next());
            if (a != 0) {
                return a;
            }
        }
        return 0;
    }

    public static int m3635a(Map map, Map map2) {
        int a = bq.m3628a(map.size(), map2.size());
        if (a != 0) {
            return a;
        }
        SortedMap treeMap = new TreeMap(f3718a);
        treeMap.putAll(map);
        Iterator it = treeMap.entrySet().iterator();
        treeMap = new TreeMap(f3718a);
        treeMap.putAll(map2);
        Iterator it2 = treeMap.entrySet().iterator();
        while (it.hasNext() && it2.hasNext()) {
            Entry entry = (Entry) it.next();
            Entry entry2 = (Entry) it2.next();
            int compare = f3718a.compare(entry.getKey(), entry2.getKey());
            if (compare != 0) {
                return compare;
            }
            a = f3718a.compare(entry.getValue(), entry2.getValue());
            if (a != 0) {
                return a;
            }
        }
        return 0;
    }

    public static void m3641a(ByteBuffer byteBuffer, StringBuilder stringBuilder) {
        byte[] array = byteBuffer.array();
        int arrayOffset = byteBuffer.arrayOffset();
        int position = arrayOffset + byteBuffer.position();
        int limit = byteBuffer.limit() + arrayOffset;
        if (limit - position > 128) {
            arrayOffset = position + 128;
        } else {
            arrayOffset = limit;
        }
        for (int i = position; i < arrayOffset; i++) {
            if (i > position) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(bq.m3640a(array[i]));
        }
        if (limit != arrayOffset) {
            stringBuilder.append(Util.TEXT_POSTFIX);
        }
    }

    public static String m3640a(byte b) {
        return Integer.toHexString((b | 256) & C0915a.f3083e).toUpperCase().substring(1);
    }

    public static byte[] m3642a(ByteBuffer byteBuffer) {
        if (bq.m3644b(byteBuffer)) {
            return byteBuffer.array();
        }
        byte[] bArr = new byte[byteBuffer.remaining()];
        bq.m3633a(byteBuffer, bArr, 0);
        return bArr;
    }

    public static boolean m3644b(ByteBuffer byteBuffer) {
        return byteBuffer.hasArray() && byteBuffer.position() == 0 && byteBuffer.arrayOffset() == 0 && byteBuffer.remaining() == byteBuffer.capacity();
    }

    public static int m3633a(ByteBuffer byteBuffer, byte[] bArr, int i) {
        int remaining = byteBuffer.remaining();
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), bArr, i, remaining);
        return remaining;
    }

    public static ByteBuffer m3645c(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        return !bq.m3644b(byteBuffer) ? ByteBuffer.wrap(bq.m3642a(byteBuffer)) : byteBuffer;
    }

    public static ByteBuffer m3646d(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(new byte[byteBuffer.remaining()]);
        if (byteBuffer.hasArray()) {
            System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset() + byteBuffer.position(), wrap.array(), 0, byteBuffer.remaining());
            return wrap;
        }
        byteBuffer.slice().get(wrap.array());
        return wrap;
    }

    public static byte[] m3643a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        return bArr2;
    }
}
