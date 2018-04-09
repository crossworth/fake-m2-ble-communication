package com.tencent.open;

import android.app.Activity;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import com.droi.btlib.connection.MessageObj;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.p025a.C1148a;
import com.tencent.open.C1335c.C1275a;
import com.tencent.open.p036a.C1314f;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.HttpUtils;
import com.tencent.open.utils.HttpUtils.HttpStatusException;
import com.tencent.open.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.umeng.facebook.GraphResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class LocationApi extends BaseApi implements C1275a {
    private HandlerThread f3962a;
    private Handler f3963b;
    private Handler f3964c;
    private C1335c f3965d;
    private Bundle f3966e;
    private IUiListener f3967f;

    /* compiled from: ProGuard */
    class C12712 implements Runnable {
        final /* synthetic */ LocationApi f3955a;

        C12712(LocationApi locationApi) {
            this.f3955a = locationApi;
        }

        public void run() {
            if (this.f3955a.f3965d.m3921a()) {
                Message.obtain(this.f3955a.f3964c, 103).sendToTarget();
            } else {
                Message.obtain(this.f3955a.f3964c, 104).sendToTarget();
            }
        }
    }

    /* compiled from: ProGuard */
    private abstract class C1273a implements IRequestListener {
        final /* synthetic */ LocationApi f3959a;

        protected abstract void mo2198a(Exception exception);

        private C1273a(LocationApi locationApi) {
            this.f3959a = locationApi;
        }

        public void onIOException(IOException iOException) {
            mo2198a(iOException);
        }

        public void onMalformedURLException(MalformedURLException malformedURLException) {
            mo2198a(malformedURLException);
        }

        public void onJSONException(JSONException jSONException) {
            mo2198a(jSONException);
        }

        public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
            mo2198a(connectTimeoutException);
        }

        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            mo2198a(socketTimeoutException);
        }

        public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
            mo2198a(networkUnavailableException);
        }

        public void onHttpStatusException(HttpStatusException httpStatusException) {
            mo2198a(httpStatusException);
        }

        public void onUnknowException(Exception exception) {
            mo2198a(exception);
        }
    }

    /* compiled from: ProGuard */
    private class C1274b extends C1273a {
        final /* synthetic */ LocationApi f3960b;
        private IUiListener f3961c;

        public C1274b(LocationApi locationApi, IUiListener iUiListener) {
            this.f3960b = locationApi;
            super();
            this.f3961c = iUiListener;
        }

        public void onComplete(JSONObject jSONObject) {
            if (this.f3961c != null) {
                this.f3961c.onComplete(jSONObject);
            }
            C1314f.m3867b("openSDK_LOG.LocationApi", "TaskRequestListener onComplete GetNearbySwitchEnd:" + SystemClock.elapsedRealtime());
        }

        protected void mo2198a(Exception exception) {
            if (this.f3961c != null) {
                this.f3961c.onError(new UiError(100, exception.getMessage(), null));
            }
        }
    }

    public LocationApi(QQToken qQToken) {
        super(qQToken);
        m3709a();
    }

    public LocationApi(QQAuth qQAuth, QQToken qQToken) {
        super(qQAuth, qQToken);
        m3709a();
    }

    private void m3709a() {
        this.f3965d = new C1335c();
        this.f3962a = new HandlerThread("get_location");
        this.f3962a.start();
        this.f3963b = new Handler(this.f3962a.getLooper());
        this.f3964c = new Handler(this, Global.getContext().getMainLooper()) {
            final /* synthetic */ LocationApi f3954a;

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 101:
                        C1314f.m3867b("openSDK_LOG.LocationApi", "location: get location timeout.");
                        this.f3954a.m3710a(-13, Constants.MSG_LOCATION_TIMEOUT_ERROR);
                        break;
                    case 103:
                        C1314f.m3867b("openSDK_LOG.LocationApi", "location: verify sosocode success.");
                        this.f3954a.f3965d.m3920a(Global.getContext(), this.f3954a);
                        this.f3954a.f3964c.sendEmptyMessageDelayed(101, 10000);
                        break;
                    case 104:
                        C1314f.m3867b("openSDK_LOG.LocationApi", "location: verify sosocode failed.");
                        this.f3954a.m3710a(-14, Constants.MSG_LOCATION_VERIFY_ERROR);
                        break;
                }
                super.handleMessage(message);
            }
        };
    }

    public void searchNearby(Activity activity, Bundle bundle, IUiListener iUiListener) {
        if (m3717c()) {
            this.f3966e = bundle;
            this.f3967f = iUiListener;
            this.f3963b.post(new C12712(this));
        } else if (iUiListener != null) {
            iUiListener.onComplete(m3718d());
        }
    }

    public void deleteLocation(Activity activity, Bundle bundle, IUiListener iUiListener) {
        if (m3717c()) {
            Bundle bundle2;
            if (bundle != null) {
                bundle2 = new Bundle(bundle);
                bundle2.putAll(composeCGIParams());
            } else {
                bundle2 = composeCGIParams();
            }
            bundle2.putString("appid", this.mToken.getAppId());
            bundle2.putString(MessageObj.TIEMSTAMP, String.valueOf(System.currentTimeMillis()));
            String str = "encrytoken";
            bundle2.putString(str, Util.encrypt("tencent&sdk&qazxc***14969%%" + this.mToken.getAccessToken() + this.mToken.getAppId() + this.mToken.getOpenId() + "qzone3.4"));
            C1314f.m3864a("openSDK_LOG.LocationApi", "location: delete params: " + bundle2);
            HttpUtils.requestAsync(this.mToken, Global.getContext(), "http://fusion.qq.com/cgi-bin/qzapps/mapp_lbs_delete.cgi", bundle2, Constants.HTTP_GET, new C1274b(this, iUiListener));
            m3713a("delete_location", GraphResponse.SUCCESS_KEY);
        } else if (iUiListener != null) {
            iUiListener.onComplete(m3718d());
        }
    }

    private void m3711a(Location location) {
        Bundle bundle;
        C1314f.m3864a("openSDK_LOG.LocationApi", "doSearchNearby location: search mParams: " + this.f3966e);
        if (this.f3966e != null) {
            bundle = new Bundle(this.f3966e);
            bundle.putAll(composeCGIParams());
        } else {
            bundle = composeCGIParams();
        }
        String valueOf = String.valueOf(location.getLatitude());
        String valueOf2 = String.valueOf(location.getLongitude());
        bundle.putString("appid", this.mToken.getAppId());
        if (!bundle.containsKey(ParamKey.LATITUDE)) {
            bundle.putString(ParamKey.LATITUDE, valueOf);
        }
        if (!bundle.containsKey(ParamKey.LONGITUDE)) {
            bundle.putString(ParamKey.LONGITUDE, valueOf2);
        }
        if (!bundle.containsKey(ParamKey.PAGE)) {
            bundle.putString(ParamKey.PAGE, String.valueOf(1));
        }
        valueOf2 = "encrytoken";
        bundle.putString(valueOf2, Util.encrypt("tencent&sdk&qazxc***14969%%" + this.mToken.getAccessToken() + this.mToken.getAppId() + this.mToken.getOpenId() + "qzone3.4"));
        C1314f.m3864a("openSDK_LOG.LocationApi", "location: search params: " + bundle);
        C1314f.m3867b("openSDK_LOG.LocationApi", "GetNearbySwitchStart:" + SystemClock.elapsedRealtime());
        HttpUtils.requestAsync(this.mToken, Global.getContext(), "http://fusion.qq.com/cgi-bin/qzapps/mapp_lbs_getnear.cgi", bundle, Constants.HTTP_GET, new C1274b(this, this.f3967f));
    }

    private void m3710a(int i, String str) {
        this.f3965d.m3922b();
        if (this.f3967f != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ret", i);
                jSONObject.put("errMsg", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.f3967f.onComplete(jSONObject);
        }
    }

    private void m3715b() {
        this.f3965d.m3922b();
    }

    private boolean m3717c() {
        ConnectivityManager connectivityManager = (ConnectivityManager) Global.getContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    private JSONObject m3718d() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ret", -9);
            jSONObject.put("errMsg", Constants.MSG_IO_ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private void m3713a(final String str, final String... strArr) {
        this.f3963b.post(new Runnable(this) {
            final /* synthetic */ LocationApi f3958c;

            public void run() {
                if (strArr != null && strArr.length != 0) {
                    C1148a.m3344a(Global.getContext(), this.f3958c.mToken, "search_nearby".equals(str) ? "id_search_nearby" : "id_delete_location", strArr);
                }
            }
        });
    }

    public void onLocationUpdate(Location location) {
        m3711a(location);
        m3715b();
        this.f3964c.removeMessages(101);
    }
}
