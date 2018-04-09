package com.amap.api.services.cloud;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public class CloudItemDetail extends CloudItem implements Parcelable {
    public static final Creator<CloudItemDetail> CREATOR = new C03191();

    static class C03191 implements Creator<CloudItemDetail> {
        C03191() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1147a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1148a(i);
        }

        public CloudItemDetail m1147a(Parcel parcel) {
            return new CloudItemDetail(parcel);
        }

        public CloudItemDetail[] m1148a(int i) {
            return new CloudItemDetail[i];
        }
    }

    public CloudItemDetail(String str, LatLonPoint latLonPoint, String str2, String str3) {
        super(str, latLonPoint, str2, str3);
    }

    protected CloudItemDetail(Parcel parcel) {
        super(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
