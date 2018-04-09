package com.amap.api.mapcore.util;

import android.content.Context;
import android.os.Handler;
import com.amap.api.maps.offlinemap.OfflineMapCity;
import com.amap.api.maps.offlinemap.OfflineMapProvince;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: OfflineMapDownloadList */
public class bm {
    public ArrayList<OfflineMapProvince> f274a = new ArrayList();
    private bx f275b;
    private Context f276c;
    private Handler f277d;

    public bm(Context context, Handler handler) {
        this.f276c = context;
        this.f277d = handler;
        this.f275b = bx.m366a(context);
    }

    private void m315a(bs bsVar) {
        if (this.f275b != null && bsVar != null) {
            this.f275b.m373a(bsVar);
        }
    }

    private void m321d(String str) {
        if (this.f275b != null) {
            this.f275b.m378c(str);
        }
    }

    private boolean m318a(int i, int i2) {
        return i2 != 1 || i <= 2 || i >= 98;
    }

    private boolean m320b(int i) {
        if (i == 4) {
            return true;
        }
        return false;
    }

    private boolean m319a(OfflineMapProvince offlineMapProvince) {
        if (offlineMapProvince == null) {
            return false;
        }
        Iterator it = offlineMapProvince.getCityList().iterator();
        while (it.hasNext()) {
            if (((OfflineMapCity) it.next()).getState() != 4) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<OfflineMapProvince> m323a() {
        ArrayList<OfflineMapProvince> arrayList = new ArrayList();
        Iterator it = this.f274a.iterator();
        while (it.hasNext()) {
            arrayList.add((OfflineMapProvince) it.next());
        }
        return arrayList;
    }

    public OfflineMapCity m322a(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        Iterator it = this.f274a.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((OfflineMapProvince) it.next()).getCityList().iterator();
            while (it2.hasNext()) {
                OfflineMapCity offlineMapCity = (OfflineMapCity) it2.next();
                if (offlineMapCity.getCode().equals(str)) {
                    return offlineMapCity;
                }
            }
        }
        return null;
    }

    public OfflineMapCity m327b(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        Iterator it = this.f274a.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((OfflineMapProvince) it.next()).getCityList().iterator();
            while (it2.hasNext()) {
                OfflineMapCity offlineMapCity = (OfflineMapCity) it2.next();
                if (offlineMapCity.getCity().trim().equalsIgnoreCase(str.trim())) {
                    return offlineMapCity;
                }
            }
        }
        return null;
    }

    public OfflineMapProvince m329c(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        Iterator it = this.f274a.iterator();
        while (it.hasNext()) {
            OfflineMapProvince offlineMapProvince = (OfflineMapProvince) it.next();
            if (offlineMapProvince.getProvinceName().trim().equalsIgnoreCase(str.trim())) {
                return offlineMapProvince;
            }
        }
        return null;
    }

    public ArrayList<OfflineMapCity> m328b() {
        ArrayList<OfflineMapCity> arrayList = new ArrayList();
        Iterator it = this.f274a.iterator();
        while (it.hasNext()) {
            Iterator it2 = ((OfflineMapProvince) it.next()).getCityList().iterator();
            while (it2.hasNext()) {
                arrayList.add((OfflineMapCity) it2.next());
            }
        }
        return arrayList;
    }

    public void m325a(List<OfflineMapProvince> list) {
        if (this.f274a.size() > 0) {
            for (int i = 0; i < this.f274a.size(); i++) {
                OfflineMapProvince offlineMapProvince = (OfflineMapProvince) this.f274a.get(i);
                OfflineMapProvince offlineMapProvince2 = (OfflineMapProvince) list.get(i);
                m317a(offlineMapProvince, offlineMapProvince2);
                ArrayList cityList = offlineMapProvince.getCityList();
                ArrayList cityList2 = offlineMapProvince2.getCityList();
                for (int i2 = 0; i2 < cityList.size(); i2++) {
                    m316a((OfflineMapCity) cityList.get(i2), (OfflineMapCity) cityList2.get(i2));
                }
            }
            return;
        }
        for (OfflineMapProvince offlineMapProvince3 : list) {
            this.f274a.add(offlineMapProvince3);
        }
    }

    private void m316a(OfflineMapCity offlineMapCity, OfflineMapCity offlineMapCity2) {
        offlineMapCity.setUrl(offlineMapCity2.getUrl());
        offlineMapCity.setVersion(offlineMapCity2.getVersion());
    }

    private void m317a(OfflineMapProvince offlineMapProvince, OfflineMapProvince offlineMapProvince2) {
        offlineMapProvince.setUrl(offlineMapProvince2.getUrl());
        offlineMapProvince.setVersion(offlineMapProvince2.getVersion());
    }

    public ArrayList<OfflineMapCity> m330c() {
        ArrayList<OfflineMapCity> arrayList;
        synchronized (this.f274a) {
            arrayList = new ArrayList();
            Iterator it = this.f274a.iterator();
            while (it.hasNext()) {
                for (OfflineMapCity offlineMapCity : ((OfflineMapProvince) it.next()).getCityList()) {
                    if (offlineMapCity.getState() == 4) {
                        arrayList.add(offlineMapCity);
                    }
                }
            }
        }
        return arrayList;
    }

    public ArrayList<OfflineMapProvince> m331d() {
        ArrayList<OfflineMapProvince> arrayList;
        synchronized (this.f274a) {
            arrayList = new ArrayList();
            Iterator it = this.f274a.iterator();
            while (it.hasNext()) {
                OfflineMapProvince offlineMapProvince = (OfflineMapProvince) it.next();
                if (offlineMapProvince.getState() == 4) {
                    arrayList.add(offlineMapProvince);
                }
            }
        }
        return arrayList;
    }

    public ArrayList<OfflineMapCity> m332e() {
        ArrayList<OfflineMapCity> arrayList;
        synchronized (this.f274a) {
            arrayList = new ArrayList();
            Iterator it = this.f274a.iterator();
            while (it.hasNext()) {
                for (OfflineMapCity offlineMapCity : ((OfflineMapProvince) it.next()).getCityList()) {
                    if (m326a(offlineMapCity.getState())) {
                        arrayList.add(offlineMapCity);
                    }
                }
            }
        }
        return arrayList;
    }

    public ArrayList<OfflineMapProvince> m333f() {
        ArrayList<OfflineMapProvince> arrayList;
        synchronized (this.f274a) {
            arrayList = new ArrayList();
            Iterator it = this.f274a.iterator();
            while (it.hasNext()) {
                OfflineMapProvince offlineMapProvince = (OfflineMapProvince) it.next();
                if (m326a(offlineMapProvince.getState())) {
                    arrayList.add(offlineMapProvince);
                }
            }
        }
        return arrayList;
    }

    public boolean m326a(int i) {
        return i == 0 || i == 2 || i == 3 || i == 1;
    }

    public void m324a(bg bgVar) {
        String adcode = bgVar.getAdcode();
        synchronized (this.f274a) {
            Iterator it = this.f274a.iterator();
            loop0:
            while (it.hasNext()) {
                OfflineMapProvince offlineMapProvince = (OfflineMapProvince) it.next();
                for (OfflineMapCity offlineMapCity : offlineMapProvince.getCityList()) {
                    if (offlineMapCity.getAdcode().trim().equals(adcode.trim())) {
                        m313a(bgVar, offlineMapCity);
                        m314a(bgVar, offlineMapProvince);
                        break loop0;
                    }
                }
            }
        }
    }

    private void m313a(bg bgVar, OfflineMapCity offlineMapCity) {
        int b = bgVar.m5706c().m439b();
        if (bgVar.m5706c().equals(bgVar.f5341a)) {
            m321d(bgVar.getAdcode());
        } else {
            if (bgVar.m5706c().equals(bgVar.f5346f)) {
                cf.m424a("saveJSONObjectToFile  CITY " + bgVar.getCity());
                bgVar.m5726w().m4027d();
            }
            if (m318a(bgVar.getcompleteCode(), bgVar.m5706c().m439b())) {
                m315a(bgVar.m5726w());
            }
        }
        offlineMapCity.setState(b);
        offlineMapCity.setCompleteCode(bgVar.getcompleteCode());
    }

    private void m314a(bg bgVar, OfflineMapProvince offlineMapProvince) {
        int b = bgVar.m5706c().m439b();
        if (b == 6) {
            offlineMapProvince.setState(b);
            offlineMapProvince.setCompleteCode(0);
            m321d(offlineMapProvince.getProvinceCode());
            try {
                cf.m425a(offlineMapProvince.getProvinceCode(), this.f276c);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (m320b(b) && m319a(offlineMapProvince)) {
            bs bsVar;
            if (bgVar.getAdcode().equals(offlineMapProvince.getProvinceCode())) {
                offlineMapProvince.setState(b);
                offlineMapProvince.setCompleteCode(bgVar.getcompleteCode());
                offlineMapProvince.setVersion(bgVar.getVersion());
                offlineMapProvince.setUrl(bgVar.getUrl());
                bsVar = new bs(offlineMapProvince, this.f276c);
                bsVar.m4023a(bgVar.m5696a());
                bsVar.m359c(bgVar.getCode());
            } else {
                offlineMapProvince.setState(b);
                offlineMapProvince.setCompleteCode(100);
                bsVar = new bs(offlineMapProvince, this.f276c);
            }
            bsVar.m4027d();
            m315a(bsVar);
            cf.m424a("saveJSONObjectToFile  province " + bsVar.m360e());
        }
    }

    public void m334g() {
        m335h();
        this.f277d = null;
        this.f275b = null;
        this.f276c = null;
    }

    public void m335h() {
        this.f274a.clear();
    }
}
