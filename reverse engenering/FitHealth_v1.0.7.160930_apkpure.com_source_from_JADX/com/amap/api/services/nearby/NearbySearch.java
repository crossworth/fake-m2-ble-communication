package com.amap.api.services.nearby;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.interfaces.INearbySearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.C0390i;
import com.amap.api.services.proguard.am;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;

public class NearbySearch {
    public static final int AMAP = 1;
    public static final int GPS = 0;
    private static NearbySearch f1233a;
    private INearbySearch f1234b;

    public interface NearbyListener {
        void onNearbyInfoSearched(NearbySearchResult nearbySearchResult, int i);

        void onNearbyInfoUploaded(int i);

        void onUserInfoCleared(int i);
    }

    public static class NearbyQuery {
        private LatLonPoint f1228a;
        private NearbySearchFunctionType f1229b = NearbySearchFunctionType.DISTANCE_SEARCH;
        private int f1230c = 1000;
        private int f1231d = AMapException.CODE_AMAP_CLIENT_ERRORCODE_MISSSING;
        private int f1232e = 1;

        public void setCenterPoint(LatLonPoint latLonPoint) {
            this.f1228a = latLonPoint;
        }

        public LatLonPoint getCenterPoint() {
            return this.f1228a;
        }

        public int getRadius() {
            return this.f1230c;
        }

        public void setRadius(int i) {
            if (i > 10000) {
                i = 10000;
            }
            this.f1230c = i;
        }

        public void setType(NearbySearchFunctionType nearbySearchFunctionType) {
            this.f1229b = nearbySearchFunctionType;
        }

        public int getType() {
            switch (this.f1229b) {
                case DRIVING_DISTANCE_SEARCH:
                    return 1;
                default:
                    return 0;
            }
        }

        public void setCoordType(int i) {
            if (i == 0 || i == 1) {
                this.f1232e = i;
            } else {
                this.f1232e = 1;
            }
        }

        public int getCoordType() {
            return this.f1232e;
        }

        public void setTimeRange(int i) {
            if (i < 5) {
                i = 5;
            } else if (i > 86400) {
                i = 86400;
            }
            this.f1231d = i;
        }

        public int getTimeRange() {
            return this.f1231d;
        }
    }

    public static synchronized NearbySearch getInstance(Context context) {
        NearbySearch nearbySearch;
        synchronized (NearbySearch.class) {
            if (f1233a == null) {
                f1233a = new NearbySearch(context);
            }
            nearbySearch = f1233a;
        }
        return nearbySearch;
    }

    private NearbySearch(Context context) {
        try {
            Context context2 = context;
            this.f1234b = (INearbySearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.NearbySearchWrapper", am.class, new Class[]{Context.class}, new Object[]{context});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1234b == null) {
            this.f1234b = new am(context);
        }
    }

    public synchronized void addNearbyListener(NearbyListener nearbyListener) {
        if (this.f1234b != null) {
            this.f1234b.addNearbyListener(nearbyListener);
        }
    }

    public synchronized void removeNearbyListener(NearbyListener nearbyListener) {
        if (this.f1234b != null) {
            this.f1234b.removeNearbyListener(nearbyListener);
        }
    }

    public void clearUserInfoAsyn() {
        if (this.f1234b != null) {
            this.f1234b.clearUserInfoAsyn();
        }
    }

    public void setUserID(String str) {
        if (this.f1234b != null) {
            this.f1234b.setUserID(str);
        }
    }

    public synchronized void startUploadNearbyInfoAuto(UploadInfoCallback uploadInfoCallback, int i) {
        if (this.f1234b != null) {
            this.f1234b.startUploadNearbyInfoAuto(uploadInfoCallback, i);
        }
    }

    public synchronized void stopUploadNearbyInfoAuto() {
        if (this.f1234b != null) {
            this.f1234b.stopUploadNearbyInfoAuto();
        }
    }

    public void uploadNearbyInfoAsyn(UploadInfo uploadInfo) {
        if (this.f1234b != null) {
            this.f1234b.uploadNearbyInfoAsyn(uploadInfo);
        }
    }

    public void searchNearbyInfoAsyn(NearbyQuery nearbyQuery) {
        if (this.f1234b != null) {
            this.f1234b.searchNearbyInfoAsyn(nearbyQuery);
        }
    }

    public NearbySearchResult searchNearbyInfo(NearbyQuery nearbyQuery) throws AMapException {
        if (this.f1234b != null) {
            return this.f1234b.searchNearbyInfo(nearbyQuery);
        }
        return null;
    }

    public static synchronized void destroy() {
        synchronized (NearbySearch.class) {
            if (f1233a != null) {
                try {
                    f1233a.m1191a();
                } catch (Throwable th) {
                    C0390i.m1594a(th, "NearbySearch", "destryoy");
                }
            }
            f1233a = null;
        }
    }

    private void m1191a() {
        if (this.f1234b != null) {
            this.f1234b.destroy();
        }
        this.f1234b = null;
    }
}
