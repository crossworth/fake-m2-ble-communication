package com.droi.sdk.core.priv;

import android.content.Context;
import android.os.Environment;
import com.droi.sdk.internal.DroiLog;
import java.io.File;

public class DroiStorageFinder {
    private static final String f2834a = "DroiSDK";

    public static String getPrivatePath() {
        return getPrivatePath(CorePriv.getContext());
    }

    public static String getPrivatePath(Context context) {
        if (context == null) {
            DroiLog.m2870e(f2834a, "The context is null");
            return null;
        }
        File file = new File(context.getApplicationInfo().dataDir + "/dcsdk");
        return (file.mkdirs() || file.isDirectory()) ? file.getAbsolutePath() : null;
    }

    public static String getSharedPath() {
        return getSharedPath(CorePriv.getContext());
    }

    public static String getSharedPath(Context context) {
        if (context == null) {
            DroiLog.m2870e(f2834a, "The context is null");
            return null;
        } else if (!C0944p.m2802e(context)) {
            return null;
        } else {
            File file;
            try {
                file = new File(Environment.getExternalStorageDirectory() + "/droi");
                if (file.exists()) {
                    File file2 = new File(Environment.getExternalStorageDirectory() + "/dcsdk");
                    if (!file2.exists()) {
                        file2.mkdir();
                        File file3 = new File(file, "data.dat");
                        if (file3.exists()) {
                            FileDescriptorHelper.copy(file3, new File(file2, "data.dat"), null);
                        }
                        file3 = new File(file, "dev_data.json");
                        if (file3.exists()) {
                            FileDescriptorHelper.copy(file3, new File(file2, "dev_data.json"), null);
                        }
                    }
                }
            } catch (Exception e) {
            }
            file = new File(Environment.getExternalStorageDirectory() + "/dcsdk");
            return (file.mkdirs() || file.isDirectory()) ? file.getAbsolutePath() : getPrivatePath();
        }
    }
}
