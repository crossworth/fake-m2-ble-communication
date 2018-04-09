package com.baidu.mapapi.search.geocode;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.search.core.C0520i;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.platform.comapi.search.C0518b;
import com.baidu.platform.comapi.search.C0658d;

public class GeoCoder extends C0520i {
    private C0658d f1580a;
    private OnGetGeoCoderResultListener f1581b;
    private boolean f1582c;
    private int f1583d;
    private int f1584e;

    private class C0542a implements C0518b {
        final /* synthetic */ GeoCoder f1579a;

        private C0542a(GeoCoder geoCoder) {
            this.f1579a = geoCoder;
        }

        public void mo1801a(int i) {
            if (!this.f1579a.f1582c && this.f1579a.f1581b != null) {
                ERRORNO errorno = null;
                switch (i) {
                    case 2:
                        errorno = ERRORNO.NETWORK_ERROR;
                        break;
                    case 8:
                        errorno = ERRORNO.NETWORK_TIME_OUT;
                        break;
                    case 11:
                        errorno = ERRORNO.RESULT_NOT_FOUND;
                        break;
                    case 107:
                        errorno = ERRORNO.PERMISSION_UNFINISHED;
                        break;
                    case 500:
                        errorno = ERRORNO.KEY_ERROR;
                        break;
                }
                if (errorno != null) {
                    switch (this.f1579a.f1584e) {
                        case 1:
                            this.f1579a.f1581b.onGetGeoCodeResult(new GeoCodeResult(errorno));
                            return;
                        case 2:
                            this.f1579a.f1581b.onGetReverseGeoCodeResult(new ReverseGeoCodeResult(errorno));
                            return;
                        default:
                            return;
                    }
                }
            }
        }

        public void mo1802a(String str) {
        }

        public void mo1803b(String str) {
        }

        public void mo1804c(String str) {
        }

        public void mo1805d(String str) {
        }

        public void mo1806e(String str) {
        }

        public void mo1807f(String str) {
        }

        public void mo1808g(String str) {
        }

        public void mo1809h(String str) {
        }

        public void mo1810i(String str) {
        }

        public void mo1811j(String str) {
        }

        public void mo1812k(String str) {
        }

        public void mo1813l(String str) {
            if (!this.f1579a.f1582c && this.f1579a.f1581b != null && str != null && str.length() > 0) {
                switch (this.f1579a.f1584e) {
                    case 1:
                        this.f1579a.f1581b.onGetGeoCodeResult(C0544b.m1523b(str));
                        return;
                    case 2:
                        this.f1579a.f1581b.onGetReverseGeoCodeResult(C0544b.m1521a(str));
                        return;
                    default:
                        return;
                }
            }
        }

        public void mo1814m(String str) {
        }

        public void mo1815n(String str) {
        }

        public void mo1816o(String str) {
        }

        public void mo1817p(String str) {
        }
    }

    GeoCoder() {
        this.f1580a = null;
        this.f1581b = null;
        this.f1582c = false;
        this.f1583d = 0;
        this.f1584e = 0;
        this.f1580a = new C0658d();
        this.f1580a.m2112a(new C0542a());
    }

    public static GeoCoder newInstance() {
        BMapManager.init();
        return new GeoCoder();
    }

    public void destroy() {
        if (!this.f1582c) {
            this.f1582c = true;
            this.f1581b = null;
            this.f1580a.m2110a();
            this.f1580a = null;
            BMapManager.destroy();
        }
    }

    public boolean geocode(GeoCodeOption geoCodeOption) {
        if (this.f1580a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (geoCodeOption == null || geoCodeOption.f1576b == null || geoCodeOption.f1575a == null) {
            throw new IllegalArgumentException("option or address or city can not be null");
        } else {
            this.f1583d = this.f1584e;
            this.f1584e = 1;
            return this.f1580a.m2132b(geoCodeOption.f1576b, geoCodeOption.f1575a);
        }
    }

    public boolean reverseGeoCode(ReverseGeoCodeOption reverseGeoCodeOption) {
        if (this.f1580a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (reverseGeoCodeOption == null || reverseGeoCodeOption.f1585a == null) {
            throw new IllegalArgumentException("option or mLocation can not be null");
        } else {
            this.f1583d = this.f1584e;
            this.f1584e = 2;
            return this.f1580a.m2113a(CoordUtil.ll2point(reverseGeoCodeOption.f1585a));
        }
    }

    public void setOnGetGeoCodeResultListener(OnGetGeoCoderResultListener onGetGeoCoderResultListener) {
        this.f1581b = onGetGeoCoderResultListener;
    }
}
