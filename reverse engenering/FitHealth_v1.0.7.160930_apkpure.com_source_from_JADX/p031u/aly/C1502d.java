package p031u.aly;

import android.content.Context;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: UMCCDBUtils */
public class C1502d {
    public static final String f3811a = "/data/data/";
    public static final String f3812b = "/databases/cc/";
    public static final String f3813c = "cc.db";
    public static final int f3814d = 1;
    public static final String f3815e = "Id";
    public static final String f3816f = "INTEGER";

    /* compiled from: UMCCDBUtils */
    public static class C1495a {
        public static final String f3797a = "aggregated";
        public static final String f3798b = "aggregated_cache";

        /* compiled from: UMCCDBUtils */
        public static class C1493a {
            public static final String f3785a = "key";
            public static final String f3786b = "totalTimestamp";
            public static final String f3787c = "value";
            public static final String f3788d = "count";
            public static final String f3789e = "label";
            public static final String f3790f = "timeWindowNum";
        }

        /* compiled from: UMCCDBUtils */
        public static class C1494b {
            public static final String f3791a = "TEXT";
            public static final String f3792b = "TEXT";
            public static final String f3793c = "INTEGER";
            public static final String f3794d = "INTEGER";
            public static final String f3795e = "TEXT";
            public static final String f3796f = "TEXT";
        }
    }

    /* compiled from: UMCCDBUtils */
    public static class C1498b {
        public static final String f3801a = "limitedck";

        /* compiled from: UMCCDBUtils */
        public static class C1496a {
            public static final String f3799a = "ck";
        }

        /* compiled from: UMCCDBUtils */
        public static class C1497b {
            public static final String f3800a = "TEXT";
        }
    }

    /* compiled from: UMCCDBUtils */
    public static class C1501c {
        public static final String f3810a = "system";

        /* compiled from: UMCCDBUtils */
        public static class C1499a {
            public static final String f3802a = "key";
            public static final String f3803b = "timeStamp";
            public static final String f3804c = "count";
            public static final String f3805d = "label";
        }

        /* compiled from: UMCCDBUtils */
        public static class C1500b {
            public static final String f3806a = "TEXT";
            public static final String f3807b = "INTEGER";
            public static final String f3808c = "INTEGER";
            public static final String f3809d = "TEXT";
        }
    }

    public static String m3740a(Context context) {
        return f3811a + context.getPackageName() + f3812b;
    }

    public static String m3741a(List<String> list) {
        return TextUtils.join("!", list);
    }

    public static List<String> m3742a(String str) {
        return new ArrayList(Arrays.asList(str.split("!")));
    }

    public static List<String> m3743b(List<String> list) {
        List<String> arrayList = new ArrayList();
        try {
            for (String str : list) {
                if (Collections.frequency(arrayList, str) < 1) {
                    arrayList.add(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
