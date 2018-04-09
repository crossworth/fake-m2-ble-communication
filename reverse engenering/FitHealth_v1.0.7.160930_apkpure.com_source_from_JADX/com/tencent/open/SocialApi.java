package com.tencent.open;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.utils.Util;

/* compiled from: ProGuard */
public class SocialApi {
    private SocialApiIml f2664a;

    public SocialApi(Context context, QQToken qQToken) {
        this.f2664a = new SocialApiIml(context, qQToken);
    }

    public void invite(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f2664a.invite(activity, bundle, iUiListener);
    }

    public void story(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f2664a.story(activity, bundle, iUiListener);
    }

    public void gift(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f2664a.gift(activity, bundle, iUiListener);
    }

    public void ask(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f2664a.ask(activity, bundle, iUiListener);
    }

    public void reactive(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f2664a.reactive(activity, bundle, iUiListener);
    }

    public void brag(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f2664a.brag(activity, bundle, iUiListener);
    }

    public void challenge(Activity activity, Bundle bundle, IUiListener iUiListener) {
        this.f2664a.challenge(activity, bundle, iUiListener);
    }

    public void grade(Activity activity, Bundle bundle, IUiListener iUiListener) {
        bundle.putString("version", Util.getAppVersion(activity));
        this.f2664a.grade(activity, bundle, iUiListener);
    }

    public void voice(Activity activity, Bundle bundle, IUiListener iUiListener) {
        bundle.putString("version", Util.getAppVersion(activity));
        this.f2664a.voice(activity, bundle, iUiListener);
    }

    public boolean checkVoiceApi(Activity activity, Bundle bundle, IUiListener iUiListener) {
        bundle.putString("version", Util.getAppVersion(activity));
        this.f2664a.grade(activity, bundle, iUiListener);
        return true;
    }
}
