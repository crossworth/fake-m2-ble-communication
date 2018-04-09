package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.busline.BusStationSearch.OnBusStationSearchListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IBusStationSearch;
import com.amap.api.services.proguard.ai;

public class BusStationSearchWrapper implements IBusStationSearch {
    private IBusStationSearch f4265a;

    public BusStationSearchWrapper(Context context, BusStationQuery busStationQuery) {
        this.f4265a = new ai(context, busStationQuery);
    }

    public BusStationResult searchBusStation() throws AMapException {
        if (this.f4265a != null) {
            return this.f4265a.searchBusStation();
        }
        return null;
    }

    public void setOnBusStationSearchListener(OnBusStationSearchListener onBusStationSearchListener) {
        if (this.f4265a != null) {
            this.f4265a.setOnBusStationSearchListener(onBusStationSearchListener);
        }
    }

    public void searchBusStationAsyn() {
        if (this.f4265a != null) {
            this.f4265a.searchBusStationAsyn();
        }
    }

    public void setQuery(BusStationQuery busStationQuery) {
        if (this.f4265a != null) {
            this.f4265a.setQuery(busStationQuery);
        }
    }

    public BusStationQuery getQuery() {
        if (this.f4265a != null) {
            return this.f4265a.getQuery();
        }
        return null;
    }
}
