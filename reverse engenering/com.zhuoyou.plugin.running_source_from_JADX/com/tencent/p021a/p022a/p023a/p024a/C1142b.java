package com.tencent.p021a.p022a.p023a.p024a;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.zhuoyou.plugin.running.app.Permissions;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

final class C1142b extends C1141f {
    C1142b(Context context) {
        super(context);
    }

    protected final void mo2138a(String str) {
        synchronized (this) {
            Log.i("MID", "write mid to InternalStorage");
            C1140a.m3313d(Environment.getExternalStorageDirectory() + "/" + C1147h.m3342f("6X8Y4XdM2Vhvn0I="));
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(Environment.getExternalStorageDirectory(), C1147h.m3342f("6X8Y4XdM2Vhvn0KfzcEatGnWaNU="))));
                bufferedWriter.write(C1147h.m3342f("4kU71lN96TJUomD1vOU9lgj9Tw==") + "," + str);
                bufferedWriter.write("\n");
                bufferedWriter.close();
            } catch (Throwable e) {
                Log.w("MID", e);
            }
        }
    }

    protected final boolean mo2139a() {
        return C1147h.m3338a(this.a, Permissions.PERMISSION_WRITE_STORAGE) && Environment.getExternalStorageState().equals("mounted");
    }

    protected final String mo2140b() {
        String str;
        synchronized (this) {
            Log.i("MID", "read mid from InternalStorage");
            try {
                for (String str2 : C1140a.m3312a(new File(Environment.getExternalStorageDirectory(), C1147h.m3342f("6X8Y4XdM2Vhvn0KfzcEatGnWaNU=")))) {
                    String[] split = str2.split(",");
                    if (split.length == 2 && split[0].equals(C1147h.m3342f("4kU71lN96TJUomD1vOU9lgj9Tw=="))) {
                        Log.i("MID", "read mid from InternalStorage:" + split[1]);
                        str2 = split[1];
                        break;
                    }
                }
                str2 = null;
            } catch (Throwable e) {
                Log.w("MID", e);
                str2 = null;
            }
        }
        return str2;
    }
}
