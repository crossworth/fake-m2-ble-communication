package com.autonavi.amap.mapcore;

/* compiled from: VMapDataCache */
class C0481e {
    String f2041a;
    int f2042b;
    int f2043c;
    int f2044d = 0;

    public C0481e(String str, int i) {
        if (str != null) {
            try {
                if (str.length() != 0) {
                    this.f2042b = (int) (System.currentTimeMillis() / 1000);
                    this.f2043c = i;
                    this.f2041a = str;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                this.f2041a = null;
            }
        }
    }
}
