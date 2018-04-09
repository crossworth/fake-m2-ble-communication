package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.p021a.C0824e;
import com.tencent.stat.p021a.C1738d;
import com.zhuoyi.system.network.util.NetworkConstants;
import java.io.File;
import java.util.Iterator;

class C0847i implements Runnable {
    private Context f2927a = null;

    public C0847i(Context context) {
        this.f2927a = context;
    }

    public void run() {
        Iterator it = StatNativeCrashReport.m2642a(this.f2927a).iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            C0824e c1738d = new C1738d(this.f2927a, StatService.m2645a(this.f2927a, false), StatNativeCrashReport.m2641a(file), 3, NetworkConstants.DOWNLOAD_BUFFER_SIZE);
            c1738d.m4864a(StatNativeCrashReport.m2643b(file));
            if (StatService.m2653c(this.f2927a) != null) {
                StatService.m2653c(this.f2927a).post(new C0849k(c1738d));
            }
            file.delete();
            StatService.f2834i.m2678d("delete tombstone file:" + file.getAbsolutePath().toString());
        }
    }
}
