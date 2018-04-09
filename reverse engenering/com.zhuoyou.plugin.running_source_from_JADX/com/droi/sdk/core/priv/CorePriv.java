package com.droi.sdk.core.priv;

import android.content.Context;
import java.util.UUID;

public class CorePriv {
    public static final String f2827a = "UNKNOWN_CHANNEL";
    public static String f2828b = "";
    public static String f2829c = "";
    public static String f2830d = null;
    public static String f2831e = null;
    private static String f2832f = null;
    private static Context f2833g = null;

    public static Context getContext() {
        return f2833g;
    }

    public static String getDeviceId() {
        PersistSettings instance = PersistSettings.instance(PersistSettings.GLOBAL_CONFIG);
        PersistSettings instance2 = PersistSettings.instance(PersistSettings.CONFIG);
        long j = instance.getLong(PersistSettings.KEY_UID_U, 0);
        long j2 = instance.getLong(PersistSettings.KEY_UID_L, 0);
        if (j == 0 && j2 == 0) {
            j = instance2.getLong(PersistSettings.KEY_UID_U, 0);
            j2 = instance2.getLong(PersistSettings.KEY_UID_L, 0);
        }
        return (j == 0 && j2 == 0) ? null : new UUID(j, j2).toString();
    }

    public static synchronized String getInstallationId() {
        String str;
        synchronized (CorePriv.class) {
            if (f2832f != null) {
                str = f2832f;
            } else {
                PersistSettings instance = PersistSettings.instance(PersistSettings.CONFIG);
                f2832f = instance.getString(PersistSettings.KEY_INSTALLATION_ID, null);
                if (f2832f == null) {
                    f2832f = UUID.randomUUID().toString();
                    instance.setString(PersistSettings.KEY_INSTALLATION_ID, f2832f);
                }
                str = f2832f;
            }
        }
        return str;
    }

    public static void setServiceContext(Context context) {
        f2833g = context;
    }
}
