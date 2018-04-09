package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.district.DistrictSearch.OnDistrictSearchListener;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.interfaces.IDistrictSearch;
import com.amap.api.services.proguard.ak;

public class DistrictSearchWrapper implements IDistrictSearch {
    private IDistrictSearch f4267a;

    public DistrictSearchWrapper(Context context) {
        this.f4267a = new ak(context);
    }

    public DistrictSearchQuery getQuery() {
        if (this.f4267a != null) {
            return this.f4267a.getQuery();
        }
        return null;
    }

    public void setQuery(DistrictSearchQuery districtSearchQuery) {
        if (this.f4267a != null) {
            this.f4267a.setQuery(districtSearchQuery);
        }
    }

    public void searchDistrictAsyn() {
        if (this.f4267a != null) {
            this.f4267a.searchDistrictAsyn();
        }
    }

    public void searchDistrictAnsy() {
        if (this.f4267a != null) {
            this.f4267a.searchDistrictAnsy();
        }
    }

    public void setOnDistrictSearchListener(OnDistrictSearchListener onDistrictSearchListener) {
        if (this.f4267a != null) {
            this.f4267a.setOnDistrictSearchListener(onDistrictSearchListener);
        }
    }
}
