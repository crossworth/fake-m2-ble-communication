package com.zhuoyi.system.network.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.zhuoyi.system.network.object.NetworkAddr;
import com.zhuoyi.system.util.Logger;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

public class NetworkUtils {
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
        if (cm == null) {
            return false;
        }
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected() && netinfo.isAvailable()) {
            return true;
        }
        return false;
    }

    public static byte getNetworkType(Context context) {
        ConnectivityManager connectivity = null;
        try {
            connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Exception e) {
        }
        if (connectivity == null) {
            return (byte) 0;
        }
        NetworkInfo info = connectivity.getActiveNetworkInfo();
        if (info == null || !info.isConnected() || !info.isAvailable()) {
            return (byte) 0;
        }
        if (info.getType() == 0) {
            switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
                case 0:
                    return (byte) 4;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    return (byte) 1;
                case 8:
                case 9:
                case 10:
                    return (byte) 2;
            }
        } else if (info.getType() == 1) {
            return (byte) 3;
        }
        return (byte) 0;
    }

    public static long getLengthByURLConnection(URLConnection urlConnection) {
        if (urlConnection == null) {
            return 0;
        }
        int i = 1;
        while (true) {
            String sHeader = urlConnection.getHeaderFieldKey(i);
            if (sHeader == null) {
                return 0;
            }
            if (sHeader.equalsIgnoreCase("Content-Length")) {
                return (long) Integer.parseInt(urlConnection.getHeaderField(sHeader));
            }
            i++;
        }
    }

    public static boolean isAddrEnable(NetworkAddr addr) {
        try {
            new URL("http://" + addr.getServerAddress() + "/macs").openConnection();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String getDataFromPromAppInfoReserved1(String data, String tag) {
        String need = "";
        Logger.m3373e("PromAppInfo", "data=" + data + "       tag=" + tag);
        if (TextUtils.isEmpty(data) || TextUtils.isEmpty(tag)) {
            return need;
        }
        if (!data.contains(tag)) {
            return need;
        }
        String[] diffAll = data.split(";");
        int count = diffAll.length;
        for (int i = 0; i < count; i++) {
            if (!TextUtils.isEmpty(diffAll[i])) {
                int first = diffAll[i].indexOf("=");
                Logger.m3373e("PromAppInfo", "first=" + first);
                if (first >= 0) {
                    String firstStr = diffAll[i].substring(0, first);
                    String secondStr = diffAll[i].substring(first + 1);
                    Logger.m3373e("PromAppInfo", "firstStr=" + firstStr);
                    Logger.m3373e("PromAppInfo", "secondStr=" + secondStr);
                    if (tag.equals(firstStr)) {
                        need = secondStr;
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        Logger.m3373e("PromAppInfo", "need=" + need);
        return need;
    }

    public static long getPushDisplayTime(String time) {
        Logger.m3373e("PromAppInfo", "displayTime = " + time);
        long displayTime = System.currentTimeMillis();
        Logger.m3373e("PromAppInfo", "currentTimeMillis = " + displayTime);
        if (TextUtils.isEmpty(time)) {
            return displayTime;
        }
        String[] timeStrings = time.split(":");
        if (timeStrings.length > 1) {
            Calendar c = Calendar.getInstance();
            if (!TextUtils.isEmpty(timeStrings[0])) {
                c.set(11, Integer.valueOf(timeStrings[0]).intValue());
            }
            c.set(12, Integer.valueOf(timeStrings[1]).intValue());
            displayTime = c.getTimeInMillis();
        }
        Logger.m3373e("PromAppInfo", "displayTimeMillis = " + displayTime);
        return displayTime;
    }

    public static boolean isShowPush(Context context, String reserved1) {
        String netType = getDataFromPromAppInfoReserved1(reserved1, "pushNetwork");
        int curNetType = getNetworkType(context);
        if ((curNetType == 0 || !"2".equals(netType)) && (curNetType != 3 || !"1".equals(netType))) {
            return false;
        }
        return true;
    }

    public static boolean isYesterday(String reserved1) {
        String yesterday = getDataFromPromAppInfoReserved1(reserved1, "todyTime");
        if (TextUtils.isEmpty(yesterday)) {
            return false;
        }
        long yesterdayMillis = Long.parseLong(yesterday);
        Logger.m3373e("PromAppInfo", "timeMillis = " + (System.currentTimeMillis() - yesterdayMillis));
        if (System.currentTimeMillis() - yesterdayMillis > 86400000) {
            return true;
        }
        return false;
    }
}
