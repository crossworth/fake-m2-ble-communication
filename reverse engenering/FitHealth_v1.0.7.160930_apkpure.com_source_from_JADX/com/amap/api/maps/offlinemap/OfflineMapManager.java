package com.amap.api.maps.offlinemap;

import android.content.Context;
import android.os.Handler;
import com.amap.api.mapcore.util.bg;
import com.amap.api.mapcore.util.bi;
import com.amap.api.mapcore.util.bi.C0208a;
import com.amap.api.mapcore.util.bm;
import com.amap.api.mapcore.util.dj;
import com.amap.api.mapcore.util.ee;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapException;
import java.util.ArrayList;
import java.util.Iterator;

public final class OfflineMapManager {
    bm f992a;
    bi f993b;
    private Context f994c;
    private AMap f995d;
    private OfflineMapDownloadListener f996e;
    private Handler f997f = new Handler();
    private Handler f998g = new Handler();

    public interface OfflineMapDownloadListener {
        void onCheckUpdate(boolean z, String str);

        void onDownload(int i, int i2, String str);

        void onRemove(boolean z, String str, String str2);
    }

    class C16071 implements C0208a {
        final /* synthetic */ OfflineMapManager f4247a;

        C16071(OfflineMapManager offlineMapManager) {
            this.f4247a = offlineMapManager;
        }

        public void mo1672a(final bg bgVar) {
            if (!(this.f4247a.f996e == null || bgVar == null)) {
                this.f4247a.f997f.post(new Runnable(this) {
                    final /* synthetic */ C16071 f983b;

                    public void run() {
                        this.f983b.f4247a.f996e.onDownload(bgVar.m5706c().m439b(), bgVar.getcompleteCode(), bgVar.getCity());
                    }
                });
            }
            if (this.f4247a.f995d != null && bgVar.m5706c().m438a(bgVar.f5346f)) {
                this.f4247a.f995d.setLoadOfflineData(false);
                this.f4247a.f995d.setLoadOfflineData(true);
            }
        }

        public void mo1673b(final bg bgVar) {
            if (this.f4247a.f996e != null && bgVar != null) {
                this.f4247a.f997f.post(new Runnable(this) {
                    final /* synthetic */ C16071 f985b;

                    public void run() {
                        if (bgVar.m5706c().equals(bgVar.f5347g) || bgVar.m5706c().equals(bgVar.f5341a)) {
                            this.f985b.f4247a.f996e.onCheckUpdate(true, bgVar.getCity());
                        } else {
                            this.f985b.f4247a.f996e.onCheckUpdate(false, bgVar.getCity());
                        }
                    }
                });
            }
        }

        public void mo1674c(final bg bgVar) {
            if (this.f4247a.f996e != null && bgVar != null) {
                this.f4247a.f997f.post(new Runnable(this) {
                    final /* synthetic */ C16071 f987b;

                    public void run() {
                        if (bgVar.m5706c().equals(bgVar.f5341a)) {
                            this.f987b.f4247a.f996e.onRemove(true, bgVar.getCity(), "");
                        } else {
                            this.f987b.f4247a.f996e.onRemove(false, bgVar.getCity(), "");
                        }
                    }
                });
            }
        }
    }

    public OfflineMapManager(Context context, OfflineMapDownloadListener offlineMapDownloadListener) {
        this.f996e = offlineMapDownloadListener;
        m1112a(context);
    }

    public OfflineMapManager(Context context, OfflineMapDownloadListener offlineMapDownloadListener, AMap aMap) {
        this.f996e = offlineMapDownloadListener;
        this.f995d = aMap;
        m1112a(context);
    }

    private void m1112a(Context context) {
        this.f994c = context.getApplicationContext();
        bi.f256b = false;
        this.f993b = bi.m277a(context);
        this.f992a = this.f993b.f261f;
        this.f993b.m291a(new C16071(this));
    }

    public void downloadByCityCode(String str) throws AMapException {
        this.f993b.m305e(str);
    }

    public void downloadByCityName(String str) throws AMapException {
        this.f993b.m302d(str);
    }

    public void downloadByProvinceName(String str) throws AMapException {
        try {
            m1111a();
            OfflineMapProvince itemByProvinceName = getItemByProvinceName(str);
            if (itemByProvinceName == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
            Iterator it = itemByProvinceName.getCityList().iterator();
            while (it.hasNext()) {
                final String city = ((OfflineMapCity) it.next()).getCity();
                this.f998g.post(new Runnable(this) {
                    final /* synthetic */ OfflineMapManager f989b;

                    public void run() {
                        try {
                            this.f989b.f993b.m302d(city);
                        } catch (Throwable e) {
                            ee.m4243a(e, "OfflineMapManager", "downloadByProvinceName");
                        }
                    }
                });
            }
        } catch (Throwable th) {
            if (th instanceof AMapException) {
                AMapException aMapException = (AMapException) th;
            } else {
                ee.m4243a(th, "OfflineMapManager", "downloadByProvinceName");
            }
        }
    }

    public void remove(String str) {
        if (this.f993b.m296b(str)) {
            this.f993b.m299c(str);
            return;
        }
        OfflineMapProvince c = this.f992a.m329c(str);
        if (c != null && c.getCityList() != null) {
            Iterator it = c.getCityList().iterator();
            while (it.hasNext()) {
                final String city = ((OfflineMapCity) it.next()).getCity();
                this.f998g.post(new Runnable(this) {
                    final /* synthetic */ OfflineMapManager f991b;

                    public void run() {
                        this.f991b.f993b.m299c(city);
                    }
                });
            }
        } else if (this.f996e != null) {
            this.f996e.onRemove(false, str, "没有该城市");
        }
    }

    public ArrayList<OfflineMapProvince> getOfflineMapProvinceList() {
        return this.f992a.m323a();
    }

    public OfflineMapCity getItemByCityCode(String str) {
        return this.f992a.m322a(str);
    }

    public OfflineMapCity getItemByCityName(String str) {
        return this.f992a.m327b(str);
    }

    public OfflineMapProvince getItemByProvinceName(String str) {
        return this.f992a.m329c(str);
    }

    public ArrayList<OfflineMapCity> getOfflineMapCityList() {
        return this.f992a.m328b();
    }

    public ArrayList<OfflineMapCity> getDownloadingCityList() {
        return this.f992a.m332e();
    }

    public ArrayList<OfflineMapProvince> getDownloadingProvinceList() {
        return this.f992a.m333f();
    }

    public ArrayList<OfflineMapCity> getDownloadOfflineMapCityList() {
        return this.f992a.m330c();
    }

    public ArrayList<OfflineMapProvince> getDownloadOfflineMapProvinceList() {
        return this.f992a.m331d();
    }

    private void m1113a(String str, String str2) throws AMapException {
        this.f993b.m292a(str);
    }

    public void updateOfflineCityByCode(String str) throws AMapException {
        OfflineMapCity itemByCityCode = getItemByCityCode(str);
        if (itemByCityCode == null || itemByCityCode.getCity() == null) {
            throw new AMapException("无效的参数 - IllegalArgumentException");
        }
        m1113a(itemByCityCode.getCity(), "cityname");
    }

    public void updateOfflineCityByName(String str) throws AMapException {
        m1113a(str, "cityname");
    }

    public void updateOfflineMapProvinceByName(String str) throws AMapException {
        m1113a(str, "cityname");
    }

    private void m1111a() throws AMapException {
        if (!dj.m595c(this.f994c)) {
            throw new AMapException("http连接失败 - ConnectionException");
        }
    }

    public void restart() {
    }

    public void stop() {
        this.f993b.m294b();
    }

    public void pause() {
        this.f993b.m297c();
    }

    public void destroy() {
        this.f993b.m300d();
        m1115b();
        this.f995d = null;
        this.f997f.removeCallbacksAndMessages(null);
        this.f997f = null;
        this.f998g.removeCallbacksAndMessages(null);
        this.f998g = null;
    }

    private void m1115b() {
        this.f996e = null;
    }
}
