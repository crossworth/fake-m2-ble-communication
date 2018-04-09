package com.tencent.map.p026a.p027a;

import android.content.Context;
import com.tencent.map.p028b.C1217f;

public class C1190a {
    private static C1217f f3709a = C1217f.m3556a();
    private static C1190a f3710b;

    public static synchronized C1190a m3486a() {
        C1190a c1190a;
        synchronized (C1190a.class) {
            if (f3710b == null) {
                f3710b = new C1190a();
            }
            c1190a = f3710b;
        }
        return c1190a;
    }

    public boolean m3487a(Context context, C1191b c1191b) {
        return f3709a.m3602a(context, c1191b);
    }

    public boolean m3488a(String str, String str2) {
        return f3709a.m3603a(str, str2);
    }

    public void m3489b() {
        f3709a.m3604b();
    }
}
