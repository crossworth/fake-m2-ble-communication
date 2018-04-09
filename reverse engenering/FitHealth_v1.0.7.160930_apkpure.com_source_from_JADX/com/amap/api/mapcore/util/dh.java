package com.amap.api.mapcore.util;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.File;

/* compiled from: ResourcesUtil */
public class dh {
    private static boolean f451a;

    static {
        f451a = false;
        f451a = new File("/system/framework/amap.jar").exists();
    }

    public static AssetManager m547a(Context context) {
        if (context == null) {
            return null;
        }
        AssetManager assets = context.getAssets();
        if (!f451a) {
            return assets;
        }
        try {
            assets.getClass().getDeclaredMethod("addAssetPath", new Class[]{String.class}).invoke(assets, new Object[]{"/system/framework/amap.jar"});
            return assets;
        } catch (Throwable th) {
            ee.m4243a(th, "ResourcesUtil", "getSelfAssets");
            return assets;
        }
    }
}
