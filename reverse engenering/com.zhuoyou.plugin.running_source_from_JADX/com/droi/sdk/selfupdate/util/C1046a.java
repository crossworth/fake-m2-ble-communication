package com.droi.sdk.selfupdate.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.text.TextUtils;
import com.droi.sdk.internal.DroiLog;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class C1046a {
    public static boolean m3255a(Context context, String str) {
        Exception e;
        boolean z = true;
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            try {
                DroiLog.m2871i("ApkUtils", "isInstalled:" + true);
            } catch (Exception e2) {
                e = e2;
                DroiLog.m2869e("ApkUtils", e);
                return z;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            z = false;
            e = exception;
            DroiLog.m2869e("ApkUtils", e);
            return z;
        }
        return z;
    }

    public static String m3253a(Context context) {
        String str = null;
        DroiLog.m2871i("ApkUtils", "getSourceApkPath");
        Object f = C1047b.m3272f(context);
        if (!TextUtils.isEmpty(f)) {
            try {
                str = context.getPackageManager().getApplicationInfo(f, 0).sourceDir;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static void m3254a(Context context, File file) {
        DroiLog.m2871i("ApkUtils", "start installApk");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.addFlags(268435456);
        context.startActivity(intent);
    }

    public static boolean m3256a(File file) {
        try {
            Process exec = Runtime.getRuntime().exec("su");
            OutputStream outputStream = exec.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
            dataOutputStream.writeBytes("LD_LIBRARY_PATH=endorb:/systemb pm install -r " + file.getPath());
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            int waitFor = exec.waitFor();
            DroiLog.m2868d("ApkUtils", "value is: " + waitFor);
            if (waitFor != 0) {
                return false;
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
            String str = "";
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                str = str + readLine;
            }
            DroiLog.m2868d("ApkUtils", "install msg is: " + str);
            if (str.contains("Failure") || str.contains("Unallowed user")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean m3258b(java.io.File r11) {
        /*
        r0 = 1;
        r1 = 0;
        r9 = -1;
        r3 = 0;
        r2 = "ApkUtils";
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "install apk background, file:";
        r4 = r4.append(r5);
        r5 = r11.getPath();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.droi.sdk.internal.DroiLog.m2871i(r2, r4);
        r2 = 4;
        r2 = new java.lang.String[r2];
        r4 = "pm";
        r2[r1] = r4;
        r4 = "install";
        r2[r0] = r4;
        r4 = 2;
        r5 = "-r";
        r2[r4] = r5;
        r4 = 3;
        r5 = r11.getPath();
        r2[r4] = r5;
        r4 = new java.lang.ProcessBuilder;
        r4.<init>(r2);
        r2 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x0113, all -> 0x00e2 }
        r2.<init>();	 Catch:{ Exception -> 0x0113, all -> 0x00e2 }
        r7 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x0113, all -> 0x00e2 }
        r7.<init>();	 Catch:{ Exception -> 0x0113, all -> 0x00e2 }
        r6 = r4.start();	 Catch:{ Exception -> 0x0113, all -> 0x00e2 }
        r5 = r6.getErrorStream();	 Catch:{ Exception -> 0x0119, all -> 0x0105 }
    L_0x004e:
        r4 = r5.read();	 Catch:{ Exception -> 0x0058, all -> 0x0109 }
        if (r4 == r9) goto L_0x0081;
    L_0x0054:
        r2.write(r4);	 Catch:{ Exception -> 0x0058, all -> 0x0109 }
        goto L_0x004e;
    L_0x0058:
        r2 = move-exception;
        r4 = r5;
        r5 = r6;
        r6 = r3;
    L_0x005c:
        r2.printStackTrace();	 Catch:{ all -> 0x010e }
        if (r4 == 0) goto L_0x0064;
    L_0x0061:
        r4.close();	 Catch:{ Exception -> 0x00dd }
    L_0x0064:
        if (r3 == 0) goto L_0x0069;
    L_0x0066:
        r3.close();	 Catch:{ Exception -> 0x00dd }
    L_0x0069:
        if (r5 == 0) goto L_0x0126;
    L_0x006b:
        r5.destroy();
        r7 = r6;
    L_0x006f:
        if (r7 == 0) goto L_0x00fb;
    L_0x0071:
        r2 = "Success";
        r2 = r7.startsWith(r2);
        if (r2 == 0) goto L_0x00fb;
    L_0x0079:
        r1 = "backgroundInstallAPK()";
        r2 = "install success";
        com.droi.sdk.internal.DroiLog.m2871i(r1, r2);
    L_0x0080:
        return r0;
    L_0x0081:
        r4 = 10;
        r2.write(r4);	 Catch:{ Exception -> 0x0058, all -> 0x0109 }
        r4 = r6.getInputStream();	 Catch:{ Exception -> 0x0058, all -> 0x0109 }
    L_0x008a:
        r8 = r4.read();	 Catch:{ Exception -> 0x0097, all -> 0x010c }
        if (r8 == r9) goto L_0x009e;
    L_0x0090:
        r7.write(r8);	 Catch:{ Exception -> 0x0097, all -> 0x010c }
        r2.write(r8);	 Catch:{ Exception -> 0x0097, all -> 0x010c }
        goto L_0x008a;
    L_0x0097:
        r2 = move-exception;
        r10 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r3;
        r3 = r10;
        goto L_0x005c;
    L_0x009e:
        r8 = r7.toByteArray();	 Catch:{ Exception -> 0x0097, all -> 0x010c }
        r7 = new java.lang.String;	 Catch:{ Exception -> 0x0097, all -> 0x010c }
        r7.<init>(r8);	 Catch:{ Exception -> 0x0097, all -> 0x010c }
        r3 = "backgroundInstallAPK()";
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x011f, all -> 0x010c }
        r8.<init>();	 Catch:{ Exception -> 0x011f, all -> 0x010c }
        r9 = "install result:";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x011f, all -> 0x010c }
        r9 = new java.lang.String;	 Catch:{ Exception -> 0x011f, all -> 0x010c }
        r2 = r2.toByteArray();	 Catch:{ Exception -> 0x011f, all -> 0x010c }
        r9.<init>(r2);	 Catch:{ Exception -> 0x011f, all -> 0x010c }
        r2 = r8.append(r9);	 Catch:{ Exception -> 0x011f, all -> 0x010c }
        r2 = r2.toString();	 Catch:{ Exception -> 0x011f, all -> 0x010c }
        com.droi.sdk.internal.DroiLog.m2871i(r3, r2);	 Catch:{ Exception -> 0x011f, all -> 0x010c }
        if (r5 == 0) goto L_0x00cd;
    L_0x00ca:
        r5.close();	 Catch:{ Exception -> 0x00d8 }
    L_0x00cd:
        if (r4 == 0) goto L_0x00d2;
    L_0x00cf:
        r4.close();	 Catch:{ Exception -> 0x00d8 }
    L_0x00d2:
        if (r6 == 0) goto L_0x006f;
    L_0x00d4:
        r6.destroy();
        goto L_0x006f;
    L_0x00d8:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x00d2;
    L_0x00dd:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x0069;
    L_0x00e2:
        r0 = move-exception;
        r4 = r3;
        r5 = r3;
        r6 = r3;
    L_0x00e6:
        if (r5 == 0) goto L_0x00eb;
    L_0x00e8:
        r5.close();	 Catch:{ Exception -> 0x00f6 }
    L_0x00eb:
        if (r4 == 0) goto L_0x00f0;
    L_0x00ed:
        r4.close();	 Catch:{ Exception -> 0x00f6 }
    L_0x00f0:
        if (r6 == 0) goto L_0x00f5;
    L_0x00f2:
        r6.destroy();
    L_0x00f5:
        throw r0;
    L_0x00f6:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00f0;
    L_0x00fb:
        r0 = "backgroundInstallAPK()";
        r2 = "install failed";
        com.droi.sdk.internal.DroiLog.m2871i(r0, r2);
        r0 = r1;
        goto L_0x0080;
    L_0x0105:
        r0 = move-exception;
        r4 = r3;
        r5 = r3;
        goto L_0x00e6;
    L_0x0109:
        r0 = move-exception;
        r4 = r3;
        goto L_0x00e6;
    L_0x010c:
        r0 = move-exception;
        goto L_0x00e6;
    L_0x010e:
        r0 = move-exception;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        goto L_0x00e6;
    L_0x0113:
        r2 = move-exception;
        r4 = r3;
        r5 = r3;
        r6 = r3;
        goto L_0x005c;
    L_0x0119:
        r2 = move-exception;
        r4 = r3;
        r5 = r6;
        r6 = r3;
        goto L_0x005c;
    L_0x011f:
        r2 = move-exception;
        r3 = r4;
        r4 = r5;
        r5 = r6;
        r6 = r7;
        goto L_0x005c;
    L_0x0126:
        r7 = r6;
        goto L_0x006f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.sdk.selfupdate.util.a.b(java.io.File):boolean");
    }

    public static boolean m3257b(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if ((packageInfo.applicationInfo.flags & 1) == 0 && (packageInfo.applicationInfo.flags & 128) == 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            DroiLog.m2869e("ApkUtils", e);
            return false;
        }
    }
}
