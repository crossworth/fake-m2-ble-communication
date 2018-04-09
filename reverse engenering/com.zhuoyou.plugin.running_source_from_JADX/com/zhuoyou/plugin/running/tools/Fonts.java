package com.zhuoyou.plugin.running.tools;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import com.zhuoyou.plugin.running.app.TheApp;

public class Fonts {
    private static AssetManager assets = TheApp.getInstance().getAssets();
    public static Typeface number = Typeface.createFromAsset(assets, "fonts/ROBOTO-LIGHT.TTF");
}
