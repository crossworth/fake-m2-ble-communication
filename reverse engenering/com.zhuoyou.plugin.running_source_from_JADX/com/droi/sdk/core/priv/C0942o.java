package com.droi.sdk.core.priv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.OAuthProvider;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import java.util.Date;
import java.util.Map;

public class C0942o extends OAuthProvider {
    private static final String f3072a = "com.sina.weibo.sdk.auth.sso.SsoHandler";
    private static final String f3073b = "Sina";
    private SsoHandler f3074c;
    private String f3075d;
    private String f3076e;
    private Date f3077f;
    private String f3078g;

    private static boolean m2779a() {
        try {
            return Class.forName(f3072a) != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public String getId() {
        return this.f3075d;
    }

    public String getOAuthProviderName() {
        return f3073b;
    }

    public String getToken() {
        return this.f3076e;
    }

    public void handleActivityResult(int i, int i2, Intent intent) {
        if (this.f3074c != null) {
            this.f3074c.authorizeCallBack(i, i2, intent);
        }
    }

    public boolean isTokenValid() {
        if (this.f3076e == null || this.f3077f == null) {
            return false;
        }
        return this.f3077f.after(new Date());
    }

    protected DroiError requestToken(Activity activity, final DroiCallback<Boolean> droiCallback) {
        if (!C0942o.m2779a()) {
            throw new RuntimeException("Sina SDK not found.");
        } else if (!C0907i.m2679a().m2686c()) {
            return new DroiError(DroiError.ERROR, "Sina app id handler is not ready.");
        } else {
            Map a = C0907i.m2679a().m2684a(f3073b);
            if (a == null || a.size() == 0) {
                return new DroiError(DroiError.ERROR, "Sina app id is not found.");
            }
            Object obj = a.get("AppId");
            if (obj == null) {
                return new DroiError(DroiError.ERROR, "Sina app id is not defined.");
            }
            this.f3078g = (String) obj;
            this.f3074c = new SsoHandler(activity, new AuthInfo(activity.getApplicationContext(), this.f3078g, "https://api.weibo.com/oauth2/default.html", "email"));
            this.f3074c.authorize(new WeiboAuthListener(this) {
                final /* synthetic */ C0942o f3071b;

                public void onCancel() {
                    if (droiCallback != null) {
                        droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.USER_CANCELED, null));
                    }
                }

                public void onComplete(Bundle bundle) {
                    if (bundle != null) {
                        this.f3071b.f3075d = bundle.getString("uid");
                        this.f3071b.f3076e = bundle.getString("access_token");
                        this.f3071b.f3077f = new Date(new Date().getTime() + ((long) (Integer.parseInt(bundle.getString("expires_in")) * 1000)));
                        if (droiCallback != null) {
                            droiCallback.result(Boolean.valueOf(true), new DroiError());
                        }
                    } else if (droiCallback != null) {
                        droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, "Sina SDK result empty."));
                    }
                }

                public void onWeiboException(WeiboException weiboException) {
                    if (droiCallback != null) {
                        droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, weiboException.toString()));
                    }
                }
            });
            return new DroiError();
        }
    }
}
