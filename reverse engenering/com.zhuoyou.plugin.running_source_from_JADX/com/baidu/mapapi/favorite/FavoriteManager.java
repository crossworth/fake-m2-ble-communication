package com.baidu.mapapi.favorite;

import android.util.Log;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.p014a.p015a.C0469a;
import com.baidu.platform.comapi.favrite.C0610a;
import com.baidu.platform.comapi.favrite.FavSyncPoi;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteManager extends C0469a {
    private static FavoriteManager f953a;
    private static C0610a f954b;

    private FavoriteManager() {
    }

    public static FavoriteManager getInstance() {
        if (f953a == null) {
            f953a = new FavoriteManager();
        }
        return f953a;
    }

    public int add(FavoritePoiInfo favoritePoiInfo) {
        if (f954b == null) {
            Log.e("baidumapsdk", "you may have not call init method!");
            return 0;
        } else if (favoritePoiInfo == null || favoritePoiInfo.f957c == null) {
            Log.e("baidumapsdk", "object or pt can not be null!");
            return 0;
        } else if (favoritePoiInfo.f956b == null || favoritePoiInfo.f956b.equals("")) {
            Log.e("baidumapsdk", "poiName can not be null or empty!");
            return -1;
        } else {
            FavSyncPoi a = C0472a.m1053a(favoritePoiInfo);
            int a2 = f954b.m1888a(a.f1906b, a);
            if (a2 != 1) {
                return a2;
            }
            favoritePoiInfo.f955a = a.f1905a;
            favoritePoiInfo.f961g = Long.parseLong(a.f1912h);
            return a2;
        }
    }

    public boolean clearAllFavPois() {
        if (f954b != null) {
            return f954b.m1893c();
        }
        Log.e("baidumapsdk", "you may have not call init method!");
        return false;
    }

    public boolean deleteFavPoi(String str) {
        if (f954b != null) {
            return (str == null || str.equals("")) ? false : f954b.m1889a(str);
        } else {
            Log.e("baidumapsdk", "you may have not call init method!");
            return false;
        }
    }

    public void destroy() {
        if (f954b != null) {
            f954b.m1891b();
            f954b = null;
            BMapManager.destroy();
        }
    }

    public List<FavoritePoiInfo> getAllFavPois() {
        if (f954b == null) {
            Log.e("baidumapsdk", "you may have not call init method!");
            return null;
        }
        String f = f954b.m1897f();
        if (f == null || f.equals("")) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(f);
            if (jSONObject.optInt("favpoinum") == 0) {
                return null;
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("favcontents");
            if (optJSONArray == null || optJSONArray.length() <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < optJSONArray.length(); i++) {
                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                if (jSONObject2 != null) {
                    arrayList.add(C0472a.m1052a(jSONObject2));
                }
            }
            return arrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FavoritePoiInfo getFavPoi(String str) {
        if (f954b == null) {
            Log.e("baidumapsdk", "you may have not call init method!");
            return null;
        } else if (str == null || str.equals("")) {
            return null;
        } else {
            FavSyncPoi b = f954b.m1890b(str);
            return b != null ? C0472a.m1051a(b) : null;
        }
    }

    public void init() {
        if (f954b == null) {
            f954b = C0610a.m1883a();
            BMapManager.init();
        }
    }

    public boolean updateFavPoi(String str, FavoritePoiInfo favoritePoiInfo) {
        if (f954b == null) {
            Log.e("baidumapsdk", "you may have not call init method!");
            return false;
        } else if (str == null || str.equals("") || favoritePoiInfo == null) {
            return false;
        } else {
            if (favoritePoiInfo == null || favoritePoiInfo.f957c == null) {
                Log.e("baidumapsdk", "object or pt can not be null!");
                return false;
            } else if (favoritePoiInfo.f956b == null || favoritePoiInfo.f956b.equals("")) {
                Log.e("baidumapsdk", "poiName can not be null or empty!");
                return false;
            } else {
                favoritePoiInfo.f955a = str;
                return f954b.m1892b(str, C0472a.m1053a(favoritePoiInfo));
            }
        }
    }
}
