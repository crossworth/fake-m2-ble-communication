package com.droi.sdk.feedback.p018a;

import android.content.Context;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class C0949a {
    public static boolean m2814a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo == null || allNetworkInfo.length <= 0) {
            return false;
        }
        for (NetworkInfo state : allNetworkInfo) {
            if (state.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean m2815a(String str) {
        return Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(str).matches();
    }

    public static boolean m2816b(String str) {
        return Pattern.compile("(0\\d{2,3}\\-?)\\d{7,8}|13\\d{9}|15\\d{9}|17\\d{9}|18\\d{9}|(?<=\\D)[2-9]\\d{7}(?=\\D)").matcher(str).matches();
    }

    public static ShapeDrawable m2812a(int i) {
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f}, new RectF(100.0f, 100.0f, 50.0f, 50.0f), null));
        shapeDrawable.getPaint().setColor(i);
        shapeDrawable.getPaint().setStyle(Style.FILL);
        return shapeDrawable;
    }

    public static StateListDrawable m2813a(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return null;
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, C0949a.m2812a(i));
        stateListDrawable.addState(new int[0], C0949a.m2812a(i2));
        return stateListDrawable;
    }

    public static String m2817c(String str) {
        String str2 = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date parse = simpleDateFormat.parse(str);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            simpleDateFormat2.setTimeZone(TimeZone.getDefault());
            str2 = simpleDateFormat2.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str2;
    }
}
