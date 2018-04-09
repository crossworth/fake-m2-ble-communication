package com.droi.sdk.core.priv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.OAuthProvider;
import com.droi.sdk.oauth.DroiOauth;
import com.droi.sdk.oauth.callback.DroiAccountCallBack;
import com.umeng.socialize.handler.TwitterPreferences;
import java.util.Date;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class C0898d extends OAuthProvider {
    private static final String f2884a = "com.droi.sdk.oauth.DroiOauth";
    private static final String f2885b = "Droi";
    private String f2886c;
    private String f2887d;
    private String f2888e;
    private Date f2889f;

    public C0898d(Context context) {
        if (C0898d.m2665a()) {
            DroiOauth.initialize(context);
        }
    }

    private static boolean m2665a() {
        try {
            return Class.forName(f2884a) != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public String getId() {
        return this.f2887d;
    }

    public String getOAuthProviderName() {
        return null;
    }

    public String getToken() {
        return this.f2886c;
    }

    public void handleActivityResult(int i, int i2, Intent intent) {
    }

    public boolean isTokenValid() {
        if (this.f2886c == null || this.f2889f == null) {
            return false;
        }
        return this.f2889f.after(new Date());
    }

    protected DroiError requestToken(Activity activity, final DroiCallback<Boolean> droiCallback) {
        if (!C0898d.m2665a()) {
            throw new RuntimeException("Droi SDK not found.");
        } else if (!C0907i.m2679a().m2686c()) {
            return new DroiError(DroiError.ERROR, "Droi app id handler is not ready.");
        } else {
            Map a = C0907i.m2679a().m2684a("Droi");
            if (a == null || a.size() == 0) {
                return new DroiError(DroiError.ERROR, "Droi app id is not found.");
            }
            Object obj = a.get("AppId");
            if (obj == null) {
                return new DroiError(DroiError.ERROR, "Droi app id is not defined.");
            }
            this.f2888e = (String) obj;
            DroiOauth.requestTokenAuth(activity, new DroiAccountCallBack(this) {
                final /* synthetic */ C0898d f2883b;

                public void onError(String str) {
                    if (droiCallback != null) {
                        droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, str));
                    }
                }

                public void onSuccess(String str) {
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        this.f2883b.f2887d = jSONObject.getString("openid");
                        this.f2883b.f2886c = jSONObject.getString(TwitterPreferences.TOKEN);
                        this.f2883b.f2889f = new Date(jSONObject.getLong("expire"));
                        if (droiCallback != null) {
                            droiCallback.result(Boolean.valueOf(true), new DroiError());
                        }
                    } catch (JSONException e) {
                        this.f2883b.f2887d = this.f2883b.f2886c = null;
                        this.f2883b.f2889f = null;
                        if (droiCallback != null) {
                            droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, e.toString()));
                        }
                    }
                }
            });
            return new DroiError();
        }
    }
}
