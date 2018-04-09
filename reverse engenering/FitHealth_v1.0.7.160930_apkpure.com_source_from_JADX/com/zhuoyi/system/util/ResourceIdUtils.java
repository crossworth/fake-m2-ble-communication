package com.zhuoyi.system.util;

import android.content.Context;
import android.content.res.Resources;

public class ResourceIdUtils {
    public static int getResourceId(Context context, String name) {
        Resources res = context.getResources();
        String[] strs = name.split("\\.");
        return res.getIdentifier(strs[2], strs[1], context.getPackageName());
    }

    public static String getStringByResId(Context context, String name) {
        return context.getString(getResourceId(context, name));
    }
}
