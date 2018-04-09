package com.baidu.location.p001b.p002a;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.location.p001b.p003b.C0516a;
import com.baidu.location.p001b.p003b.C0517b;
import com.zhuoyou.plugin.bluetooth.data.BMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public final class C0514b {
    private static final boolean f2247a = false;
    private static final String f2248do = "DeviceId";
    private static final String f2249for = "30212102dicudiab";
    private static final String f2250if = "baidu/.cuid";
    private static final String f2251int = "com.baidu.deviceid";

    static final class C0513a {
        private static final String f2244do = "bd_setting_i";
        public final boolean f2245a;
        public final String f2246if;

        private C0513a(String str, boolean z) {
            this.f2246if = str;
            this.f2245a = z;
        }

        static C0513a m2170a(Context context) {
            String a;
            boolean z;
            Throwable e;
            boolean z2 = true;
            String str = "";
            try {
                CharSequence string = System.getString(context.getContentResolver(), f2244do);
                if (TextUtils.isEmpty(string)) {
                    a = C0513a.m2171a(context, "");
                } else {
                    CharSequence charSequence = string;
                }
                try {
                    System.putString(context.getContentResolver(), f2244do, a);
                    z = false;
                } catch (Exception e2) {
                    e = e2;
                    Log.e(C0514b.f2248do, "Settings.System.getString or putString failed", e);
                    if (TextUtils.isEmpty(a)) {
                        a = C0513a.m2171a(context, "");
                        z = true;
                    } else {
                        z = true;
                    }
                    if (z) {
                        z2 = false;
                    }
                    return new C0513a(a, z2);
                }
            } catch (Throwable e3) {
                Throwable th = e3;
                a = str;
                e = th;
                Log.e(C0514b.f2248do, "Settings.System.getString or putString failed", e);
                if (TextUtils.isEmpty(a)) {
                    a = C0513a.m2171a(context, "");
                    z = true;
                } else {
                    z = true;
                }
                if (z) {
                    z2 = false;
                }
                return new C0513a(a, z2);
            }
            if (z) {
                z2 = false;
            }
            return new C0513a(a, z2);
        }

        private static String m2171a(Context context, String str) {
            CharSequence deviceId;
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager != null) {
                    deviceId = telephonyManager.getDeviceId();
                    return TextUtils.isEmpty(deviceId) ? str : deviceId;
                }
            } catch (Throwable e) {
                Log.e(C0514b.f2248do, "Read IMEI failed", e);
            }
            deviceId = null;
            if (TextUtils.isEmpty(deviceId)) {
            }
        }
    }

    private C0514b() {
    }

    public static String m2172a(Context context) {
        C0514b.m2174a(context, "android.permission.WRITE_SETTINGS");
        C0514b.m2174a(context, "android.permission.READ_PHONE_STATE");
        C0514b.m2174a(context, "android.permission.WRITE_EXTERNAL_STORAGE");
        C0513a a = C0513a.m2170a(context);
        String str = a.f2246if;
        boolean z = !a.f2245a;
        String str2 = C0514b.m2177if(context);
        String str3 = "";
        if (z) {
            return C0515c.m2179a(("com.baidu" + str2).getBytes(), true);
        }
        String str4 = null;
        Object string = System.getString(context.getContentResolver(), f2251int);
        if (TextUtils.isEmpty(string)) {
            str4 = C0515c.m2179a(("com.baidu" + str + str2).getBytes(), true);
            string = System.getString(context.getContentResolver(), str4);
            if (!TextUtils.isEmpty(string)) {
                System.putString(context.getContentResolver(), f2251int, string);
                C0514b.m2175a(str, (String) string);
            }
        }
        if (TextUtils.isEmpty(string)) {
            string = C0514b.m2173a(str);
            if (!TextUtils.isEmpty(string)) {
                System.putString(context.getContentResolver(), str4, string);
                System.putString(context.getContentResolver(), f2251int, string);
            }
        }
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        str3 = C0515c.m2179a((str + str2 + UUID.randomUUID().toString()).getBytes(), true);
        System.putString(context.getContentResolver(), str4, str3);
        System.putString(context.getContentResolver(), f2251int, str3);
        C0514b.m2175a(str, str3);
        return str3;
    }

    private static String m2173a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String str2 = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(Environment.getExternalStorageDirectory(), f2250if)));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(readLine);
                stringBuilder.append(BMessage.CRLF);
            }
            bufferedReader.close();
            String[] split = new String(C0516a.m2180a(f2249for, f2249for, C0517b.m2183a(stringBuilder.toString().getBytes()))).split("=");
            return (split != null && split.length == 2 && str.equals(split[0])) ? split[1] : str2;
        } catch (FileNotFoundException e) {
            return str2;
        } catch (IOException e2) {
            return str2;
        } catch (Exception e3) {
            return str2;
        }
    }

    private static void m2174a(Context context, String str) {
        if ((context.checkCallingOrSelfPermission(str) == 0 ? 1 : null) == null) {
            throw new SecurityException("Permission Denial: requires permission " + str);
        }
    }

    private static void m2175a(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("=");
            stringBuilder.append(str2);
            File file = new File(Environment.getExternalStorageDirectory(), f2250if);
            try {
                new File(file.getParent()).mkdirs();
                FileWriter fileWriter = new FileWriter(file, false);
                fileWriter.write(C0517b.m2182a(C0516a.m2181if(f2249for, f2249for, stringBuilder.toString().getBytes()), "utf-8"));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
            } catch (Exception e2) {
            }
        }
    }

    public static String m2176do(Context context) {
        return C0513a.m2170a(context).f2246if;
    }

    public static String m2177if(Context context) {
        String str = "";
        Object string = Secure.getString(context.getContentResolver(), "android_id");
        return TextUtils.isEmpty(string) ? "" : string;
    }
}
