package com.droi.sdk.push.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import com.droi.sdk.core.Core;
import com.droi.sdk.internal.DroiDataCollector;
import com.sina.weibo.sdk.constant.WBConstants;
import com.zhuoyou.plugin.running.app.Permissions;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONObject;

public class C1015j {
    public static String m3150a(Context context, String str) {
        String str2 = null;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                str2 = applicationInfo.metaData.getString(str);
            }
        } catch (NameNotFoundException e) {
            C1012g.m3142d("Get mete data failed: " + str);
        }
        return str2;
    }

    public static String m3151a(Context context, String str, String str2) {
        String str3 = null;
        JSONObject jSONObject = new JSONObject();
        String[] e = C1015j.m3170e(context);
        String channelName = Core.getChannelName(context);
        if (!TextUtils.isEmpty(str)) {
            try {
                jSONObject.put("osType", 1);
                if (e != null && e.length == 2) {
                    jSONObject.put("appVer", e[0]);
                    jSONObject.put("appBuild", e[1]);
                }
                jSONObject.put("proVer", 1);
                jSONObject.put("sdkVer", 2);
                jSONObject.put("deviceId", str);
                jSONObject.put("channelId", channelName);
                if (C1015j.m3168d(str2)) {
                    jSONObject.put("tags", str2);
                }
                str3 = jSONObject.toString();
            } catch (Exception e2) {
                C1012g.m3137a(e2);
            }
        }
        return str3;
    }

    public static String m3152a(Context context, String str, String str2, String str3, String str4) {
        IOException e;
        Throwable th;
        Exception exception;
        BufferedInputStream bufferedInputStream = null;
        String str5 = "";
        if (!(context == null || str == null || "".equals(str.trim()) || str3 == null || "".equals(str3.trim()) || str4 == null || "".equals(str4.trim()))) {
            BufferedInputStream bufferedInputStream2;
            try {
                Response execute = new OkHttpClient().newCall(new Builder().url(str).addHeader(WBConstants.SSO_APP_KEY, str4).post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), C1009d.m3130a(str2.getBytes("UTF-8"), str3.getBytes("UTF-8")))).build()).execute();
                if (execute.isSuccessful()) {
                    ResponseBody body = execute.body();
                    if (body != null) {
                        bufferedInputStream2 = new BufferedInputStream(body.byteStream());
                        try {
                            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(1024);
                            while (true) {
                                int read = bufferedInputStream2.read();
                                if (read == -1) {
                                    break;
                                }
                                byteArrayBuffer.append((byte) read);
                            }
                            if (byteArrayBuffer.length() > 0) {
                                str5 = Boolean.valueOf(execute.header("isPress")).booleanValue() ? new String(C1017l.m3177a(C1009d.m3131b(byteArrayBuffer.toByteArray(), str3.getBytes("UTF-8"))), "UTF-8").trim() : new String(C1009d.m3131b(byteArrayBuffer.toByteArray(), str3.getBytes("UTF-8")), "UTF-8").trim();
                            }
                            if (bufferedInputStream2 != null) {
                                bufferedInputStream2.close();
                            }
                        } catch (IOException e2) {
                            e = e2;
                            try {
                                throw e;
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        } catch (Exception e3) {
                            Exception exception2 = e3;
                            bufferedInputStream = bufferedInputStream2;
                            exception = exception2;
                            try {
                                C1012g.m3137a(exception);
                                if (bufferedInputStream != null) {
                                    bufferedInputStream.close();
                                }
                                return str5;
                            } catch (Throwable th3) {
                                th = th3;
                                bufferedInputStream2 = bufferedInputStream;
                                if (bufferedInputStream2 != null) {
                                    bufferedInputStream2.close();
                                }
                                throw th;
                            }
                        }
                    }
                }
                bufferedInputStream2 = null;
                if (bufferedInputStream2 != null) {
                    bufferedInputStream2.close();
                }
            } catch (IOException e4) {
                e = e4;
                bufferedInputStream2 = null;
                throw e;
            } catch (Exception e5) {
                exception = e5;
                C1012g.m3137a(exception);
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                return str5;
            } catch (Throwable th4) {
                th = th4;
                bufferedInputStream2 = null;
                if (bufferedInputStream2 != null) {
                    bufferedInputStream2.close();
                }
                throw th;
            }
        }
        return str5;
    }

    public static String m3153a(String str) {
        MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(str.getBytes("UTF-8"));
        byte[] digest = instance.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            stringBuffer.append(Character.forDigit((digest[i] & 240) >> 4, 16));
            stringBuffer.append(Character.forDigit(digest[i] & 15, 16));
        }
        return stringBuffer.toString();
    }

    public static void m3154a(Context context, Serializable serializable, String str) {
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;
        Exception e;
        FileOutputStream fileOutputStream2;
        FileOutputStream fileOutputStream3;
        ObjectOutputStream objectOutputStream2;
        OutputStream fileOutputStream4;
        OutputStream outputStream;
        Throwable th;
        OutputStream outputStream2;
        ObjectOutputStream objectOutputStream3 = null;
        try {
            File file = new File(C1011f.m3134a());
            if (!file.exists()) {
                file.mkdirs();
            }
            fileOutputStream = new FileOutputStream(C1011f.m3134a() + str);
            try {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                try {
                    objectOutputStream.writeObject(serializable);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (Exception e2) {
                            C1012g.m3139b(e2);
                            fileOutputStream2 = fileOutputStream;
                        }
                    }
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    fileOutputStream2 = fileOutputStream;
                } catch (Exception e3) {
                    e2 = e3;
                    objectOutputStream3 = objectOutputStream;
                    fileOutputStream3 = fileOutputStream;
                    try {
                        C1012g.m3139b(e2);
                        if (fileOutputStream3 != null) {
                            try {
                                fileOutputStream3.close();
                            } catch (Exception e22) {
                                C1012g.m3139b(e22);
                                objectOutputStream2 = objectOutputStream3;
                                fileOutputStream2 = fileOutputStream3;
                                objectOutputStream = objectOutputStream2;
                            }
                        }
                        if (objectOutputStream3 != null) {
                            objectOutputStream3.close();
                        }
                        objectOutputStream2 = objectOutputStream3;
                        fileOutputStream2 = fileOutputStream3;
                        objectOutputStream = objectOutputStream2;
                        fileOutputStream4 = new FileOutputStream(C1011f.m3135a(context) + str);
                        try {
                            objectOutputStream3 = new ObjectOutputStream(fileOutputStream4);
                            try {
                                objectOutputStream3.writeObject(serializable);
                                if (fileOutputStream4 != null) {
                                    try {
                                        fileOutputStream4.close();
                                    } catch (Exception e222) {
                                        C1012g.m3139b(e222);
                                        return;
                                    }
                                }
                                if (objectOutputStream3 == null) {
                                    objectOutputStream3.close();
                                }
                            } catch (Exception e4) {
                                e222 = e4;
                                outputStream = fileOutputStream4;
                                try {
                                    C1012g.m3139b(e222);
                                    if (fileOutputStream3 != null) {
                                        try {
                                            fileOutputStream3.close();
                                        } catch (Exception e2222) {
                                            C1012g.m3139b(e2222);
                                            return;
                                        }
                                    }
                                    if (objectOutputStream3 == null) {
                                        objectOutputStream3.close();
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    objectOutputStream2 = objectOutputStream3;
                                    fileOutputStream2 = fileOutputStream3;
                                    objectOutputStream = objectOutputStream2;
                                    if (fileOutputStream2 != null) {
                                        try {
                                            fileOutputStream2.close();
                                        } catch (Exception e5) {
                                            C1012g.m3139b(e5);
                                            throw th;
                                        }
                                    }
                                    if (objectOutputStream != null) {
                                        objectOutputStream.close();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                objectOutputStream = objectOutputStream3;
                                outputStream2 = fileOutputStream4;
                                if (fileOutputStream2 != null) {
                                    fileOutputStream2.close();
                                }
                                if (objectOutputStream != null) {
                                    objectOutputStream.close();
                                }
                                throw th;
                            }
                        } catch (Exception e6) {
                            e2222 = e6;
                            objectOutputStream3 = objectOutputStream;
                            outputStream = fileOutputStream4;
                            C1012g.m3139b(e2222);
                            if (fileOutputStream3 != null) {
                                fileOutputStream3.close();
                            }
                            if (objectOutputStream3 == null) {
                                objectOutputStream3.close();
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            outputStream2 = fileOutputStream4;
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                            }
                            if (objectOutputStream != null) {
                                objectOutputStream.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        fileOutputStream = fileOutputStream3;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e52) {
                                C1012g.m3139b(e52);
                                throw th;
                            }
                        }
                        if (objectOutputStream3 != null) {
                            objectOutputStream3.close();
                        }
                        throw th;
                    }
                } catch (Throwable th6) {
                    th = th6;
                    objectOutputStream3 = objectOutputStream;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (objectOutputStream3 != null) {
                        objectOutputStream3.close();
                    }
                    throw th;
                }
            } catch (Exception e7) {
                e2222 = e7;
                fileOutputStream3 = fileOutputStream;
                C1012g.m3139b(e2222);
                if (fileOutputStream3 != null) {
                    fileOutputStream3.close();
                }
                if (objectOutputStream3 != null) {
                    objectOutputStream3.close();
                }
                objectOutputStream2 = objectOutputStream3;
                fileOutputStream2 = fileOutputStream3;
                objectOutputStream = objectOutputStream2;
                fileOutputStream4 = new FileOutputStream(C1011f.m3135a(context) + str);
                objectOutputStream3 = new ObjectOutputStream(fileOutputStream4);
                objectOutputStream3.writeObject(serializable);
                if (fileOutputStream4 != null) {
                    fileOutputStream4.close();
                }
                if (objectOutputStream3 == null) {
                    objectOutputStream3.close();
                }
            } catch (Throwable th7) {
                th = th7;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (objectOutputStream3 != null) {
                    objectOutputStream3.close();
                }
                throw th;
            }
        } catch (Exception e8) {
            e2222 = e8;
            fileOutputStream3 = null;
            C1012g.m3139b(e2222);
            if (fileOutputStream3 != null) {
                fileOutputStream3.close();
            }
            if (objectOutputStream3 != null) {
                objectOutputStream3.close();
            }
            objectOutputStream2 = objectOutputStream3;
            fileOutputStream2 = fileOutputStream3;
            objectOutputStream = objectOutputStream2;
            fileOutputStream4 = new FileOutputStream(C1011f.m3135a(context) + str);
            objectOutputStream3 = new ObjectOutputStream(fileOutputStream4);
            objectOutputStream3.writeObject(serializable);
            if (fileOutputStream4 != null) {
                fileOutputStream4.close();
            }
            if (objectOutputStream3 == null) {
                objectOutputStream3.close();
            }
        } catch (Throwable th8) {
            th = th8;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (objectOutputStream3 != null) {
                objectOutputStream3.close();
            }
            throw th;
        }
        try {
            fileOutputStream4 = new FileOutputStream(C1011f.m3135a(context) + str);
            objectOutputStream3 = new ObjectOutputStream(fileOutputStream4);
            objectOutputStream3.writeObject(serializable);
            if (fileOutputStream4 != null) {
                fileOutputStream4.close();
            }
            if (objectOutputStream3 == null) {
                objectOutputStream3.close();
            }
        } catch (Exception e9) {
            e2222 = e9;
            objectOutputStream2 = objectOutputStream;
            fileOutputStream3 = fileOutputStream2;
            objectOutputStream3 = objectOutputStream2;
            C1012g.m3139b(e2222);
            if (fileOutputStream3 != null) {
                fileOutputStream3.close();
            }
            if (objectOutputStream3 == null) {
                objectOutputStream3.close();
            }
        } catch (Throwable th9) {
            th = th9;
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            throw th;
        }
    }

    public static boolean m3155a() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean m3156a(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean m3157a(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            C1012g.m3139b(e);
            return false;
        }
    }

    public static boolean m3158a(Serializable serializable, String str) {
        FileOutputStream fileOutputStream;
        Exception e;
        FileOutputStream fileOutputStream2;
        Throwable th;
        ObjectOutputStream objectOutputStream = null;
        if (C1015j.m3155a()) {
            String str2 = C1011f.m3134a() + "/" + str;
            try {
                File file = new File(str2.substring(0, str2.lastIndexOf("/")));
                if (!file.exists()) {
                    file.mkdirs();
                }
                fileOutputStream = new FileOutputStream(str2);
                try {
                    ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(fileOutputStream);
                    try {
                        objectOutputStream2.writeObject(serializable);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Exception e2) {
                                C1012g.m3139b(e2);
                            }
                        }
                        if (objectOutputStream2 != null) {
                            objectOutputStream2.close();
                        }
                        return true;
                    } catch (Exception e3) {
                        e = e3;
                        objectOutputStream = objectOutputStream2;
                        fileOutputStream2 = fileOutputStream;
                        try {
                            C1012g.m3139b(e);
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (Exception e4) {
                                    C1012g.m3139b(e4);
                                    return false;
                                }
                            }
                            if (objectOutputStream != null) {
                                return false;
                            }
                            objectOutputStream.close();
                            return false;
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream = fileOutputStream2;
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (Exception e42) {
                                    C1012g.m3139b(e42);
                                    throw th;
                                }
                            }
                            if (objectOutputStream != null) {
                                objectOutputStream.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        objectOutputStream = objectOutputStream2;
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (objectOutputStream != null) {
                            objectOutputStream.close();
                        }
                        throw th;
                    }
                } catch (Exception e5) {
                    e42 = e5;
                    fileOutputStream2 = fileOutputStream;
                    C1012g.m3139b(e42);
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                    if (objectOutputStream != null) {
                        return false;
                    }
                    objectOutputStream.close();
                    return false;
                } catch (Throwable th4) {
                    th = th4;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e42 = e6;
                fileOutputStream2 = null;
                C1012g.m3139b(e42);
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                if (objectOutputStream != null) {
                    return false;
                }
                objectOutputStream.close();
                return false;
            } catch (Throwable th5) {
                th = th5;
                fileOutputStream = null;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                throw th;
            }
        }
        C1012g.m3142d("SD card no exist");
        return false;
    }

    public static byte[] m3159a(String str, byte[] bArr) {
        try {
            return C1009d.m3131b(bArr, str.getBytes("UTF-8"));
        } catch (Exception e) {
            C1012g.m3142d("Decrypt data error, check appid and secret");
            C1012g.m3139b(e);
            return null;
        }
    }

    public static Serializable m3160b(Context context, String str) {
        FileInputStream fileInputStream;
        Serializable serializable;
        ObjectInputStream objectInputStream;
        FileInputStream fileInputStream2;
        Throwable th;
        ObjectInputStream objectInputStream2 = null;
        File file = new File(C1011f.m3134a() + str);
        if (!file.exists()) {
            file = new File(C1011f.m3135a(context) + str);
            if (!file.exists()) {
                return null;
            }
        }
        try {
            fileInputStream = new FileInputStream(file);
            try {
                ObjectInputStream objectInputStream3 = new ObjectInputStream(fileInputStream);
                try {
                    serializable = (Serializable) objectInputStream3.readObject();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e) {
                            C1012g.m3139b(e);
                        }
                    }
                    if (objectInputStream3 != null) {
                        try {
                            objectInputStream3.close();
                        } catch (Exception e2) {
                            C1012g.m3139b(e2);
                        }
                    }
                } catch (Exception e3) {
                    objectInputStream = objectInputStream3;
                    fileInputStream2 = fileInputStream;
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (Exception e4) {
                            C1012g.m3139b(e4);
                        }
                    }
                    if (objectInputStream != null) {
                        try {
                            objectInputStream.close();
                            serializable = null;
                        } catch (Exception e5) {
                            C1012g.m3139b(e5);
                            serializable = null;
                        }
                    } else {
                        serializable = null;
                    }
                    return serializable;
                } catch (Throwable th2) {
                    th = th2;
                    objectInputStream2 = objectInputStream3;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e42) {
                            C1012g.m3139b(e42);
                        }
                    }
                    if (objectInputStream2 != null) {
                        try {
                            objectInputStream2.close();
                        } catch (Exception e22) {
                            C1012g.m3139b(e22);
                        }
                    }
                    throw th;
                }
            } catch (Exception e6) {
                objectInputStream = null;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                if (objectInputStream != null) {
                    serializable = null;
                } else {
                    objectInputStream.close();
                    serializable = null;
                }
                return serializable;
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream2 != null) {
                    objectInputStream2.close();
                }
                throw th;
            }
        } catch (Exception e7) {
            objectInputStream = null;
            fileInputStream2 = null;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (objectInputStream != null) {
                objectInputStream.close();
                serializable = null;
            } else {
                serializable = null;
            }
            return serializable;
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            throw th;
        }
        return serializable;
    }

    public static Serializable m3161b(String str) {
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        Serializable serializable;
        Exception e;
        Throwable th;
        if (C1015j.m3155a()) {
            File file = new File(C1011f.m3134a() + "/" + str);
            if (!file.exists()) {
                return null;
            }
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    objectInputStream = new ObjectInputStream(fileInputStream);
                    try {
                        serializable = (Serializable) objectInputStream.readObject();
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e2) {
                                C1012g.m3139b(e2);
                            }
                        }
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                    } catch (FileNotFoundException e3) {
                        e = e3;
                        try {
                            C1012g.m3137a(e);
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (Exception e4) {
                                    C1012g.m3139b(e4);
                                    serializable = null;
                                }
                            }
                            if (objectInputStream != null) {
                                objectInputStream.close();
                            }
                            serializable = null;
                            return serializable;
                        } catch (Throwable th2) {
                            th = th2;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (Exception e22) {
                                    C1012g.m3139b(e22);
                                    throw th;
                                }
                            }
                            if (objectInputStream != null) {
                                objectInputStream.close();
                            }
                            throw th;
                        }
                    } catch (StreamCorruptedException e5) {
                        e4 = e5;
                        C1012g.m3137a(e4);
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e42) {
                                C1012g.m3139b(e42);
                                serializable = null;
                            }
                        }
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        serializable = null;
                        return serializable;
                    } catch (IOException e6) {
                        e42 = e6;
                        C1012g.m3137a(e42);
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e422) {
                                C1012g.m3139b(e422);
                                serializable = null;
                            }
                        }
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        serializable = null;
                        return serializable;
                    } catch (ClassNotFoundException e7) {
                        e422 = e7;
                        C1012g.m3137a(e422);
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (Exception e4222) {
                                C1012g.m3139b(e4222);
                                serializable = null;
                            }
                        }
                        if (objectInputStream != null) {
                            objectInputStream.close();
                        }
                        serializable = null;
                        return serializable;
                    }
                } catch (FileNotFoundException e8) {
                    e4222 = e8;
                    objectInputStream = null;
                    C1012g.m3137a(e4222);
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    serializable = null;
                    return serializable;
                } catch (StreamCorruptedException e9) {
                    e4222 = e9;
                    objectInputStream = null;
                    C1012g.m3137a(e4222);
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    serializable = null;
                    return serializable;
                } catch (IOException e10) {
                    e4222 = e10;
                    objectInputStream = null;
                    C1012g.m3137a(e4222);
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    serializable = null;
                    return serializable;
                } catch (ClassNotFoundException e11) {
                    e4222 = e11;
                    objectInputStream = null;
                    C1012g.m3137a(e4222);
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    serializable = null;
                    return serializable;
                } catch (Throwable th3) {
                    th = th3;
                    objectInputStream = null;
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e12) {
                e4222 = e12;
                fileInputStream = null;
                objectInputStream = null;
                C1012g.m3137a(e4222);
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                serializable = null;
                return serializable;
            } catch (StreamCorruptedException e13) {
                e4222 = e13;
                fileInputStream = null;
                objectInputStream = null;
                C1012g.m3137a(e4222);
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                serializable = null;
                return serializable;
            } catch (IOException e14) {
                e4222 = e14;
                fileInputStream = null;
                objectInputStream = null;
                C1012g.m3137a(e4222);
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                serializable = null;
                return serializable;
            } catch (ClassNotFoundException e15) {
                e4222 = e15;
                fileInputStream = null;
                objectInputStream = null;
                C1012g.m3137a(e4222);
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                serializable = null;
                return serializable;
            } catch (Throwable th4) {
                th = th4;
                fileInputStream = null;
                objectInputStream = null;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                throw th;
            }
            return serializable;
        }
        C1012g.m3142d("SD card no exist");
        return null;
    }

    public static String m3162b(Context context) {
        return "UDP";
    }

    public static int m3163c(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            Intent intent = new Intent("android.intent.action.MAIN", null);
            intent.setPackage(packageInfo.packageName);
            ResolveInfo resolveInfo = (ResolveInfo) context.getPackageManager().queryIntentActivities(intent, 0).iterator().next();
            if (!(resolveInfo == null || resolveInfo.activityInfo.applicationInfo.icon == 0)) {
                return resolveInfo.activityInfo.applicationInfo.icon;
            }
        } catch (Exception e) {
            C1012g.m3137a(e);
        }
        return 17301634;
    }

    public static long m3164c(String str) {
        long j = 0;
        if (!(str == null || str.length() == 0)) {
            try {
                j = new SimpleDateFormat("yyyyMMddHHmm").parse(str).getTime();
            } catch (Exception e) {
                C1012g.m3139b(e);
            }
        }
        return j;
    }

    public static boolean m3165c(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            C1012g.m3142d(str + " has not installed!");
        }
        return packageInfo != null;
    }

    public static Bitmap m3166d(Context context, String str) {
        Bitmap decodeStream;
        Exception e;
        try {
            InputStream open = context.getResources().getAssets().open(str);
            decodeStream = BitmapFactory.decodeStream(open);
            try {
                open.close();
            } catch (IOException e2) {
                e = e2;
                C1012g.m3139b(e);
                return decodeStream;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            decodeStream = null;
            e = exception;
            C1012g.m3139b(e);
            return decodeStream;
        }
        return decodeStream;
    }

    public static boolean m3167d(Context context) {
        String curNetworkType = DroiDataCollector.getCurNetworkType(context);
        return "cmwap".equalsIgnoreCase(curNetworkType) || "uniwap".equalsIgnoreCase(curNetworkType) || "ctwap".equalsIgnoreCase(curNetworkType);
    }

    public static boolean m3168d(String str) {
        return (str == null || str.length() == 0) ? false : true;
    }

    public static byte[] m3169e(String str) {
        if (str == null) {
            return null;
        }
        String replace = str.replace("-", "");
        int length = replace.length();
        if (length <= 0) {
            return null;
        }
        int i = length / 2;
        if (length % 2 == 1) {
            replace = "0" + replace;
            i++;
        }
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) Integer.parseInt(replace.substring(i2 * 2, (i2 * 2) + 2), 16);
        }
        return bArr;
    }

    public static String[] m3170e(Context context) {
        String[] strArr = new String[]{"undef", "undef"};
        Context applicationContext = context.getApplicationContext();
        try {
            PackageInfo packageInfo = applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);
            strArr[0] = Integer.toString(packageInfo.versionCode);
            strArr[1] = packageInfo.versionName;
        } catch (Exception e) {
            C1012g.m3139b(e);
        }
        return strArr;
    }

    public static String m3171f(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        int myPid = Process.myPid();
        List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (!(runningAppProcesses == null || runningAppProcesses.isEmpty())) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == myPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        return null;
    }

    public static boolean m3172g(Context context) {
        return C1015j.m3155a() && C1015j.m3173h(context);
    }

    private static boolean m3173h(Context context) {
        if (VERSION.SDK_INT < 23 || context == null) {
            return true;
        }
        String[] strArr = new String[]{Permissions.PERMISSION_WRITE_STORAGE, Permissions.PERMISSION_READ_STORAGE};
        context.getPackageManager();
        for (String checkSelfPermission : strArr) {
            if (PermissionChecker.checkSelfPermission(context, checkSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }
}
