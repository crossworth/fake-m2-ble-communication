package com.amap.api.services.district;

import android.content.Context;
import com.amap.api.services.interfaces.IDistrictSearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.ak;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;

public class DistrictSearch {
    private IDistrictSearch f1145a;

    public interface OnDistrictSearchListener {
        void onDistrictSearched(DistrictResult districtResult);
    }

    public DistrictSearch(Context context) {
        try {
            Context context2 = context;
            this.f1145a = (IDistrictSearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.DistrictSearchWrapper", ak.class, new Class[]{Context.class}, new Object[]{context});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1145a == null) {
            this.f1145a = new ak(context);
        }
    }

    public DistrictSearchQuery getQuery() {
        if (this.f1145a != null) {
            return this.f1145a.getQuery();
        }
        return null;
    }

    public void setQuery(DistrictSearchQuery districtSearchQuery) {
        if (this.f1145a != null) {
            this.f1145a.setQuery(districtSearchQuery);
        }
    }

    public void searchDistrictAsyn() {
        if (this.f1145a != null) {
            this.f1145a.searchDistrictAsyn();
        }
    }

    public void searchDistrictAnsy() {
        if (this.f1145a != null) {
            this.f1145a.searchDistrictAnsy();
        }
    }

    public void setOnDistrictSearchListener(OnDistrictSearchListener onDistrictSearchListener) {
        if (this.f1145a != null) {
            this.f1145a.setOnDistrictSearchListener(onDistrictSearchListener);
        }
    }
}
