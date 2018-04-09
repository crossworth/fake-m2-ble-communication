package com.droi.sdk.core.priv;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.OAuthProvider;
import com.droi.sdk.internal.DroiLog;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.util.Date;
import java.util.Map;
import org.json.JSONObject;

public final class C0910k extends OAuthProvider {
    private static Tencent f2946e = null;
    private static IUiListener f2947f = null;
    private static final String f2948g = "QQ";
    private static final String f2949h = "QQOAuthProvider";
    private static final String f2950i = "com.tencent.tauth.Tencent";
    private String f2951a;
    private String f2952b;
    private Date f2953c;
    private String f2954d;

    private static boolean m2696a() {
        try {
            return Class.forName(f2950i) != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public String getId() {
        return this.f2952b;
    }

    public String getOAuthProviderName() {
        return "QQ";
    }

    public String getToken() {
        return this.f2951a;
    }

    public void handleActivityResult(int i, int i2, Intent intent) {
        if (f2947f != null) {
            Log.d("Test", "onActivityResult");
            Tencent.onActivityResultData(i, i2, intent, f2947f);
        }
    }

    public boolean isTokenValid() {
        if (this.f2951a == null || this.f2953c == null) {
            return false;
        }
        return this.f2953c.after(new Date());
    }

    protected DroiError requestToken(Activity activity, final DroiCallback<Boolean> droiCallback) {
        if (!C0910k.m2696a()) {
            throw new RuntimeException("Can not found QQ sdk.");
        } else if (C0907i.m2679a().m2686c()) {
            Map a = C0907i.m2679a().m2684a("QQ");
            if (a == null || !a.containsKey("AppId")) {
                DroiLog.m2870e(f2949h, "QQ not found.");
                return new DroiError(DroiError.ERROR, "QQ not found.");
            }
            this.f2954d = (String) a.get("AppId");
            if (f2946e == null) {
                f2946e = Tencent.createInstance(this.f2954d, CorePriv.getContext());
            }
            f2947f = new IUiListener(this) {
                final /* synthetic */ C0910k f2945b;

                public void onCancel() {
                    Log.d(C0910k.f2949h, "cancel");
                    if (droiCallback != null) {
                        droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.USER_CANCELED, "User canceled."));
                    }
                }

                public void onComplete(Object obj) {
                    JSONObject jSONObject = null;
                    try {
                        if (obj instanceof String) {
                            jSONObject = new JSONObject((String) obj);
                        } else if (obj instanceof JSONObject) {
                            jSONObject = (JSONObject) obj;
                        }
                        if (jSONObject == null) {
                            DroiLog.m2870e(C0910k.f2949h, "Type (" + obj.getClass() + ") unknown to handle.");
                            return;
                        }
                        this.f2945b.f2951a = jSONObject.getString("access_token");
                        this.f2945b.f2952b = jSONObject.getString("openid");
                        this.f2945b.f2953c = new Date(new Date().getTime() + ((long) (jSONObject.getInt("expires_in") * 1000)));
                        if (droiCallback != null) {
                            droiCallback.result(Boolean.valueOf(true), new DroiError());
                        }
                    } catch (Exception e) {
                        DroiLog.m2870e(C0910k.f2949h, e.toString());
                        if (droiCallback != null) {
                            droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, e.toString()));
                        }
                    }
                }

                public void onError(UiError uiError) {
                    Log.d(C0910k.f2949h, uiError.errorMessage);
                    if (droiCallback != null) {
                        droiCallback.result(Boolean.valueOf(false), new DroiError(uiError.errorCode, uiError.errorMessage));
                    }
                }
            };
            f2946e.login(activity, "get_user_info", f2947f);
            return new DroiError();
        } else {
            DroiLog.m2870e(f2949h, "Can not find OAuth keys");
            return new DroiError(DroiError.ERROR, "Can not found OAuth keys.");
        }
    }
}
