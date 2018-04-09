package com.droi.sdk.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.internal.DroiLog;
import java.util.ArrayList;
import java.util.Iterator;

public class NetworkUtils {
    public static final int f2750a = 0;
    public static final int f2751b = 1;
    public static final int f2752c = 2;
    public static final int f2753d = 4;
    private static final String f2754e = "NetworkUtils";
    private static C0862a f2755f;
    private static ArrayList<DroiCallback<Integer>> f2756g;

    public static class C0862a extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            Log.d(NetworkUtils.f2754e, "NetworkChange receiver in.");
            NetworkUtils.notifyNetworkStateChange(NetworkUtils.getNetworkState(context));
        }
    }

    public static int getNetworkState(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return 0;
        }
        switch (activeNetworkInfo.getType()) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
                return 2;
            case 1:
            case 6:
                return 1;
            default:
                return 4;
        }
    }

    public static boolean isWifiOrMobileAvailable(int i) {
        return i == 1 || i == 2;
    }

    public static void notifyNetworkStateChange(int i) {
        if (f2756g != null && f2756g.size() != 0) {
            ArrayList arrayList = (ArrayList) f2756g.clone();
            DroiError droiError = new DroiError();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                DroiCallback droiCallback = (DroiCallback) it.next();
                if (droiCallback != null) {
                    droiCallback.result(Integer.valueOf(i), droiError);
                }
            }
        }
    }

    public static void registerNetworkStateListener(Context context, DroiCallback<Integer> droiCallback) {
        if (f2755f == null) {
            f2755f = new C0862a();
            try {
                context.registerReceiver(f2755f, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                f2756g = new ArrayList();
            } catch (Exception e) {
                DroiLog.m2874w(f2754e, "Register receiver fail. " + e);
                f2755f = null;
            }
        }
        if (f2755f != null && !f2756g.contains(droiCallback)) {
            f2756g.add(droiCallback);
        }
    }

    public static void unregisterNetworkStateListener(Context context, DroiCallback<Integer> droiCallback) {
        if (f2756g != null && f2756g.contains(droiCallback)) {
            f2756g.remove(droiCallback);
            if (f2756g.size() == 0) {
                context.unregisterReceiver(f2755f);
                f2755f = null;
            }
        }
    }
}
