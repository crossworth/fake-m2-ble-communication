package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Build.VERSION;
import com.amap.api.mapcore.util.fa.C0255a;
import com.amap.api.mapcore.util.fd.C0256a;
import com.amap.api.mapcore.util.fr.C0263a;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;

/* compiled from: DexDownLoad */
public class ey extends Thread implements C0263a {
    private ez f4198a;
    private fr f4199b;
    private dv f4200c;
    private String f4201d;
    private RandomAccessFile f4202e;
    private String f4203f;
    private Context f4204g;
    private String f4205h;
    private String f4206i;
    private String f4207j;
    private String f4208k;
    private int f4209l;
    private int f4210m;

    public ey(Context context, ez ezVar, dv dvVar) {
        try {
            this.f4204g = context.getApplicationContext();
            this.f4200c = dvVar;
            if (ezVar != null) {
                this.f4198a = ezVar;
                this.f4199b = new fr(new fh(this.f4198a));
                String[] split = this.f4198a.m844a().split("/");
                this.f4203f = split[split.length - 1];
                split = this.f4203f.split("_");
                this.f4205h = split[0];
                this.f4206i = split[2];
                this.f4207j = split[1];
                this.f4209l = Integer.parseInt(split[3]);
                this.f4210m = Integer.parseInt(split[4].split("\\.")[0]);
                this.f4208k = ezVar.m845b();
                this.f4201d = fa.m852a(context, this.f4203f);
            }
        } catch (Throwable th) {
            eb.m742a(th, "DexDownLoad", "DexDownLoad");
        }
    }

    public void m4271a() {
        try {
            start();
        } catch (Throwable th) {
            eb.m742a(th, "DexDownLoad", "startDownload");
        }
    }

    public void run() {
        try {
            if (m4270g()) {
                this.f4199b.m958a(this);
            }
        } catch (Throwable th) {
            eb.m742a(th, "DexDownLoad", "run");
        }
    }

    private boolean m4266a(ek ekVar, fd fdVar, String str, String str2, String str3, String str4) {
        if ("errorstatus".equals(fdVar.m884e())) {
            if (!new File(fa.m862b(this.f4204g, this.f4200c.m706a(), this.f4200c.m708b())).exists()) {
                fa.m851a(this.f4204g, ekVar, this.f4200c);
                try {
                    ex.m840a().m843b(this.f4204g, this.f4200c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else if (!new File(this.f4201d).exists()) {
            return false;
        } else {
            List b = ekVar.m808b(fd.m876a(fa.m853a(this.f4204g, str, str2), str, str2, str3), fd.class);
            if (b != null && b.size() > 0) {
                return true;
            }
            try {
                fa.m855a(this.f4204g, ekVar, this.f4200c, new C0256a(fa.m853a(this.f4204g, str, this.f4200c.m708b()), str4, str, str2, str3).m872a("usedex").m873a(), this.f4201d);
                ex.m840a().m843b(this.f4204g, this.f4200c);
            } catch (Throwable e2) {
                eb.m742a(e2, "DexDownLoad", "processDownloadedFile()");
            } catch (Throwable e22) {
                eb.m742a(e22, "DexDownLoad", "processDownloadedFile()");
            } catch (Throwable e222) {
                eb.m742a(e222, "DexDownLoad", "processDownloadedFile()");
            }
            return true;
        }
    }

    private boolean m4267b() {
        ek ekVar = new ek(this.f4204g, fc.m4276a());
        try {
            List a = C0255a.m848a(ekVar, this.f4205h, "usedex");
            if (a != null && a.size() > 0 && ff.m886a(((fd) a.get(0)).m883d(), this.f4207j) > 0) {
                return true;
            }
        } catch (Throwable th) {
            eb.m742a(th, "DexDownLoad", "isDownloaded()");
        }
        fd a2 = C0255a.m847a(ekVar, this.f4203f);
        return a2 != null ? m4266a(ekVar, a2, this.f4205h, this.f4206i, this.f4207j, this.f4208k) : false;
    }

    private boolean m4268c() {
        if (this.f4200c != null && this.f4200c.m706a().equals(this.f4205h) && this.f4200c.m708b().equals(this.f4206i)) {
            return true;
        }
        return false;
    }

    private boolean m4269f() {
        return VERSION.SDK_INT >= this.f4210m && VERSION.SDK_INT <= this.f4209l;
    }

    private boolean m4265a(Context context) {
        return dq.m656m(context) == 1;
    }

    private boolean m4270g() {
        try {
            if (!m4268c() || m4267b() || !m4269f() || !m4265a(this.f4204g)) {
                return false;
            }
            m4264a(this.f4200c.m706a());
            return true;
        } catch (Throwable th) {
            eb.m742a(th, "DexDownLoad", "isNeedDownload()");
            return false;
        }
    }

    private void m4264a(String str) {
        ek ekVar = new ek(this.f4204g, fc.m4276a());
        List a = C0255a.m848a(ekVar, str, "copy");
        fa.m858a(a);
        if (a != null && a.size() > 1) {
            int size = a.size();
            for (int i = 1; i < size; i++) {
                fa.m863b(this.f4204g, ekVar, ((fd) a.get(i)).m879a());
            }
        }
    }

    public void mo1625a(byte[] bArr, long j) {
        try {
            if (this.f4202e == null) {
                File file = new File(this.f4201d);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.f4202e = new RandomAccessFile(file, "rw");
            }
        } catch (Throwable e) {
            eb.m742a(e, "DexDownLoad", "onDownload()");
        } catch (Throwable e2) {
            eb.m742a(e2, "DexDownLoad", "onDownload()");
            return;
        }
        try {
            this.f4202e.seek(j);
            this.f4202e.write(bArr);
        } catch (Throwable e22) {
            eb.m742a(e22, "DexDownLoad", "onDownload()");
        }
    }

    public void mo1624a(Throwable th) {
        try {
            if (this.f4202e != null) {
                this.f4202e.close();
            }
        } catch (Throwable e) {
            eb.m742a(e, "DexDownLoad", "onException()");
        } catch (Throwable e2) {
            eb.m742a(e2, "DexDownLoad", "onException()");
        }
    }

    public void mo1627e() {
        try {
            if (this.f4202e != null) {
                try {
                    this.f4202e.close();
                } catch (Throwable e) {
                    eb.m742a(e, "DexDownLoad", "onFinish()");
                }
                String b = this.f4198a.m845b();
                if (fa.m861a(this.f4201d, b)) {
                    String c = this.f4198a.m846c();
                    ek ekVar = new ek(this.f4204g, fc.m4276a());
                    C0255a.m849a(ekVar, new C0256a(this.f4203f, b, this.f4205h, c, this.f4207j).m872a("copy").m873a(), fd.m876a(this.f4203f, this.f4205h, c, this.f4207j));
                    fa.m855a(this.f4204g, ekVar, this.f4200c, new C0256a(fa.m853a(this.f4204g, this.f4205h, this.f4200c.m708b()), b, this.f4205h, c, this.f4207j).m872a("usedex").m873a(), this.f4201d);
                    ex.m840a().m843b(this.f4204g, this.f4200c);
                    return;
                }
                try {
                    new File(this.f4201d).delete();
                } catch (Throwable e2) {
                    eb.m742a(e2, "DexDownLoad", "onFinish()");
                }
            }
        } catch (Throwable e22) {
            eb.m742a(e22, "DexDownLoad", "onFinish()");
        } catch (Throwable e222) {
            eb.m742a(e222, "DexDownLoad", "onFinish()");
        } catch (Throwable e2222) {
            eb.m742a(e2222, "DexDownLoad", "onFinish()");
        }
    }

    public void mo1626d() {
    }
}
