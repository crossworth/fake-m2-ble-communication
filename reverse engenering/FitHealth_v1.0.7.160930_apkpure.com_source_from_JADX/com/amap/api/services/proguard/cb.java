package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Build.VERSION;
import com.amap.api.services.proguard.cd.C0374a;
import com.amap.api.services.proguard.cg.C0375a;
import com.amap.api.services.proguard.cs.C0385a;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;

/* compiled from: DexDownLoad */
public class cb extends Thread implements C0385a {
    private cc f4361a;
    private cs f4362b;
    private ba f4363c;
    private String f4364d;
    private RandomAccessFile f4365e;
    private String f4366f;
    private Context f4367g;
    private String f4368h;
    private String f4369i;
    private String f4370j;
    private String f4371k;
    private int f4372l;
    private int f4373m;

    public cb(Context context, cc ccVar, ba baVar) {
        try {
            this.f4367g = context.getApplicationContext();
            this.f4363c = baVar;
            if (ccVar != null) {
                this.f4361a = ccVar;
                this.f4362b = new cs(new ck(this.f4361a));
                String[] split = this.f4361a.m1441a().split("/");
                this.f4366f = split[split.length - 1];
                split = this.f4366f.split("_");
                this.f4368h = split[0];
                this.f4369i = split[2];
                this.f4370j = split[1];
                this.f4372l = Integer.parseInt(split[3]);
                this.f4373m = Integer.parseInt(split[4].split("\\.")[0]);
                this.f4371k = ccVar.m1442b();
                this.f4364d = cd.m1449a(context, this.f4366f);
            }
        } catch (Throwable th) {
            be.m1340a(th, "DexDownLoad", "DexDownLoad");
        }
    }

    public void m4464a() {
        try {
            start();
        } catch (Throwable th) {
            be.m1340a(th, "DexDownLoad", "startDownload");
        }
    }

    public void run() {
        try {
            if (m4463g()) {
                this.f4362b.m1554a(this);
            }
        } catch (Throwable th) {
            be.m1340a(th, "DexDownLoad", "run");
        }
    }

    private boolean m4459a(bn bnVar, cg cgVar, String str, String str2, String str3, String str4) {
        if ("errorstatus".equals(cgVar.m1481e())) {
            if (!new File(cd.m1459b(this.f4367g, this.f4363c.m1308a(), this.f4363c.m1309b())).exists()) {
                cd.m1448a(this.f4367g, bnVar, this.f4363c);
                try {
                    ca.m1437a().m1440b(this.f4367g, this.f4363c);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        } else if (!new File(this.f4364d).exists()) {
            return false;
        } else {
            List b = bnVar.m1405b(cg.m1473a(cd.m1450a(this.f4367g, str, str2), str, str2, str3), cg.class);
            if (b != null && b.size() > 0) {
                return true;
            }
            try {
                cd.m1452a(this.f4367g, bnVar, this.f4363c, new C0375a(cd.m1450a(this.f4367g, str, this.f4363c.m1309b()), str4, str, str2, str3).m1469a("usedex").m1470a(), this.f4364d);
                ca.m1437a().m1440b(this.f4367g, this.f4363c);
            } catch (Throwable e2) {
                be.m1340a(e2, "DexDownLoad", "processDownloadedFile()");
            } catch (Throwable e22) {
                be.m1340a(e22, "DexDownLoad", "processDownloadedFile()");
            } catch (Throwable e222) {
                be.m1340a(e222, "DexDownLoad", "processDownloadedFile()");
            }
            return true;
        }
    }

    private boolean m4460d() {
        bn bnVar = new bn(this.f4367g, cf.m4469c());
        try {
            List a = C0374a.m1445a(bnVar, this.f4368h, "usedex");
            if (a != null && a.size() > 0 && ci.m1483a(((cg) a.get(0)).m1480d(), this.f4370j) > 0) {
                return true;
            }
        } catch (Throwable th) {
            be.m1340a(th, "DexDownLoad", "isDownloaded()");
        }
        cg a2 = C0374a.m1444a(bnVar, this.f4366f);
        return a2 != null ? m4459a(bnVar, a2, this.f4368h, this.f4369i, this.f4370j, this.f4371k) : false;
    }

    private boolean m4461e() {
        if (this.f4363c != null && this.f4363c.m1308a().equals(this.f4368h) && this.f4363c.m1309b().equals(this.f4369i)) {
            return true;
        }
        return false;
    }

    private boolean m4462f() {
        return VERSION.SDK_INT >= this.f4373m && VERSION.SDK_INT <= this.f4372l;
    }

    private boolean m4458a(Context context) {
        return aw.m1257m(context) == 1;
    }

    private boolean m4463g() {
        try {
            if (!m4461e() || m4460d() || !m4462f() || !m4458a(this.f4367g)) {
                return false;
            }
            m4457a(this.f4363c.m1308a());
            return true;
        } catch (Throwable th) {
            be.m1340a(th, "DexDownLoad", "isNeedDownload()");
            return false;
        }
    }

    private void m4457a(String str) {
        bn bnVar = new bn(this.f4367g, cf.m4469c());
        List a = C0374a.m1445a(bnVar, str, "copy");
        cd.m1455a(a);
        if (a != null && a.size() > 1) {
            int size = a.size();
            for (int i = 1; i < size; i++) {
                cd.m1460b(this.f4367g, bnVar, ((cg) a.get(i)).m1476a());
            }
        }
    }

    public void mo1772a(byte[] bArr, long j) {
        try {
            if (this.f4365e == null) {
                File file = new File(this.f4364d);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                this.f4365e = new RandomAccessFile(file, "rw");
            }
        } catch (Throwable e) {
            be.m1340a(e, "DexDownLoad", "onDownload()");
        } catch (Throwable e2) {
            be.m1340a(e2, "DexDownLoad", "onDownload()");
            return;
        }
        try {
            this.f4365e.seek(j);
            this.f4365e.write(bArr);
        } catch (Throwable e22) {
            be.m1340a(e22, "DexDownLoad", "onDownload()");
        }
    }

    public void mo1771a(Throwable th) {
        try {
            if (this.f4365e != null) {
                this.f4365e.close();
            }
        } catch (Throwable e) {
            be.m1340a(e, "DexDownLoad", "onException()");
        } catch (Throwable e2) {
            be.m1340a(e2, "DexDownLoad", "onException()");
        }
    }

    public void mo1773b() {
        try {
            if (this.f4365e != null) {
                try {
                    this.f4365e.close();
                } catch (Throwable e) {
                    be.m1340a(e, "DexDownLoad", "onFinish()");
                }
                String b = this.f4361a.m1442b();
                if (cd.m1458a(this.f4364d, b)) {
                    String c = this.f4361a.m1443c();
                    bn bnVar = new bn(this.f4367g, cf.m4469c());
                    C0374a.m1446a(bnVar, new C0375a(this.f4366f, b, this.f4368h, c, this.f4370j).m1469a("copy").m1470a(), cg.m1473a(this.f4366f, this.f4368h, c, this.f4370j));
                    cd.m1452a(this.f4367g, bnVar, this.f4363c, new C0375a(cd.m1450a(this.f4367g, this.f4368h, this.f4363c.m1309b()), b, this.f4368h, c, this.f4370j).m1469a("usedex").m1470a(), this.f4364d);
                    ca.m1437a().m1440b(this.f4367g, this.f4363c);
                    return;
                }
                try {
                    new File(this.f4364d).delete();
                } catch (Throwable e2) {
                    be.m1340a(e2, "DexDownLoad", "onFinish()");
                }
            }
        } catch (Throwable e22) {
            be.m1340a(e22, "DexDownLoad", "onFinish()");
        } catch (Throwable e222) {
            be.m1340a(e222, "DexDownLoad", "onFinish()");
        } catch (Throwable e2222) {
            be.m1340a(e2222, "DexDownLoad", "onFinish()");
        }
    }

    public void mo1774c() {
    }
}
