package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch.OnDistrictSearchListener;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.interfaces.IDistrictSearch;
import java.util.HashMap;

/* compiled from: DistrictSearchCore */
public class ak implements IDistrictSearch {
    private static HashMap<Integer, DistrictResult> f4299f;
    private Context f4300a;
    private DistrictSearchQuery f4301b;
    private OnDistrictSearchListener f4302c;
    private DistrictSearchQuery f4303d;
    private int f4304e;
    private Handler f4305g = C0407q.m1654a();

    /* compiled from: DistrictSearchCore */
    class C03421 extends Thread {
        final /* synthetic */ ak f1282a;

        C03421(ak akVar) {
            this.f1282a = akVar;
        }

        public void run() {
            Message obtainMessage = C0407q.m1654a().obtainMessage();
            Parcelable districtResult = new DistrictResult();
            districtResult.setQuery(this.f1282a.f4301b);
            Bundle bundle;
            try {
                districtResult = this.f1282a.m4388b();
                if (districtResult != null) {
                    districtResult.setAMapException(new AMapException());
                }
                obtainMessage.arg1 = 4;
                obtainMessage.obj = this.f1282a.f4302c;
                bundle = new Bundle();
                bundle.putParcelable("result", districtResult);
                obtainMessage.setData(bundle);
                if (this.f1282a.f4305g != null) {
                    this.f1282a.f4305g.sendMessage(obtainMessage);
                }
            } catch (AMapException e) {
                districtResult.setAMapException(e);
                obtainMessage.arg1 = 4;
                obtainMessage.obj = this.f1282a.f4302c;
                bundle = new Bundle();
                bundle.putParcelable("result", districtResult);
                obtainMessage.setData(bundle);
                if (this.f1282a.f4305g != null) {
                    this.f1282a.f4305g.sendMessage(obtainMessage);
                }
            } catch (Throwable th) {
                obtainMessage.arg1 = 4;
                obtainMessage.obj = this.f1282a.f4302c;
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("result", districtResult);
                obtainMessage.setData(bundle2);
                if (this.f1282a.f4305g != null) {
                    this.f1282a.f4305g.sendMessage(obtainMessage);
                }
            }
        }
    }

    public ak(Context context) {
        this.f4300a = context.getApplicationContext();
    }

    private void m4386a(DistrictResult districtResult) {
        f4299f = new HashMap();
        if (this.f4301b != null && districtResult != null && this.f4304e > 0 && this.f4304e > this.f4301b.getPageNum()) {
            f4299f.put(Integer.valueOf(this.f4301b.getPageNum()), districtResult);
        }
    }

    public DistrictSearchQuery getQuery() {
        return this.f4301b;
    }

    public void setQuery(DistrictSearchQuery districtSearchQuery) {
        this.f4301b = districtSearchQuery;
    }

    private boolean m4387a() {
        if (this.f4301b == null) {
            return false;
        }
        return true;
    }

    protected DistrictResult m4393a(int i) throws AMapException {
        if (m4390b(i)) {
            return (DistrictResult) f4299f.get(Integer.valueOf(i));
        }
        throw new AMapException("无效的参数 - IllegalArgumentException");
    }

    private boolean m4390b(int i) {
        return i < this.f4304e && i >= 0;
    }

    private DistrictResult m4388b() throws AMapException {
        try {
            DistrictResult districtResult = new DistrictResult();
            C0394o.m1652a(this.f4300a);
            if (!m4387a()) {
                this.f4301b = new DistrictSearchQuery();
            }
            districtResult.setQuery(this.f4301b.clone());
            if (!this.f4301b.weakEquals(this.f4303d)) {
                this.f4304e = 0;
                this.f4303d = this.f4301b.clone();
                if (f4299f != null) {
                    f4299f.clear();
                }
            }
            if (this.f4304e == 0) {
                districtResult = (DistrictResult) new C2048j(this.f4300a, this.f4301b.clone()).m4358a();
                if (districtResult != null) {
                    this.f4304e = districtResult.getPageCount();
                    m4386a(districtResult);
                }
            } else {
                districtResult = m4393a(this.f4301b.getPageNum());
                if (districtResult == null) {
                    districtResult = (DistrictResult) new C2048j(this.f4300a, this.f4301b.clone()).m4358a();
                    if (this.f4301b != null && districtResult != null && this.f4304e > 0 && this.f4304e > this.f4301b.getPageNum()) {
                        f4299f.put(Integer.valueOf(this.f4301b.getPageNum()), districtResult);
                    }
                }
            }
            return districtResult;
        } catch (Throwable e) {
            C0390i.m1594a(e, "DistrictSearch", "searchDistrict");
            throw e;
        }
    }

    public void searchDistrictAsyn() {
        new C03421(this).start();
    }

    public void searchDistrictAnsy() {
        searchDistrictAsyn();
    }

    public void setOnDistrictSearchListener(OnDistrictSearchListener onDistrictSearchListener) {
        this.f4302c = onDistrictSearchListener;
    }
}
