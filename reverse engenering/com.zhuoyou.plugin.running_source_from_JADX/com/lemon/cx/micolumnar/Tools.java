package com.lemon.cx.micolumnar;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import java.util.Calendar;

public class Tools {
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new Point(dm.widthPixels, dm.heightPixels);
    }

    public static boolean inSameWeek(Calendar cal1, Calendar cal2) {
        int subYear = cal1.get(1) - cal2.get(1);
        if (subYear == 0) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }
        } else if (subYear == 1 && cal2.get(2) == 11) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }
        } else if (subYear == -1 && cal1.get(2) == 11 && cal1.get(3) == cal2.get(3)) {
            return true;
        }
        return false;
    }

    public static boolean inSameMonth(Calendar cal1, Calendar cal2) {
        return cal1.get(2) == cal2.get(2);
    }

    public static boolean inSameDay(Calendar date1, Calendar date2) {
        boolean isSameYear;
        boolean isSameMonth;
        if (date1.get(1) == date2.get(1)) {
            isSameYear = true;
        } else {
            isSameYear = false;
        }
        if (isSameYear && date1.get(2) == date2.get(2)) {
            isSameMonth = true;
        } else {
            isSameMonth = false;
        }
        if (isSameMonth && date1.get(5) == date2.get(5)) {
            return true;
        }
        return false;
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static float px2dip(Context context, float pxValue) {
        return (pxValue / context.getResources().getDisplayMetrics().density) + 0.5f;
    }
}
