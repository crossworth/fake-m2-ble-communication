package com.zhuoyou.plugin.album;

import android.content.Context;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class ScreenHelper {
    private static int mHeight;
    private static int mWidth;

    public static int getScreenWidth(Context context) {
        if (mWidth == 0) {
            calculateScreenDimensions(context);
        }
        return mWidth;
    }

    public static int getScreenHeight(Context context) {
        if (mHeight == 0) {
            calculateScreenDimensions(context);
        }
        return mHeight;
    }

    private static void calculateScreenDimensions(Context context) {
        Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (VERSION.SDK_INT >= 13) {
            Point point = new Point();
            display.getSize(point);
            mWidth = point.x;
            mHeight = point.y;
            return;
        }
        mWidth = display.getWidth();
        mHeight = display.getHeight();
    }

    public static float dpToPx(Context context, int dp) {
        return TypedValue.applyDimension(1, (float) dp, context.getResources().getDisplayMetrics());
    }
}
