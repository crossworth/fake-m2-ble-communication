package com.aps;

import android.location.GpsStatus.NmeaListener;

final class al implements NmeaListener {
    private /* synthetic */ ai f1755a;

    private al(ai aiVar) {
        this.f1755a = aiVar;
    }

    public final void onNmeaReceived(long j, String str) {
        try {
            this.f1755a.f1738k = j;
            this.f1755a.f1739l = str;
        } catch (Exception e) {
        }
    }
}
