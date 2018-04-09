package com.zhuoyi.account.util;

import android.content.Context;
import android.content.res.Resources;
import com.facebook.internal.AnalyticsEvents;

public final class MyResource {
    private static Context mContext;
    private static String mPackageName;
    private static Resources mResources;

    public MyResource(Context paramContext) {
        mContext = paramContext;
    }

    private static final int baseInfo(String paramString1, String paramString2) {
        if (mResources != null) {
            return mResources.getIdentifier(paramString1, paramString2, getPackageName());
        }
        if (mResources == null) {
            mResources = mContext.getApplicationContext().getResources();
        }
        return mResources.getIdentifier(paramString1, paramString2, getPackageName());
    }

    private static final String getPackageName() {
        if (mPackageName == null) {
            mPackageName = mContext.getApplicationContext().getPackageName();
        }
        return mPackageName;
    }

    public static final int getDrawable(String paramString) {
        return baseInfo(paramString, "drawable");
    }

    public static final int getId(String paramString) {
        return baseInfo(paramString, "id");
    }

    public static final int getLayout(String paramString) {
        return baseInfo(paramString, "layout");
    }

    public static final int getString(String paramString) {
        return baseInfo(paramString, "string");
    }

    public static final int getRaw(String paramString) {
        return baseInfo(paramString, "raw");
    }

    public static final int getStyle(String paramString) {
        return baseInfo(paramString, AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE);
    }
}
