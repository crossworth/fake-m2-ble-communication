package com.autonavi.amap.mapcore;

import com.umeng.socialize.common.SocializeConstants;
import java.util.ArrayList;
import java.util.HashMap;

public class VMapDataCache {
    private static final int MAXSIZE = 400;
    private static VMapDataCache instance;
    HashMap<String, C0481e> vCancelMapDataHs = new HashMap();
    ArrayList<String> vCancelMapDataList = new ArrayList();
    HashMap<String, C0481e> vMapDataHs = new HashMap();
    ArrayList<String> vMapDataList = new ArrayList();

    public static VMapDataCache getInstance() {
        if (instance == null) {
            instance = new VMapDataCache();
        }
        return instance;
    }

    public synchronized void reset() {
        this.vMapDataHs.clear();
        this.vMapDataList.clear();
        this.vCancelMapDataHs.clear();
        this.vCancelMapDataList.clear();
    }

    public int getSize() {
        return this.vMapDataHs.size();
    }

    static String getKey(String str, int i) {
        return str + SocializeConstants.OP_DIVIDER_MINUS + i;
    }

    public synchronized C0481e getRecoder(String str, int i) {
        C0481e c0481e;
        c0481e = (C0481e) this.vMapDataHs.get(getKey(str, i));
        if (c0481e != null) {
            c0481e.f2044d++;
        }
        return c0481e;
    }

    public synchronized C0481e getCancelRecoder(String str, int i) {
        C0481e c0481e;
        c0481e = (C0481e) this.vCancelMapDataHs.get(getKey(str, i));
        if (c0481e != null && (System.currentTimeMillis() / 1000) - ((long) c0481e.f2042b) > 10) {
            c0481e = null;
        }
        return c0481e;
    }

    public synchronized C0481e putRecoder(byte[] bArr, String str, int i) {
        C0481e c0481e;
        c0481e = new C0481e(str, i);
        if (c0481e.f2041a == null) {
            c0481e = null;
        } else {
            if (this.vMapDataHs.size() > 400) {
                this.vMapDataHs.remove(this.vMapDataList.get(0));
                this.vMapDataList.remove(0);
            }
            this.vMapDataHs.put(getKey(str, i), c0481e);
            this.vMapDataList.add(getKey(str, i));
        }
        return c0481e;
    }

    public synchronized C0481e putCancelRecoder(byte[] bArr, String str, int i) {
        C0481e c0481e = null;
        synchronized (this) {
            if (getRecoder(str, i) == null) {
                C0481e c0481e2 = new C0481e(str, i);
                if (c0481e2.f2041a != null) {
                    if (this.vCancelMapDataHs.size() > 400) {
                        this.vCancelMapDataHs.remove(this.vMapDataList.get(0));
                        this.vCancelMapDataList.remove(0);
                    }
                    this.vCancelMapDataHs.put(getKey(str, i), c0481e2);
                    this.vCancelMapDataList.add(getKey(str, i));
                    c0481e = c0481e2;
                }
            }
        }
        return c0481e;
    }
}
