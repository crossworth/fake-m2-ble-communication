package com.aps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

final class am extends BroadcastReceiver {
    final /* synthetic */ ai f1756a;

    private am(ai aiVar) {
        this.f1756a = aiVar;
    }

    public final void onReceive(Context context, Intent intent) {
        if (context != null && intent != null) {
            try {
                if (this.f1756a.f1731d != null && this.f1756a.f1752z != null && this.f1756a.f1751y != null && intent.getAction() != null && "android.net.wifi.SCAN_RESULTS".equals(intent.getAction())) {
                    List scanResults = this.f1756a.f1731d.getScanResults();
                    synchronized (this) {
                        this.f1756a.f1751y.clear();
                        this.f1756a.f1745r = System.currentTimeMillis();
                        if (scanResults != null && scanResults.size() > 0) {
                            for (int i = 0; i < scanResults.size(); i++) {
                                this.f1756a.f1751y.add((ScanResult) scanResults.get(i));
                            }
                        }
                    }
                    TimerTask anVar = new an(this);
                    if (this.f1756a.f1752z != null) {
                        this.f1756a.f1752z.cancel();
                        this.f1756a.f1752z = null;
                    }
                    this.f1756a.f1752z = new Timer();
                    this.f1756a.f1752z.schedule(anVar, (long) ai.f1724C);
                }
            } catch (Exception e) {
            }
        }
    }
}
