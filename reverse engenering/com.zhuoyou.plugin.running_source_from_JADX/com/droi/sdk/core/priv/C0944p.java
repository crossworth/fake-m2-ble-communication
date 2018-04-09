package com.droi.sdk.core.priv;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.Process;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewCompat;
import android.util.Base64;
import com.droi.btlib.service.BtManagerService;
import com.zhuoyou.plugin.running.app.Permissions;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.UUID;
import java.util.zip.CRC32;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class C0944p {
    protected static final char[] f3079a = "0123456789abcdef".toCharArray();
    private static final byte[] f3080b = new byte[]{(byte) 0, (byte) 8, (byte) 5, (byte) 2, (byte) 5, (byte) 7, (byte) 8, (byte) 0};
    private static final BigInteger f3081c = BigInteger.ONE.shiftLeft(64);

    public static class C0943a {
        private static long m2781a(String str, int i) {
            byte[] bytes = str.getBytes();
            if (i != 8) {
                return -1;
            }
            long j = 0;
            for (int i2 = 0; i2 < i; i2++) {
                j *= 36;
                byte b = bytes[(8 - i2) - 1];
                if (b >= (byte) 48 && b <= (byte) 57) {
                    j += (long) ((b - 48) + 26);
                } else if (b < (byte) 97 || b > (byte) 122) {
                    return -1;
                } else {
                    j += (long) (b - 97);
                }
            }
            return j ^ 603050306030L;
        }

        public static short m2782a(String str) {
            try {
                byte[] c = C0944p.m2797c(str.substring(8));
                byte b = C0943a.m2784b(str);
                int i = (((c[20] & 255) + ((c[21] & 255) * 256)) + ((c[22] & 255) * 65536)) + ((c[23] & 255) * ViewCompat.MEASURED_STATE_TOO_SMALL);
                short s = (short) ((c[16] & 255) + ((c[17] & 255) * 256));
                if (((short) (((c[18] & 255) + ((c[19] & 255) * 256)) / BtManagerService.CLASSIC_CMD_SET_ALARM)) != (short) 1) {
                    return (short) -1;
                }
                CRC32 crc32 = new CRC32();
                crc32.update(str.getBytes(), 0, 8);
                crc32.update(c, 20, 4);
                int value = (int) crc32.getValue();
                return (((short) (((short) ((((value >> 24) & 255) * 256) + ((value >> 16) & 255))) ^ ((short) ((value & 255) + (((value >> 8) & 255) * 256))))) == s && i % (b & 255) == 0) ? (short) (((i & -1) / 239) / (b & 255)) : (short) -1;
            } catch (Exception e) {
                return (short) -1;
            }
        }

        public static boolean m2783a(short s) {
            return (s == (short) -1 || (s & 8192) == 0) ? false : true;
        }

        private static byte m2784b(String str) {
            int i = 0;
            long a = C0943a.m2781a(str, 8);
            byte b = (byte) 0;
            while (i < 8) {
                b = (byte) ((int) (((long) b) ^ ((a & 255) & 255)));
                a >>= 8;
                i++;
            }
            return b;
        }
    }

    public static String m2785a(long j) {
        BigInteger valueOf = BigInteger.valueOf(j);
        if (valueOf.signum() < 0) {
            valueOf = valueOf.add(f3081c);
        }
        return valueOf.toString();
    }

    public static String m2786a(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            cArr[i * 2] = f3079a[i2 >>> 4];
            cArr[(i * 2) + 1] = f3079a[i2 & 15];
        }
        return new String(cArr);
    }

    public static boolean m2787a(Context context) {
        int intExtra = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED")).getIntExtra("status", -1);
        return intExtra == 2 || intExtra == 5;
    }

    public static byte[] m2788a(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }

    public static byte[] m2789a(byte[] bArr, byte[] bArr2) {
        try {
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(f3080b);
            Key secretKeySpec = new SecretKeySpec(bArr2, "DES");
            Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            instance.init(1, secretKeySpec, ivParameterSpec);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static long[] m2790a() {
        try {
            Class cls = Class.forName("android.os.ServiceManager");
            Object newInstance = cls.newInstance();
            Object invoke = cls.getMethod("getService", new Class[]{String.class}).invoke(newInstance, new Object[]{new String("TydNativeMisc")});
            Class cls2 = Class.forName("com.freeme.internal.server.INativeMiscService$Stub");
            invoke = cls2.getMethod("asInterface", new Class[]{IBinder.class}).invoke(cls2, new Object[]{invoke});
            UUID fromString = UUID.fromString((String) invoke.getClass().getMethod("getDeviceUUID", new Class[0]).invoke(invoke, new Object[0]));
            return new long[]{fromString.getMostSignificantBits(), fromString.getLeastSignificantBits()};
        } catch (Exception e) {
            return null;
        }
    }

    public static String m2791b(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }

    public static boolean m2792b(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static byte[] m2793b(String str) {
        return Base64.decode(str, 2);
    }

    public static byte[] m2794b(byte[] bArr, byte[] bArr2) {
        try {
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(f3080b);
            Key secretKeySpec = new SecretKeySpec(bArr2, "DES");
            Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            instance.init(2, secretKeySpec, ivParameterSpec);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            return null;
        }
    }

    public static String m2795c(byte[] bArr) {
        return Base64.encodeToString(bArr, 10);
    }

    public static boolean m2796c(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        int type = activeNetworkInfo.getType();
        boolean z = type == 1 || type == 6;
        return z;
    }

    public static byte[] m2797c(String str) {
        return Base64.decode(str, 10);
    }

    public static String m2798d(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        int myPid = Process.myPid();
        for (RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public static boolean m2799d(String str) {
        try {
            return C0943a.m2783a(C0943a.m2782a(str));
        } catch (Exception e) {
            return false;
        }
    }

    public static byte[] m2800d(byte[] bArr) {
        try {
            return MessageDigest.getInstance("MD5").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String m2801e(String str) {
        return str == null ? null : C0944p.m2786a(C0944p.m2800d(str.getBytes()));
    }

    public static boolean m2802e(Context context) {
        boolean z = false;
        if (VERSION.SDK_INT < 23) {
            return true;
        }
        String[] strArr = new String[]{Permissions.PERMISSION_WRITE_STORAGE, Permissions.PERMISSION_READ_STORAGE};
        context.getPackageManager();
        for (String checkSelfPermission : strArr) {
            if (PermissionChecker.checkSelfPermission(context, checkSelfPermission) != 0) {
                break;
            }
        }
        z = true;
        return z;
    }

    public static byte[] m2803e(byte[] bArr) {
        try {
            return MessageDigest.getInstance("SHA256").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String m2804f(String str) {
        return str == null ? null : C0944p.m2786a(C0944p.m2803e(str.getBytes()));
    }
}
