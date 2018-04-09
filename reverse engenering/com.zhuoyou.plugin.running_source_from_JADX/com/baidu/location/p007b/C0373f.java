package com.baidu.location.p007b;

import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.location.C0455f;
import com.baidu.location.Jni;
import com.baidu.location.p005a.C0332a;
import com.baidu.location.p006h.C0458a;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0460c;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p012f.C0454h;
import com.baidu.location.p013g.C0457a;
import com.tencent.connect.common.Constants;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.URL;

public class C0373f implements UncaughtExceptionHandler {
    private static C0373f f378a = null;
    private int f379b = 0;

    private C0373f() {
    }

    public static C0373f m423a() {
        if (f378a == null) {
            f378a = new C0373f();
        }
        return f378a;
    }

    private String m424a(Throwable th) {
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    private void m425a(File file, String str, String str2) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(280);
            randomAccessFile.writeInt(12346);
            randomAccessFile.seek(300);
            randomAccessFile.writeLong(System.currentTimeMillis());
            byte[] bytes = str.getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            randomAccessFile.seek(600);
            bytes = str2.getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            if (!m426a(str, str2)) {
                randomAccessFile.seek(280);
                randomAccessFile.writeInt(1326);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    private boolean m426a(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        if (!C0454h.m952h()) {
            return false;
        }
        try {
            URL url = new URL(C0468j.f903e);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("e0");
            stringBuffer.append("=");
            stringBuffer.append(str);
            stringBuffer.append("&");
            stringBuffer.append("e1");
            stringBuffer.append("=");
            stringBuffer.append(str2);
            stringBuffer.append("&");
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(Constants.HTTP_POST);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setConnectTimeout(C0458a.f827b);
            httpURLConnection.setReadTimeout(C0458a.f827b);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(stringBuffer.toString().getBytes());
            outputStream.flush();
            outputStream.close();
            return httpURLConnection.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }

    public void m427b() {
        String str = null;
        try {
            File file = new File((Environment.getExternalStorageDirectory().getPath() + "/traces") + "/error_fs2.dat");
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(280);
                if (1326 == randomAccessFile.readInt()) {
                    String str2;
                    byte[] bArr;
                    randomAccessFile.seek(308);
                    int readInt = randomAccessFile.readInt();
                    if (readInt <= 0 || readInt >= 2048) {
                        str2 = null;
                    } else {
                        bArr = new byte[readInt];
                        randomAccessFile.read(bArr, 0, readInt);
                        str2 = new String(bArr, 0, readInt);
                    }
                    randomAccessFile.seek(600);
                    readInt = randomAccessFile.readInt();
                    if (readInt > 0 && readInt < 2048) {
                        bArr = new byte[readInt];
                        randomAccessFile.read(bArr, 0, readInt);
                        str = new String(bArr, 0, readInt);
                    }
                    if (m426a(str2, str)) {
                        randomAccessFile.seek(280);
                        randomAccessFile.writeInt(12346);
                    }
                }
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        File file;
        String a;
        String encode;
        RandomAccessFile randomAccessFile;
        Object obj;
        File file2 = null;
        this.f379b++;
        if (this.f379b > 2) {
            Process.killProcess(Process.myPid());
            return;
        }
        Object obj2;
        String str;
        File file3;
        if (System.currentTimeMillis() - C0457a.m972b() < 10000 && 7.01f > C0455f.getFrameVersion()) {
            if (System.currentTimeMillis() - C0460c.m989a().m994c() < 40000) {
                file = new File(C0468j.m1029g() + File.separator + C0455f.getJarFileName());
                if (file.exists()) {
                    file.delete();
                }
            } else {
                C0460c.m989a().m993b(System.currentTimeMillis());
            }
        }
        try {
            Object obj3;
            String str2;
            Object obj4;
            a = m424a(th);
            if (a != null) {
                try {
                    if (a.contains("com.baidu.location")) {
                        obj3 = 1;
                        Log.d(C0458a.f826a, "errorhandle = " + a);
                        str2 = C0459b.m980a().m981a(false) + C0332a.m176a().m188d();
                        obj4 = obj3;
                        encode = str2 == null ? Jni.encode(str2) : null;
                        obj2 = obj4;
                        if (obj2 != null) {
                            try {
                                str = Environment.getExternalStorageDirectory().getPath() + "/traces";
                                file = new File(str + "/error_fs2.dat");
                                if (file.exists()) {
                                    file3 = new File(str);
                                    if (!file3.exists()) {
                                        file3.mkdirs();
                                    }
                                    if (file.createNewFile()) {
                                        file2 = file;
                                    }
                                    m425a(file2, encode, a);
                                } else {
                                    randomAccessFile = new RandomAccessFile(file, "rw");
                                    randomAccessFile.seek(300);
                                    if (System.currentTimeMillis() - randomAccessFile.readLong() > LogBuilder.MAX_INTERVAL) {
                                        m425a(file, encode, a);
                                    }
                                    randomAccessFile.close();
                                }
                            } catch (Exception e) {
                            }
                        }
                        Process.killProcess(Process.myPid());
                    }
                } catch (Exception e2) {
                    obj2 = a;
                    obj = file;
                    encode = null;
                    obj2 = null;
                    if (obj2 != null) {
                        str = Environment.getExternalStorageDirectory().getPath() + "/traces";
                        file = new File(str + "/error_fs2.dat");
                        if (file.exists()) {
                            randomAccessFile = new RandomAccessFile(file, "rw");
                            randomAccessFile.seek(300);
                            if (System.currentTimeMillis() - randomAccessFile.readLong() > LogBuilder.MAX_INTERVAL) {
                                m425a(file, encode, a);
                            }
                            randomAccessFile.close();
                        } else {
                            file3 = new File(str);
                            if (file3.exists()) {
                                file3.mkdirs();
                            }
                            if (file.createNewFile()) {
                                file2 = file;
                            }
                            m425a(file2, encode, a);
                        }
                    }
                    Process.killProcess(Process.myPid());
                }
            }
            obj3 = null;
            Log.d(C0458a.f826a, "errorhandle = " + a);
            str2 = C0459b.m980a().m981a(false) + C0332a.m176a().m188d();
            if (str2 == null) {
            }
            obj4 = obj3;
            encode = str2 == null ? Jni.encode(str2) : null;
            obj2 = obj4;
        } catch (Exception e3) {
            file = null;
            obj = file;
            encode = null;
            obj2 = null;
            if (obj2 != null) {
                str = Environment.getExternalStorageDirectory().getPath() + "/traces";
                file = new File(str + "/error_fs2.dat");
                if (file.exists()) {
                    file3 = new File(str);
                    if (file3.exists()) {
                        file3.mkdirs();
                    }
                    if (file.createNewFile()) {
                        file2 = file;
                    }
                    m425a(file2, encode, a);
                } else {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(300);
                    if (System.currentTimeMillis() - randomAccessFile.readLong() > LogBuilder.MAX_INTERVAL) {
                        m425a(file, encode, a);
                    }
                    randomAccessFile.close();
                }
            }
            Process.killProcess(Process.myPid());
        }
        if (obj2 != null) {
            str = Environment.getExternalStorageDirectory().getPath() + "/traces";
            file = new File(str + "/error_fs2.dat");
            if (file.exists()) {
                randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(300);
                if (System.currentTimeMillis() - randomAccessFile.readLong() > LogBuilder.MAX_INTERVAL) {
                    m425a(file, encode, a);
                }
                randomAccessFile.close();
            } else {
                file3 = new File(str);
                if (file3.exists()) {
                    file3.mkdirs();
                }
                if (file.createNewFile()) {
                    file2 = file;
                }
                m425a(file2, encode, a);
            }
        }
        Process.killProcess(Process.myPid());
    }
}
