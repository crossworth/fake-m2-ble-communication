package com.baidu.mapapi.search.share;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.search.core.C0520i;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.share.RouteShareURLOption.RouteShareMode;
import com.baidu.platform.comapi.search.C0518b;
import com.baidu.platform.comapi.search.C0658d;

public class ShareUrlSearch extends C0520i {
    private C0658d f1810a;
    private OnGetShareUrlResultListener f1811b;
    private boolean f1812c;
    private int f1813d;
    private int f1814e;

    private class C0577a implements C0518b {
        final /* synthetic */ ShareUrlSearch f1809a;

        private C0577a(ShareUrlSearch shareUrlSearch) {
            this.f1809a = shareUrlSearch;
        }

        public void mo1801a(int i) {
            if (!this.f1809a.f1812c && this.f1809a.f1811b != null) {
                ERRORNO errorno = null;
                switch (i) {
                    case 2:
                        errorno = ERRORNO.NETWORK_ERROR;
                        break;
                    case 3:
                        errorno = ERRORNO.RESULT_NOT_FOUND;
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
                    switch (this.f1809a.f1814e) {
                        case 1:
                            this.f1809a.f1811b.onGetPoiDetailShareUrlResult(new ShareUrlResult(errorno));
                            return;
                        case 2:
                            this.f1809a.f1811b.onGetLocationShareUrlResult(new ShareUrlResult(errorno));
                            return;
                        case 3:
                            this.f1809a.f1811b.onGetRouteShareUrlResult(new ShareUrlResult(errorno));
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
            if (!this.f1809a.f1812c && this.f1809a.f1811b != null) {
                switch (this.f1809a.f1814e) {
                    case 1:
                        this.f1809a.f1811b.onGetPoiDetailShareUrlResult(C0579b.m1751a(str));
                        return;
                    case 2:
                        this.f1809a.f1811b.onGetLocationShareUrlResult(C0579b.m1751a(str));
                        return;
                    case 3:
                        this.f1809a.f1811b.onGetRouteShareUrlResult(C0579b.m1751a(str));
                        return;
                    default:
                        return;
                }
            }
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

    ShareUrlSearch() {
        this.f1810a = null;
        this.f1811b = null;
        this.f1812c = false;
        this.f1813d = 0;
        this.f1814e = 0;
        this.f1810a = new C0658d();
        this.f1810a.m2112a(new C0577a());
    }

    private boolean m1745a(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int m1746b(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static ShareUrlSearch newInstance() {
        BMapManager.init();
        return new ShareUrlSearch();
    }

    public void destroy() {
        if (!this.f1812c) {
            this.f1812c = true;
            this.f1811b = null;
            this.f1810a.m2110a();
            this.f1810a = null;
            BMapManager.destroy();
        }
    }

    public boolean requestLocationShareUrl(LocationShareURLOption locationShareURLOption) {
        if (this.f1810a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (locationShareURLOption == null || locationShareURLOption.f1796a == null || locationShareURLOption.f1797b == null || locationShareURLOption.f1798c == null) {
            throw new IllegalArgumentException("option or name or snippet  can not be null");
        } else {
            this.f1813d = this.f1814e;
            this.f1814e = 2;
            return this.f1810a.m2115a(CoordUtil.ll2point(locationShareURLOption.f1796a), locationShareURLOption.f1797b, locationShareURLOption.f1798c);
        }
    }

    public boolean requestPoiDetailShareUrl(PoiDetailShareURLOption poiDetailShareURLOption) {
        if (this.f1810a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (poiDetailShareURLOption == null || poiDetailShareURLOption.f1799a == null) {
            throw new IllegalArgumentException("option or uid can not be null");
        } else {
            this.f1813d = this.f1814e;
            this.f1814e = 1;
            return this.f1810a.m2131b(poiDetailShareURLOption.f1799a);
        }
    }

    public boolean requestRouteShareUrl(RouteShareURLOption routeShareURLOption) {
        if (this.f1810a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (routeShareURLOption == null) {
            throw new IllegalStateException("option is null");
        } else if (routeShareURLOption.getmMode().ordinal() < 0) {
            return false;
        } else {
            if (routeShareURLOption.f1802a == null || routeShareURLOption.f1803b == null) {
                throw new IllegalArgumentException("start or end point can not be null");
            }
            boolean a;
            this.f1813d = this.f1814e;
            this.f1814e = 3;
            if (routeShareURLOption.f1804c == RouteShareMode.BUS_ROUTE_SHARE_MODE) {
                if ((routeShareURLOption.f1802a.getLocation() == null || routeShareURLOption.f1803b.getLocation() == null) && routeShareURLOption.f1806e < 0) {
                    throw new IllegalArgumentException("city code can not be null if don't set start or end point");
                }
                a = this.f1810a.m2114a(CoordUtil.ll2point(routeShareURLOption.f1802a.getLocation()), CoordUtil.ll2point(routeShareURLOption.f1803b.getLocation()), routeShareURLOption.f1802a.getName(), routeShareURLOption.f1803b.getName(), -1, -1, routeShareURLOption.f1804c.ordinal(), routeShareURLOption.f1806e, routeShareURLOption.f1805d);
            } else if (routeShareURLOption.f1802a.getLocation() == null && !m1745a(routeShareURLOption.f1802a.getCity())) {
                throw new IllegalArgumentException("start cityCode must be set if not set start location");
            } else if (routeShareURLOption.f1803b.getLocation() != null || m1745a(routeShareURLOption.f1803b.getCity())) {
                a = this.f1810a.m2114a(CoordUtil.ll2point(routeShareURLOption.f1802a.getLocation()), CoordUtil.ll2point(routeShareURLOption.f1803b.getLocation()), routeShareURLOption.f1802a.getName(), routeShareURLOption.f1803b.getName(), m1746b(routeShareURLOption.f1802a.getCity()), m1746b(routeShareURLOption.f1803b.getCity()), routeShareURLOption.f1804c.ordinal(), 0, 0);
            } else {
                throw new IllegalArgumentException("end cityCode must be set if not set end location");
            }
            return a;
        }
    }

    public void setOnGetShareUrlResultListener(OnGetShareUrlResultListener onGetShareUrlResultListener) {
        this.f1811b = onGetShareUrlResultListener;
    }
}
