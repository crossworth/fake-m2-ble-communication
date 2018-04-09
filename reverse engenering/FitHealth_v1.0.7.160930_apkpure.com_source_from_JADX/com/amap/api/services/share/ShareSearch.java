package com.amap.api.services.share;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.LatLonSharePoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.interfaces.IShareSearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.ap;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;

public class ShareSearch {
    public static final int BusComfortable = 4;
    public static final int BusDefault = 0;
    public static final int BusLeaseChange = 2;
    public static final int BusLeaseWalk = 3;
    public static final int BusNoSubway = 5;
    public static final int BusSaveMoney = 1;
    public static final int DrivingAvoidCongestion = 4;
    public static final int DrivingDefault = 0;
    public static final int DrivingNoHighWay = 3;
    public static final int DrivingNoHighWayAvoidCongestion = 6;
    public static final int DrivingNoHighWaySaveMoney = 5;
    public static final int DrivingNoHighWaySaveMoneyAvoidCongestion = 8;
    public static final int DrivingSaveMoney = 1;
    public static final int DrivingSaveMoneyAvoidCongestion = 7;
    public static final int DrivingShortDistance = 2;
    public static final int NaviAvoidCongestion = 4;
    public static final int NaviDefault = 0;
    public static final int NaviNoHighWay = 3;
    public static final int NaviNoHighWayAvoidCongestion = 6;
    public static final int NaviNoHighWaySaveMoney = 5;
    public static final int NaviNoHighWaySaveMoneyAvoidCongestion = 8;
    public static final int NaviSaveMoney = 1;
    public static final int NaviSaveMoneyAvoidCongestion = 7;
    public static final int NaviShortDistance = 2;
    private IShareSearch f1652a;

    public interface OnShareSearchListener {
        void onBusRouteShareUrlSearched(String str, int i);

        void onDrivingRouteShareUrlSearched(String str, int i);

        void onLocationShareUrlSearched(String str, int i);

        void onNaviShareUrlSearched(String str, int i);

        void onPoiShareUrlSearched(String str, int i);

        void onWalkRouteShareUrlSearched(String str, int i);
    }

    public static class ShareBusRouteQuery {
        private ShareFromAndTo f1640a;
        private int f1641b;

        public ShareBusRouteQuery(ShareFromAndTo shareFromAndTo, int i) {
            this.f1640a = shareFromAndTo;
            this.f1641b = i;
        }

        public int getBusMode() {
            return this.f1641b;
        }

        public ShareFromAndTo getShareFromAndTo() {
            return this.f1640a;
        }
    }

    public static class ShareDrivingRouteQuery {
        private ShareFromAndTo f1642a;
        private int f1643b;

        public ShareDrivingRouteQuery(ShareFromAndTo shareFromAndTo, int i) {
            this.f1642a = shareFromAndTo;
            this.f1643b = i;
        }

        public int getDrivingMode() {
            return this.f1643b;
        }

        public ShareFromAndTo getShareFromAndTo() {
            return this.f1642a;
        }
    }

    public static class ShareFromAndTo {
        private LatLonPoint f1644a;
        private LatLonPoint f1645b;
        private String f1646c = "起点";
        private String f1647d = "终点";

        public ShareFromAndTo(LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
            this.f1644a = latLonPoint;
            this.f1645b = latLonPoint2;
        }

        public void setFromName(String str) {
            this.f1646c = str;
        }

        public void setToName(String str) {
            this.f1647d = str;
        }

        public LatLonPoint getFrom() {
            return this.f1644a;
        }

        public LatLonPoint getTo() {
            return this.f1645b;
        }

        public String getFromName() {
            return this.f1646c;
        }

        public String getToName() {
            return this.f1647d;
        }
    }

    public static class ShareNaviQuery {
        private ShareFromAndTo f1648a;
        private int f1649b;

        public ShareNaviQuery(ShareFromAndTo shareFromAndTo, int i) {
            this.f1648a = shareFromAndTo;
            this.f1649b = i;
        }

        public ShareFromAndTo getFromAndTo() {
            return this.f1648a;
        }

        public int getNaviMode() {
            return this.f1649b;
        }
    }

    public static class ShareWalkRouteQuery {
        private ShareFromAndTo f1650a;
        private int f1651b;

        public ShareWalkRouteQuery(ShareFromAndTo shareFromAndTo, int i) {
            this.f1650a = shareFromAndTo;
            this.f1651b = i;
        }

        public int getWalkMode() {
            return this.f1651b;
        }

        public ShareFromAndTo getShareFromAndTo() {
            return this.f1650a;
        }
    }

    public ShareSearch(Context context) {
        try {
            Context context2 = context;
            this.f1652a = (IShareSearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.ShareSearchWrapper", ap.class, new Class[]{Context.class}, new Object[]{context});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1652a == null) {
            this.f1652a = new ap(context);
        }
    }

    public void setOnShareSearchListener(OnShareSearchListener onShareSearchListener) {
        if (this.f1652a != null) {
            this.f1652a.setOnShareSearchListener(onShareSearchListener);
        }
    }

    public void searchPoiShareUrlAsyn(PoiItem poiItem) {
        if (this.f1652a != null) {
            this.f1652a.searchPoiShareUrlAsyn(poiItem);
        }
    }

    public void searchBusRouteShareUrlAsyn(ShareBusRouteQuery shareBusRouteQuery) {
        if (this.f1652a != null) {
            this.f1652a.searchBusRouteShareUrlAsyn(shareBusRouteQuery);
        }
    }

    public void searchWalkRouteShareUrlAsyn(ShareWalkRouteQuery shareWalkRouteQuery) {
        if (this.f1652a != null) {
            this.f1652a.searchWalkRouteShareUrlAsyn(shareWalkRouteQuery);
        }
    }

    public void searchDrivingRouteShareUrlAsyn(ShareDrivingRouteQuery shareDrivingRouteQuery) {
        if (this.f1652a != null) {
            this.f1652a.searchDrivingRouteShareUrlAsyn(shareDrivingRouteQuery);
        }
    }

    public void searchNaviShareUrlAsyn(ShareNaviQuery shareNaviQuery) {
        if (this.f1652a != null) {
            this.f1652a.searchNaviShareUrlAsyn(shareNaviQuery);
        }
    }

    public void searchLocationShareUrlAsyn(LatLonSharePoint latLonSharePoint) {
        if (this.f1652a != null) {
            this.f1652a.searchLocationShareUrlAsyn(latLonSharePoint);
        }
    }

    public String searchPoiShareUrl(PoiItem poiItem) throws AMapException {
        if (this.f1652a != null) {
            this.f1652a.searchPoiShareUrl(poiItem);
        }
        return null;
    }

    public String searchNaviShareUrl(ShareNaviQuery shareNaviQuery) throws AMapException {
        if (this.f1652a != null) {
            this.f1652a.searchNaviShareUrl(shareNaviQuery);
        }
        return null;
    }

    public String searchLocationShareUrl(LatLonSharePoint latLonSharePoint) throws AMapException {
        if (this.f1652a != null) {
            this.f1652a.searchLocationShareUrl(latLonSharePoint);
        }
        return null;
    }

    public String searchBusRouteShareUrl(ShareBusRouteQuery shareBusRouteQuery) throws AMapException {
        if (this.f1652a != null) {
            this.f1652a.searchBusRouteShareUrl(shareBusRouteQuery);
        }
        return null;
    }

    public String searchDrivingRouteShareUrl(ShareDrivingRouteQuery shareDrivingRouteQuery) throws AMapException {
        if (this.f1652a != null) {
            this.f1652a.searchDrivingRouteShareUrl(shareDrivingRouteQuery);
        }
        return null;
    }

    public String searchWalkRouteShareUrl(ShareWalkRouteQuery shareWalkRouteQuery) throws AMapException {
        if (this.f1652a != null) {
            this.f1652a.searchWalkRouteShareUrl(shareWalkRouteQuery);
        }
        return null;
    }
}
