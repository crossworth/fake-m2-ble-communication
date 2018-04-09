package p031u.aly;

import android.text.TextUtils;
import java.util.List;

/* compiled from: UMCCAggregatedRestrictionManager */
public class C1516n {

    /* compiled from: UMCCAggregatedRestrictionManager */
    private static class C1515a {
        private static final C1516n f3865a = new C1516n();

        private C1515a() {
        }
    }

    private C1516n() {
    }

    public static C1516n m3847a() {
        return C1515a.f3865a;
    }

    public boolean m3849a(String str) {
        if ("".equals(str)) {
            return true;
        }
        if (str == null) {
            return false;
        }
        if (str.getBytes().length >= C1507j.f3829b || !m3848a(str, 48)) {
            return false;
        }
        return true;
    }

    public boolean m3852b(String str) {
        if (!TextUtils.isEmpty(str) && str.length() < 16 && m3848a(str, 48)) {
            return true;
        }
        return false;
    }

    public boolean m3850a(List<String> list) {
        if (list == null) {
            return false;
        }
        if (list.size() <= 1) {
            return true;
        }
        for (int i = 1; i < list.size(); i++) {
            if (TextUtils.isEmpty((CharSequence) list.get(i))) {
                return false;
            }
            if (!m3848a((String) list.get(i), 48)) {
                return false;
            }
        }
        return true;
    }

    private boolean m3848a(String str, int i) {
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) < i) {
                return false;
            }
        }
        return true;
    }

    public int m3851b() {
        return 8;
    }

    public int m3854c() {
        return 128;
    }

    public int m3855d() {
        return 512;
    }

    public boolean m3853b(List<String> list) {
        if (list == null || list.size() <= 0) {
            return false;
        }
        int i = 0;
        for (String bytes : list) {
            i = bytes.getBytes().length + i;
        }
        if (i < 256) {
            return true;
        }
        return false;
    }
}
