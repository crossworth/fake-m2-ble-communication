package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.interfaces.INearbySearch;
import com.amap.api.services.nearby.NearbySearch.NearbyListener;
import com.amap.api.services.nearby.NearbySearch.NearbyQuery;
import com.amap.api.services.nearby.NearbySearchResult;
import com.amap.api.services.nearby.UploadInfo;
import com.amap.api.services.nearby.UploadInfoCallback;
import com.amap.api.services.proguard.am;

public class NearbySearchWrapper implements INearbySearch {
    private INearbySearch f4269a;

    public NearbySearchWrapper(Context context) {
        this.f4269a = new am(context);
    }

    public void addNearbyListener(NearbyListener nearbyListener) {
        if (this.f4269a != null) {
            this.f4269a.addNearbyListener(nearbyListener);
        }
    }

    public void removeNearbyListener(NearbyListener nearbyListener) {
        if (this.f4269a != null) {
            this.f4269a.removeNearbyListener(nearbyListener);
        }
    }

    public void clearUserInfoAsyn() {
        if (this.f4269a != null) {
            this.f4269a.clearUserInfoAsyn();
        }
    }

    public void setUserID(String str) {
        if (this.f4269a != null) {
            this.f4269a.setUserID(str);
        }
    }

    public void startUploadNearbyInfoAuto(UploadInfoCallback uploadInfoCallback, int i) {
        if (this.f4269a != null) {
            this.f4269a.startUploadNearbyInfoAuto(uploadInfoCallback, i);
        }
    }

    public void stopUploadNearbyInfoAuto() {
        if (this.f4269a != null) {
            this.f4269a.stopUploadNearbyInfoAuto();
        }
    }

    public void uploadNearbyInfoAsyn(UploadInfo uploadInfo) {
        if (this.f4269a != null) {
            this.f4269a.uploadNearbyInfoAsyn(uploadInfo);
        }
    }

    public void searchNearbyInfoAsyn(NearbyQuery nearbyQuery) {
        if (this.f4269a != null) {
            this.f4269a.searchNearbyInfoAsyn(nearbyQuery);
        }
    }

    public NearbySearchResult searchNearbyInfo(NearbyQuery nearbyQuery) throws AMapException {
        if (this.f4269a != null) {
            return this.f4269a.searchNearbyInfo(nearbyQuery);
        }
        return null;
    }

    public void destroy() {
        if (this.f4269a != null) {
            this.f4269a.destroy();
        }
    }
}
