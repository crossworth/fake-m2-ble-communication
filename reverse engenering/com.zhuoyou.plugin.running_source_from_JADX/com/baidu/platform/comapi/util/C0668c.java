package com.baidu.platform.comapi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
import com.baidu.platform.comjni.engine.AppEngine;

public class C0668c {
    public static boolean f2179a = false;
    public static String f2180b = "";
    public static int f2181c = 0;

    public static void m2150a(Context context) {
        NetworkInfo b = C0668c.m2152b(context);
        if (b != null && b.isAvailable()) {
            String toLowerCase = b.getTypeName().toLowerCase();
            if (toLowerCase.equals("wifi") && b.isConnected()) {
                AppEngine.SetProxyInfo(null, 0);
                f2179a = false;
            } else if (toLowerCase.equals("mobile") || (toLowerCase.equals("wifi") && !C0668c.m2151a(b))) {
                String extraInfo = b.getExtraInfo();
                f2179a = false;
                if (extraInfo != null) {
                    extraInfo = extraInfo.toLowerCase();
                    if (extraInfo.startsWith("cmwap") || extraInfo.startsWith("uniwap") || extraInfo.startsWith("3gwap")) {
                        f2180b = "10.0.0.172";
                        f2181c = 80;
                        f2179a = true;
                    } else if (extraInfo.startsWith("ctwap")) {
                        f2180b = "10.0.0.200";
                        f2181c = 80;
                        f2179a = true;
                    } else if (extraInfo.startsWith("cmnet") || extraInfo.startsWith("uninet") || extraInfo.startsWith("ctnet") || extraInfo.startsWith("3gnet")) {
                        f2179a = false;
                    }
                } else {
                    extraInfo = Proxy.getDefaultHost();
                    int defaultPort = Proxy.getDefaultPort();
                    if (extraInfo != null && extraInfo.length() > 0) {
                        if ("10.0.0.172".equals(extraInfo.trim())) {
                            f2180b = "10.0.0.172";
                            f2181c = defaultPort;
                            f2179a = true;
                        } else if ("10.0.0.200".equals(extraInfo.trim())) {
                            f2180b = "10.0.0.200";
                            f2181c = 80;
                            f2179a = true;
                        }
                    }
                }
                if (f2179a) {
                    AppEngine.SetProxyInfo(f2180b, f2181c);
                } else {
                    AppEngine.SetProxyInfo(null, 0);
                }
            }
        }
    }

    private static boolean m2151a(NetworkInfo networkInfo) {
        boolean z = true;
        if (networkInfo != null) {
            try {
                if (!(1 == networkInfo.getType() && networkInfo.isConnected())) {
                    z = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        z = false;
        return z;
    }

    public static NetworkInfo m2152b(Context context) {
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e) {
            return null;
        }
    }

    public static String m2153c(Context context) {
        int i = 1;
        NetworkInfo b = C0668c.m2152b(context);
        if (b != null) {
            if (b.getType() != 1) {
                switch (((TelephonyManager) context.getSystemService("phone")).getNetworkType()) {
                    case 1:
                    case 2:
                        i = 6;
                        break;
                    case 3:
                    case 9:
                    case 10:
                    case 15:
                        i = 9;
                        break;
                    case 4:
                        i = 5;
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 12:
                        i = 7;
                        break;
                    case 8:
                        i = 8;
                        break;
                    case 11:
                        i = 2;
                        break;
                    case 13:
                        i = 4;
                        break;
                    case 14:
                        i = 10;
                        break;
                }
            }
            return Integer.toString(i);
        }
        i = 0;
        return Integer.toString(i);
    }
}
