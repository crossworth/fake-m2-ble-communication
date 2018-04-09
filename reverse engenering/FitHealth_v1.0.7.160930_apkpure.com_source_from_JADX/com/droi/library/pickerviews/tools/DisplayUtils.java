package com.droi.library.pickerviews.tools;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class DisplayUtils {
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new Point(dm.widthPixels, dm.heightPixels);
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = 0;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            statusHeight = context.getResources().getDimensionPixelSize(Integer.parseInt(clazz.getField("status_bar_height").get(clazz.newInstance()).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static int getTextWidth(Paint paint, String str) {
        int iRet = 0;
        if (str != null && str.length() > 0) {
            int len = str.length();
            float[] widths = new float[len];
            paint.getTextWidths(str, widths);
            for (int j = 0; j < len; j++) {
                iRet += (int) Math.ceil((double) widths[j]);
            }
        }
        return iRet;
    }
}
