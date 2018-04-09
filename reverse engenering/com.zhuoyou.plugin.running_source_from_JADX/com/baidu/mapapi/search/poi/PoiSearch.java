package com.baidu.mapapi.search.poi;

import com.baidu.lbsapi.auth.LBSAuthManager;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.MapBound;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.mapapi.search.core.C0520i;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.platform.comapi.search.C0518b;
import com.baidu.platform.comapi.search.C0658d;
import java.util.HashMap;
import java.util.Map;

public class PoiSearch extends C0520i {
    private C0658d f1652a;
    private OnGetPoiSearchResultListener f1653b;
    private boolean f1654c;
    private int f1655d;
    private int f1656e;
    private boolean f1657f;
    private int f1658g;

    private class C0548a implements C0518b {
        final /* synthetic */ PoiSearch f1651a;

        private C0548a(PoiSearch poiSearch) {
            this.f1651a = poiSearch;
        }

        public void mo1801a(int i) {
            if (!this.f1651a.f1654c && this.f1651a.f1653b != null) {
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
                    case LBSAuthManager.CODE_UNAUTHENTICATE /*601*/:
                        errorno = ERRORNO.POIINDOOR_BID_ERROR;
                        break;
                    case LBSAuthManager.CODE_AUTHENTICATING /*602*/:
                        errorno = ERRORNO.POIINDOOR_FLOOR_ERROR;
                        break;
                    case 603:
                        errorno = ERRORNO.POIINDOOR_SERVER_ERROR;
                        break;
                }
                if (errorno == null) {
                    return;
                }
                if (this.f1651a.f1656e == 4) {
                    this.f1651a.f1653b.onGetPoiDetailResult(new PoiDetailResult(errorno));
                } else if (this.f1651a.f1656e == 5) {
                    this.f1651a.f1653b.onGetPoiIndoorResult(new PoiIndoorResult(errorno));
                } else {
                    this.f1651a.f1653b.onGetPoiResult(new PoiResult(errorno));
                }
            }
        }

        public void mo1802a(String str) {
            if (!this.f1651a.f1654c && str != null && str.length() > 0 && this.f1651a.f1653b != null) {
                this.f1651a.f1653b.onGetPoiResult(C0552d.m1567a(str, this.f1651a.f1658g, this.f1651a.f1652a.m2129b()));
            }
        }

        public void mo1803b(String str) {
            if (str != null && str.length() > 0 && this.f1651a.f1653b != null) {
                this.f1651a.f1653b.onGetPoiResult(C0552d.m1566a(str));
            }
        }

        public void mo1804c(String str) {
        }

        public void mo1805d(String str) {
            if (!this.f1651a.f1654c && str != null && str.length() > 0 && this.f1651a.f1653b != null) {
                PoiDetailResult poiDetailResult = new PoiDetailResult();
                if (poiDetailResult.m1529a(str)) {
                    this.f1651a.f1653b.onGetPoiDetailResult(poiDetailResult);
                } else {
                    this.f1651a.f1653b.onGetPoiDetailResult(new PoiDetailResult(ERRORNO.RESULT_NOT_FOUND));
                }
            }
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
        }

        public void mo1814m(String str) {
        }

        public void mo1815n(String str) {
        }

        public void mo1816o(String str) {
        }

        public void mo1817p(String str) {
            if (!this.f1651a.f1654c && str != null && str.length() > 0 && this.f1651a.f1653b != null) {
                this.f1651a.f1653b.onGetPoiIndoorResult(C0552d.m1568b(str));
            }
        }
    }

    PoiSearch() {
        this.f1652a = null;
        this.f1653b = null;
        this.f1654c = false;
        this.f1655d = 0;
        this.f1656e = 0;
        this.f1658g = 0;
        this.f1652a = new C0658d();
        this.f1652a.m2112a(new C0548a());
    }

    public static PoiSearch newInstance() {
        BMapManager.init();
        return new PoiSearch();
    }

    public void destroy() {
        if (!this.f1654c) {
            this.f1654c = true;
            this.f1653b = null;
            this.f1652a.m2110a();
            this.f1652a = null;
            BMapManager.destroy();
        }
    }

    public boolean searchInBound(PoiBoundSearchOption poiBoundSearchOption) {
        if (this.f1652a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (poiBoundSearchOption == null || poiBoundSearchOption.f1591a == null || poiBoundSearchOption.f1592b == null) {
            throw new IllegalArgumentException("option or bound or keyworld can not be null");
        } else {
            this.f1655d = this.f1656e;
            this.f1656e = 2;
            this.f1658g = poiBoundSearchOption.f1594d;
            this.f1652a.m2111a(poiBoundSearchOption.f1595e);
            MapBound mapBound = new MapBound();
            mapBound.setPtRT(CoordUtil.ll2point(poiBoundSearchOption.f1591a.northeast));
            mapBound.setPtLB(CoordUtil.ll2point(poiBoundSearchOption.f1591a.southwest));
            return this.f1652a.m2123a(poiBoundSearchOption.f1592b, 1, poiBoundSearchOption.f1594d, mapBound, (int) poiBoundSearchOption.f1593c, null, null);
        }
    }

    public boolean searchInCity(PoiCitySearchOption poiCitySearchOption) {
        if (this.f1652a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (poiCitySearchOption == null || poiCitySearchOption.f1596a == null || poiCitySearchOption.f1597b == null) {
            throw new IllegalArgumentException("option or city or keyworld can not be null");
        } else {
            this.f1655d = this.f1656e;
            this.f1656e = 1;
            this.f1658g = poiCitySearchOption.f1600e;
            this.f1652a.m2111a(poiCitySearchOption.f1601f);
            return this.f1652a.m2127a(poiCitySearchOption.f1597b, poiCitySearchOption.f1596a, poiCitySearchOption.f1600e, null, (int) poiCitySearchOption.f1598c, null);
        }
    }

    public boolean searchNearby(PoiNearbySearchOption poiNearbySearchOption) {
        if (this.f1652a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (poiNearbySearchOption == null || poiNearbySearchOption.f1637b == null || poiNearbySearchOption.f1636a == null) {
            throw new IllegalArgumentException("option or location or keyworld can not be null");
        } else if (poiNearbySearchOption.f1638c <= 0) {
            return false;
        } else {
            this.f1655d = this.f1656e;
            this.f1656e = 3;
            this.f1658g = poiNearbySearchOption.f1640e;
            this.f1652a.m2111a(poiNearbySearchOption.f1641f);
            Point ll2point = CoordUtil.ll2point(poiNearbySearchOption.f1637b);
            Point point = new Point(ll2point.f1465x - poiNearbySearchOption.f1638c, ll2point.f1466y - poiNearbySearchOption.f1638c);
            Point point2 = new Point(ll2point.f1465x + poiNearbySearchOption.f1638c, ll2point.f1466y + poiNearbySearchOption.f1638c);
            MapBound mapBound = new MapBound();
            mapBound.setPtLB(point);
            mapBound.setPtRT(point2);
            Map hashMap = new HashMap();
            hashMap.put("distance", Integer.valueOf(poiNearbySearchOption.f1638c));
            return this.f1652a.m2122a(poiNearbySearchOption.f1636a, 1, poiNearbySearchOption.f1640e, (int) poiNearbySearchOption.f1639d, mapBound, mapBound, hashMap, poiNearbySearchOption.f1642g.ordinal());
        }
    }

    public boolean searchPoiDetail(PoiDetailSearchOption poiDetailSearchOption) {
        if (this.f1652a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (poiDetailSearchOption == null || poiDetailSearchOption.f1626a == null) {
            throw new IllegalArgumentException("option or uid can not be null");
        } else {
            this.f1655d = this.f1656e;
            this.f1656e = 4;
            this.f1657f = poiDetailSearchOption.f1627b;
            return this.f1652a.m2121a(poiDetailSearchOption.f1626a);
        }
    }

    public boolean searchPoiIndoor(PoiIndoorOption poiIndoorOption) {
        if (this.f1652a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (poiIndoorOption == null || poiIndoorOption.f1628a == null || poiIndoorOption.f1629b == null) {
            throw new IllegalArgumentException("option or indoor bid or keyword can not be null");
        } else {
            this.f1655d = this.f1656e;
            this.f1656e = 5;
            return this.f1652a.m2126a(poiIndoorOption.f1628a, poiIndoorOption.f1629b, poiIndoorOption.f1631d, poiIndoorOption.f1632e, poiIndoorOption.f1630c);
        }
    }

    public void setOnGetPoiSearchResultListener(OnGetPoiSearchResultListener onGetPoiSearchResultListener) {
        this.f1653b = onGetPoiSearchResultListener;
    }
}
