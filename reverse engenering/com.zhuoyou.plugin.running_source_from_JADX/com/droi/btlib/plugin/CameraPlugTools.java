package com.droi.btlib.plugin;

import android.app.KeyguardManager;
import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class CameraPlugTools {
    private static final String FILE_NAME = "auto_camera";
    private static final String S_GAP = "     ";

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getFilePath(android.content.Context r12) {
        /*
        r8 = 0;
        r9 = android.os.Environment.getExternalStorageState();
        r10 = "mounted";
        r7 = r9.equals(r10);
        if (r7 == 0) goto L_0x0054;
    L_0x000d:
        r8 = android.os.Environment.getExternalStorageDirectory();
        r6 = r8.toString();
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r9 = r9.append(r6);
        r10 = "/Running/autocamera/data/";
        r9 = r9.append(r10);
        r3 = r9.toString();
        r1 = r3;
        r4 = new java.io.File;	 Catch:{ Exception -> 0x006f }
        r4.<init>(r1);	 Catch:{ Exception -> 0x006f }
        r9 = r4.exists();	 Catch:{ Exception -> 0x006f }
        if (r9 != 0) goto L_0x0056;
    L_0x0034:
        r2 = r4.mkdirs();	 Catch:{ Exception -> 0x006f }
        if (r2 == 0) goto L_0x003a;
    L_0x003a:
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r9 = r9.append(r3);
        r10 = "auto_camera";
        r9 = r9.append(r10);
        r10 = ".txt";
        r9 = r9.append(r10);
        r5 = r9.toString();
    L_0x0053:
        return r5;
    L_0x0054:
        r5 = 0;
        goto L_0x0053;
    L_0x0056:
        r9 = "gchk";
        r10 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x006f }
        r10.<init>();	 Catch:{ Exception -> 0x006f }
        r11 = "folderPath is exists ";
        r10 = r10.append(r11);	 Catch:{ Exception -> 0x006f }
        r10 = r10.append(r3);	 Catch:{ Exception -> 0x006f }
        r10 = r10.toString();	 Catch:{ Exception -> 0x006f }
        android.util.Log.v(r9, r10);	 Catch:{ Exception -> 0x006f }
        goto L_0x003a;
    L_0x006f:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.droi.btlib.plugin.CameraPlugTools.getFilePath(android.content.Context):java.lang.String");
    }

    private static boolean createFile(Context ctx) {
        boolean ret = false;
        String path = getFilePath(ctx);
        Log.i("gchk", "createFile path =" + path);
        File file = new File(path);
        if (file.exists()) {
            Log.i("gchk", "createFile file exsited");
            return true;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return ret;
        }
    }

    public static String getDataString(Context ctx, String key) {
        if (!createFile(ctx)) {
            return null;
        }
        Log.i("gchk", "getDataString");
        String value = null;
        try {
            InputStream iStream = new FileInputStream(getFilePath(ctx));
            InputStreamReader isr = new InputStreamReader(iStream, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str;
            do {
                str = br.readLine();
                if (str == null || str.equals("")) {
                    break;
                }
                String[] s = str.split(S_GAP);
                if (s[0].equals(key)) {
                    value = s[1];
                    break;
                }
            } while (str != null);
            br.close();
            isr.close();
            iStream.close();
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("gchk", "e = " + e.getMessage());
            return null;
        }
    }

    public static boolean getDataBoolean(Context ctx, String key, boolean default_value) {
        if (!createFile(ctx)) {
            return default_value;
        }
        Log.i("gchk", "getDataBoolean");
        boolean value = default_value;
        try {
            InputStream iStream = new FileInputStream(getFilePath(ctx));
            InputStreamReader isr = new InputStreamReader(iStream, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str;
            do {
                str = br.readLine();
                if (str == null || str.equals("")) {
                    break;
                }
                String[] s = str.split(S_GAP);
                if (s[0].equals(key)) {
                    value = Boolean.parseBoolean(s[1]);
                    break;
                }
            } while (str != null);
            br.close();
            isr.close();
            iStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("gchk", "e = " + e.getMessage());
        }
        return value;
    }

    public static boolean saveDataBoolean(Context context, String key, boolean value) {
        if (createFile(context)) {
            return save(context, reBuilderData(context, key, key + S_GAP + value));
        }
        return false;
    }

    public static boolean saveDataString(Context context, String key, String value) {
        if (createFile(context)) {
            return save(context, reBuilderData(context, key, key + S_GAP + value));
        }
        return false;
    }

    private static boolean save(Context context, String s) {
        Log.i("gchk", "save s=" + s);
        try {
            OutputStream fout = new FileOutputStream(getFilePath(context), false);
            fout.write(s.getBytes());
            fout.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("gchk", "e1 =" + e.getMessage());
            return false;
        } catch (IOException e2) {
            Log.i("gchk", "e2 =" + e2.getMessage());
            e2.printStackTrace();
            return false;
        }
    }

    private static String reBuilderData(Context ctx, String key, String value) {
        Log.i("gchk", "reBuilderData");
        StringBuilder sb = new StringBuilder();
        try {
            InputStream iStream = new FileInputStream(getFilePath(ctx));
            InputStreamReader isr = new InputStreamReader(iStream, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = br.readLine();
            if (str == null || str.equals("") || str.equals("null")) {
                br.close();
                isr.close();
                iStream.close();
                Log.i("gchk", "add first ");
                return value + "\n";
            }
            boolean find = false;
            while (str != null) {
                if (str.split(S_GAP)[0].equals(key)) {
                    find = true;
                    str = value;
                }
                sb.append(str + "\n");
                str = br.readLine();
            }
            if (!find) {
                sb.append(value);
                sb.append("\n");
            }
            br.close();
            isr.close();
            iStream.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("gchk", "e = " + e.getMessage());
        }
    }

    public static boolean isScreenLocked(Context context) {
        Boolean isScreenLocked = Boolean.valueOf(((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode());
        Log.i("gchk", "isScreenLocked(), isScreenLocked=" + isScreenLocked);
        return isScreenLocked.booleanValue();
    }
}
