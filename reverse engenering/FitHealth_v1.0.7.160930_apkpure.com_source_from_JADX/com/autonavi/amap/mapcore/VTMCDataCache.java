package com.autonavi.amap.mapcore;

import java.util.ArrayList;
import java.util.Hashtable;

public class VTMCDataCache {
    public static final int MAXSIZE = 500;
    public static final int MAX_EXPIREDTIME = 300;
    private static VTMCDataCache instance;
    static Hashtable<String, C0482f> vtmcHs = new Hashtable();
    static ArrayList<String> vtmcList = new ArrayList();
    public int mNewestTimeStamp = 0;

    public static VTMCDataCache getInstance() {
        if (instance == null) {
            instance = new VTMCDataCache();
        }
        return instance;
    }

    public void reset() {
        vtmcList.clear();
        vtmcHs.clear();
    }

    public int getSize() {
        return vtmcList.size();
    }

    private void deleteData(String str) {
        vtmcHs.remove(str);
        for (int i = 0; i < vtmcList.size(); i++) {
            if (((String) vtmcList.get(i)).equals(str)) {
                vtmcList.remove(i);
                return;
            }
        }
    }

    public synchronized C0482f getData(String str, boolean z) {
        C0482f c0482f;
        c0482f = (C0482f) vtmcHs.get(str);
        if (!z) {
            if (c0482f == null) {
                c0482f = null;
            } else if (((int) (System.currentTimeMillis() / 1000)) - c0482f.f2047c > 300) {
                c0482f = null;
            } else if (this.mNewestTimeStamp > c0482f.f2049e) {
                c0482f = null;
            }
        }
        return c0482f;
    }

    public synchronized C0482f putData(byte[] bArr) {
        C0482f c0482f;
        C0482f c0482f2 = new C0482f(bArr);
        if (this.mNewestTimeStamp < c0482f2.f2049e) {
            this.mNewestTimeStamp = c0482f2.f2049e;
        }
        c0482f = (C0482f) vtmcHs.get(c0482f2.f2046b);
        if (c0482f != null) {
            if (c0482f.f2048d.equals(c0482f2.f2048d)) {
                c0482f.m2083a(this.mNewestTimeStamp);
            } else {
                deleteData(c0482f2.f2046b);
            }
        }
        if (vtmcList.size() > 500) {
            vtmcHs.remove(vtmcList.get(0));
            vtmcList.remove(0);
        }
        vtmcHs.put(c0482f2.f2046b, c0482f2);
        vtmcList.add(c0482f2.f2046b);
        c0482f = c0482f2;
        return c0482f;
    }
}
