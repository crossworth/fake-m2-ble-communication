package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.p039a.C1365e;
import com.tencent.stat.p039a.C1369d;
import java.io.File;
import java.util.Iterator;

class C1400i implements Runnable {
    private Context f4463a = null;

    public C1400i(Context context) {
        this.f4463a = context;
    }

    public void run() {
        Iterator it = StatNativeCrashReport.m4026a(this.f4463a).iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            C1365e c1369d = new C1369d(this.f4463a, StatService.m4029a(this.f4463a, false), StatNativeCrashReport.m4025a(file), 3, 10240);
            c1369d.m4055a(StatNativeCrashReport.m4027b(file));
            if (StatService.m4037c(this.f4463a) != null) {
                StatService.m4037c(this.f4463a).post(new C1402k(c1369d));
            }
            file.delete();
            StatService.f4336i.m4083d("delete tombstone file:" + file.getAbsolutePath().toString());
        }
    }
}
