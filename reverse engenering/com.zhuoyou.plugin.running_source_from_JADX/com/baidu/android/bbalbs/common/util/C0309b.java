package com.baidu.android.bbalbs.common.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.android.bbalbs.common.p004a.C0302a;
import com.baidu.android.bbalbs.common.p004a.C0303b;
import com.baidu.android.bbalbs.common.p004a.C0304c;
import com.baidu.android.bbalbs.common.p004a.C0305d;
import com.tencent.stat.DeviceInfo;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.running.app.Permissions;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.crypto.Cipher;
import org.json.JSONArray;
import org.json.JSONObject;

public final class C0309b {
    private static C0308b f57d;
    private final Context f58a;
    private int f59b = 0;
    private PublicKey f60c;

    private static class C0307a {
        public ApplicationInfo f50a;
        public int f51b;
        public boolean f52c;
        public boolean f53d;

        private C0307a() {
            this.f51b = 0;
            this.f52c = false;
            this.f53d = false;
        }
    }

    private static class C0308b {
        public String f54a;
        public String f55b;
        public int f56c;

        private C0308b() {
            this.f56c = 2;
        }

        public static C0308b m71a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                Object string = jSONObject.getString("deviceid");
                String string2 = jSONObject.getString(SocializeProtocolConstants.PROTOCOL_KEY_IMEI);
                int i = jSONObject.getInt(DeviceInfo.TAG_VERSION);
                if (TextUtils.isEmpty(string) || string2 == null) {
                    return null;
                }
                C0308b c0308b = new C0308b();
                c0308b.f54a = string;
                c0308b.f55b = string2;
                c0308b.f56c = i;
                return c0308b;
            } catch (Throwable e) {
                C0309b.m89b(e);
                return null;
            }
        }

        public String m72a() {
            try {
                return new JSONObject().put("deviceid", this.f54a).put(SocializeProtocolConstants.PROTOCOL_KEY_IMEI, this.f55b).put(DeviceInfo.TAG_VERSION, this.f56c).toString();
            } catch (Throwable e) {
                C0309b.m89b(e);
                return null;
            }
        }

        public String m73b() {
            String str = this.f55b;
            if (TextUtils.isEmpty(str)) {
                str = "0";
            }
            return this.f54a + "|" + new StringBuffer(str).reverse().toString();
        }
    }

    private C0309b(Context context) {
        this.f58a = context.getApplicationContext();
        m78a();
    }

    public static String m74a(Context context) {
        return C0309b.m90c(context).m73b();
    }

    private static String m75a(File file) {
        FileReader fileReader;
        Throwable e;
        Throwable th;
        String str = null;
        try {
            fileReader = new FileReader(file);
            try {
                char[] cArr = new char[8192];
                CharArrayWriter charArrayWriter = new CharArrayWriter();
                while (true) {
                    int read = fileReader.read(cArr);
                    if (read <= 0) {
                        break;
                    }
                    charArrayWriter.write(cArr, 0, read);
                }
                str = charArrayWriter.toString();
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (Throwable e2) {
                        C0309b.m89b(e2);
                    }
                }
            } catch (Exception e3) {
                e2 = e3;
                try {
                    C0309b.m89b(e2);
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (Throwable e22) {
                            C0309b.m89b(e22);
                        }
                    }
                    return str;
                } catch (Throwable th2) {
                    th = th2;
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (Throwable e222) {
                            C0309b.m89b(e222);
                        }
                    }
                    throw th;
                }
            }
        } catch (Exception e4) {
            e222 = e4;
            Object obj = str;
            C0309b.m89b(e222);
            if (fileReader != null) {
                fileReader.close();
            }
            return str;
        } catch (Throwable e2222) {
            fileReader = str;
            th = e2222;
            if (fileReader != null) {
                fileReader.close();
            }
            throw th;
        }
        return str;
    }

    private static String m76a(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        String str = "";
        str = "";
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            str = toHexString.length() == 1 ? str + "0" + toHexString : str + toHexString;
        }
        return str.toLowerCase();
    }

    private List<C0307a> m77a(Intent intent, boolean z) {
        List<C0307a> arrayList = new ArrayList();
        PackageManager packageManager = this.f58a.getPackageManager();
        List<ResolveInfo> queryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers != null) {
            for (ResolveInfo resolveInfo : queryBroadcastReceivers) {
                if (!(resolveInfo.activityInfo == null || resolveInfo.activityInfo.applicationInfo == null)) {
                    try {
                        Bundle bundle = packageManager.getReceiverInfo(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name), 128).metaData;
                        if (bundle != null) {
                            Object string = bundle.getString("galaxy_data");
                            if (!TextUtils.isEmpty(string)) {
                                byte[] a = C0303b.m64a(string.getBytes("utf-8"));
                                JSONObject jSONObject = new JSONObject(new String(a));
                                C0307a c0307a = new C0307a();
                                c0307a.f51b = jSONObject.getInt("priority");
                                c0307a.f50a = resolveInfo.activityInfo.applicationInfo;
                                if (this.f58a.getPackageName().equals(resolveInfo.activityInfo.applicationInfo.packageName)) {
                                    c0307a.f53d = true;
                                }
                                if (z) {
                                    Object string2 = bundle.getString("galaxy_sf");
                                    if (!TextUtils.isEmpty(string2)) {
                                        int i;
                                        PackageInfo packageInfo = packageManager.getPackageInfo(resolveInfo.activityInfo.applicationInfo.packageName, 64);
                                        JSONArray jSONArray = jSONObject.getJSONArray("sigs");
                                        String[] strArr = new String[jSONArray.length()];
                                        for (i = 0; i < strArr.length; i++) {
                                            strArr[i] = jSONArray.getString(i);
                                        }
                                        if (m82a(strArr, m84a(packageInfo.signatures))) {
                                            byte[] a2 = C0309b.m83a(C0303b.m64a(string2.getBytes()), this.f60c);
                                            i = (a2 == null || !Arrays.equals(a2, C0305d.m68a(a))) ? 0 : 1;
                                            if (i != 0) {
                                                c0307a.f52c = true;
                                            }
                                        }
                                    }
                                }
                                arrayList.add(c0307a);
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        Collections.sort(arrayList, new C0310c(this));
        return arrayList;
    }

    private void m78a() {
        ByteArrayInputStream byteArrayInputStream;
        Throwable e;
        ByteArrayInputStream byteArrayInputStream2 = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(C0306a.m70a());
            try {
                this.f60c = CertificateFactory.getInstance("X.509").generateCertificate(byteArrayInputStream).getPublicKey();
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Throwable e2) {
                        C0309b.m89b(e2);
                    }
                }
            } catch (Exception e3) {
                if (byteArrayInputStream != null) {
                    try {
                        byteArrayInputStream.close();
                    } catch (Throwable e22) {
                        C0309b.m89b(e22);
                    }
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                byteArrayInputStream2 = byteArrayInputStream;
                e22 = th2;
                if (byteArrayInputStream2 != null) {
                    try {
                        byteArrayInputStream2.close();
                    } catch (Throwable th3) {
                        C0309b.m89b(th3);
                    }
                }
                throw e22;
            }
        } catch (Exception e4) {
            byteArrayInputStream = null;
            if (byteArrayInputStream != null) {
                byteArrayInputStream.close();
            }
        } catch (Throwable th4) {
            e22 = th4;
            if (byteArrayInputStream2 != null) {
                byteArrayInputStream2.close();
            }
            throw e22;
        }
    }

    private boolean m80a(String str) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.f58a.openFileOutput("libcuid.so", 1);
            fileOutputStream.write(str.getBytes());
            fileOutputStream.flush();
            if (fileOutputStream == null) {
                return true;
            }
            try {
                fileOutputStream.close();
                return true;
            } catch (Throwable e) {
                C0309b.m89b(e);
                return true;
            }
        } catch (Throwable e2) {
            C0309b.m89b(e2);
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Throwable e22) {
                    C0309b.m89b(e22);
                }
            }
            return false;
        } catch (Throwable th) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Throwable e3) {
                    C0309b.m89b(e3);
                }
            }
        }
    }

    private boolean m81a(String str, String str2) {
        try {
            return System.putString(this.f58a.getContentResolver(), str, str2);
        } catch (Throwable e) {
            C0309b.m89b(e);
            return false;
        }
    }

    private boolean m82a(String[] strArr, String[] strArr2) {
        int i = 0;
        if (strArr == null || strArr2 == null || strArr.length != strArr2.length) {
            return false;
        }
        HashSet hashSet = new HashSet();
        for (Object add : strArr) {
            hashSet.add(add);
        }
        HashSet hashSet2 = new HashSet();
        int length = strArr2.length;
        while (i < length) {
            hashSet2.add(strArr2[i]);
            i++;
        }
        return hashSet.equals(hashSet2);
    }

    private static byte[] m83a(byte[] bArr, PublicKey publicKey) throws Exception {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(2, publicKey);
        return instance.doFinal(bArr);
    }

    private String[] m84a(Signature[] signatureArr) {
        String[] strArr = new String[signatureArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = C0309b.m76a(C0305d.m68a(signatureArr[i].toByteArray()));
        }
        return strArr;
    }

    private C0308b m85b() {
        boolean z;
        boolean z2;
        String str;
        String str2;
        C0308b a;
        C0308b e;
        String str3 = null;
        int i = 0;
        List a2 = m77a(new Intent("com.baidu.intent.action.GALAXY").setPackage(this.f58a.getPackageName()), true);
        int i2;
        if (a2 == null || a2.size() == 0) {
            for (i2 = 0; i2 < 3; i2++) {
                Log.w("DeviceId", "galaxy lib host missing meta-data,make sure you know the right way to integrate galaxy");
            }
            z = false;
        } else {
            C0307a c0307a;
            c0307a = (C0307a) a2.get(0);
            z2 = c0307a.f52c;
            if (!c0307a.f52c) {
                for (i2 = 0; i2 < 3; i2++) {
                    Log.w("DeviceId", "galaxy config err, In the release version of the signature should be matched");
                }
            }
            z = z2;
        }
        File file = new File(this.f58a.getFilesDir(), "libcuid.so");
        C0308b a3 = file.exists() ? C0308b.m71a(C0309b.m97f(C0309b.m75a(file))) : null;
        if (a3 == null) {
            this.f59b |= 16;
            List<C0307a> a4 = m77a(new Intent("com.baidu.intent.action.GALAXY"), z);
            if (a4 != null) {
                str = "files";
                file = this.f58a.getFilesDir();
                if (str.equals(file.getName())) {
                    str2 = str;
                } else {
                    Log.e("DeviceId", "fetal error:: app files dir name is unexpectedly :: " + file.getAbsolutePath());
                    str2 = file.getName();
                }
                for (C0307a c0307a2 : a4) {
                    if (!c0307a2.f53d) {
                        File file2 = new File(new File(c0307a2.f50a.dataDir, str2), "libcuid.so");
                        if (file2.exists()) {
                            a = C0308b.m71a(C0309b.m97f(C0309b.m75a(file2)));
                            if (a != null) {
                                break;
                            }
                        }
                        a = a3;
                        a3 = a;
                    }
                }
            }
        }
        a = a3;
        if (a == null) {
            a = C0308b.m71a(C0309b.m97f(m87b("com.baidu.deviceid.v2")));
        }
        boolean c = m92c(Permissions.PERMISSION_READ_STORAGE);
        if (a == null && c) {
            this.f59b |= 2;
            e = m95e();
        } else {
            e = a;
        }
        if (e == null) {
            this.f59b |= 8;
            e = m93d();
        }
        if (e == null && c) {
            this.f59b |= 1;
            str = m99h("");
            e = m94d(str);
            i = 1;
        } else {
            str = null;
        }
        if (e == null) {
            this.f59b |= 4;
            if (i == 0) {
                str = m99h("");
            }
            C0308b c0308b = new C0308b();
            str2 = C0309b.m86b(this.f58a);
            c0308b.f54a = C0304c.m67a((VERSION.SDK_INT < 23 ? str + str2 + UUID.randomUUID().toString() : "com.baidu" + str2).getBytes(), true);
            c0308b.f55b = str;
            a = c0308b;
        } else {
            a = e;
        }
        file = new File(this.f58a.getFilesDir(), "libcuid.so");
        if (!((this.f59b & 16) == 0 && file.exists())) {
            str2 = TextUtils.isEmpty(null) ? C0309b.m96e(a.m72a()) : null;
            m80a(str2);
            str3 = str2;
        }
        z2 = m91c();
        if (z2 && ((this.f59b & 2) != 0 || TextUtils.isEmpty(m87b("com.baidu.deviceid.v2")))) {
            if (TextUtils.isEmpty(str3)) {
                str3 = C0309b.m96e(a.m72a());
            }
            m81a("com.baidu.deviceid.v2", str3);
        }
        if (m92c(Permissions.PERMISSION_WRITE_STORAGE)) {
            File file3 = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid2");
            if (!((this.f59b & 8) == 0 && file3.exists())) {
                if (TextUtils.isEmpty(str3)) {
                    str3 = C0309b.m96e(a.m72a());
                }
                C0309b.m98g(str3);
            }
        }
        if (z2 && ((this.f59b & 1) != 0 || TextUtils.isEmpty(m87b("com.baidu.deviceid")))) {
            m81a("com.baidu.deviceid", a.f54a);
            m81a("bd_setting_i", a.f55b);
        }
        if (z2 && !TextUtils.isEmpty(a.f55b)) {
            file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid");
            if (!((this.f59b & 2) == 0 && file.exists())) {
                C0309b.m88b(a.f55b, a.f54a);
            }
        }
        return a;
    }

    public static String m86b(Context context) {
        String str = "";
        Object string = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        return TextUtils.isEmpty(string) ? "" : string;
    }

    private String m87b(String str) {
        try {
            return System.getString(this.f58a.getContentResolver(), str);
        } catch (Throwable e) {
            C0309b.m89b(e);
            return null;
        }
    }

    private static void m88b(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("=");
            stringBuilder.append(str2);
            File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig");
            File file2 = new File(file, ".cuid");
            try {
                if (file.exists() && !file.isDirectory()) {
                    File file3;
                    Random random = new Random();
                    File parentFile = file.getParentFile();
                    String name = file.getName();
                    do {
                        file3 = new File(parentFile, name + random.nextInt() + ".tmp");
                    } while (file3.exists());
                    file.renameTo(file3);
                    file3.delete();
                }
                file.mkdirs();
                FileWriter fileWriter = new FileWriter(file2, false);
                fileWriter.write(C0303b.m63a(C0302a.m61a("30212102dicudiab", "30212102dicudiab", stringBuilder.toString().getBytes()), "utf-8"));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
            } catch (Exception e2) {
            }
        }
    }

    private static void m89b(Throwable th) {
    }

    private static C0308b m90c(Context context) {
        if (f57d == null) {
            synchronized (C0308b.class) {
                if (f57d == null) {
                    SystemClock.uptimeMillis();
                    f57d = new C0309b(context).m85b();
                    SystemClock.uptimeMillis();
                }
            }
        }
        return f57d;
    }

    private boolean m91c() {
        return m92c("android.permission.WRITE_SETTINGS");
    }

    private boolean m92c(String str) {
        return this.f58a.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    private C0308b m93d() {
        Object b = m87b("com.baidu.deviceid");
        String b2 = m87b("bd_setting_i");
        if (TextUtils.isEmpty(b2)) {
            b2 = m99h("");
            if (!TextUtils.isEmpty(b2)) {
                m81a("bd_setting_i", b2);
            }
        }
        if (TextUtils.isEmpty(b)) {
            b = m87b(C0304c.m67a(("com.baidu" + b2 + C0309b.m86b(this.f58a)).getBytes(), true));
        }
        if (TextUtils.isEmpty(b)) {
            return null;
        }
        C0308b c0308b = new C0308b();
        c0308b.f54a = b;
        c0308b.f55b = b2;
        return c0308b;
    }

    private C0308b m94d(String str) {
        Object obj = null;
        Object obj2 = VERSION.SDK_INT < 23 ? 1 : null;
        if (obj2 != null && TextUtils.isEmpty(str)) {
            return null;
        }
        String str2;
        C0308b c0308b;
        Object obj3 = "";
        File file = new File(Environment.getExternalStorageDirectory(), "baidu/.cuid");
        if (!file.exists()) {
            file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid");
            int i = 1;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(readLine);
                stringBuilder.append("\r\n");
            }
            bufferedReader.close();
            String[] split = new String(C0302a.m62b("30212102dicudiab", "30212102dicudiab", C0303b.m64a(stringBuilder.toString().getBytes()))).split("=");
            if (split != null && split.length == 2) {
                if (obj2 != null && str.equals(split[0])) {
                    obj3 = split[1];
                    str2 = str;
                    if (obj == null) {
                        C0309b.m88b(str2, obj3);
                    }
                    if (!TextUtils.isEmpty(obj3)) {
                        return null;
                    }
                    c0308b = new C0308b();
                    c0308b.f54a = obj3;
                    c0308b.f55b = str2;
                    return c0308b;
                } else if (obj2 == null) {
                    if (TextUtils.isEmpty(str)) {
                        str = split[1];
                    }
                    obj3 = split[1];
                    str2 = str;
                    if (obj == null) {
                        try {
                            C0309b.m88b(str2, obj3);
                        } catch (FileNotFoundException e) {
                        } catch (IOException e2) {
                        } catch (Exception e3) {
                        }
                    }
                    if (!TextUtils.isEmpty(obj3)) {
                        return null;
                    }
                    c0308b = new C0308b();
                    c0308b.f54a = obj3;
                    c0308b.f55b = str2;
                    return c0308b;
                }
            }
            str2 = str;
            if (obj == null) {
                C0309b.m88b(str2, obj3);
            }
        } catch (FileNotFoundException e4) {
            str2 = str;
        } catch (IOException e5) {
            str2 = str;
        } catch (Exception e6) {
            str2 = str;
        }
        if (!TextUtils.isEmpty(obj3)) {
            return null;
        }
        c0308b = new C0308b();
        c0308b.f54a = obj3;
        c0308b.f55b = str2;
        return c0308b;
    }

    private C0308b m95e() {
        File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid2");
        if (file.exists()) {
            Object a = C0309b.m75a(file);
            if (!TextUtils.isEmpty(a)) {
                try {
                    return C0308b.m71a(new String(C0302a.m62b("30212102dicudiab", "30212102dicudiab", C0303b.m64a(a.getBytes()))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static String m96e(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return C0303b.m63a(C0302a.m61a("30212102dicudiab", "30212102dicudiab", str.getBytes()), "utf-8");
        } catch (Throwable e) {
            C0309b.m89b(e);
            return "";
        } catch (Throwable e2) {
            C0309b.m89b(e2);
            return "";
        }
    }

    private static String m97f(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new String(C0302a.m62b("30212102dicudiab", "30212102dicudiab", C0303b.m64a(str.getBytes())));
        } catch (Throwable e) {
            C0309b.m89b(e);
            return "";
        }
    }

    private static void m98g(String str) {
        File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig");
        File file2 = new File(file, ".cuid2");
        try {
            if (file.exists() && !file.isDirectory()) {
                File file3;
                Random random = new Random();
                File parentFile = file.getParentFile();
                String name = file.getName();
                do {
                    file3 = new File(parentFile, name + random.nextInt() + ".tmp");
                } while (file3.exists());
                file.renameTo(file3);
                file3.delete();
            }
            file.mkdirs();
            FileWriter fileWriter = new FileWriter(file2, false);
            fileWriter.write(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
        } catch (Exception e2) {
        }
    }

    private String m99h(String str) {
        String deviceId;
        CharSequence i;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.f58a.getSystemService("phone");
            if (telephonyManager != null) {
                deviceId = telephonyManager.getDeviceId();
                i = C0309b.m100i(deviceId);
                return TextUtils.isEmpty(i) ? str : i;
            }
        } catch (Throwable e) {
            Log.e("DeviceId", "Read IMEI failed", e);
        }
        deviceId = null;
        i = C0309b.m100i(deviceId);
        if (TextUtils.isEmpty(i)) {
        }
    }

    private static String m100i(String str) {
        return (str == null || !str.contains(":")) ? str : "";
    }
}
