package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.amap.api.maps.AMapException;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapProvince;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;

/* compiled from: OfflineDownloadManager */
public class bi {
    public static String f255a = "";
    public static boolean f256b = false;
    public static String f257d = "";
    private static volatile bi f258j;
    CopyOnWriteArrayList<bg> f259c = new CopyOnWriteArrayList();
    C0209b f260e = null;
    public bm f261f;
    bo f262g;
    bl f263h = null;
    private Context f264i;
    private C0208a f265k;
    private br f266l;
    private bx f267m;
    private ExecutorService f268n = null;
    private ExecutorService f269o = null;

    /* compiled from: OfflineDownloadManager */
    public interface C0208a {
        void mo1672a(bg bgVar);

        void mo1673b(bg bgVar);

        void mo1674c(bg bgVar);
    }

    /* compiled from: OfflineDownloadManager */
    class C0209b extends Handler {
        final /* synthetic */ bi f254a;

        public C0209b(bi biVar, Looper looper) {
            this.f254a = biVar;
            super(looper);
        }

        public void handleMessage(Message message) {
            try {
                message.getData();
                Object obj = message.obj;
                if (obj instanceof bg) {
                    bg bgVar = (bg) obj;
                    cf.m424a("OfflineMapHandler handleMessage CitObj  name: " + bgVar.getCity() + " complete: " + bgVar.getcompleteCode() + " status: " + bgVar.getState());
                    if (this.f254a.f265k != null) {
                        this.f254a.f265k.mo1672a(bgVar);
                        return;
                    }
                    return;
                }
                cf.m424a("Do not callback by CityObject! ");
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private bi(Context context) {
        this.f264i = context;
        m282f();
    }

    public static bi m277a(Context context) {
        if (f258j == null) {
            synchronized (bi.class) {
                if (f258j == null && !f256b) {
                    f258j = new bi(context.getApplicationContext());
                }
            }
        }
        return f258j;
    }

    private void m282f() {
        this.f267m = bx.m366a(this.f264i.getApplicationContext());
        this.f260e = new C0209b(this, this.f264i.getMainLooper());
        this.f261f = new bm(this.f264i, this.f260e);
        this.f266l = br.m340a(1);
        f255a = dj.m589b(this.f264i);
        m285g();
        this.f263h = new bl(this.f264i);
        this.f263h.start();
        Iterator it = this.f261f.m323a().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((OfflineMapProvince) it.next()).getCityList().iterator();
            while (it2.hasNext()) {
                this.f259c.add(new bg(this.f264i, (OfflineMapCity) it2.next()));
            }
        }
        m287h();
    }

    private void m285g() {
        if (!dj.m589b(this.f264i).equals("")) {
            String c;
            File file = new File(dj.m589b(this.f264i) + "offlinemapv4.png");
            if (file.exists()) {
                c = cf.m430c(file);
            } else {
                c = cf.m423a(this.f264i, "offlinemapv4.png");
            }
            if (c != null) {
                try {
                    m283f(c);
                } catch (Throwable e) {
                    ee.m4243a(e, "MapDownloadManager", "paseJson io");
                    e.printStackTrace();
                }
            }
        }
    }

    private void m283f(String str) throws JSONException {
        List b = cf.m427b(str);
        if (b != null && b.size() != 0) {
            this.f261f.m325a(b);
        }
    }

    private void m287h() {
        Iterator it = this.f267m.m371a().iterator();
        while (it.hasNext()) {
            bs bsVar = (bs) it.next();
            if (!(bsVar == null || bsVar.m360e() == null || bsVar.m362g().length() < 1)) {
                if (!(bsVar.l == 4 || bsVar.l == 7 || bsVar.l < 0)) {
                    bsVar.l = 3;
                }
                bg g = m284g(bsVar.m360e());
                if (g != null) {
                    String f = bsVar.m361f();
                    if (f == null || f.equals(f257d)) {
                        g.m5697a(bsVar.l);
                        g.setCompleteCode(bsVar.m365j());
                    } else {
                        this.f267m.m378c(bsVar.m362g());
                        g.m5697a(7);
                    }
                    List<String> a = this.f267m.m372a(bsVar.m362g());
                    StringBuffer stringBuffer = new StringBuffer();
                    for (String append : a) {
                        stringBuffer.append(append);
                        stringBuffer.append(";");
                    }
                    g.m5703a(stringBuffer.toString());
                    this.f261f.m324a(g);
                }
            }
        }
    }

    public void m293a(ArrayList<bs> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            bs bsVar = (bs) it.next();
            bg g = m284g(bsVar.m360e());
            if (g != null) {
                g.m5700a(bsVar);
                m298c(g);
            }
        }
    }

    public void m292a(final String str) {
        if (str != null) {
            if (this.f268n == null) {
                this.f268n = Executors.newSingleThreadExecutor();
            }
            this.f268n.execute(new Runnable(this) {
                final /* synthetic */ bi f251b;

                public void run() {
                    bg a = this.f251b.m284g(str);
                    try {
                        if (a.m5706c().equals(a.f5346f)) {
                            String adcode = a.getAdcode();
                            if (adcode.length() > 0) {
                                adcode = this.f251b.f267m.m380e(adcode);
                                if (bi.f257d.length() > 0 && !adcode.equals(bi.f257d)) {
                                    a.m5712i();
                                    this.f251b.f265k.mo1673b(a);
                                    return;
                                }
                            }
                            this.f251b.m288i();
                            bj bjVar = (bj) new bk(this.f251b.f264i, bi.f257d).m4188d();
                            if (this.f251b.f265k != null) {
                                if (bjVar == null) {
                                    this.f251b.f265k.mo1673b(a);
                                    return;
                                } else if (bjVar.m308a()) {
                                    this.f251b.m289a();
                                }
                            }
                            this.f251b.f265k.mo1673b(a);
                        }
                    } catch (Exception e) {
                    } finally {
                        this.f251b.f265k.mo1673b(a);
                    }
                }
            });
        } else if (this.f265k != null) {
            this.f265k.mo1673b(null);
        }
    }

    private void m288i() throws AMapException {
        if (!dj.m595c(this.f264i)) {
            throw new AMapException("http连接失败 - ConnectionException");
        }
    }

    protected void m289a() throws AMapException {
        bp bpVar = new bp(this.f264i, "");
        bpVar.m5738a(this.f264i);
        List list = (List) bpVar.m4188d();
        if (this.f259c != null) {
            this.f261f.m325a(list);
        }
        Iterator it = this.f259c.iterator();
        while (it.hasNext()) {
            bg bgVar = (bg) it.next();
            String version = bgVar.getVersion();
            if (bgVar.getState() == 4 && f257d.length() > 0 && !version.equals(f257d)) {
                bgVar.m5712i();
            }
        }
    }

    public boolean m296b(String str) {
        if (m284g(str) == null) {
            return false;
        }
        return true;
    }

    public void m299c(String str) {
        bg g = m284g(str);
        if (g != null) {
            m301d(g);
            m290a(g);
        } else if (this.f265k != null) {
            this.f265k.mo1674c(g);
        }
    }

    public void m290a(final bg bgVar) {
        if (this.f262g == null) {
            this.f262g = new bo(this.f264i);
        }
        if (this.f269o == null) {
            this.f269o = Executors.newSingleThreadExecutor();
        }
        this.f269o.execute(new Runnable(this) {
            final /* synthetic */ bi f253b;

            public void run() {
                if (bgVar.m5706c().equals(bgVar.f5341a)) {
                    this.f253b.f265k.mo1674c(bgVar);
                } else if (bgVar.getState() == 7 || bgVar.getState() == -1) {
                    this.f253b.f262g.m338a(bgVar);
                } else {
                    this.f253b.f262g.m338a(bgVar);
                    this.f253b.f265k.mo1674c(bgVar);
                }
            }
        });
    }

    public void m295b(bg bgVar) {
        try {
            this.f266l.m344a(bgVar, this.f264i, null);
        } catch (dk e) {
            e.printStackTrace();
        }
    }

    public void m298c(bg bgVar) {
        this.f261f.m324a(bgVar);
        Message obtainMessage = this.f260e.obtainMessage();
        obtainMessage.obj = bgVar;
        this.f260e.sendMessage(obtainMessage);
    }

    public void m294b() {
        Iterator it = this.f259c.iterator();
        while (it.hasNext()) {
            bg bgVar = (bg) it.next();
            if (bgVar.m5706c().equals(bgVar.f5343c) || bgVar.m5706c().equals(bgVar.f5342b)) {
                bgVar.m5709f();
            }
        }
    }

    public void m297c() {
        Iterator it = this.f259c.iterator();
        while (it.hasNext()) {
            bg bgVar = (bg) it.next();
            if (bgVar.m5706c().equals(bgVar.f5343c)) {
                bgVar.m5709f();
                return;
            }
        }
    }

    public void m300d() {
        if (!(this.f268n == null || this.f268n.isShutdown())) {
            this.f268n.shutdownNow();
        }
        if (this.f263h != null) {
            if (this.f263h.isAlive()) {
                this.f263h.interrupt();
            }
            this.f263h = null;
        }
        this.f266l.m345b();
        this.f261f.m334g();
        m303e();
        f258j = null;
        f256b = true;
    }

    private bg m284g(String str) {
        if (str == null || str.length() < 1) {
            return null;
        }
        Iterator it = this.f259c.iterator();
        while (it.hasNext()) {
            bg bgVar = (bg) it.next();
            if (str.equals(bgVar.getCity())) {
                return bgVar;
            }
        }
        return null;
    }

    private bg m286h(String str) {
        if (str == null || str.length() < 1) {
            return null;
        }
        Iterator it = this.f259c.iterator();
        while (it.hasNext()) {
            bg bgVar = (bg) it.next();
            if (str.equals(bgVar.getCode())) {
                return bgVar;
            }
        }
        return null;
    }

    public void m302d(String str) throws AMapException {
        bg g = m284g(str);
        if (g != null) {
            g.setVersion(f257d);
            g.m5709f();
            return;
        }
        throw new AMapException("无效的参数 - IllegalArgumentException");
    }

    public void m305e(String str) throws AMapException {
        bg h = m286h(str);
        if (h != null) {
            h.m5709f();
            return;
        }
        throw new AMapException("无效的参数 - IllegalArgumentException");
    }

    public void m301d(bg bgVar) {
        this.f266l.m343a((bq) bgVar);
    }

    public void m304e(bg bgVar) {
        this.f266l.m346b(bgVar);
    }

    public void m291a(C0208a c0208a) {
        this.f265k = c0208a;
    }

    public void m303e() {
        this.f265k = null;
    }
}
