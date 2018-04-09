package com.tencent.connect.avatar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.Util;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class QQAvatar extends BaseApi {
    private IUiListener f4537a;

    public QQAvatar(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    private Intent m4666a() {
        Intent intent = new Intent();
        intent.setClass(this.mContext, ImageActivity.class);
        return intent;
    }

    public void setAvatar(Activity activity, Uri uri, IUiListener iUiListener, int i) {
        if (this.f4537a != null) {
            this.f4537a.onCancel();
        }
        this.f4537a = iUiListener;
        Bundle bundle = new Bundle();
        bundle.putString("picture", uri.toString());
        bundle.putInt("exitAnim", i);
        bundle.putString("appid", this.mToken.getAppId());
        bundle.putString("access_token", this.mToken.getAccessToken());
        bundle.putLong("expires_in", this.mToken.getExpireTimeInSecond());
        bundle.putString("openid", this.mToken.getOpenId());
        this.mActivityIntent = m4666a();
        if (hasActivityForIntent()) {
            m4667a(activity, bundle);
        }
    }

    private void m4667a(Activity activity, Bundle bundle) {
        m4668a(bundle);
        this.mActivityIntent.putExtra(Constants.KEY_ACTION, "action_avatar");
        this.mActivityIntent.putExtra(Constants.KEY_PARAMS, bundle);
        startAssitActivity(activity, this.f4537a);
    }

    private void m4668a(Bundle bundle) {
        if (this.mToken != null) {
            bundle.putString("appid", this.mToken.getAppId());
            if (this.mToken.isSessionValid()) {
                bundle.putString(Constants.PARAM_KEY_STR, this.mToken.getAccessToken());
                bundle.putString(Constants.PARAM_KEY_TYPE, "0x80");
            }
            String openId = this.mToken.getOpenId();
            if (openId != null) {
                bundle.putString("hopenid", openId);
            }
            bundle.putString(Constants.PARAM_PLATFORM, "androidqz");
            try {
                bundle.putString(Constants.PARAM_PLATFORM_ID, this.mContext.getSharedPreferences(Constants.PREFERENCE_PF, 0).getString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF));
            } catch (Exception e) {
                e.printStackTrace();
                bundle.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
            }
        }
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            int intExtra = intent.getIntExtra(Constants.KEY_ERROR_CODE, 0);
            if (intExtra == 0) {
                String stringExtra = intent.getStringExtra(Constants.KEY_RESPONSE);
                if (stringExtra != null) {
                    try {
                        this.f4537a.onComplete(Util.parseJson(stringExtra));
                        return;
                    } catch (JSONException e) {
                        this.f4537a.onError(new UiError(-4, Constants.MSG_JSON_ERROR, stringExtra));
                        return;
                    }
                }
                this.f4537a.onComplete(new JSONObject());
                return;
            }
            this.f4537a.onError(new UiError(intExtra, intent.getStringExtra(Constants.KEY_ERROR_MSG), intent.getStringExtra(Constants.KEY_ERROR_DETAIL)));
            return;
        }
        this.f4537a.onCancel();
    }
}
