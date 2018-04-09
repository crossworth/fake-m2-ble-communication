package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.busline.BusStationSearch.OnBusStationSearchListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IBusStationSearch;
import com.amap.api.services.proguard.C0407q.C0397b;
import java.util.ArrayList;

/* compiled from: BusStationSearchCore */
public class ai implements IBusStationSearch {
    private Context f4286a;
    private OnBusStationSearchListener f4287b;
    private BusStationQuery f4288c;
    private BusStationQuery f4289d;
    private ArrayList<BusStationResult> f4290e = new ArrayList();
    private int f4291f;
    private Handler f4292g;

    /* compiled from: BusStationSearchCore */
    class C03391 implements Runnable {
        final /* synthetic */ ai f1276a;

        C03391(ai aiVar) {
            this.f1276a = aiVar;
        }

        public void run() {
            Message obtainMessage = C0407q.m1654a().obtainMessage();
            try {
                obtainMessage.arg1 = 7;
                C0397b c0397b = new C0397b();
                c0397b.f1553b = this.f1276a.f4287b;
                obtainMessage.obj = c0397b;
                BusStationResult searchBusStation = this.f1276a.searchBusStation();
                obtainMessage.what = 1000;
                c0397b.f1552a = searchBusStation;
            } catch (AMapException e) {
                obtainMessage.what = e.getErrorCode();
            } finally {
                this.f1276a.f4292g.sendMessage(obtainMessage);
            }
        }
    }

    public ai(Context context, BusStationQuery busStationQuery) {
        this.f4286a = context.getApplicationContext();
        this.f4288c = busStationQuery;
        this.f4292g = C0407q.m1654a();
    }

    public BusStationResult searchBusStation() throws AMapException {
        try {
            C0394o.m1652a(this.f4286a);
            if (this.f4288c == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
            if (!this.f4288c.weakEquals(this.f4289d)) {
                this.f4289d = this.f4288c.clone();
                this.f4291f = 0;
                if (this.f4290e != null) {
                    this.f4290e.clear();
                }
            }
            BusStationResult busStationResult;
            if (this.f4291f == 0) {
                busStationResult = (BusStationResult) new C2046d(this.f4286a, this.f4288c).m4358a();
                this.f4291f = busStationResult.getPageCount();
                m4372a(busStationResult);
                return busStationResult;
            }
            busStationResult = m4375b(this.f4288c.getPageNumber());
            if (busStationResult != null) {
                return busStationResult;
            }
            busStationResult = (BusStationResult) new C2046d(this.f4286a, this.f4288c).m4358a();
            this.f4290e.set(this.f4288c.getPageNumber(), busStationResult);
            return busStationResult;
        } catch (Throwable e) {
            C0390i.m1594a(e, "BusStationSearch", "searchBusStation");
            throw new AMapException(e.getErrorMessage());
        }
    }

    private void m4372a(BusStationResult busStationResult) {
        this.f4290e = new ArrayList();
        for (int i = 0; i <= this.f4291f; i++) {
            this.f4290e.add(null);
        }
        if (this.f4291f > 0) {
            this.f4290e.set(this.f4288c.getPageNumber(), busStationResult);
        }
    }

    private boolean m4373a(int i) {
        return i <= this.f4291f && i >= 0;
    }

    private BusStationResult m4375b(int i) {
        if (m4373a(i)) {
            return (BusStationResult) this.f4290e.get(i);
        }
        throw new IllegalArgumentException("page out of range");
    }

    public void setOnBusStationSearchListener(OnBusStationSearchListener onBusStationSearchListener) {
        this.f4287b = onBusStationSearchListener;
    }

    public void searchBusStationAsyn() {
        new Thread(new C03391(this)).start();
    }

    public void setQuery(BusStationQuery busStationQuery) {
        if (!busStationQuery.weakEquals(this.f4288c)) {
            this.f4288c = busStationQuery;
        }
    }

    public BusStationQuery getQuery() {
        return this.f4288c;
    }
}
