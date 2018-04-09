package com.tencent.connect.avatar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.Constants;
import com.tencent.connect.common.UIListenerManager;
import com.tencent.open.p037b.C1322d;
import com.tencent.open.utils.Global;
import com.tencent.tauth.IUiListener;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;

/* compiled from: ProGuard */
public class QQAvatar extends BaseApi {
    private IUiListener f3626a;

    public QQAvatar(QQToken qQToken) {
        super(qQToken);
    }

    private Intent m3438a(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, ImageActivity.class);
        return intent;
    }

    public void setAvatar(Activity activity, Uri uri, IUiListener iUiListener, int i) {
        if (this.f3626a != null) {
            this.f3626a.onCancel();
        }
        this.f3626a = iUiListener;
        Bundle bundle = new Bundle();
        bundle.putString("picture", uri.toString());
        bundle.putInt("exitAnim", i);
        bundle.putString("appid", this.mToken.getAppId());
        bundle.putString("access_token", this.mToken.getAccessToken());
        bundle.putLong("expires_in", this.mToken.getExpireTimeInSecond());
        bundle.putString("openid", this.mToken.getOpenId());
        Intent a = m3438a(activity);
        if (hasActivityForIntent(a)) {
            m3439a(activity, bundle, a);
            C1322d.m3896a().m3898a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SET_AVATAR, Constants.VIA_REPORT_TYPE_SET_AVATAR, "18", "0");
            return;
        }
        C1322d.m3896a().m3898a(this.mToken.getOpenId(), this.mToken.getAppId(), Constants.VIA_SET_AVATAR, Constants.VIA_REPORT_TYPE_SET_AVATAR, "18", "1");
    }

    private void m3439a(Activity activity, Bundle bundle, Intent intent) {
        m3440a(bundle);
        intent.putExtra(Constants.KEY_ACTION, "action_avatar");
        intent.putExtra(Constants.KEY_PARAMS, bundle);
        UIListenerManager.getInstance().setListenerWithRequestcode(Constants.REQUEST_AVATER, this.f3626a);
        startAssitActivity(activity, intent, (int) Constants.REQUEST_AVATER);
    }

    private void m3440a(Bundle bundle) {
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
            bundle.putString("platform", "androidqz");
            try {
                bundle.putString(Constants.PARAM_PLATFORM_ID, Global.getContext().getSharedPreferences(Constants.PREFERENCE_PF, 0).getString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF));
            } catch (Exception e) {
                e.printStackTrace();
                bundle.putString(Constants.PARAM_PLATFORM_ID, Constants.DEFAULT_PF);
            }
        }
        bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_VERSION, Constants.SDK_VERSION);
        bundle.putString("sdkp", "a");
    }
}
