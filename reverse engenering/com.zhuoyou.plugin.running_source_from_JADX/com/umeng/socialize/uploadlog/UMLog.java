package com.umeng.socialize.uploadlog;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.utils.ContextUtil;

public class UMLog {
    private static boolean isAuth = false;
    private static boolean isShare = false;

    public static void putShare() {
        if (ContextUtil.getContext() != null && !isShare) {
            Editor edit = ContextUtil.getContext().getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).edit();
            edit.putBoolean("share", true);
            edit.commit();
            isShare = true;
        }
    }

    public static void putAuth() {
        if (ContextUtil.getContext() != null && !isAuth) {
            Editor edit = ContextUtil.getContext().getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).edit();
            edit.putBoolean("auth", true);
            edit.commit();
            isShare = true;
        }
    }

    public static Bundle getShareAndAuth() {
        Bundle bundle = new Bundle();
        if (ContextUtil.getContext() != null) {
            SharedPreferences sharedPreferences = ContextUtil.getContext().getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0);
            bundle.putBoolean("share", sharedPreferences.getBoolean("share", false));
            bundle.putBoolean("auth", sharedPreferences.getBoolean("auth", false));
        } else {
            bundle.putBoolean("share", false);
            bundle.putBoolean("auth", false);
        }
        return bundle;
    }
}
