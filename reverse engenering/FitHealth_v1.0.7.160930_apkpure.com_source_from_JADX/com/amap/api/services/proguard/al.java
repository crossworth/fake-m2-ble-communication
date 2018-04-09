package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.interfaces.IGeocodeSearch;
import com.amap.api.services.proguard.C0407q.C0400e;
import com.amap.api.services.proguard.C0407q.C0404i;
import java.util.List;

/* compiled from: GeocodeSearchCore */
public final class al implements IGeocodeSearch {
    private Context f4306a;
    private OnGeocodeSearchListener f4307b;
    private Handler f4308c = C0407q.m1654a();

    public al(Context context) {
        this.f4306a = context.getApplicationContext();
    }

    public RegeocodeAddress getFromLocation(RegeocodeQuery regeocodeQuery) throws AMapException {
        try {
            C0394o.m1652a(this.f4306a);
            if (regeocodeQuery != null) {
                return (RegeocodeAddress) new aa(this.f4306a, regeocodeQuery).m4358a();
            }
            throw new AMapException("无效的参数 - IllegalArgumentException");
        } catch (Throwable e) {
            C0390i.m1594a(e, "GeocodeSearch", "getFromLocationAsyn");
            throw e;
        }
    }

    public List<GeocodeAddress> getFromLocationName(GeocodeQuery geocodeQuery) throws AMapException {
        try {
            C0394o.m1652a(this.f4306a);
            if (geocodeQuery != null) {
                return (List) new C2050l(this.f4306a, geocodeQuery).m4358a();
            }
            throw new AMapException("无效的参数 - IllegalArgumentException");
        } catch (Throwable e) {
            C0390i.m1594a(e, "GeocodeSearch", "getFromLocationName");
            throw e;
        }
    }

    public void setOnGeocodeSearchListener(OnGeocodeSearchListener onGeocodeSearchListener) {
        this.f4307b = onGeocodeSearchListener;
    }

    public void getFromLocationAsyn(final RegeocodeQuery regeocodeQuery) {
        new Thread(new Runnable(this) {
            final /* synthetic */ al f1284b;

            public void run() {
                Message obtainMessage = C0407q.m1654a().obtainMessage();
                try {
                    obtainMessage.arg1 = 2;
                    obtainMessage.what = 201;
                    C0404i c0404i = new C0404i();
                    c0404i.f1567b = this.f1284b.f4307b;
                    obtainMessage.obj = c0404i;
                    c0404i.f1566a = new RegeocodeResult(regeocodeQuery, this.f1284b.getFromLocation(regeocodeQuery));
                    obtainMessage.arg2 = 1000;
                } catch (AMapException e) {
                    obtainMessage.arg2 = e.getErrorCode();
                } finally {
                    this.f1284b.f4308c.sendMessage(obtainMessage);
                }
            }
        }).start();
    }

    public void getFromLocationNameAsyn(final GeocodeQuery geocodeQuery) {
        new Thread(new Runnable(this) {
            final /* synthetic */ al f1286b;

            public void run() {
                Message obtainMessage = C0407q.m1654a().obtainMessage();
                try {
                    obtainMessage.what = 200;
                    obtainMessage.arg1 = 2;
                    obtainMessage.arg2 = 1000;
                    C0400e c0400e = new C0400e();
                    c0400e.f1559b = this.f1286b.f4307b;
                    obtainMessage.obj = c0400e;
                    c0400e.f1558a = new GeocodeResult(geocodeQuery, this.f1286b.getFromLocationName(geocodeQuery));
                } catch (AMapException e) {
                    obtainMessage.arg2 = e.getErrorCode();
                } finally {
                    this.f1286b.f4308c.sendMessage(obtainMessage);
                }
            }
        }).start();
    }
}
