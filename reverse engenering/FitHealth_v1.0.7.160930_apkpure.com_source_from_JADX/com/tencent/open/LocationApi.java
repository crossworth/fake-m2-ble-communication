package com.tencent.open;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.facebook.GraphResponse;
import com.sina.weibo.sdk.constant.WBPageConstants.ParamKey;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.p010a.C0687a;
import com.tencent.open.C0810d.C0809a;
import com.tencent.p004a.p005a.C1711d;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.HttpUtils.HttpStatusException;
import com.tencent.utils.HttpUtils.NetworkUnavailableException;
import com.tencent.utils.Util;
import com.zhuoyou.plugin.bluetooth.data.MessageObj;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class LocationApi extends BaseApi implements C0809a {
    private HandlerThread f4603a;
    private Handler f4604b;
    private Handler f4605c;
    private C0810d f4606d;
    private Bundle f4607e;
    private IUiListener f4608f;

    /* compiled from: ProGuard */
    class C07762 implements Runnable {
        final /* synthetic */ LocationApi f2662a;

        C07762(LocationApi locationApi) {
            this.f2662a = locationApi;
        }

        public void run() {
            if (this.f2662a.f4606d.m2592a()) {
                Message.obtain(this.f2662a.f4605c, 103).sendToTarget();
            } else {
                Message.obtain(this.f2662a.f4605c, 104).sendToTarget();
            }
        }
    }

    /* compiled from: ProGuard */
    private abstract class C1724b implements IRequestListener {
        final /* synthetic */ LocationApi f4602b;

        protected abstract void mo3083a(Exception exception);

        private C1724b(LocationApi locationApi) {
            this.f4602b = locationApi;
        }

        public void onIOException(IOException iOException) {
            mo3083a(iOException);
        }

        public void onMalformedURLException(MalformedURLException malformedURLException) {
            mo3083a(malformedURLException);
        }

        public void onJSONException(JSONException jSONException) {
            mo3083a(jSONException);
        }

        public void onConnectTimeoutException(ConnectTimeoutException connectTimeoutException) {
            mo3083a(connectTimeoutException);
        }

        public void onSocketTimeoutException(SocketTimeoutException socketTimeoutException) {
            mo3083a(socketTimeoutException);
        }

        public void onNetworkUnavailableException(NetworkUnavailableException networkUnavailableException) {
            mo3083a(networkUnavailableException);
        }

        public void onHttpStatusException(HttpStatusException httpStatusException) {
            mo3083a(httpStatusException);
        }

        public void onUnknowException(Exception exception) {
            mo3083a(exception);
        }
    }

    /* compiled from: ProGuard */
    private class C2004a extends C1724b {
        final /* synthetic */ LocationApi f5430a;
        private IUiListener f5431c;

        public C2004a(LocationApi locationApi, IUiListener iUiListener) {
            this.f5430a = locationApi;
            super();
            this.f5431c = iUiListener;
        }

        public void onComplete(JSONObject jSONObject) {
            if (this.f5431c != null) {
                this.f5431c.onComplete(jSONObject);
            }
        }

        protected void mo3083a(Exception exception) {
            if (this.f5431c != null) {
                this.f5431c.onError(new UiError(100, exception.getMessage(), null));
            }
        }
    }

    public LocationApi(Context context, QQToken qQToken) {
        super(context, qQToken);
        m4739a();
    }

    public LocationApi(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(context, qQAuth, qQToken);
        m4739a();
    }

    private void m4739a() {
        this.f4606d = new C0810d();
        this.f4603a = new HandlerThread("get_location");
        this.f4603a.start();
        this.f4604b = new Handler(this.f4603a.getLooper());
        this.f4605c = new Handler(this, this.mContext.getMainLooper()) {
            final /* synthetic */ LocationApi f2663a;

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 101:
                        C1711d.m4638b("openSDK_LOG", "location: get location timeout.");
                        this.f2663a.m4740a(-13, Constants.MSG_LOCATION_TIMEOUT_ERROR);
                        break;
                    case 103:
                        C1711d.m4638b("openSDK_LOG", "location: verify sosocode success.");
                        this.f2663a.f4606d.m2591a(this.f2663a.mContext, this.f2663a);
                        this.f2663a.f4605c.sendEmptyMessageDelayed(101, 10000);
                        break;
                    case 104:
                        C1711d.m4638b("openSDK_LOG", "location: verify sosocode failed.");
                        this.f2663a.m4740a(-14, Constants.MSG_LOCATION_VERIFY_ERROR);
                        break;
                }
                super.handleMessage(message);
            }
        };
    }

    public void searchNearby(Activity activity, Bundle bundle, IUiListener iUiListener) {
        if (m4747c()) {
            this.f4607e = bundle;
            this.f4608f = iUiListener;
            this.f4604b.post(new C07762(this));
        } else if (iUiListener != null) {
            iUiListener.onComplete(m4749d());
        }
    }

    public void deleteLocation(Activity activity, Bundle bundle, IUiListener iUiListener) {
        if (m4747c()) {
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
            C1711d.m4638b("openSDK_LOG", "location: delete params: " + bundle2);
            HttpUtils.requestAsync(this.mToken, this.mContext, "http://fusion.qq.com/cgi-bin/qzapps/mapp_lbs_delete.cgi", bundle2, "GET", new C2004a(this, iUiListener));
            m4743a("delete_location", GraphResponse.SUCCESS_KEY);
        } else if (iUiListener != null) {
            iUiListener.onComplete(m4749d());
        }
    }

    private void m4741a(Location location) {
        Bundle bundle;
        C1711d.m4638b("openSDK_LOG", "location: search mParams: " + this.f4607e);
        if (this.f4607e != null) {
            bundle = new Bundle(this.f4607e);
            bundle.putAll(composeCGIParams());
        } else {
            bundle = composeCGIParams();
        }
        String valueOf = String.valueOf(location.getLatitude());
        String valueOf2 = String.valueOf(location.getLongitude());
        bundle.putString("appid", this.mToken.getAppId());
        if (!bundle.containsKey("latitude")) {
            bundle.putString("latitude", valueOf);
        }
        if (!bundle.containsKey("longitude")) {
            bundle.putString("longitude", valueOf2);
        }
        if (!bundle.containsKey(ParamKey.PAGE)) {
            bundle.putString(ParamKey.PAGE, String.valueOf(1));
        }
        valueOf2 = "encrytoken";
        bundle.putString(valueOf2, Util.encrypt("tencent&sdk&qazxc***14969%%" + this.mToken.getAccessToken() + this.mToken.getAppId() + this.mToken.getOpenId() + "qzone3.4"));
        C1711d.m4638b("openSDK_LOG", "location: search params: " + bundle);
        HttpUtils.requestAsync(this.mToken, this.mContext, "http://fusion.qq.com/cgi-bin/qzapps/mapp_lbs_getnear.cgi", bundle, "GET", new C2004a(this, this.f4608f));
    }

    private void m4740a(int i, String str) {
        this.f4606d.m2593b();
        if (this.f4608f != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("ret", i);
                jSONObject.put("errMsg", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.f4608f.onComplete(jSONObject);
        }
    }

    private void m4745b() {
        this.f4606d.m2593b();
    }

    private boolean m4747c() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    private JSONObject m4749d() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ret", -9);
            jSONObject.put("errMsg", Constants.MSG_IO_ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    private void m4743a(final String str, final String... strArr) {
        this.f4604b.post(new Runnable(this) {
            final /* synthetic */ LocationApi f2661c;

            public void run() {
                if (strArr != null && strArr.length != 0) {
                    C0687a.m2306a(this.f2661c.mContext, this.f2661c.mToken, "search_nearby".equals(str) ? "id_search_nearby" : "id_delete_location", strArr);
                }
            }
        });
    }

    public void onLocationUpdate(Location location) {
        m4741a(location);
        m4745b();
        this.f4605c.removeMessages(101);
    }
}
