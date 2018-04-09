package com.amap.api.services.busline;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IBusLineSearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.ah;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;

public class BusLineSearch {
    private IBusLineSearch f1048a = null;

    public interface OnBusLineSearchListener {
        void onBusLineSearched(BusLineResult busLineResult, int i);
    }

    public BusLineSearch(Context context, BusLineQuery busLineQuery) {
        try {
            Context context2 = context;
            this.f1048a = (IBusLineSearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.BusLineSearchWrapper", ah.class, new Class[]{Context.class, BusLineQuery.class}, new Object[]{context, busLineQuery});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1048a == null) {
            this.f1048a = new ah(context, busLineQuery);
        }
    }

    public BusLineResult searchBusLine() throws AMapException {
        if (this.f1048a != null) {
            return this.f1048a.searchBusLine();
        }
        return null;
    }

    public void setOnBusLineSearchListener(OnBusLineSearchListener onBusLineSearchListener) {
        if (this.f1048a != null) {
            this.f1048a.setOnBusLineSearchListener(onBusLineSearchListener);
        }
    }

    public void searchBusLineAsyn() {
        if (this.f1048a != null) {
            this.f1048a.searchBusLineAsyn();
        }
    }

    public void setQuery(BusLineQuery busLineQuery) {
        if (this.f1048a != null) {
            this.f1048a.setQuery(busLineQuery);
        }
    }

    public BusLineQuery getQuery() {
        if (this.f1048a != null) {
            return this.f1048a.getQuery();
        }
        return null;
    }
}
