package com.droi.sdk.push.utils;

import android.content.Context;
import com.umeng.facebook.share.internal.ShareConstants;

public class C1013h {
    public static int m3143a(Context context, String str) {
        return context.getResources().getIdentifier(str, "layout", context.getPackageName());
    }

    public static int m3144b(Context context, String str) {
        return context.getResources().getIdentifier(str, "string", context.getPackageName());
    }

    public static int m3145c(Context context, String str) {
        return context.getResources().getIdentifier(str, "drawable", context.getPackageName());
    }

    public static int m3146d(Context context, String str) {
        return context.getResources().getIdentifier(str, ShareConstants.WEB_DIALOG_PARAM_ID, context.getPackageName());
    }
}
