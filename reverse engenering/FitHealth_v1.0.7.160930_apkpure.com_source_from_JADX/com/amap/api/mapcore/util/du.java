package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.amap.api.mapcore.util.fr.C0263a;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;

/* compiled from: SDKCoordinatorDownload */
public class du extends Thread implements C0263a {
    private static String f4172h = "sodownload";
    private static String f4173i = "sofail";
    private fr f4174a = new fr(this.f4175b);
    private C1599a f4175b;
    private RandomAccessFile f4176c;
    private String f4177d;
    private String f4178e;
    private String f4179f;
    private Context f4180g;

    /* compiled from: SDKCoordinatorDownload */
    private static class C1599a extends fw {
        private String f4171a;

        C1599a(String str) {
            this.f4171a = str;
        }

        public Map<String, String> mo1632c() {
            return null;
        }

        public Map<String, String> mo1631b() {
            return null;
        }

        public String mo1630a() {
            return this.f4171a;
        }
    }

    public du(Context context, String str, String str2, String str3) {
        this.f4180g = context;
        this.f4179f = str3;
        this.f4177d = m4221a(context, str + "temp.so");
        this.f4178e = m4221a(context, "libwgs2gcj.so");
        this.f4175b = new C1599a(str2);
    }

    public static String m4221a(Context context, String str) {
        return context.getFilesDir().getAbsolutePath() + File.separator + "libso" + File.separator + str;
    }

    private static String m4222b(Context context, String str) {
        return m4221a(context, str);
    }

    public void m4224a() {
        if (this.f4175b != null && !TextUtils.isEmpty(this.f4175b.mo1630a()) && this.f4175b.mo1630a().contains("libJni_wgs2gcj.so") && this.f4175b.mo1630a().contains(Build.CPU_ABI) && !new File(this.f4178e).exists()) {
            start();
        }
    }

    public void run() {
        try {
            File file = new File(m4222b(this.f4180g, "tempfile"));
            if (file.exists()) {
                file.delete();
            }
            this.f4174a.m958a(this);
        } catch (Throwable th) {
            eb.m742a(th, "SDKCoordinatorDownload", "run");
            m4223b();
        }
    }

    public void mo1625a(byte[] bArr, long j) {
        try {
            if (this.f4176c == null) {
                File file = new File(this.f4177d);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.f4176c = new RandomAccessFile(file, "rw");
            }
        } catch (Throwable e) {
            eb.m742a(e, "SDKCoordinatorDownload", "onDownload");
            m4223b();
        } catch (Throwable e2) {
            m4223b();
            eb.m742a(e2, "SDKCoordinatorDownload", "onDownload");
            return;
        }
        try {
            this.f4176c.seek(j);
            this.f4176c.write(bArr);
        } catch (Throwable e22) {
            m4223b();
            eb.m742a(e22, "SDKCoordinatorDownload", "onDownload");
        }
    }

    public void mo1626d() {
        m4223b();
    }

    public void mo1627e() {
        try {
            if (this.f4176c != null) {
                this.f4176c.close();
            }
            String a = ds.m678a(this.f4177d);
            if (a == null || !a.equalsIgnoreCase(this.f4179f)) {
                m4223b();
            } else if (new File(this.f4178e).exists()) {
                m4223b();
            } else {
                new File(this.f4177d).renameTo(new File(this.f4178e));
            }
        } catch (Throwable th) {
            m4223b();
            File file = new File(this.f4178e);
            if (file.exists()) {
                file.delete();
            }
            eb.m742a(th, "SDKCoordinatorDownload", "onFinish");
        }
    }

    public void mo1624a(Throwable th) {
        try {
            if (this.f4176c != null) {
                this.f4176c.close();
            }
            m4223b();
            File file = new File(m4222b(this.f4180g, "tempfile"));
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                file.createNewFile();
            }
        } catch (Throwable th2) {
            eb.m742a(th2, "SDKCoordinatorDownload", "onException");
        }
    }

    private void m4223b() {
        File file = new File(this.f4177d);
        if (file.exists()) {
            file.delete();
        }
    }
}
