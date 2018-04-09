package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonSharePoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.interfaces.IShareSearch;
import com.amap.api.services.proguard.ap;
import com.amap.api.services.share.ShareSearch.OnShareSearchListener;
import com.amap.api.services.share.ShareSearch.ShareBusRouteQuery;
import com.amap.api.services.share.ShareSearch.ShareDrivingRouteQuery;
import com.amap.api.services.share.ShareSearch.ShareNaviQuery;
import com.amap.api.services.share.ShareSearch.ShareWalkRouteQuery;

public class ShareSearchWrapper implements IShareSearch {
    private IShareSearch f4272a;

    public ShareSearchWrapper(Context context) {
        this.f4272a = new ap(context);
    }

    public void setOnShareSearchListener(OnShareSearchListener onShareSearchListener) {
        if (this.f4272a != null) {
            this.f4272a.setOnShareSearchListener(onShareSearchListener);
        }
    }

    public void searchPoiShareUrlAsyn(PoiItem poiItem) {
        if (this.f4272a != null) {
            this.f4272a.searchPoiShareUrlAsyn(poiItem);
        }
    }

    public void searchBusRouteShareUrlAsyn(ShareBusRouteQuery shareBusRouteQuery) {
        if (this.f4272a != null) {
            this.f4272a.searchBusRouteShareUrlAsyn(shareBusRouteQuery);
        }
    }

    public void searchWalkRouteShareUrlAsyn(ShareWalkRouteQuery shareWalkRouteQuery) {
        if (this.f4272a != null) {
            this.f4272a.searchWalkRouteShareUrlAsyn(shareWalkRouteQuery);
        }
    }

    public void searchDrivingRouteShareUrlAsyn(ShareDrivingRouteQuery shareDrivingRouteQuery) {
        if (this.f4272a != null) {
            this.f4272a.searchDrivingRouteShareUrlAsyn(shareDrivingRouteQuery);
        }
    }

    public void searchNaviShareUrlAsyn(ShareNaviQuery shareNaviQuery) {
        if (this.f4272a != null) {
            this.f4272a.searchNaviShareUrlAsyn(shareNaviQuery);
        }
    }

    public void searchLocationShareUrlAsyn(LatLonSharePoint latLonSharePoint) {
        if (this.f4272a != null) {
            this.f4272a.searchLocationShareUrlAsyn(latLonSharePoint);
        }
    }

    public String searchPoiShareUrl(PoiItem poiItem) throws AMapException {
        if (this.f4272a != null) {
            return this.f4272a.searchPoiShareUrl(poiItem);
        }
        return null;
    }

    public String searchNaviShareUrl(ShareNaviQuery shareNaviQuery) throws AMapException {
        if (this.f4272a != null) {
            return this.f4272a.searchNaviShareUrl(shareNaviQuery);
        }
        return null;
    }

    public String searchLocationShareUrl(LatLonSharePoint latLonSharePoint) throws AMapException {
        if (this.f4272a != null) {
            return this.f4272a.searchLocationShareUrl(latLonSharePoint);
        }
        return null;
    }

    public String searchBusRouteShareUrl(ShareBusRouteQuery shareBusRouteQuery) throws AMapException {
        if (this.f4272a != null) {
            return this.f4272a.searchBusRouteShareUrl(shareBusRouteQuery);
        }
        return null;
    }

    public String searchDrivingRouteShareUrl(ShareDrivingRouteQuery shareDrivingRouteQuery) throws AMapException {
        if (this.f4272a != null) {
            return this.f4272a.searchDrivingRouteShareUrl(shareDrivingRouteQuery);
        }
        return null;
    }

    public String searchWalkRouteShareUrl(ShareWalkRouteQuery shareWalkRouteQuery) throws AMapException {
        if (this.f4272a != null) {
            return this.f4272a.searchWalkRouteShareUrl(shareWalkRouteQuery);
        }
        return null;
    }
}
