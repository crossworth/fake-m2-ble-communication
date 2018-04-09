package com.aps;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;

final class ak extends PhoneStateListener {
    private /* synthetic */ ai f1754a;

    private ak(ai aiVar) {
        this.f1754a = aiVar;
    }

    public final void onCellLocationChanged(CellLocation cellLocation) {
        try {
            this.f1754a.f1746s = System.currentTimeMillis();
            this.f1754a.f1749w = cellLocation;
            super.onCellLocationChanged(cellLocation);
        } catch (Exception e) {
        }
    }

    public final void onServiceStateChanged(ServiceState serviceState) {
        try {
            if (serviceState.getState() == 0) {
                this.f1754a.f1737j = true;
                String[] a = ai.m1774b(this.f1754a.f1729b);
                this.f1754a.f1741n = Integer.parseInt(a[0]);
                this.f1754a.f1742o = Integer.parseInt(a[1]);
            } else {
                this.f1754a.f1737j = false;
            }
            super.onServiceStateChanged(serviceState);
        } catch (Exception e) {
        }
    }

    public final void onSignalStrengthsChanged(SignalStrength signalStrength) {
        try {
            if (this.f1754a.f1735h) {
                this.f1754a.f1736i = signalStrength.getCdmaDbm();
            } else {
                this.f1754a.f1736i = signalStrength.getGsmSignalStrength();
                if (this.f1754a.f1736i == 99) {
                    this.f1754a.f1736i = -1;
                } else {
                    this.f1754a.f1736i = (this.f1754a.f1736i * 2) - 113;
                }
            }
            super.onSignalStrengthsChanged(signalStrength);
        } catch (Exception e) {
        }
    }
}
