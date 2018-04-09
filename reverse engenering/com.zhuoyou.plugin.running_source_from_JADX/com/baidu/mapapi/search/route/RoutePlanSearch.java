package com.baidu.mapapi.search.route;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.mapapi.search.core.C0520i;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption.DrivingPolicy;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption.DrivingTrafficPolicy;
import com.baidu.mapapi.search.route.TransitRoutePlanOption.TransitPolicy;
import com.baidu.platform.comapi.search.C0518b;
import com.baidu.platform.comapi.search.C0656a;
import com.baidu.platform.comapi.search.C0658d;
import com.baidu.platform.comapi.search.C0660f;
import java.util.ArrayList;

public final class RoutePlanSearch extends C0520i {
    private C0658d f1756a;
    private OnGetRoutePlanResultListener f1757b;
    private boolean f1758c;
    private int f1759d;
    private int f1760e;
    private int f1761f;

    private class C0554a implements C0518b {
        final /* synthetic */ RoutePlanSearch f1755a;

        private C0554a(RoutePlanSearch routePlanSearch) {
            this.f1755a = routePlanSearch;
        }

        public void mo1801a(int i) {
            if (!this.f1755a.f1758c && this.f1755a.f1757b != null) {
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
                    case 12:
                        errorno = ERRORNO.NOT_SUPPORT_BUS;
                        break;
                    case 13:
                        errorno = ERRORNO.NOT_SUPPORT_BUS_2CITY;
                        break;
                    case 14:
                        errorno = ERRORNO.ST_EN_TOO_NEAR;
                        break;
                    case 107:
                        errorno = ERRORNO.PERMISSION_UNFINISHED;
                        break;
                    case 500:
                        errorno = ERRORNO.KEY_ERROR;
                        break;
                    case 701:
                        errorno = ERRORNO.MASS_TRANSIT_SERVER_ERROR;
                        break;
                    case 702:
                        errorno = ERRORNO.MASS_TRANSIT_OPTION_ERROR;
                        break;
                    case 703:
                        errorno = ERRORNO.MASS_TRANSIT_NO_POI_ERROR;
                        break;
                }
                if (errorno != null) {
                    switch (this.f1755a.f1760e) {
                        case 0:
                            this.f1755a.f1757b.onGetTransitRouteResult(new TransitRouteResult(errorno));
                            return;
                        case 1:
                            this.f1755a.f1757b.onGetWalkingRouteResult(new WalkingRouteResult(errorno));
                            return;
                        case 2:
                            this.f1755a.f1757b.onGetDrivingRouteResult(new DrivingRouteResult(errorno));
                            return;
                        case 3:
                            this.f1755a.f1757b.onGetBikingRouteResult(new BikingRouteResult(errorno));
                            return;
                        case 4:
                            this.f1755a.f1757b.onGetIndoorRouteResult(new IndoorRouteResult(errorno));
                            return;
                        case 5:
                            this.f1755a.f1757b.onGetMassTransitRouteResult(new MassTransitRouteResult(errorno));
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
            if (!this.f1755a.f1758c && str != null && str.length() != 0 && this.f1755a.f1757b != null) {
                switch (this.f1755a.f1760e) {
                    case 0:
                        TransitRouteResult transitRouteResult = new TransitRouteResult(ERRORNO.AMBIGUOUS_ROURE_ADDR);
                        transitRouteResult.m1646a(C0568n.m1709j(str));
                        this.f1755a.f1757b.onGetTransitRouteResult(transitRouteResult);
                        return;
                    case 1:
                        WalkingRouteResult walkingRouteResult = new WalkingRouteResult(ERRORNO.AMBIGUOUS_ROURE_ADDR);
                        walkingRouteResult.m1656a(C0568n.m1709j(str));
                        this.f1755a.f1757b.onGetWalkingRouteResult(walkingRouteResult);
                        return;
                    case 2:
                        DrivingRouteResult drivingRouteResult = new DrivingRouteResult(ERRORNO.AMBIGUOUS_ROURE_ADDR);
                        drivingRouteResult.m1589a(C0568n.m1709j(str));
                        if (drivingRouteResult.getSuggestAddrInfo().getSuggestEndCity() == null && drivingRouteResult.getSuggestAddrInfo().getSuggestEndNode() == null && drivingRouteResult.getSuggestAddrInfo().getSuggestStartCity() == null && drivingRouteResult.getSuggestAddrInfo().getSuggestStartNode() == null && drivingRouteResult.getSuggestAddrInfo().getSuggestWpCity() == null && drivingRouteResult.getSuggestAddrInfo().getSuggestWpNode() == null) {
                            drivingRouteResult.error = ERRORNO.RESULT_NOT_FOUND;
                        }
                        this.f1755a.f1757b.onGetDrivingRouteResult(drivingRouteResult);
                        return;
                    case 3:
                        BikingRouteResult bikingRouteResult = new BikingRouteResult(ERRORNO.AMBIGUOUS_ROURE_ADDR);
                        bikingRouteResult.m1577a(C0568n.m1709j(str));
                        this.f1755a.f1757b.onGetBikingRouteResult(bikingRouteResult);
                        return;
                    default:
                        return;
                }
            }
        }

        public void mo1805d(String str) {
        }

        public void mo1806e(String str) {
        }

        public void mo1807f(String str) {
            if (!this.f1755a.f1758c && str != null && str.length() != 0 && this.f1755a.f1757b != null) {
                this.f1755a.f1757b.onGetDrivingRouteResult(C0568n.m1695c(str));
            }
        }

        public void mo1808g(String str) {
            if (!this.f1755a.f1758c && str != null && str.length() != 0 && this.f1755a.f1757b != null) {
                this.f1755a.f1757b.onGetIndoorRouteResult(C0568n.m1699d(str));
            }
        }

        public void mo1809h(String str) {
            if (!this.f1755a.f1758c && str != null && str.length() != 0 && this.f1755a.f1757b != null) {
                this.f1755a.f1757b.onGetWalkingRouteResult(C0568n.m1703e(str));
            }
        }

        public void mo1810i(String str) {
            if (!this.f1755a.f1758c && str != null && str.length() != 0 && this.f1755a.f1757b != null) {
                this.f1755a.f1757b.onGetTransitRouteResult(C0568n.m1685a(str));
            }
        }

        public void mo1811j(String str) {
            this.f1755a.f1757b.onGetMassTransitRouteResult(C0568n.m1690b(str));
        }

        public void mo1812k(String str) {
            if (!this.f1755a.f1758c && str != null && str.length() != 0 && this.f1755a.f1757b != null) {
                this.f1755a.f1757b.onGetBikingRouteResult(C0568n.m1704f(str));
            }
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

    RoutePlanSearch() {
        this.f1756a = null;
        this.f1757b = null;
        this.f1758c = false;
        this.f1759d = 0;
        this.f1760e = 0;
        this.f1756a = new C0658d();
        this.f1756a.m2112a(new C0554a());
    }

    private ArrayList<C0660f> m1629a(DrivingRoutePlanOption drivingRoutePlanOption) {
        if (drivingRoutePlanOption.f1693e == null) {
            return null;
        }
        ArrayList<C0660f> arrayList = new ArrayList();
        for (PlanNode planNode : drivingRoutePlanOption.f1693e) {
            if (planNode != null && (planNode.getLocation() != null || (planNode.getName() != null && planNode.getCity() != null && planNode.getName().length() > 0 && planNode.getCity().length() > 0))) {
                C0660f c0660f = new C0660f();
                if (planNode.getName() != null) {
                    c0660f.f2159b = planNode.getName();
                }
                if (planNode.getLocation() != null) {
                    c0660f.f2158a = CoordUtil.ll2point(planNode.getLocation());
                }
                if (planNode.getCity() == null) {
                    c0660f.f2160c = "";
                } else {
                    c0660f.f2160c = planNode.getCity();
                }
                arrayList.add(c0660f);
            }
        }
        return arrayList;
    }

    public static RoutePlanSearch newInstance() {
        BMapManager.init();
        return new RoutePlanSearch();
    }

    public boolean bikingSearch(BikingRoutePlanOption bikingRoutePlanOption) {
        if (this.f1756a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (bikingRoutePlanOption == null || bikingRoutePlanOption.f1668b == null || bikingRoutePlanOption.f1667a == null) {
            throw new IllegalArgumentException("route plan option , origin or destination can not be null");
        } else {
            C0656a c0656a = new C0656a();
            if (bikingRoutePlanOption.f1667a.getName() != null) {
                c0656a.f2145d = bikingRoutePlanOption.f1667a.getName();
            }
            if (bikingRoutePlanOption.f1667a.getLocation() != null) {
                c0656a.f2144c = bikingRoutePlanOption.f1667a.getLocation();
                c0656a.f2142a = 1;
            }
            C0656a c0656a2 = new C0656a();
            if (bikingRoutePlanOption.f1668b.getName() != null) {
                c0656a2.f2145d = bikingRoutePlanOption.f1668b.getName();
            }
            if (bikingRoutePlanOption.f1668b.getLocation() != null) {
                c0656a2.f2144c = bikingRoutePlanOption.f1668b.getLocation();
                c0656a2.f2142a = 1;
            }
            this.f1759d = this.f1760e;
            this.f1760e = 3;
            return this.f1756a.m2118a(c0656a, c0656a2, bikingRoutePlanOption.f1667a.getCity(), bikingRoutePlanOption.f1668b.getCity());
        }
    }

    public void destroy() {
        if (!this.f1758c) {
            this.f1758c = true;
            this.f1757b = null;
            this.f1756a.m2110a();
            this.f1756a = null;
            BMapManager.destroy();
        }
    }

    public boolean drivingSearch(DrivingRoutePlanOption drivingRoutePlanOption) {
        if (this.f1756a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (drivingRoutePlanOption == null || drivingRoutePlanOption.f1690b == null || drivingRoutePlanOption.f1689a == null) {
            throw new IllegalArgumentException("route plan option , origin or destination can not be null");
        } else {
            if (drivingRoutePlanOption.f1692d == null) {
                drivingRoutePlanOption.f1692d = DrivingPolicy.ECAR_TIME_FIRST;
            }
            C0656a c0656a = new C0656a();
            if (drivingRoutePlanOption.f1689a.getName() != null) {
                c0656a.f2145d = drivingRoutePlanOption.f1689a.getName();
            }
            if (drivingRoutePlanOption.f1689a.getLocation() != null) {
                c0656a.f2143b = CoordUtil.ll2point(drivingRoutePlanOption.f1689a.getLocation());
                c0656a.f2142a = 1;
            }
            C0656a c0656a2 = new C0656a();
            if (drivingRoutePlanOption.f1690b.getName() != null) {
                c0656a2.f2145d = drivingRoutePlanOption.f1690b.getName();
            }
            if (drivingRoutePlanOption.f1690b.getLocation() != null) {
                c0656a2.f2143b = CoordUtil.ll2point(drivingRoutePlanOption.f1690b.getLocation());
                c0656a2.f2142a = 1;
            }
            this.f1759d = this.f1760e;
            this.f1760e = 2;
            int i = DrivingTrafficPolicy.ROUTE_PATH.getInt();
            if (drivingRoutePlanOption.f1694f != null) {
                i = drivingRoutePlanOption.f1694f.getInt();
            }
            return this.f1756a.m2119a(c0656a, c0656a2, drivingRoutePlanOption.f1691c, drivingRoutePlanOption.f1689a.getCity(), drivingRoutePlanOption.f1690b.getCity(), null, 12, drivingRoutePlanOption.f1692d.getInt(), i, m1629a(drivingRoutePlanOption), null);
        }
    }

    public boolean masstransitSearch(MassTransitRoutePlanOption massTransitRoutePlanOption) {
        if (this.f1756a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (massTransitRoutePlanOption == null || massTransitRoutePlanOption.f1739b == null || massTransitRoutePlanOption.f1738a == null) {
            throw new IllegalArgumentException("route plan option,origin or destination can not be null");
        } else if (massTransitRoutePlanOption.f1738a.getLocation() == null && (massTransitRoutePlanOption.f1738a.getName() == null || massTransitRoutePlanOption.f1738a.getCity() == null)) {
            throw new IllegalArgumentException("route plan option,origin is illegal");
        } else if (massTransitRoutePlanOption.f1739b.getLocation() == null && (massTransitRoutePlanOption.f1739b.getName() == null || massTransitRoutePlanOption.f1739b.getCity() == null)) {
            throw new IllegalArgumentException("route plan option,destination is illegal");
        } else {
            this.f1759d = this.f1760e;
            this.f1760e = 5;
            this.f1761f = massTransitRoutePlanOption.f1745h;
            this.f1756a.m2111a(massTransitRoutePlanOption.f1744g);
            return this.f1756a.m2116a(massTransitRoutePlanOption.f1738a, massTransitRoutePlanOption.f1739b, massTransitRoutePlanOption.f1740c, massTransitRoutePlanOption.f1741d.getInt(), massTransitRoutePlanOption.f1742e.getInt(), massTransitRoutePlanOption.f1743f.getInt(), this.f1761f);
        }
    }

    public void setOnGetRoutePlanResultListener(OnGetRoutePlanResultListener onGetRoutePlanResultListener) {
        this.f1757b = onGetRoutePlanResultListener;
    }

    public boolean transitSearch(TransitRoutePlanOption transitRoutePlanOption) {
        if (this.f1756a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (transitRoutePlanOption == null || transitRoutePlanOption.f1779c == null || transitRoutePlanOption.f1778b == null || transitRoutePlanOption.f1777a == null) {
            throw new IllegalArgumentException("route plan option,origin or destination or city can not be null");
        } else {
            if (transitRoutePlanOption.f1780d == null) {
                transitRoutePlanOption.f1780d = TransitPolicy.EBUS_TIME_FIRST;
            }
            C0656a c0656a = new C0656a();
            if (transitRoutePlanOption.f1777a.getName() != null) {
                c0656a.f2145d = transitRoutePlanOption.f1777a.getName();
            }
            if (transitRoutePlanOption.f1777a.getLocation() != null) {
                c0656a.f2143b = CoordUtil.ll2point(transitRoutePlanOption.f1777a.getLocation());
                c0656a.f2142a = 1;
            }
            C0656a c0656a2 = new C0656a();
            if (transitRoutePlanOption.f1778b.getName() != null) {
                c0656a2.f2145d = transitRoutePlanOption.f1778b.getName();
            }
            if (transitRoutePlanOption.f1778b.getLocation() != null) {
                c0656a2.f2143b = CoordUtil.ll2point(transitRoutePlanOption.f1778b.getLocation());
                c0656a2.f2142a = 1;
            }
            this.f1759d = this.f1760e;
            this.f1760e = 0;
            return this.f1756a.m2117a(c0656a, c0656a2, transitRoutePlanOption.f1779c, null, 12, transitRoutePlanOption.f1780d.getInt(), null);
        }
    }

    public boolean walkingIndoorSearch(IndoorRoutePlanOption indoorRoutePlanOption) {
        if (this.f1756a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (indoorRoutePlanOption == null || indoorRoutePlanOption.f1713b == null || indoorRoutePlanOption.f1712a == null) {
            throw new IllegalArgumentException("option , origin or destination can not be null");
        } else {
            GeoPoint ll2mc = CoordUtil.ll2mc(indoorRoutePlanOption.f1712a.m1591a());
            GeoPoint ll2mc2 = CoordUtil.ll2mc(indoorRoutePlanOption.f1713b.m1591a());
            String replaceAll = (String.format("%f,%f", new Object[]{Double.valueOf(ll2mc.getLongitudeE6()), Double.valueOf(ll2mc.getLatitudeE6())}) + "|" + indoorRoutePlanOption.f1712a.m1592b()).replaceAll(" ", "");
            String replaceAll2 = (String.format("%f,%f", new Object[]{Double.valueOf(ll2mc2.getLongitudeE6()), Double.valueOf(ll2mc2.getLatitudeE6())}) + "|" + indoorRoutePlanOption.f1713b.m1592b()).replaceAll(" ", "");
            this.f1759d = this.f1760e;
            this.f1760e = 4;
            return this.f1756a.m2128a(replaceAll, replaceAll2, null);
        }
    }

    public boolean walkingSearch(WalkingRoutePlanOption walkingRoutePlanOption) {
        if (this.f1756a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (walkingRoutePlanOption == null || walkingRoutePlanOption.f1792b == null || walkingRoutePlanOption.f1791a == null) {
            throw new IllegalArgumentException("option , origin or destination can not be null");
        } else {
            C0656a c0656a = new C0656a();
            if (walkingRoutePlanOption.f1791a.getName() != null) {
                c0656a.f2145d = walkingRoutePlanOption.f1791a.getName();
            }
            if (walkingRoutePlanOption.f1791a.getLocation() != null) {
                c0656a.f2143b = CoordUtil.ll2point(walkingRoutePlanOption.f1791a.getLocation());
                c0656a.f2142a = 1;
            }
            C0656a c0656a2 = new C0656a();
            if (walkingRoutePlanOption.f1792b.getName() != null) {
                c0656a2.f2145d = walkingRoutePlanOption.f1792b.getName();
            }
            if (walkingRoutePlanOption.f1792b.getLocation() != null) {
                c0656a2.f2143b = CoordUtil.ll2point(walkingRoutePlanOption.f1792b.getLocation());
                c0656a2.f2142a = 1;
            }
            this.f1759d = this.f1760e;
            this.f1760e = 1;
            return this.f1756a.m2120a(c0656a, c0656a2, null, walkingRoutePlanOption.f1791a.getCity(), walkingRoutePlanOption.f1792b.getCity(), null, 12, null);
        }
    }
}
