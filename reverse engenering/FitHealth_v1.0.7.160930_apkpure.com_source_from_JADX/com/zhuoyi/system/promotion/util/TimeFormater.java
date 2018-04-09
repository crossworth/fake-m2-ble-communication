package com.zhuoyi.system.promotion.util;

import com.zhuoyi.system.util.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormater {
    public static String formatTime(long time) {
        String result = time;
        if (time > 0) {
            try {
                result = new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date(time));
            } catch (Throwable e) {
                Logger.m3375p(e);
            }
        }
        return result;
    }
}
