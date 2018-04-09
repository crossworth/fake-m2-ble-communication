package p031u.aly;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/* compiled from: UMCCTimeRange */
public class C1521q {
    public static final int f3870a = 1;
    private static final int f3871b = 1000;
    private static final int f3872c = 1001;
    private static final int f3873d = 1002;

    public static String m3878a(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return String.valueOf(((long) ((((instance.get(12) / 6) + 1) + (instance.get(11) * 10)) - 1)) + (C1521q.m3882d(j) * 240));
    }

    private static long m3882d(long j) {
        long j2 = 0;
        try {
            long time = new SimpleDateFormat("yyyy").parse("1970").getTime();
            long j3 = (j - time) / 86400000;
            if ((j - time) % 86400000 > 0) {
                j2 = 1;
            }
            return j2 + j3;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long m3880b(long j) {
        return C1521q.m3877a(j, 1001);
    }

    public static long m3881c(long j) {
        return C1521q.m3877a(j, 1002);
    }

    private static long m3877a(long j, int i) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        int i2 = ((instance.get(12) / 6) + 1) + (instance.get(11) * 10);
        int i3 = instance.get(13);
        int i4 = 0;
        if (i == 1002) {
            i4 = 360 - (((instance.get(12) % 6) * 60) + i3);
        } else if (i == 1001) {
            i4 = 60 - (i3 % 60);
            if (i2 % 6 == 0) {
                i4 += 60;
            }
        }
        return (long) (i4 * 1000);
    }

    public static boolean m3879a(long j, long j2) {
        if (C1521q.m3883e(j) == C1521q.m3883e(j2)) {
            return true;
        }
        return false;
    }

    private static int m3883e(long j) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(j);
        return instance.get(5);
    }
}
