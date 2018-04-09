package com.amap.api.services.busline;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IBusStationSearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.ai;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;

public class BusStationSearch {
    private IBusStationSearch f1064a;

    public interface OnBusStationSearchListener {
        void onBusStationSearched(BusStationResult busStationResult, int i);
    }

    public BusStationSearch(Context context, BusStationQuery busStationQuery) {
        try {
            Context context2 = context;
            this.f1064a = (IBusStationSearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.BusStationSearchWrapper", ai.class, new Class[]{Context.class, BusStationQuery.class}, new Object[]{context, busStationQuery});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1064a == null) {
            this.f1064a = new ai(context, busStationQuery);
        }
    }

    public BusStationResult searchBusStation() throws AMapException {
        if (this.f1064a != null) {
            return this.f1064a.searchBusStation();
        }
        return null;
    }

    public void setOnBusStationSearchListener(OnBusStationSearchListener onBusStationSearchListener) {
        if (this.f1064a != null) {
            this.f1064a.setOnBusStationSearchListener(onBusStationSearchListener);
        }
    }

    public void searchBusStationAsyn() {
        if (this.f1064a != null) {
            this.f1064a.searchBusStationAsyn();
        }
    }

    public void setQuery(BusStationQuery busStationQuery) {
        if (this.f1064a != null) {
            this.f1064a.setQuery(busStationQuery);
        }
    }

    public BusStationQuery getQuery() {
        if (this.f1064a != null) {
            return this.f1064a.getQuery();
        }
        return null;
    }
}
