package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.cloud.CloudSearch.OnCloudSearchListener;
import com.amap.api.services.cloud.CloudSearch.Query;
import com.amap.api.services.interfaces.ICloudSearch;
import com.amap.api.services.proguard.aj;

public class CloudSearchWrapper implements ICloudSearch {
    private ICloudSearch f4266a;

    public CloudSearchWrapper(Context context) {
        this.f4266a = new aj(context);
    }

    public void setOnCloudSearchListener(OnCloudSearchListener onCloudSearchListener) {
        if (this.f4266a != null) {
            this.f4266a.setOnCloudSearchListener(onCloudSearchListener);
        }
    }

    public void searchCloudAsyn(Query query) {
        if (this.f4266a != null) {
            this.f4266a.searchCloudAsyn(query);
        }
    }

    public void searchCloudDetailAsyn(String str, String str2) {
        if (this.f4266a != null) {
            this.f4266a.searchCloudDetailAsyn(str, str2);
        }
    }
}
