package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch.OnBusLineSearchListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.IBusLineSearch;
import com.amap.api.services.proguard.C0407q.C0396a;
import java.util.ArrayList;

/* compiled from: BusLineSearchCore */
public class ah implements IBusLineSearch {
    private Context f4279a;
    private OnBusLineSearchListener f4280b;
    private BusLineQuery f4281c;
    private BusLineQuery f4282d;
    private int f4283e;
    private ArrayList<BusLineResult> f4284f = new ArrayList();
    private Handler f4285g = null;

    /* compiled from: BusLineSearchCore */
    class C03381 implements Runnable {
        final /* synthetic */ ah f1275a;

        C03381(ah ahVar) {
            this.f1275a = ahVar;
        }

        public void run() {
            Message obtainMessage = C0407q.m1654a().obtainMessage();
            try {
                obtainMessage.arg1 = 3;
                obtainMessage.what = 1000;
                C0396a c0396a = new C0396a();
                obtainMessage.obj = c0396a;
                c0396a.f1551b = this.f1275a.f4280b;
                c0396a.f1550a = this.f1275a.searchBusLine();
            } catch (AMapException e) {
                obtainMessage.what = e.getErrorCode();
            } finally {
                this.f1275a.f4285g.sendMessage(obtainMessage);
            }
        }
    }

    public ah(Context context, BusLineQuery busLineQuery) {
        this.f4279a = context.getApplicationContext();
        this.f4281c = busLineQuery;
        this.f4282d = busLineQuery.clone();
        this.f4285g = C0407q.m1654a();
    }

    public BusLineResult searchBusLine() throws AMapException {
        try {
            C0394o.m1652a(this.f4279a);
            if (m4367a()) {
                if (!this.f4281c.weakEquals(this.f4282d)) {
                    this.f4282d = this.f4281c.clone();
                    this.f4283e = 0;
                    if (this.f4284f != null) {
                        this.f4284f.clear();
                    }
                }
                BusLineResult busLineResult;
                if (this.f4283e == 0) {
                    busLineResult = (BusLineResult) new C2046d(this.f4279a, this.f4281c.clone()).m4358a();
                    m4366a(busLineResult);
                    return busLineResult;
                }
                busLineResult = m4370b(this.f4281c.getPageNumber());
                if (busLineResult != null) {
                    return busLineResult;
                }
                busLineResult = (BusLineResult) new C2046d(this.f4279a, this.f4281c).m4358a();
                this.f4284f.set(this.f4281c.getPageNumber(), busLineResult);
                return busLineResult;
            }
            throw new AMapException("无效的参数 - IllegalArgumentException");
        } catch (Throwable e) {
            C0390i.m1594a(e, "BusLineSearch", "searchBusLine");
            throw new AMapException(e.getErrorMessage());
        }
    }

    private void m4366a(BusLineResult busLineResult) {
        this.f4284f = new ArrayList();
        for (int i = 0; i < this.f4283e; i++) {
            this.f4284f.add(null);
        }
        if (this.f4283e >= 0 && m4368a(this.f4281c.getPageNumber())) {
            this.f4284f.set(this.f4281c.getPageNumber(), busLineResult);
        }
    }

    private boolean m4368a(int i) {
        return i < this.f4283e && i >= 0;
    }

    private BusLineResult m4370b(int i) {
        if (m4368a(i)) {
            return (BusLineResult) this.f4284f.get(i);
        }
        throw new IllegalArgumentException("page out of range");
    }

    public void setOnBusLineSearchListener(OnBusLineSearchListener onBusLineSearchListener) {
        this.f4280b = onBusLineSearchListener;
    }

    public void searchBusLineAsyn() {
        new Thread(new C03381(this)).start();
    }

    public void setQuery(BusLineQuery busLineQuery) {
        if (!this.f4281c.weakEquals(busLineQuery)) {
            this.f4281c = busLineQuery;
            this.f4282d = busLineQuery.clone();
        }
    }

    public BusLineQuery getQuery() {
        return this.f4281c;
    }

    private boolean m4367a() {
        if (this.f4281c == null || C0390i.m1595a(this.f4281c.getQueryString())) {
            return false;
        }
        return true;
    }
}
