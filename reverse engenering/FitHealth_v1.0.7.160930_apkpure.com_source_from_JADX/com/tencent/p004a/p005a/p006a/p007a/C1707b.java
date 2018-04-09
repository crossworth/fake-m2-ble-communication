package com.tencent.p004a.p005a.p006a.p007a;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

final class C1707b extends C0667f {
    C1707b(Context context) {
        super(context);
    }

    protected final boolean mo2077a() {
        return C0669h.m2235a(this.e, "android.permission.WRITE_EXTERNAL_STORAGE") && Environment.getExternalStorageState().equals("mounted");
    }

    protected final String mo2078b() {
        String str;
        synchronized (this) {
            Log.i("MID", "read mid from InternalStorage");
            try {
                for (String str2 : C0665a.m2220a(new File(Environment.getExternalStorageDirectory(), C0669h.m2240f("6X8Y4XdM2Vhvn0KfzcEatGnWaNU=")))) {
                    String[] split = str2.split(SeparatorConstants.SEPARATOR_ADS_ID);
                    if (split.length == 2 && split[0].equals(C0669h.m2240f("4kU71lN96TJUomD1vOU9lgj9Tw=="))) {
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

    protected final void mo2079b(String str) {
        synchronized (this) {
            Log.i("MID", "write mid to InternalStorage");
            C0665a.m2219a(Environment.getExternalStorageDirectory() + "/" + C0669h.m2240f("6X8Y4XdM2Vhvn0I="));
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(Environment.getExternalStorageDirectory(), C0669h.m2240f("6X8Y4XdM2Vhvn0KfzcEatGnWaNU="))));
                bufferedWriter.write(C0669h.m2240f("4kU71lN96TJUomD1vOU9lgj9Tw==") + SeparatorConstants.SEPARATOR_ADS_ID + str);
                bufferedWriter.write("\n");
                bufferedWriter.close();
            } catch (Throwable e) {
                Log.w("MID", e);
            }
        }
    }
}
