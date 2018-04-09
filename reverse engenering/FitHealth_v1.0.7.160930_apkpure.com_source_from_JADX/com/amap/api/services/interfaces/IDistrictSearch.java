package com.amap.api.services.interfaces;

import com.amap.api.services.district.DistrictSearch.OnDistrictSearchListener;
import com.amap.api.services.district.DistrictSearchQuery;

public interface IDistrictSearch {
    DistrictSearchQuery getQuery();

    void searchDistrictAnsy();

    void searchDistrictAsyn();

    void setOnDistrictSearchListener(OnDistrictSearchListener onDistrictSearchListener);

    void setQuery(DistrictSearchQuery districtSearchQuery);
}
