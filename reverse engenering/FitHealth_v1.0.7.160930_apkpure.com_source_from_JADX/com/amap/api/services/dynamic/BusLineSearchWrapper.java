package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch.OnBusLineSearchListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IBusLineSearch;
import com.amap.api.services.proguard.ah;

public class BusLineSearchWrapper implements IBusLineSearch {
    private IBusLineSearch f4264a = null;

    public BusLineSearchWrapper(Context context, BusLineQuery busLineQuery) {
        this.f4264a = new ah(context, busLineQuery);
    }

    public BusLineResult searchBusLine() throws AMapException {
        if (this.f4264a != null) {
            return this.f4264a.searchBusLine();
        }
        return null;
    }

    public void setOnBusLineSearchListener(OnBusLineSearchListener onBusLineSearchListener) {
        if (this.f4264a != null) {
            this.f4264a.setOnBusLineSearchListener(onBusLineSearchListener);
        }
    }

    public void searchBusLineAsyn() {
        if (this.f4264a != null) {
            this.f4264a.searchBusLineAsyn();
        }
    }

    public void setQuery(BusLineQuery busLineQuery) {
        if (this.f4264a != null) {
            this.f4264a.setQuery(busLineQuery);
        }
    }

    public BusLineQuery getQuery() {
        if (this.f4264a != null) {
            return this.f4264a.getQuery();
        }
        return null;
    }
}
