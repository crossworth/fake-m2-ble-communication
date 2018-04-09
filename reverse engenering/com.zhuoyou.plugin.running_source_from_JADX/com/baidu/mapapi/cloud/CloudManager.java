package com.baidu.mapapi.cloud;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MessageCenter;
import com.baidu.mapapi.VersionInfo;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.baidu.platform.comapi.NativeLoader;
import com.baidu.platform.comjni.map.cloud.C0674a;
import com.tencent.tauth.Tencent;
import com.tyd.aidlservice.internal.Constants;
import com.umeng.socialize.common.SocializeConstants;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudManager {
    private static final String f938a = CloudManager.class.getSimpleName();
    private static CloudManager f939c;
    private Bundle f940b = null;
    private C0674a f941d;
    private Handler f942e;
    private CloudListener f943f;

    static class C0471a extends Handler {
        WeakReference<CloudManager> f937a;

        C0471a(CloudManager cloudManager) {
            this.f937a = new WeakReference(cloudManager);
        }

        public void handleMessage(Message message) {
            if (((CloudManager) this.f937a.get()).f941d != null && message.what == 131072 && ((CloudManager) this.f937a.get()).f943f != null) {
                String a;
                switch (message.arg1) {
                    case Tencent.REQUEST_LOGIN /*10001*/:
                        if (message.arg2 == 0) {
                            a = ((CloudManager) this.f937a.get()).m1042b((int) Tencent.REQUEST_LOGIN);
                            if (a == null || "".equals(a)) {
                                ((CloudManager) this.f937a.get()).f943f.onGetSearchResult(null, -1);
                                return;
                            }
                            CloudSearchResult cloudSearchResult = new CloudSearchResult();
                            try {
                                cloudSearchResult.mo1758a(new JSONObject(a));
                                ((CloudManager) this.f937a.get()).f943f.onGetSearchResult(cloudSearchResult, message.arg2);
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                ((CloudManager) this.f937a.get()).f943f.onGetSearchResult(null, -1);
                                return;
                            }
                        }
                        ((CloudManager) this.f937a.get()).f943f.onGetSearchResult(null, ((CloudManager) this.f937a.get()).m1036a(message.arg2));
                        return;
                    case 10002:
                        if (message.arg2 == 0) {
                            a = ((CloudManager) this.f937a.get()).m1042b(10002);
                            if (a == null || "".equals(a)) {
                                ((CloudManager) this.f937a.get()).f943f.onGetDetailSearchResult(null, -1);
                                return;
                            }
                            DetailSearchResult detailSearchResult = new DetailSearchResult();
                            try {
                                detailSearchResult.mo1758a(new JSONObject(a));
                                ((CloudManager) this.f937a.get()).f943f.onGetDetailSearchResult(detailSearchResult, message.arg2);
                                return;
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                ((CloudManager) this.f937a.get()).f943f.onGetDetailSearchResult(null, -1);
                                return;
                            }
                        }
                        ((CloudManager) this.f937a.get()).f943f.onGetDetailSearchResult(null, ((CloudManager) this.f937a.get()).m1036a(message.arg2));
                        return;
                    case 10003:
                        if (message.arg2 == 0) {
                            a = ((CloudManager) this.f937a.get()).m1042b(10003);
                            if (a == null || "".equals(a)) {
                                ((CloudManager) this.f937a.get()).f943f.onGetCloudRgcResult(null, -1);
                                return;
                            }
                            CloudRgcResult cloudRgcResult = new CloudRgcResult();
                            try {
                                cloudRgcResult.parseFromJSON(new JSONObject(a));
                                ((CloudManager) this.f937a.get()).f943f.onGetCloudRgcResult(cloudRgcResult, message.arg2);
                                return;
                            } catch (JSONException e22) {
                                e22.printStackTrace();
                                ((CloudManager) this.f937a.get()).f943f.onGetCloudRgcResult(null, -1);
                                return;
                            }
                        }
                        ((CloudManager) this.f937a.get()).f943f.onGetCloudRgcResult(null, ((CloudManager) this.f937a.get()).m1036a(message.arg2));
                        return;
                    default:
                        return;
                }
            }
        }
    }

    static {
        if (VersionInfo.getApiVersion().equals(VersionInfo.getApiVersion())) {
            NativeLoader.getInstance().loadLibrary(VersionInfo.getKitName());
            return;
        }
        throw new BaiduMapSDKException("the version of cloud is not match with base");
    }

    private int m1036a(int i) {
        if (i > 10000) {
            switch (i + Constants.KD_GENKEYVALIDATION_ERROR) {
                case 2:
                    return 2;
                default:
                    return 1;
            }
        }
        switch (i) {
            case -1:
                return -1;
            case 2:
                return -3;
            case 8:
                return -2;
            case 107:
                return -4;
            default:
                return -1;
        }
    }

    private boolean m1039a(BaseCloudSearchInfo baseCloudSearchInfo) {
        if (baseCloudSearchInfo == null) {
            return false;
        }
        String a = baseCloudSearchInfo.mo1757a();
        if (a == null || a.equals("")) {
            return false;
        }
        this.f940b.putString("url", a);
        this.f941d.m2252a(this.f940b);
        return true;
    }

    private String m1042b(int i) {
        String str;
        try {
            str = new String(this.f941d.m2253a(i), "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            str = null;
        }
        return (str == null || str.trim().length() > 0) ? str : null;
    }

    public static CloudManager getInstance() {
        if (f939c == null) {
            f939c = new CloudManager();
        }
        return f939c;
    }

    public boolean boundSearch(BoundSearchInfo boundSearchInfo) {
        return m1039a((BaseCloudSearchInfo) boundSearchInfo);
    }

    public void destroy() {
        if (this.f941d != null) {
            MessageCenter.unregistMessage(131072, this.f942e);
            this.f941d.m2254b();
            this.f941d = null;
            BMapManager.destroy();
        }
    }

    public boolean detailSearch(DetailSearchInfo detailSearchInfo) {
        if (detailSearchInfo == null) {
            return false;
        }
        String a = detailSearchInfo.mo1757a();
        if (a == null || a.equals("")) {
            return false;
        }
        this.f940b.putString("url", a);
        this.f941d.m2255b(this.f940b);
        return true;
    }

    public void init(CloudListener cloudListener) {
        this.f943f = cloudListener;
        if (this.f941d == null) {
            BMapManager.init();
            this.f941d = new C0674a();
            if (this.f941d.m2251a() == 0) {
                this.f941d = null;
                return;
            }
            this.f940b = new Bundle();
            this.f942e = new C0471a(this);
            MessageCenter.registMessage(131072, this.f942e);
        }
    }

    public boolean localSearch(LocalSearchInfo localSearchInfo) {
        return m1039a((BaseCloudSearchInfo) localSearchInfo);
    }

    public boolean nearbySearch(NearbySearchInfo nearbySearchInfo) {
        return m1039a((BaseCloudSearchInfo) nearbySearchInfo);
    }

    public boolean rgcSearch(CloudRgcInfo cloudRgcInfo) {
        if (cloudRgcInfo == null || cloudRgcInfo.location == null || cloudRgcInfo.location == "") {
            return false;
        }
        this.f940b.putString(SocializeConstants.KEY_LOCATION, cloudRgcInfo.location);
        this.f940b.putInt("geotableid", cloudRgcInfo.geoTableId);
        this.f941d.m2256c(this.f940b);
        return true;
    }
}
