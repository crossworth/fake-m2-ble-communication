package com.amap.api.services.district;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.AMapException;
import java.util.ArrayList;

public final class DistrictResult implements Parcelable {
    public Creator<DistrictResult> CREATOR = new C03241(this);
    private DistrictSearchQuery f1141a;
    private ArrayList<DistrictItem> f1142b = new ArrayList();
    private int f1143c;
    private AMapException f1144d;

    class C03241 implements Creator<DistrictResult> {
        final /* synthetic */ DistrictResult f1140a;

        C03241(DistrictResult districtResult) {
            this.f1140a = districtResult;
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1168a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1169a(i);
        }

        public DistrictResult m1168a(Parcel parcel) {
            return new DistrictResult(parcel);
        }

        public DistrictResult[] m1169a(int i) {
            return new DistrictResult[i];
        }
    }

    public DistrictResult(DistrictSearchQuery districtSearchQuery, ArrayList<DistrictItem> arrayList) {
        this.f1141a = districtSearchQuery;
        this.f1142b = arrayList;
    }

    public ArrayList<DistrictItem> getDistrict() {
        return this.f1142b;
    }

    public void setDistrict(ArrayList<DistrictItem> arrayList) {
        this.f1142b = arrayList;
    }

    public DistrictSearchQuery getQuery() {
        return this.f1141a;
    }

    public void setQuery(DistrictSearchQuery districtSearchQuery) {
        this.f1141a = districtSearchQuery;
    }

    public int getPageCount() {
        return this.f1143c;
    }

    public void setPageCount(int i) {
        this.f1143c = i;
    }

    public AMapException getAMapException() {
        return this.f1144d;
    }

    public void setAMapException(AMapException aMapException) {
        this.f1144d = aMapException;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f1141a, i);
        parcel.writeTypedList(this.f1142b);
    }

    protected DistrictResult(Parcel parcel) {
        this.f1141a = (DistrictSearchQuery) parcel.readParcelable(DistrictSearchQuery.class.getClassLoader());
        this.f1142b = parcel.createTypedArrayList(DistrictItem.CREATOR);
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        if (this.f1141a == null) {
            i = 0;
        } else {
            i = this.f1141a.hashCode();
        }
        i = (i + 31) * 31;
        if (this.f1142b != null) {
            i2 = this.f1142b.hashCode();
        }
        return i + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        DistrictResult districtResult = (DistrictResult) obj;
        if (this.f1141a == null) {
            if (districtResult.f1141a != null) {
                return false;
            }
        } else if (!this.f1141a.equals(districtResult.f1141a)) {
            return false;
        }
        if (this.f1142b == null) {
            if (districtResult.f1142b != null) {
                return false;
            }
            return true;
        } else if (this.f1142b.equals(districtResult.f1142b)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "DistrictResult [mDisQuery=" + this.f1141a + ", mDistricts=" + this.f1142b + "]";
    }
}
