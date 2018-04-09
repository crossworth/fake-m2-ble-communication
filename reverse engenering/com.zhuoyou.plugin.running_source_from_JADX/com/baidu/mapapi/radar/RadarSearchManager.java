package com.baidu.mapapi.radar;

import android.os.Handler;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.UIMsg.d_ResultType;
import com.baidu.mapapi.VersionInfo;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.NativeLoader;
import com.baidu.platform.comapi.radar.C0514c;
import com.baidu.platform.comapi.radar.C0653a;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RadarSearchManager implements C0514c {
    private static RadarSearchManager f1485a;
    private static String f1486b = "";
    private static int f1487l = 10;
    private ArrayList<RadarSearchListener> f1488c = new ArrayList();
    private Timer f1489d = new Timer();
    private TimerTask f1490e;
    private boolean f1491f = false;
    private Handler f1492g;
    private RadarUploadInfoCallback f1493h;
    private RadarUploadInfo f1494i;
    private long f1495j;
    private int f1496k = 0;

    static {
        if (VersionInfo.getApiVersion().equals(VersionInfo.getApiVersion())) {
            NativeLoader.getInstance().loadLibrary(VersionInfo.getKitName());
            return;
        }
        throw new BaiduMapSDKException("the version of radar is not match with base");
    }

    private RadarSearchManager() {
        BMapManager.init();
        C0653a.m2087a().m2090a((C0514c) this);
    }

    private RadarNearbyResult m1379a(String str) {
        int i = 0;
        if (str == null || str.equals("")) {
            return null;
        }
        RadarNearbyResult radarNearbyResult = new RadarNearbyResult();
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("total");
            radarNearbyResult.totalNum = optInt;
            int optInt2 = jSONObject.optInt(ParamKey.COUNT);
            radarNearbyResult.pageNum = (optInt % f1487l > 0 ? 1 : 0) + (optInt / f1487l);
            radarNearbyResult.pageIndex = this.f1496k;
            if (optInt2 <= 0) {
                return radarNearbyResult;
            }
            JSONArray optJSONArray = jSONObject.optJSONArray("contents");
            if (optJSONArray == null) {
                return radarNearbyResult;
            }
            List arrayList = new ArrayList();
            while (i < optJSONArray.length()) {
                RadarNearbyInfo radarNearbyInfo = new RadarNearbyInfo();
                JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                if (optJSONObject != null) {
                    radarNearbyInfo.userID = optJSONObject.optString("uid");
                    radarNearbyInfo.pt = new LatLng(optJSONObject.optDouble("locy"), optJSONObject.optDouble("locx"));
                    radarNearbyInfo.distance = optJSONObject.optInt("distance");
                    radarNearbyInfo.mobileName = optJSONObject.optString("mb");
                    radarNearbyInfo.mobileOS = optJSONObject.optString(SocializeProtocolConstants.PROTOCOL_KEY_OS);
                    radarNearbyInfo.comments = optJSONObject.optString("comments");
                    radarNearbyInfo.timeStamp = new Date(((long) optJSONObject.optDouble(LogColumns.TIME)) * 1000);
                    arrayList.add(radarNearbyInfo);
                }
                i++;
            }
            radarNearbyResult.infoList = arrayList;
            return radarNearbyResult;
        } catch (JSONException e) {
            e.printStackTrace();
            return radarNearbyResult;
        }
    }

    private RadarSearchError m1380a(int i) {
        RadarSearchError radarSearchError = RadarSearchError.RADAR_NO_ERROR;
        switch (i) {
            case 0:
                return RadarSearchError.RADAR_NO_ERROR;
            case 2:
            case 404:
                return RadarSearchError.RADAR_NETWORK_ERROR;
            case 8:
                return RadarSearchError.RADAR_NETWORK_TIMEOUT;
            case 500:
            case 504:
                return RadarSearchError.RADAR_AK_ERROR;
            case 502:
                return RadarSearchError.RADAR_FORBID_BY_USER;
            case 503:
                return RadarSearchError.RADAR_FORBID_BY_ADMIN;
            case d_ResultType.SUGGESTION_SEARCH /*506*/:
                return RadarSearchError.RADAR_AK_NOT_BIND;
            case 507:
                return RadarSearchError.RADAR_USERID_NOT_EXIST;
            case d_ResultType.LONG_URL /*508*/:
                return RadarSearchError.RADAR_PERMISSION_UNFINISHED;
            default:
                return RadarSearchError.RADAR_NO_RESULT;
        }
    }

    private boolean m1384a(RadarUploadInfo radarUploadInfo) {
        if (radarUploadInfo == null || f1486b == null || f1486b.equals("") || System.currentTimeMillis() - this.f1495j < 5000) {
            return false;
        }
        this.f1494i = radarUploadInfo;
        this.f1495j = System.currentTimeMillis();
        return C0653a.m2087a().m2093a(f1486b, radarUploadInfo.pt, radarUploadInfo.comments);
    }

    public static RadarSearchManager getInstance() {
        if (f1485a == null) {
            f1485a = new RadarSearchManager();
        }
        return f1485a;
    }

    public void addNearbyInfoListener(RadarSearchListener radarSearchListener) {
        if (f1485a != null && radarSearchListener != null) {
            this.f1488c.add(radarSearchListener);
        }
    }

    public void clearUserInfo() {
        if (f1485a != null && f1486b != null && !f1486b.equals("")) {
            C0653a.m2087a().m2091a(f1486b);
        }
    }

    public void destroy() {
        if (f1485a != null) {
            if (this.f1491f) {
                stopUploadAuto();
                this.f1489d.cancel();
            }
            C0653a.m2087a().m2094b();
            C0653a.m2087a().m2096d();
            BMapManager.destroy();
            f1485a = null;
        }
    }

    public boolean nearbyInfoRequest(RadarNearbySearchOption radarNearbySearchOption) {
        int i = 1;
        if (f1485a == null || radarNearbySearchOption == null) {
            return false;
        }
        int i2;
        if (radarNearbySearchOption.f1480e == RadarNearbySearchSortType.distance_from_far_to_near || radarNearbySearchOption.f1480e == RadarNearbySearchSortType.distance_from_near_to_far) {
            if (radarNearbySearchOption.f1480e == RadarNearbySearchSortType.distance_from_far_to_near) {
                i2 = 0;
            } else {
                i = 0;
                i2 = 0;
            }
        } else if (radarNearbySearchOption.f1480e == RadarNearbySearchSortType.time_from_past_to_recent) {
            i2 = 1;
        } else {
            i2 = 1;
            i = 0;
        }
        if ((radarNearbySearchOption.f1478c == null && this.f1494i == null) || f1486b.equals("")) {
            return false;
        }
        LatLng latLng;
        if (radarNearbySearchOption.f1478c != null) {
            latLng = radarNearbySearchOption.f1478c;
        } else if (this.f1494i.pt == null) {
            return false;
        } else {
            latLng = this.f1494i.pt;
        }
        String str = "";
        if (!(radarNearbySearchOption.f1481f == null || radarNearbySearchOption.f1482g == null)) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(radarNearbySearchOption.f1481f);
            long timeInMillis = instance.getTimeInMillis();
            instance.setTime(radarNearbySearchOption.f1482g);
            long timeInMillis2 = instance.getTimeInMillis();
            if (timeInMillis >= timeInMillis2) {
                return false;
            }
            str = String.valueOf(timeInMillis / 1000) + "," + String.valueOf(timeInMillis2 / 1000);
        }
        f1487l = radarNearbySearchOption.f1479d;
        this.f1496k = radarNearbySearchOption.f1477b;
        return C0653a.m2087a().m2092a(f1486b, latLng, radarNearbySearchOption.f1476a, radarNearbySearchOption.f1477b, radarNearbySearchOption.f1479d, i2, i, str);
    }

    public void onGetClearInfoResult(int i) {
        RadarSearchError a = m1380a(i);
        if (this.f1488c != null && this.f1488c.size() > 0) {
            Iterator it = this.f1488c.iterator();
            while (it.hasNext()) {
                ((RadarSearchListener) it.next()).onGetClearInfoState(a);
            }
        }
    }

    public void onGetNearByResult(String str, int i) {
        if (this.f1488c != null && this.f1488c.size() != 0) {
            RadarSearchError a = m1380a(i);
            if (a != RadarSearchError.RADAR_NO_ERROR) {
                Iterator it = this.f1488c.iterator();
                while (it.hasNext()) {
                    ((RadarSearchListener) it.next()).onGetNearbyInfoList(null, a);
                }
                return;
            }
            RadarNearbyResult a2 = m1379a(str);
            if (a2 == null || a2.infoList == null || a2.infoList.size() <= 0) {
                Iterator it2 = this.f1488c.iterator();
                while (it2.hasNext()) {
                    ((RadarSearchListener) it2.next()).onGetNearbyInfoList(a2, RadarSearchError.RADAR_NO_RESULT);
                }
                return;
            }
            Iterator it3 = this.f1488c.iterator();
            while (it3.hasNext()) {
                ((RadarSearchListener) it3.next()).onGetNearbyInfoList(a2, a);
            }
        }
    }

    public void onGetUploadResult(int i) {
        RadarSearchError a = m1380a(i);
        if (this.f1488c != null && this.f1488c.size() > 0) {
            Iterator it = this.f1488c.iterator();
            while (it.hasNext()) {
                ((RadarSearchListener) it.next()).onGetUploadState(a);
            }
        }
    }

    public void removeNearbyInfoListener(RadarSearchListener radarSearchListener) {
        if (f1485a != null && this.f1488c.contains(radarSearchListener)) {
            this.f1488c.remove(radarSearchListener);
        }
    }

    public void setUserID(String str) {
        if (f1485a != null) {
            if (str == null || str.equals("")) {
                f1486b = SysOSUtil.getDeviceID();
            } else {
                f1486b = str;
            }
            this.f1494i = null;
        }
    }

    public void startUploadAuto(RadarUploadInfoCallback radarUploadInfoCallback, int i) {
        if (f1485a != null && i >= 5000 && radarUploadInfoCallback != null && !this.f1491f) {
            this.f1491f = true;
            this.f1493h = radarUploadInfoCallback;
            this.f1492g = new C0515a(this);
            this.f1490e = new C0516b(this);
            this.f1489d.schedule(this.f1490e, 1000, (long) i);
        }
    }

    public void stopUploadAuto() {
        if (f1485a != null && this.f1491f) {
            this.f1491f = false;
            this.f1493h = null;
            this.f1490e.cancel();
            this.f1492g = null;
        }
    }

    public boolean uploadInfoRequest(RadarUploadInfo radarUploadInfo) {
        return f1485a == null ? false : m1384a(radarUploadInfo);
    }
}
