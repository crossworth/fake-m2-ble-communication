package com.aps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class ap extends BroadcastReceiver {
    ap(C0475y c0475y) {
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (intent.getAction().equals("android.location.GPS_FIX_CHANGE")) {
                    C0475y.f1975b = false;
                }
            } catch (Exception e) {
            }
        }
    }
}
