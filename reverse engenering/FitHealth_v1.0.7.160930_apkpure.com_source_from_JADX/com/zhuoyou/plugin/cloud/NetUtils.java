package com.zhuoyou.plugin.cloud;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

public class NetUtils {
    public static int getAPNType(Context context) {
        int netType = -1;
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (networkInfo == null) {
            return -1;
        }
        int nType = networkInfo.getType();
        if (nType == 0) {
            String netString = "";
            if (!TextUtils.isEmpty(networkInfo.getExtraInfo())) {
                netString = networkInfo.getExtraInfo().toLowerCase();
            }
            if (netString.equals("cmnet") || netString.equals("uninet")) {
                netType = 3;
            } else {
                netType = 2;
            }
        } else if (nType == 1) {
            netType = 1;
        }
        return netType;
    }
}
