package com.baidu.mapapi.map.offline;

import com.baidu.mapapi.BMapManager;
import com.baidu.platform.comapi.map.C0646s;
import com.baidu.platform.comapi.map.C0647t;
import com.baidu.platform.comapi.map.C0650w;
import java.util.ArrayList;
import java.util.Iterator;

public class MKOfflineMap {
    public static final int TYPE_DOWNLOAD_UPDATE = 0;
    public static final int TYPE_NETWORK_ERROR = 2;
    public static final int TYPE_NEW_OFFLINE = 6;
    public static final int TYPE_VER_UPDATE = 4;
    private static final String f1438a = MKOfflineMap.class.getSimpleName();
    private C0647t f1439b;
    private MKOfflineMapListener f1440c;

    public void destroy() {
        this.f1439b.m2077d(0);
        this.f1439b.m2072b(null);
        this.f1439b.m2071b();
        BMapManager.destroy();
    }

    public ArrayList<MKOLUpdateElement> getAllUpdateInfo() {
        ArrayList e = this.f1439b.m2078e();
        if (e == null) {
            return null;
        }
        ArrayList<MKOLUpdateElement> arrayList = new ArrayList();
        Iterator it = e.iterator();
        while (it.hasNext()) {
            arrayList.add(OfflineMapUtil.getUpdatElementFromLocalMapElement(((C0650w) it.next()).m2082a()));
        }
        return arrayList;
    }

    public ArrayList<MKOLSearchRecord> getHotCityList() {
        ArrayList c = this.f1439b.m2074c();
        if (c == null) {
            return null;
        }
        ArrayList<MKOLSearchRecord> arrayList = new ArrayList();
        Iterator it = c.iterator();
        while (it.hasNext()) {
            arrayList.add(OfflineMapUtil.getSearchRecordFromLocalCityInfo((C0646s) it.next()));
        }
        return arrayList;
    }

    public ArrayList<MKOLSearchRecord> getOfflineCityList() {
        ArrayList d = this.f1439b.m2076d();
        if (d == null) {
            return null;
        }
        ArrayList<MKOLSearchRecord> arrayList = new ArrayList();
        Iterator it = d.iterator();
        while (it.hasNext()) {
            arrayList.add(OfflineMapUtil.getSearchRecordFromLocalCityInfo((C0646s) it.next()));
        }
        return arrayList;
    }

    public MKOLUpdateElement getUpdateInfo(int i) {
        C0650w g = this.f1439b.m2081g(i);
        return g == null ? null : OfflineMapUtil.getUpdatElementFromLocalMapElement(g.m2082a());
    }

    @Deprecated
    public int importOfflineData() {
        return importOfflineData(false);
    }

    @Deprecated
    public int importOfflineData(boolean z) {
        int i;
        int i2 = 0;
        ArrayList e = this.f1439b.m2078e();
        if (e != null) {
            i2 = e.size();
            i = i2;
        } else {
            i = 0;
        }
        this.f1439b.m2070a(z, true);
        ArrayList e2 = this.f1439b.m2078e();
        if (e2 != null) {
            i2 = e2.size();
        }
        return i2 - i;
    }

    public boolean init(MKOfflineMapListener mKOfflineMapListener) {
        BMapManager.init();
        this.f1439b = C0647t.m2062a();
        if (this.f1439b == null) {
            return false;
        }
        this.f1439b.m2068a(new C0500a(this));
        this.f1440c = mKOfflineMapListener;
        return true;
    }

    public boolean pause(int i) {
        return this.f1439b.m2075c(i);
    }

    public boolean remove(int i) {
        return this.f1439b.m2079e(i);
    }

    public ArrayList<MKOLSearchRecord> searchCity(String str) {
        ArrayList a = this.f1439b.m2067a(str);
        if (a == null) {
            return null;
        }
        ArrayList<MKOLSearchRecord> arrayList = new ArrayList();
        Iterator it = a.iterator();
        while (it.hasNext()) {
            arrayList.add(OfflineMapUtil.getSearchRecordFromLocalCityInfo((C0646s) it.next()));
        }
        return arrayList;
    }

    public boolean start(int i) {
        if (this.f1439b == null) {
            return false;
        }
        if (this.f1439b.m2078e() != null) {
            Iterator it = this.f1439b.m2078e().iterator();
            while (it.hasNext()) {
                C0650w c0650w = (C0650w) it.next();
                if (c0650w.f2132a.f2120a == i) {
                    return (c0650w.f2132a.f2129j || c0650w.f2132a.f2131l == 2 || c0650w.f2132a.f2131l == 3 || c0650w.f2132a.f2131l == 6) ? this.f1439b.m2073b(i) : false;
                }
            }
        }
        return this.f1439b.m2069a(i);
    }

    public boolean update(int i) {
        if (this.f1439b == null) {
            return false;
        }
        if (this.f1439b.m2078e() != null) {
            Iterator it = this.f1439b.m2078e().iterator();
            while (it.hasNext()) {
                C0650w c0650w = (C0650w) it.next();
                if (c0650w.f2132a.f2120a == i) {
                    return c0650w.f2132a.f2129j ? this.f1439b.m2080f(i) : false;
                }
            }
        }
        return false;
    }
}
