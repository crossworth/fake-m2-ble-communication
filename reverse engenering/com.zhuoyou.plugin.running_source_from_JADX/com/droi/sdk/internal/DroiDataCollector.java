package com.droi.sdk.internal;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import com.android.internal.telephony.IPhoneSubInfo;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import dalvik.system.DexClassLoader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DroiDataCollector {
    @SuppressLint({"NewApi"})
    public static String MTKgetSimScById(Context context, int i) {
        File file = new File("/system/framework/mediatek-framework.jar");
        if (!file.exists()) {
            file = new File("/system/framework/com.mediatek.framework.jar");
        }
        if (file.exists()) {
            try {
                Class loadClass = new DexClassLoader(file.toString(), context.getFilesDir().getAbsolutePath(), null, Thread.currentThread().getContextClassLoader()).loadClass("com.mediatek.telephony.TelephonyManagerEx");
                Class[] clsArr = new Class[]{Integer.TYPE};
                Object[] objArr = new Object[]{Integer.valueOf(i)};
                String str = (String) m2852a("getScAddress", clsArr, objArr, loadClass, loadClass.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context}));
                if (str != null) {
                    return str;
                }
            } catch (Exception e) {
            }
        }
        try {
            loadClass = Class.forName("com.mediatek.telephony.TelephonyManagerEx");
            return (String) m2852a("getScAddress", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(i)}, loadClass, loadClass.getConstructor(new Class[]{Context.class}).newInstance(new Object[]{context}));
        } catch (Exception e2) {
            return null;
        }
    }

    private static int m2851a(Context context) {
        int b = m2854b(context);
        if (b > 0) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                Integer num;
                Integer num2;
                if (b > 1) {
                    Method declaredMethod;
                    int intValue;
                    try {
                        Method declaredMethod2 = telephonyManager.getClass().getDeclaredMethod("getDefaultDataPhoneId", new Class[]{Context.class});
                        if (declaredMethod2 != null) {
                            declaredMethod2.setAccessible(true);
                            num = (Integer) declaredMethod2.invoke(telephonyManager, new Object[]{context});
                            if (num != null && num.intValue() >= 0) {
                                b = num.intValue();
                                if (b != -1) {
                                    return b;
                                }
                                try {
                                    declaredMethod = telephonyManager.getClass().getDeclaredMethod("getSmsDefaultSim", new Class[0]);
                                    if (declaredMethod != null) {
                                        declaredMethod.setAccessible(true);
                                        num2 = (Integer) declaredMethod.invoke(telephonyManager, new Object[0]);
                                        if (num2 != null && num2.intValue() >= 0) {
                                            intValue = num2.intValue();
                                            b = intValue;
                                            if (b != -1) {
                                                return b;
                                            }
                                        }
                                    }
                                    intValue = b;
                                    b = intValue;
                                } catch (Exception e) {
                                }
                                if (b != -1) {
                                    return b;
                                }
                            }
                        }
                        b = -1;
                    } catch (Exception e2) {
                        b = -1;
                    }
                    if (b != -1) {
                        return b;
                    }
                    declaredMethod = telephonyManager.getClass().getDeclaredMethod("getSmsDefaultSim", new Class[0]);
                    if (declaredMethod != null) {
                        declaredMethod.setAccessible(true);
                        num2 = (Integer) declaredMethod.invoke(telephonyManager, new Object[0]);
                        intValue = num2.intValue();
                        b = intValue;
                        if (b != -1) {
                            return b;
                        }
                    }
                    intValue = b;
                    b = intValue;
                    if (b != -1) {
                        return b;
                    }
                } else if (b == 1) {
                    Method declaredMethod3;
                    try {
                        declaredMethod3 = telephonyManager.getClass().getDeclaredMethod("getDefault", new Class[]{Integer.TYPE});
                        if (declaredMethod3 != null) {
                            declaredMethod3.setAccessible(true);
                            TelephonyManager telephonyManager2 = (TelephonyManager) declaredMethod3.invoke(telephonyManager.getClass(), new Object[]{Integer.valueOf(0)});
                            if (telephonyManager2 != null && telephonyManager2.getSimState() == 5) {
                                return 0;
                            }
                            telephonyManager2 = (TelephonyManager) declaredMethod3.invoke(telephonyManager.getClass(), new Object[]{Integer.valueOf(1)});
                            if (telephonyManager2 != null && telephonyManager2.getSimState() == 5) {
                                return 1;
                            }
                        }
                    } catch (Exception e3) {
                    }
                    try {
                        declaredMethod3 = telephonyManager.getClass().getDeclaredMethod("getSimStateGemini", new Class[]{Integer.TYPE});
                        if (declaredMethod3 != null) {
                            declaredMethod3.setAccessible(true);
                            num = (Integer) declaredMethod3.invoke(telephonyManager, new Object[]{Integer.valueOf(0)});
                            if (num != null && num.intValue() == 5) {
                                return 0;
                            }
                            num2 = (Integer) declaredMethod3.invoke(telephonyManager, new Object[]{Integer.valueOf(1)});
                            if (num2 != null && num2.intValue() == 5) {
                                return 1;
                            }
                        }
                    } catch (Exception e4) {
                    }
                }
            }
        }
        return 0;
    }

    private static Object m2852a(String str, Class<?>[] clsArr, Object[] objArr, Class<?> cls, Object obj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (cls == null && obj == null) {
            return null;
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        Object obj2 = new Object();
        return obj == null ? declaredMethod.invoke(cls, objArr) : declaredMethod.invoke(obj, objArr);
    }

    private static boolean m2853a() {
        String str = Build.TAGS;
        return str != null && str.contains("test-keys");
    }

    private static int m2854b(Context context) {
        int i;
        Integer num;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null) {
                return 0;
            }
            Method declaredMethod;
            int i2;
            try {
                declaredMethod = telephonyManager.getClass().getDeclaredMethod("getDefault", new Class[]{Integer.TYPE});
                if (declaredMethod != null) {
                    declaredMethod.setAccessible(true);
                    TelephonyManager telephonyManager2 = (TelephonyManager) declaredMethod.invoke(telephonyManager.getClass(), new Object[]{Integer.valueOf(0)});
                    i = (telephonyManager2 == null || telephonyManager2.getSimState() != 5) ? 0 : 1;
                    try {
                        telephonyManager2 = (TelephonyManager) declaredMethod.invoke(telephonyManager.getClass(), new Object[]{Integer.valueOf(1)});
                        i2 = (telephonyManager2 == null || telephonyManager2.getSimState() != 5) ? i : i + 1;
                    } catch (Exception e) {
                        i2 = i;
                        if (i2 < 1) {
                            return i2;
                        }
                        try {
                            declaredMethod = telephonyManager.getClass().getDeclaredMethod("getSimStateGemini", new Class[]{Integer.TYPE});
                            if (declaredMethod != null) {
                                i2 = 0;
                            } else {
                                declaredMethod.setAccessible(true);
                                num = (Integer) declaredMethod.invoke(telephonyManager, new Object[]{Integer.valueOf(0)});
                                if (num == null) {
                                }
                                try {
                                    num = (Integer) declaredMethod.invoke(telephonyManager, new Object[]{Integer.valueOf(1)});
                                    if (num == null) {
                                    }
                                } catch (Exception e2) {
                                    i2 = i;
                                    return i2 >= 1 ? telephonyManager.getSimState() == 5 ? 0 : 1 : i2;
                                }
                            }
                        } catch (Exception e3) {
                            i = 0;
                            i2 = i;
                            if (i2 >= 1) {
                                if (telephonyManager.getSimState() == 5) {
                                }
                            }
                        }
                        if (i2 >= 1) {
                        }
                    }
                }
                i2 = 0;
            } catch (Exception e4) {
                i = 0;
                i2 = i;
                if (i2 < 1) {
                    return i2;
                }
                declaredMethod = telephonyManager.getClass().getDeclaredMethod("getSimStateGemini", new Class[]{Integer.TYPE});
                if (declaredMethod != null) {
                    declaredMethod.setAccessible(true);
                    num = (Integer) declaredMethod.invoke(telephonyManager, new Object[]{Integer.valueOf(0)});
                    if (num == null) {
                    }
                    num = (Integer) declaredMethod.invoke(telephonyManager, new Object[]{Integer.valueOf(1)});
                    if (num == null) {
                    }
                } else {
                    i2 = 0;
                }
                if (i2 >= 1) {
                }
            }
            if (i2 < 1) {
                return i2;
            }
            declaredMethod = telephonyManager.getClass().getDeclaredMethod("getSimStateGemini", new Class[]{Integer.TYPE});
            if (declaredMethod != null) {
                declaredMethod.setAccessible(true);
                num = (Integer) declaredMethod.invoke(telephonyManager, new Object[]{Integer.valueOf(0)});
                i = (num == null && num.intValue() == 5) ? 1 : 0;
                num = (Integer) declaredMethod.invoke(telephonyManager, new Object[]{Integer.valueOf(1)});
                i2 = (num == null && num.intValue() == 5) ? i + 1 : i;
            } else {
                i2 = 0;
            }
            if (i2 >= 1) {
            }
        } catch (Exception e5) {
            return 0;
        }
    }

    private static boolean m2855b() {
        for (String file : new String[]{"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"}) {
            if (new File(file).exists()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean m2856c() {
        /*
        r0 = 1;
        r1 = 0;
        r2 = 0;
        r3 = java.lang.Runtime.getRuntime();	 Catch:{ Throwable -> 0x0039, all -> 0x0042 }
        r4 = 2;
        r4 = new java.lang.String[r4];	 Catch:{ Throwable -> 0x0039, all -> 0x0042 }
        r5 = 0;
        r6 = "/system/xbin/which";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x0039, all -> 0x0042 }
        r5 = 1;
        r6 = "su";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x0039, all -> 0x0042 }
        r2 = r3.exec(r4);	 Catch:{ Throwable -> 0x0039, all -> 0x0042 }
        r3 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x0049, all -> 0x0042 }
        r4 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x0049, all -> 0x0042 }
        r5 = r2.getInputStream();	 Catch:{ Throwable -> 0x0049, all -> 0x0042 }
        r4.<init>(r5);	 Catch:{ Throwable -> 0x0049, all -> 0x0042 }
        r3.<init>(r4);	 Catch:{ Throwable -> 0x0049, all -> 0x0042 }
        r3 = r3.readLine();	 Catch:{ Throwable -> 0x0049, all -> 0x0042 }
        if (r3 == 0) goto L_0x0032;
    L_0x002c:
        if (r2 == 0) goto L_0x0031;
    L_0x002e:
        r2.destroy();
    L_0x0031:
        return r0;
    L_0x0032:
        if (r2 == 0) goto L_0x0037;
    L_0x0034:
        r2.destroy();
    L_0x0037:
        r0 = r1;
        goto L_0x0031;
    L_0x0039:
        r0 = move-exception;
        r0 = r2;
    L_0x003b:
        if (r0 == 0) goto L_0x0040;
    L_0x003d:
        r0.destroy();
    L_0x0040:
        r0 = r1;
        goto L_0x0031;
    L_0x0042:
        r0 = move-exception;
        if (r2 == 0) goto L_0x0048;
    L_0x0045:
        r2.destroy();
    L_0x0048:
        throw r0;
    L_0x0049:
        r0 = move-exception;
        r0 = r2;
        goto L_0x003b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.internal.DroiDataCollector.c():boolean");
    }

    public static String getAndroidID(Context context) {
        String string = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        return string == null ? "" : string;
    }

    public static long getAvailRamSize(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem >> 10;
    }

    public static String getAvailRomSize() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return Long.toString(((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) >> 10) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "M";
    }

    public static String getBaseStationInfo(Context context) {
        String mccmnc = getMCCMNC(context);
        if ("".equals(mccmnc) || mccmnc.length() <= 3) {
            return "";
        }
        mccmnc = mccmnc.substring(0, 3) + ":" + mccmnc.substring(3) + ":" + getLAC(context) + ":" + getCID(context);
        return "::".equals(mccmnc) ? "" : mccmnc;
    }

    public static String getBuildBaseband() {
        String str = Build.RADIO;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildBoard() {
        String str = Build.BOARD;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildBootloader() {
        String str = Build.BOOTLOADER;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildBrand() {
        String str = Build.BRAND;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildDevice() {
        String str = Build.DEVICE;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildDisplay() {
        String str = Build.DISPLAY;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildFingerprint() {
        String str = Build.FINGERPRINT;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildHardware() {
        String str = Build.HARDWARE;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildId() {
        String str = Build.ID;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildManufacturer() {
        String str = Build.MANUFACTURER;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildModel() {
        String str = Build.MODEL;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildProduct() {
        String str = Build.PRODUCT;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildTags() {
        String str = Build.TAGS;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildType() {
        String str = Build.TYPE;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getBuildVersionRelease() {
        String str = VERSION.RELEASE;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static String getCID(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (!(telephonyManager == null || TextUtils.isEmpty(telephonyManager.getSimOperator()))) {
                CellLocation cellLocation = telephonyManager.getCellLocation();
                if (cellLocation instanceof CdmaCellLocation) {
                    CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
                    if (cdmaCellLocation != null) {
                        return Integer.toString(cdmaCellLocation.getBaseStationId());
                    }
                } else if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
                    if (gsmCellLocation != null) {
                        return Integer.toString(gsmCellLocation.getCid());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurNetworkType(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        String str = "";
        if (activeNetworkInfo == null) {
            Log.v("", "cann't get network type");
            return str;
        }
        str = activeNetworkInfo.getTypeName();
        return str.equalsIgnoreCase("mobile") ? activeNetworkInfo.getExtraInfo() : str;
    }

    public static long getCurRunTime() {
        return SystemClock.elapsedRealtime();
    }

    public static String getCustomVersion() {
        String str;
        Exception e;
        String str2 = "android.os.SystemProperties";
        String[] strArr = new String[]{"ro.build.version.freemeos", "ro.huaqin.build.version", "ro.custom.build.version"};
        String str3 = "unknown";
        String str4 = null;
        int i = 0;
        while (i < strArr.length) {
            try {
                Class cls = Class.forName(str2);
                str = (String) m2852a("get", new Class[]{String.class, String.class}, new Object[]{strArr[i], str3}, cls, cls.newInstance());
                if (str != null) {
                    try {
                        if (!str.equals(str3)) {
                            break;
                        }
                    } catch (Exception e2) {
                        e = e2;
                        e.printStackTrace();
                        i++;
                        str4 = str;
                    }
                } else {
                    continue;
                }
            } catch (Exception e3) {
                Exception exception = e3;
                str = str4;
                e = exception;
                e.printStackTrace();
                i++;
                str4 = str;
            }
            i++;
            str4 = str;
        }
        str = str4;
        return (str == null || str.equals(str3)) ? "" : str;
    }

    public static String getIccid(Context context) {
        return getIccid(context, 0);
    }

    public static String getIccid(Context context, int i) {
        String simSerialNumber;
        String str = null;
        if (i == 0) {
            try {
                simSerialNumber = ((TelephonyManager) context.getSystemService("phone")).getSimSerialNumber();
            } catch (Exception e) {
                simSerialNumber = str;
            }
        } else {
            try {
                str = ((TelephonyManager) TelephonyManager.class.getDeclaredConstructor(new Class[]{Context.class, Integer.TYPE}).newInstance(new Object[]{context, Integer.valueOf(1)})).getSimSerialNumber();
            } catch (Exception e2) {
            }
            if (str == null) {
                try {
                    simSerialNumber = ((IPhoneSubInfo) m2852a("getSubscriberInfo", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(1)}, TelephonyManager.class, (TelephonyManager) TelephonyManager.class.getDeclaredConstructor(new Class[]{Context.class}).newInstance(new Object[]{context}))).getIccSerialNumber();
                } catch (Exception e3) {
                    simSerialNumber = str;
                }
            } else {
                simSerialNumber = str;
            }
        }
        return simSerialNumber == null ? "" : simSerialNumber;
    }

    public static String getImei(Context context) {
        return getImei(context, 0);
    }

    public static String getImei(Context context, int i) {
        String deviceId;
        String str = null;
        if (i == 0) {
            try {
                deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            } catch (Exception e) {
                deviceId = str;
            }
        } else {
            try {
                str = ((TelephonyManager) TelephonyManager.class.getDeclaredConstructor(new Class[]{Context.class, Integer.TYPE}).newInstance(new Object[]{context, Integer.valueOf(1)})).getDeviceId();
            } catch (Exception e2) {
            }
            if (str == null) {
                try {
                    deviceId = ((IPhoneSubInfo) m2852a("getSubscriberInfo", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(1)}, TelephonyManager.class, (TelephonyManager) TelephonyManager.class.getDeclaredConstructor(new Class[]{Context.class}).newInstance(new Object[]{context}))).getDeviceId();
                } catch (Exception e3) {
                    deviceId = str;
                }
            } else {
                deviceId = str;
            }
        }
        return deviceId == null ? "" : deviceId;
    }

    public static String getImsi(Context context) {
        String str = null;
        try {
            int a = m2851a(context);
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                Method declaredMethod;
                String subscriberId;
                try {
                    declaredMethod = telephonyManager.getClass().getDeclaredMethod("getDefault", new Class[]{Integer.TYPE});
                    if (declaredMethod != null) {
                        declaredMethod.setAccessible(true);
                        TelephonyManager telephonyManager2 = (TelephonyManager) declaredMethod.invoke(telephonyManager.getClass(), new Object[]{Integer.valueOf(a)});
                        if (telephonyManager2 != null) {
                            subscriberId = telephonyManager2.getSubscriberId();
                            str = subscriberId;
                            if (str != null) {
                                if (str.length() > 0) {
                                    return str;
                                }
                            }
                            declaredMethod = telephonyManager.getClass().getDeclaredMethod("getSubscriberIdGemini", new Class[]{Integer.TYPE});
                            if (declaredMethod == null) {
                                declaredMethod.setAccessible(true);
                                subscriberId = (String) declaredMethod.invoke(telephonyManager, new Object[]{Integer.valueOf(a)});
                            } else {
                                subscriberId = str;
                            }
                            str = subscriberId;
                            if (str != null) {
                                if (str.length() > 0) {
                                    return str;
                                }
                            }
                            str = telephonyManager.getSubscriberId();
                            if (str != null && str.length() > 0) {
                                return str;
                            }
                        }
                    }
                    subscriberId = null;
                    str = subscriberId;
                } catch (Exception e) {
                }
                if (str != null) {
                    if (str.length() > 0) {
                        return str;
                    }
                }
                try {
                    declaredMethod = telephonyManager.getClass().getDeclaredMethod("getSubscriberIdGemini", new Class[]{Integer.TYPE});
                    if (declaredMethod == null) {
                        subscriberId = str;
                    } else {
                        declaredMethod.setAccessible(true);
                        subscriberId = (String) declaredMethod.invoke(telephonyManager, new Object[]{Integer.valueOf(a)});
                    }
                    str = subscriberId;
                } catch (Exception e2) {
                }
                if (str != null) {
                    if (str.length() > 0) {
                        return str;
                    }
                }
                str = telephonyManager.getSubscriberId();
                return str;
            }
        } catch (Exception e3) {
        }
        return "";
    }

    public static boolean getIsRoot() {
        return m2853a() || m2855b() || m2856c();
    }

    public static String getKernelVersion() {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/version"), 256);
            CharSequence readLine = bufferedReader.readLine();
            bufferedReader.close();
            String str = "\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)";
            Matcher matcher = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\((?:[^(]*\\([^)]*\\))?[^)]*\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(readLine);
            return !matcher.matches() ? "Unavailable" : matcher.groupCount() < 4 ? "Unavailable" : new StringBuilder(matcher.group(1)).append("\n").append(matcher.group(2)).append(" ").append(matcher.group(3)).append("\n").append(matcher.group(4)).toString();
        } catch (IOException e) {
            return "Unavailable";
        } catch (Throwable th) {
            bufferedReader.close();
        }
    }

    public static String getLAC(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (!(telephonyManager == null || TextUtils.isEmpty(telephonyManager.getSimOperator()))) {
                CellLocation cellLocation = telephonyManager.getCellLocation();
                if (cellLocation instanceof CdmaCellLocation) {
                    CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
                    if (cdmaCellLocation != null) {
                        return Integer.toString(cdmaCellLocation.getNetworkId());
                    }
                } else if (cellLocation instanceof GsmCellLocation) {
                    GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
                    if (gsmCellLocation != null) {
                        return Integer.toString(gsmCellLocation.getLac());
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String getLang(Context context) {
        return context.getResources().getConfiguration().locale.getLanguage();
    }

    public static int getLcdHeight(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getHeight();
    }

    public static String getLcdSize(Context context) {
        return getLcdWidth(context) + "*" + getLcdHeight(context);
    }

    public static int getLcdWidth(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getWidth();
    }

    public static String getMCCMNC(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                Object simOperator = telephonyManager.getSimOperator();
                if (!TextUtils.isEmpty(simOperator)) {
                    return simOperator;
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String getPhoneNumber(Context context) {
        return getPhoneNumber(context, 0);
    }

    public static String getPhoneNumber(Context context, int i) {
        String line1Number;
        String str = null;
        if (i == 0) {
            try {
                line1Number = ((TelephonyManager) context.getSystemService("phone")).getLine1Number();
            } catch (Exception e) {
                line1Number = str;
            }
        } else {
            try {
                str = ((TelephonyManager) TelephonyManager.class.getDeclaredConstructor(new Class[]{Context.class, Integer.TYPE}).newInstance(new Object[]{context, Integer.valueOf(1)})).getLine1Number();
            } catch (Exception e2) {
            }
            if (str == null) {
                try {
                    line1Number = ((IPhoneSubInfo) m2852a("getSubscriberInfo", new Class[]{Integer.TYPE}, new Object[]{Integer.valueOf(1)}, TelephonyManager.class, (TelephonyManager) TelephonyManager.class.getDeclaredConstructor(new Class[]{Context.class}).newInstance(new Object[]{context}))).getLine1Number();
                } catch (Exception e3) {
                    line1Number = str;
                }
            } else {
                line1Number = str;
            }
        }
        return line1Number == null ? "" : line1Number;
    }

    public static String getRunningServicePackageName(Context context, String str) {
        List runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (runningServices.size() > 0) {
            for (int i = 0; i < runningServices.size(); i++) {
                if (((RunningServiceInfo) runningServices.get(i)).service.getClassName().equals(str)) {
                    return ((RunningServiceInfo) runningServices.get(i)).service.getPackageName();
                }
            }
        }
        return null;
    }

    public static int getSmsNum(Context context) {
        return context.getContentResolver().query(Uri.parse("content://sms"), null, null, null, null).getCount();
    }

    public static String getSmsSc(Context context) {
        return getSmsSc(context, 0);
    }

    public static String getSmsSc(Context context, int i) {
        return "";
    }

    public static String getTatalRAMSize() {
        Class cls;
        String[] strArr = new String[]{"MemTotal:"};
        long[] jArr = new long[]{-1};
        try {
            cls = Class.forName("android.os.Process");
        } catch (Exception e) {
            e.printStackTrace();
            cls = null;
        }
        try {
            cls.getMethod("readProcLines", new Class[]{String.class, String[].class, long[].class}).invoke(cls.newInstance(), new Object[]{"/proc/meminfo", strArr, jArr});
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return jArr[0] != -1 ? Long.toString(jArr[0] / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "M" : "null";
    }

    public static String getTotalRomSize() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getPath());
        return Long.toString(((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "M";
    }

    public static String getVersionIncremental() {
        String str = VERSION.INCREMENTAL;
        return str.equalsIgnoreCase("unknown") ? "" : str;
    }

    public static int getVersionSdkInt() {
        return VERSION.SDK_INT;
    }

    public static String getVmVersion() {
        return System.getProperty("java.vm.version");
    }

    public static String getWifiMAC(Context context) {
        try {
            return ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }
}
