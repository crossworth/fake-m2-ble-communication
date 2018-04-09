package com.baidu.mapapi.favorite;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.Point;
import com.baidu.platform.comapi.favrite.FavSyncPoi;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONObject;

class C0472a {
    static FavoritePoiInfo m1051a(FavSyncPoi favSyncPoi) {
        if (favSyncPoi == null || favSyncPoi.f1907c == null || favSyncPoi.f1906b.equals("")) {
            return null;
        }
        FavoritePoiInfo favoritePoiInfo = new FavoritePoiInfo();
        favoritePoiInfo.f955a = favSyncPoi.f1905a;
        favoritePoiInfo.f956b = favSyncPoi.f1906b;
        favoritePoiInfo.f957c = new LatLng(((double) favSyncPoi.f1907c.f1466y) / 1000000.0d, ((double) favSyncPoi.f1907c.f1465x) / 1000000.0d);
        favoritePoiInfo.f959e = favSyncPoi.f1909e;
        favoritePoiInfo.f960f = favSyncPoi.f1910f;
        favoritePoiInfo.f958d = favSyncPoi.f1908d;
        favoritePoiInfo.f961g = Long.parseLong(favSyncPoi.f1912h);
        return favoritePoiInfo;
    }

    static FavoritePoiInfo m1052a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        FavoritePoiInfo favoritePoiInfo = new FavoritePoiInfo();
        JSONObject optJSONObject = jSONObject.optJSONObject(SocializeProtocolConstants.PROTOCOL_KEY_SNSACCOUNT_ICON);
        if (optJSONObject != null) {
            favoritePoiInfo.f957c = new LatLng(((double) optJSONObject.optInt("y")) / 1000000.0d, ((double) optJSONObject.optInt("x")) / 1000000.0d);
        }
        favoritePoiInfo.f956b = jSONObject.optString("uspoiname");
        favoritePoiInfo.f961g = Long.parseLong(jSONObject.optString("addtimesec"));
        favoritePoiInfo.f958d = jSONObject.optString("addr");
        favoritePoiInfo.f960f = jSONObject.optString("uspoiuid");
        favoritePoiInfo.f959e = jSONObject.optString("ncityid");
        favoritePoiInfo.f955a = jSONObject.optString("key");
        return favoritePoiInfo;
    }

    static FavSyncPoi m1053a(FavoritePoiInfo favoritePoiInfo) {
        if (favoritePoiInfo == null || favoritePoiInfo.f957c == null || favoritePoiInfo.f956b == null || favoritePoiInfo.f956b.equals("")) {
            return null;
        }
        FavSyncPoi favSyncPoi = new FavSyncPoi();
        favSyncPoi.f1906b = favoritePoiInfo.f956b;
        favSyncPoi.f1907c = new Point((int) (favoritePoiInfo.f957c.longitude * 1000000.0d), (int) (favoritePoiInfo.f957c.latitude * 1000000.0d));
        favSyncPoi.f1908d = favoritePoiInfo.f958d;
        favSyncPoi.f1909e = favoritePoiInfo.f959e;
        favSyncPoi.f1910f = favoritePoiInfo.f960f;
        favSyncPoi.f1913i = false;
        return favSyncPoi;
    }
}
