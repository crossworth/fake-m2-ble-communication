package com.droi.sdk.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.priv.C0901f;
import com.droi.sdk.core.priv.C0907i;
import com.droi.sdk.core.priv.C0910k;
import com.droi.sdk.core.priv.C0942o;
import com.droi.sdk.core.priv.C0947r;

public abstract class OAuthProvider {
    private String password;
    private String userId;

    public enum AuthProvider {
        QQ,
        Sina,
        Weixin,
        Facebook
    }

    public static OAuthProvider createAuthProvider(AuthProvider authProvider, Context context) {
        switch (authProvider) {
            case QQ:
                return new C0910k();
            case Sina:
                return new C0942o();
            case Weixin:
                return new C0947r();
            case Facebook:
                return new C0901f();
            default:
                return null;
        }
    }

    public static boolean fetchOAUthKeysInBackground(final DroiCallback<Boolean> droiCallback) {
        TaskDispatcher currentTaskDispatcher = TaskDispatcher.currentTaskDispatcher();
        final DroiError droiError = new DroiError(DroiError.UNKNOWN_ERROR, null);
        return DroiTask.create(new DroiRunnable() {
            public void run() {
                droiError.copy(C0907i.m2679a().m2685b());
            }
        }).callback(new DroiRunnable() {
            public void run() {
                if (droiCallback != null) {
                    droiCallback.result(Boolean.valueOf(droiError.isOk()), droiError);
                }
            }
        }, currentTaskDispatcher.name()).runInBackground("TaskDispatcher_DroiBackgroundThread").booleanValue();
    }

    public static DroiError fetchOAuthKeys() {
        return C0907i.m2679a().m2685b();
    }

    public abstract String getId();

    public abstract String getOAuthProviderName();

    public String getPassword() {
        return this.password;
    }

    public abstract String getToken();

    public String getUserId() {
        return this.userId;
    }

    public abstract void handleActivityResult(int i, int i2, Intent intent);

    public abstract boolean isTokenValid();

    protected abstract DroiError requestToken(Activity activity, DroiCallback<Boolean> droiCallback);

    public void setPassword(String str) {
        this.password = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
