package com.tencent.open;

import android.app.Activity;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;

/* compiled from: ProGuard */
public class SocialApi {
    private SocialApiIml f3968a;

    public SocialApi(QQToken qQToken) {
        this.f3968a = new SocialApiIml(qQToken);
    }

    public void invite(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3968a.invite(activity, bundle, iUiListener);
    }

    public void story(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3968a.story(activity, bundle, iUiListener);
    }

    public void gift(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3968a.gift(activity, bundle, iUiListener);
    }

    public void ask(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3968a.ask(activity, bundle, iUiListener);
    }

    public void reactive(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3968a.reactive(activity, bundle, iUiListener);
    }

    public void brag(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3968a.brag(activity, bundle, iUiListener);
    }

    public void challenge(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f3968a.challenge(activity, bundle, iUiListener);
    }

    public void grade(Activity activity, Bundle bundle, IUiListener iUiListener) {
        bundle.putString("version", Util.getAppVersion(activity));
        this.f3968a.grade(activity, bundle, iUiListener);
    }

    public void voice(Activity activity, Bundle bundle, IUiListener iUiListener) {
        bundle.putString("version", Util.getAppVersion(activity));
        this.f3968a.voice(activity, bundle, iUiListener);
    }

    public boolean checkVoiceApi(Activity activity, Bundle bundle, IUiListener iUiListener) {
        bundle.putString("version", Util.getAppVersion(activity));
        this.f3968a.grade(activity, bundle, iUiListener);
        return true;
    }
}
