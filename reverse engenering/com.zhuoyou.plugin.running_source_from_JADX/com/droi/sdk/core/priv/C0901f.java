package com.droi.sdk.core.priv;

import android.app.Activity;
import android.content.Intent;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.OAuthProvider;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import java.util.Arrays;

public class C0901f extends OAuthProvider {
    private static final String f2924a = "facebook";
    private String f2925b;
    private CallbackManager f2926c = Factory.create();

    public String getId() {
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        return currentAccessToken == null ? null : currentAccessToken.getUserId();
    }

    public String getOAuthProviderName() {
        return f2924a;
    }

    public String getToken() {
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        return currentAccessToken == null ? null : currentAccessToken.getToken();
    }

    public void handleActivityResult(int i, int i2, Intent intent) {
        if (this.f2926c != null) {
            this.f2926c.onActivityResult(i, i2, intent);
        }
    }

    public boolean isTokenValid() {
        AccessToken currentAccessToken = AccessToken.getCurrentAccessToken();
        return (currentAccessToken == null || currentAccessToken.isExpired()) ? false : true;
    }

    protected DroiError requestToken(Activity activity, final DroiCallback<Boolean> droiCallback) {
        LoginManager instance = LoginManager.getInstance();
        instance.registerCallback(this.f2926c, new FacebookCallback<LoginResult>(this) {
            final /* synthetic */ C0901f f2923b;

            public void m2667a(LoginResult loginResult) {
                loginResult.getAccessToken();
                if (droiCallback != null) {
                    droiCallback.result(Boolean.valueOf(true), new DroiError());
                }
            }

            public void onCancel() {
                if (droiCallback != null) {
                    droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.USER_CANCELED, null));
                }
            }

            public void onError(FacebookException facebookException) {
                if (droiCallback != null) {
                    droiCallback.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, facebookException.toString()));
                }
            }

            public /* synthetic */ void onSuccess(Object obj) {
                m2667a((LoginResult) obj);
            }
        });
        instance.logInWithReadPermissions(activity, Arrays.asList(new String[]{"public_profile", "email"}));
        return new DroiError();
    }
}
