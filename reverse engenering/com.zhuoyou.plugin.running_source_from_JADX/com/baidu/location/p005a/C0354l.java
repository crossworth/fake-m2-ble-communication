package com.baidu.location.p005a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import com.baidu.location.C0455f;
import com.baidu.location.Jni;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0459b;
import com.baidu.location.p006h.C0460c;
import com.baidu.location.p006h.C0467i;
import com.baidu.location.p006h.C0468j;
import com.baidu.location.p012f.C0443b;
import com.baidu.location.p012f.C0444c;
import com.baidu.location.p012f.C0454h;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class C0354l extends C0335e {
    private static C0354l f285p = null;
    String f286a;
    String f287b;
    String f288c;
    String f289d;
    int f290e;
    Handler f291f;

    private C0354l() {
        this.f286a = null;
        this.f287b = null;
        this.f288c = null;
        this.f289d = null;
        this.f290e = 1;
        this.f291f = null;
        this.f291f = new Handler();
    }

    public static void m322a(File file, File file2) throws IOException {
        BufferedOutputStream bufferedOutputStream;
        Throwable th;
        BufferedInputStream bufferedInputStream = null;
        try {
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
            try {
                bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
            } catch (Throwable th2) {
                th = th2;
                bufferedOutputStream = null;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                throw th;
            }
            try {
                byte[] bArr = new byte[5120];
                while (true) {
                    int read = bufferedInputStream2.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    bufferedOutputStream.write(bArr, 0, read);
                }
                bufferedOutputStream.flush();
                file.delete();
                if (bufferedInputStream2 != null) {
                    bufferedInputStream2.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream = bufferedInputStream2;
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream != null) {
                    bufferedOutputStream.close();
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            bufferedOutputStream = null;
            if (bufferedInputStream != null) {
                bufferedInputStream.close();
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
            throw th;
        }
    }

    private boolean m323a(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 0) {
                String a = C0444c.m876a(C0443b.m855a().m872e());
                if (a.equals("3G") || a.equals("4G")) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean m325a(String str, String str2) {
        File file = new File(C0468j.m1029g() + File.separator + "tmp");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            while (true) {
                int read = bufferedInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            httpURLConnection.disconnect();
            fileOutputStream.close();
            if (file.length() < 10240) {
                file.delete();
                return false;
            }
            file.renameTo(new File(C0468j.m1029g() + File.separator + str2));
            return true;
        } catch (Exception e) {
            file.delete();
            return false;
        }
    }

    public static C0354l m326b() {
        if (f285p == null) {
            f285p = new C0354l();
        }
        return f285p;
    }

    private Handler m329f() {
        return this.f291f;
    }

    private void m330g() {
        try {
            RandomAccessFile randomAccessFile;
            File file = new File(C0468j.m1029g() + "/grtcfrsa.dat");
            if (!file.exists()) {
                File file2 = new File(C0467i.f869a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(2);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.seek(8);
                    byte[] bytes = "1980_01_01:0".getBytes();
                    randomAccessFile.writeInt(bytes.length);
                    randomAccessFile.write(bytes);
                    randomAccessFile.seek(200);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.seek(800);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.close();
                } else {
                    return;
                }
            }
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(200);
            randomAccessFile.writeBoolean(true);
            if (this.f290e == 1) {
                randomAccessFile.writeBoolean(true);
            } else {
                randomAccessFile.writeBoolean(false);
            }
            if (this.f289d != null) {
                byte[] bytes2 = this.f289d.getBytes();
                randomAccessFile.writeInt(bytes2.length);
                randomAccessFile.write(bytes2);
            } else if (Math.abs(C0455f.getFrameVersion() - 7.01f) < 1.0E-8f) {
                randomAccessFile.writeInt(0);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    private void m331h() {
        if (this.f286a != null && C0454h.m952h()) {
            new C0358p(this).start();
        }
    }

    private boolean m332i() {
        return (this.f288c == null || new File(C0468j.m1029g() + File.separator + this.f288c).exists()) ? true : C0354l.m325a("http://" + this.f286a + "/" + this.f288c, this.f288c);
    }

    private void m333j() {
        if (this.f287b != null) {
            File file = new File(C0468j.m1029g() + File.separator + this.f287b);
            if (!file.exists() && C0354l.m325a("http://" + this.f286a + "/" + this.f287b, this.f287b)) {
                String a = C0468j.m1014a(file, "SHA-256");
                if (this.f289d != null && a != null && C0468j.m1021b(a, this.f289d, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiP7BS5IjEOzrKGR9/Ww9oSDhdX1ir26VOsYjT1T6tk2XumRpkHRwZbrucDcNnvSB4QsqiEJnvTSRi7YMbh2H9sLMkcvHlMV5jAErNvnuskWfcvf7T2mq7EUZI/Hf4oVZhHV0hQJRFVdTcjWI6q2uaaKM3VMh+roDesiE7CR2biQIDAQAB")) {
                    File file2 = new File(C0468j.m1029g() + File.separator + C0455f.replaceFileName);
                    if (file2.exists()) {
                        file2.delete();
                    }
                    try {
                        C0354l.m322a(file, file2);
                    } catch (Exception e) {
                        file2.delete();
                    }
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    public void mo1741a() {
        int i = 0;
        StringBuffer stringBuffer = new StringBuffer(128);
        stringBuffer.append("&sdk=");
        stringBuffer.append(7.01f);
        stringBuffer.append("&fw=");
        stringBuffer.append(C0455f.getFrameVersion());
        stringBuffer.append("&suit=");
        stringBuffer.append(2);
        if (C0459b.m980a().f841b == null) {
            stringBuffer.append("&im=");
            stringBuffer.append(C0459b.m980a().f840a);
        } else {
            stringBuffer.append("&cu=");
            stringBuffer.append(C0459b.m980a().f841b);
        }
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append("&sv=");
        String str = VERSION.RELEASE;
        if (str != null && str.length() > 10) {
            str = str.substring(0, 10);
        }
        stringBuffer.append(str);
        try {
            if (VERSION.SDK_INT > 20) {
                String[] strArr = Build.SUPPORTED_ABIS;
                str = null;
                while (i < strArr.length) {
                    str = i == 0 ? strArr[i] + ";" : str + strArr[i] + ";";
                    i++;
                }
            } else {
                str = Build.CPU_ABI2;
            }
        } catch (Error e) {
            str = null;
        } catch (Exception e2) {
            str = null;
        }
        if (str != null) {
            stringBuffer.append("&cpuabi=");
            stringBuffer.append(str);
        }
        stringBuffer.append("&pack=");
        stringBuffer.append(C0459b.f835d);
        this.h = C0468j.m1024d() + "?&it=" + Jni.en1(stringBuffer.toString());
    }

    public void mo1742a(boolean z) {
        if (z) {
            try {
                JSONObject jSONObject = new JSONObject(this.j);
                if ("up".equals(jSONObject.getString("res"))) {
                    this.f286a = jSONObject.getString("upath");
                    if (jSONObject.has("u1")) {
                        this.f287b = jSONObject.getString("u1");
                    }
                    if (jSONObject.has("u2")) {
                        this.f288c = jSONObject.getString("u2");
                    }
                    if (jSONObject.has("u1_rsa")) {
                        this.f289d = jSONObject.getString("u1_rsa");
                    }
                    m329f().post(new C0357o(this));
                }
                if (jSONObject.has("ison")) {
                    this.f290e = jSONObject.getInt("ison");
                }
                m330g();
            } catch (Exception e) {
            }
        }
        C0460c.m989a().m991a(System.currentTimeMillis());
    }

    public void mo1746c() {
        if (System.currentTimeMillis() - C0460c.m989a().m992b() > LogBuilder.MAX_INTERVAL) {
            m329f().postDelayed(new C0355m(this), 10000);
            m329f().postDelayed(new C0356n(this), 5000);
        }
    }
}
