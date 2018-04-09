package com.amap.api.services.proguard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch.OnBusLineSearchListener;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.busline.BusStationSearch.OnBusStationSearchListener;
import com.amap.api.services.cloud.CloudItemDetail;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch.OnCloudSearchListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch.OnDistrictSearchListener;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.nearby.NearbySearch.NearbyListener;
import com.amap.api.services.nearby.NearbySearchResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.share.ShareSearch.OnShareSearchListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch.OnWeatherSearchListener;
import java.util.List;

/* compiled from: MessageHandler */
public class C0407q extends Handler {
    private static C0407q f1572a;

    /* compiled from: MessageHandler */
    public static class C0396a {
        public BusLineResult f1550a;
        public OnBusLineSearchListener f1551b;
    }

    /* compiled from: MessageHandler */
    public static class C0397b {
        public BusStationResult f1552a;
        public OnBusStationSearchListener f1553b;
    }

    /* compiled from: MessageHandler */
    public static class C0398c {
        public CloudItemDetail f1554a;
        public OnCloudSearchListener f1555b;
    }

    /* compiled from: MessageHandler */
    public static class C0399d {
        public CloudResult f1556a;
        public OnCloudSearchListener f1557b;
    }

    /* compiled from: MessageHandler */
    public static class C0400e {
        public GeocodeResult f1558a;
        public OnGeocodeSearchListener f1559b;
    }

    /* compiled from: MessageHandler */
    public static class C0401f {
        public List<NearbyListener> f1560a;
        public NearbySearchResult f1561b;
    }

    /* compiled from: MessageHandler */
    public static class C0402g {
        public PoiItem f1562a;
        public OnPoiSearchListener f1563b;
    }

    /* compiled from: MessageHandler */
    public static class C0403h {
        public PoiResult f1564a;
        public OnPoiSearchListener f1565b;
    }

    /* compiled from: MessageHandler */
    public static class C0404i {
        public RegeocodeResult f1566a;
        public OnGeocodeSearchListener f1567b;
    }

    /* compiled from: MessageHandler */
    public static class C0405j {
        public LocalWeatherForecastResult f1568a;
        public OnWeatherSearchListener f1569b;
    }

    /* compiled from: MessageHandler */
    public static class C0406k {
        public LocalWeatherLiveResult f1570a;
        public OnWeatherSearchListener f1571b;
    }

    public static synchronized C0407q m1654a() {
        C0407q c0407q;
        synchronized (C0407q.class) {
            if (f1572a == null) {
                if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()) {
                    f1572a = new C0407q(Looper.getMainLooper());
                } else {
                    f1572a = new C0407q();
                }
            }
            c0407q = f1572a;
        }
        return c0407q;
    }

    C0407q() {
    }

    C0407q(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        try {
            switch (message.arg1) {
                case 1:
                    m1665k(message);
                    return;
                case 2:
                    m1662h(message);
                    return;
                case 3:
                    m1664j(message);
                    return;
                case 4:
                    m1663i(message);
                    return;
                case 5:
                    m1661g(message);
                    return;
                case 6:
                    m1660f(message);
                    return;
                case 7:
                    m1659e(message);
                    return;
                case 8:
                    m1658d(message);
                    return;
                case 9:
                    m1657c(message);
                    return;
                case 10:
                    m1656b(message);
                    return;
                case 11:
                    m1655a(message);
                    return;
                case 12:
                    m1666l(message);
                    return;
                case 13:
                    m1667m(message);
                    return;
                default:
                    return;
            }
        } catch (Throwable th) {
            C0390i.m1594a(th, "MessageHandler", "handleMessage");
        }
        C0390i.m1594a(th, "MessageHandler", "handleMessage");
    }

    private void m1655a(Message message) {
        int i = message.arg2;
        OnShareSearchListener onShareSearchListener = (OnShareSearchListener) message.obj;
        String string = message.getData().getString("shareurlkey");
        if (onShareSearchListener != null) {
            switch (message.what) {
                case AMapException.CODE_AMAP_ENGINE_RESPONSE_ERROR /*1100*/:
                    onShareSearchListener.onPoiShareUrlSearched(string, i);
                    return;
                case AMapException.CODE_AMAP_ENGINE_RESPONSE_DATA_ERROR /*1101*/:
                    onShareSearchListener.onLocationShareUrlSearched(string, i);
                    return;
                case AMapException.CODE_AMAP_ENGINE_CONNECT_TIMEOUT /*1102*/:
                    onShareSearchListener.onNaviShareUrlSearched(string, i);
                    return;
                case AMapException.CODE_AMAP_ENGINE_RETURN_TIMEOUT /*1103*/:
                    onShareSearchListener.onBusRouteShareUrlSearched(string, i);
                    return;
                case 1104:
                    onShareSearchListener.onDrivingRouteShareUrlSearched(string, i);
                    return;
                case 1105:
                    onShareSearchListener.onWalkRouteShareUrlSearched(string, i);
                    return;
                default:
                    return;
            }
        }
    }

    private void m1656b(Message message) {
        List<NearbyListener> list = (List) message.obj;
        if (list != null && list.size() != 0) {
            for (NearbyListener onNearbyInfoUploaded : list) {
                onNearbyInfoUploaded.onNearbyInfoUploaded(message.what);
            }
        }
    }

    private void m1657c(Message message) {
        C0401f c0401f = (C0401f) message.obj;
        if (c0401f != null) {
            List<NearbyListener> list = c0401f.f1560a;
            if (list != null && list.size() != 0) {
                NearbySearchResult nearbySearchResult = null;
                if (message.what == 1000) {
                    nearbySearchResult = c0401f.f1561b;
                }
                for (NearbyListener onNearbyInfoSearched : list) {
                    onNearbyInfoSearched.onNearbyInfoSearched(nearbySearchResult, message.what);
                }
            }
        }
    }

    private void m1658d(Message message) {
        List<NearbyListener> list = (List) message.obj;
        if (list != null && list.size() != 0) {
            for (NearbyListener onUserInfoCleared : list) {
                onUserInfoCleared.onUserInfoCleared(message.what);
            }
        }
    }

    private void m1659e(Message message) {
        C0397b c0397b = (C0397b) message.obj;
        if (c0397b != null) {
            OnBusStationSearchListener onBusStationSearchListener = c0397b.f1553b;
            if (onBusStationSearchListener != null) {
                BusStationResult busStationResult;
                if (message.what == 1000) {
                    busStationResult = c0397b.f1552a;
                } else {
                    busStationResult = null;
                }
                onBusStationSearchListener.onBusStationSearched(busStationResult, message.what);
            }
        }
    }

    private void m1660f(Message message) {
        OnPoiSearchListener onPoiSearchListener;
        Bundle data;
        if (message.what == 600) {
            C0403h c0403h = (C0403h) message.obj;
            if (c0403h != null) {
                onPoiSearchListener = c0403h.f1565b;
                if (onPoiSearchListener != null) {
                    data = message.getData();
                    if (data != null) {
                        onPoiSearchListener.onPoiSearched(c0403h.f1564a, data.getInt("errorCode"));
                    }
                }
            }
        } else if (message.what == 602) {
            C0402g c0402g = (C0402g) message.obj;
            if (c0402g != null) {
                onPoiSearchListener = c0402g.f1563b;
                data = message.getData();
                if (data != null) {
                    onPoiSearchListener.onPoiItemSearched(c0402g.f1562a, data.getInt("errorCode"));
                }
            }
        }
    }

    private void m1661g(Message message) {
        InputtipsListener inputtipsListener = (InputtipsListener) message.obj;
        if (inputtipsListener != null) {
            List list = null;
            if (message.what == 1000) {
                list = message.getData().getParcelableArrayList("result");
            }
            inputtipsListener.onGetInputtips(list, message.what);
        }
    }

    private void m1662h(Message message) {
        OnGeocodeSearchListener onGeocodeSearchListener;
        if (message.what == 201) {
            C0404i c0404i = (C0404i) message.obj;
            if (c0404i != null) {
                onGeocodeSearchListener = c0404i.f1567b;
                if (onGeocodeSearchListener != null) {
                    onGeocodeSearchListener.onRegeocodeSearched(c0404i.f1566a, message.arg2);
                }
            }
        } else if (message.what == 200) {
            C0400e c0400e = (C0400e) message.obj;
            if (c0400e != null) {
                onGeocodeSearchListener = c0400e.f1559b;
                if (onGeocodeSearchListener != null) {
                    onGeocodeSearchListener.onGeocodeSearched(c0400e.f1558a, message.arg2);
                }
            }
        }
    }

    private void m1663i(Message message) {
        OnDistrictSearchListener onDistrictSearchListener = (OnDistrictSearchListener) message.obj;
        if (onDistrictSearchListener != null) {
            onDistrictSearchListener.onDistrictSearched((DistrictResult) message.getData().getParcelable("result"));
        }
    }

    private void m1664j(Message message) {
        C0396a c0396a = (C0396a) message.obj;
        if (c0396a != null) {
            OnBusLineSearchListener onBusLineSearchListener = c0396a.f1551b;
            if (onBusLineSearchListener != null) {
                BusLineResult busLineResult;
                if (message.what == 1000) {
                    busLineResult = c0396a.f1550a;
                } else {
                    busLineResult = null;
                }
                onBusLineSearchListener.onBusLineSearched(busLineResult, message.what);
            }
        }
    }

    private void m1665k(Message message) {
        OnRouteSearchListener onRouteSearchListener = (OnRouteSearchListener) message.obj;
        if (onRouteSearchListener != null) {
            Bundle data;
            if (message.what == 100) {
                data = message.getData();
                if (data != null) {
                    onRouteSearchListener.onBusRouteSearched((BusRouteResult) message.getData().getParcelable("result"), data.getInt("errorCode"));
                }
            } else if (message.what == 101) {
                data = message.getData();
                if (data != null) {
                    onRouteSearchListener.onDriveRouteSearched((DriveRouteResult) message.getData().getParcelable("result"), data.getInt("errorCode"));
                }
            } else if (message.what == 102) {
                data = message.getData();
                if (data != null) {
                    onRouteSearchListener.onWalkRouteSearched((WalkRouteResult) message.getData().getParcelable("result"), data.getInt("errorCode"));
                }
            }
        }
    }

    private void m1666l(Message message) {
        if (message.what == 700) {
            C0399d c0399d = (C0399d) message.obj;
            if (c0399d != null) {
                c0399d.f1557b.onCloudSearched(c0399d.f1556a, message.arg2);
            }
        } else if (message.what == 701) {
            C0398c c0398c = (C0398c) message.obj;
            if (c0398c != null) {
                c0398c.f1555b.onCloudItemDetailSearched(c0398c.f1554a, message.arg2);
            }
        }
    }

    private void m1667m(Message message) {
        OnWeatherSearchListener onWeatherSearchListener;
        Bundle data;
        if (message.what == 1301) {
            C0406k c0406k = (C0406k) message.obj;
            if (c0406k != null) {
                onWeatherSearchListener = c0406k.f1571b;
                if (onWeatherSearchListener != null) {
                    data = message.getData();
                    if (data != null) {
                        onWeatherSearchListener.onWeatherLiveSearched(c0406k.f1570a, data.getInt("errorCode"));
                    }
                }
            }
        } else if (message.what == 1302) {
            C0405j c0405j = (C0405j) message.obj;
            if (c0405j != null) {
                onWeatherSearchListener = c0405j.f1569b;
                if (onWeatherSearchListener != null) {
                    data = message.getData();
                    if (data != null) {
                        onWeatherSearchListener.onWeatherForecastSearched(c0405j.f1568a, data.getInt("errorCode"));
                    }
                }
            }
        }
    }
}
