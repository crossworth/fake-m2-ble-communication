package com.amap.api.services.district;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DistrictItem implements Parcelable {
    public static final Creator<DistrictItem> CREATOR = new C03231();
    private String f1133a;
    private String f1134b;
    private String f1135c;
    private LatLonPoint f1136d;
    private String f1137e;
    private List<DistrictItem> f1138f;
    private String[] f1139g;

    static class C03231 implements Creator<DistrictItem> {
        C03231() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1166a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1167a(i);
        }

        public DistrictItem m1166a(Parcel parcel) {
            return new DistrictItem(parcel);
        }

        public DistrictItem[] m1167a(int i) {
            return new DistrictItem[i];
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1133a);
        parcel.writeString(this.f1134b);
        parcel.writeString(this.f1135c);
        parcel.writeParcelable(this.f1136d, i);
        parcel.writeString(this.f1137e);
        parcel.writeTypedList(this.f1138f);
        parcel.writeInt(this.f1139g.length);
        parcel.writeStringArray(this.f1139g);
    }

    public void setDistrictBoundary(String[] strArr) {
        this.f1139g = strArr;
    }

    public String[] districtBoundary() {
        return this.f1139g;
    }

    public DistrictItem() {
        this.f1138f = new ArrayList();
        this.f1139g = new String[0];
    }

    public DistrictItem(String str, String str2, String str3, LatLonPoint latLonPoint, String str4) {
        this.f1138f = new ArrayList();
        this.f1139g = new String[0];
        this.f1135c = str;
        this.f1133a = str2;
        this.f1134b = str3;
        this.f1136d = latLonPoint;
        this.f1137e = str4;
    }

    public String getCitycode() {
        return this.f1133a;
    }

    public void setCitycode(String str) {
        this.f1133a = str;
    }

    public String getAdcode() {
        return this.f1134b;
    }

    public void setAdcode(String str) {
        this.f1134b = str;
    }

    public String getName() {
        return this.f1135c;
    }

    public void setName(String str) {
        this.f1135c = str;
    }

    public LatLonPoint getCenter() {
        return this.f1136d;
    }

    public void setCenter(LatLonPoint latLonPoint) {
        this.f1136d = latLonPoint;
    }

    public String getLevel() {
        return this.f1137e;
    }

    public void setLevel(String str) {
        this.f1137e = str;
    }

    public List<DistrictItem> getSubDistrict() {
        return this.f1138f;
    }

    public void setSubDistrict(ArrayList<DistrictItem> arrayList) {
        this.f1138f = arrayList;
    }

    public int describeContents() {
        return 0;
    }

    public DistrictItem(Parcel parcel) {
        this.f1138f = new ArrayList();
        this.f1139g = new String[0];
        this.f1133a = parcel.readString();
        this.f1134b = parcel.readString();
        this.f1135c = parcel.readString();
        this.f1136d = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
        this.f1137e = parcel.readString();
        this.f1138f = parcel.createTypedArrayList(CREATOR);
        this.f1139g = new String[parcel.readInt()];
        parcel.readStringArray(this.f1139g);
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((this.f1136d == null ? 0 : this.f1136d.hashCode()) + (((this.f1134b == null ? 0 : this.f1134b.hashCode()) + 31) * 31)) * 31;
        if (this.f1133a == null) {
            i = 0;
        } else {
            i = this.f1133a.hashCode();
        }
        hashCode = (((i + hashCode) * 31) + Arrays.hashCode(this.f1139g)) * 31;
        if (this.f1138f == null) {
            i = 0;
        } else {
            i = this.f1138f.hashCode();
        }
        i = ((this.f1137e == null ? 0 : this.f1137e.hashCode()) + ((i + hashCode) * 31)) * 31;
        if (this.f1135c != null) {
            i2 = this.f1135c.hashCode();
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
        DistrictItem districtItem = (DistrictItem) obj;
        if (this.f1134b == null) {
            if (districtItem.f1134b != null) {
                return false;
            }
        } else if (!this.f1134b.equals(districtItem.f1134b)) {
            return false;
        }
        if (this.f1136d == null) {
            if (districtItem.f1136d != null) {
                return false;
            }
        } else if (!this.f1136d.equals(districtItem.f1136d)) {
            return false;
        }
        if (this.f1133a == null) {
            if (districtItem.f1133a != null) {
                return false;
            }
        } else if (!this.f1133a.equals(districtItem.f1133a)) {
            return false;
        }
        if (!Arrays.equals(this.f1139g, districtItem.f1139g)) {
            return false;
        }
        if (this.f1138f == null) {
            if (districtItem.f1138f != null) {
                return false;
            }
        } else if (!this.f1138f.equals(districtItem.f1138f)) {
            return false;
        }
        if (this.f1137e == null) {
            if (districtItem.f1137e != null) {
                return false;
            }
        } else if (!this.f1137e.equals(districtItem.f1137e)) {
            return false;
        }
        if (this.f1135c == null) {
            if (districtItem.f1135c != null) {
                return false;
            }
            return true;
        } else if (this.f1135c.equals(districtItem.f1135c)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "DistrictItem [mCitycode=" + this.f1133a + ", mAdcode=" + this.f1134b + ", mName=" + this.f1135c + ", mCenter=" + this.f1136d + ", mLevel=" + this.f1137e + ", mDistricts=" + this.f1138f + "]";
    }
}
