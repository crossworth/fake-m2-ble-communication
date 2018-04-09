package com.aps;

import android.telephony.CellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;

public final class ay {
    int f1790a = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    int f1791b = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    int f1792c = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    int f1793d = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    int f1794e = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

    ay(CellLocation cellLocation) {
        if (cellLocation == null) {
            return;
        }
        if (cellLocation instanceof GsmCellLocation) {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
            this.f1794e = gsmCellLocation.getCid();
            this.f1793d = gsmCellLocation.getLac();
        } else if (cellLocation instanceof CdmaCellLocation) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
            this.f1792c = cdmaCellLocation.getBaseStationId();
            this.f1791b = cdmaCellLocation.getNetworkId();
            this.f1790a = cdmaCellLocation.getSystemId();
        }
    }
}
