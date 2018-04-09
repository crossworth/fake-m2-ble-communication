package com.amap.api.services.district;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.proguard.C0390i;

public class DistrictSearchQuery implements Parcelable, Cloneable {
    public static final Creator<DistrictSearchQuery> CREATOR = new C03251();
    public static final String KEYWORDS_BUSINESS = "biz_area";
    public static final String KEYWORDS_CITY = "city";
    public static final String KEYWORDS_COUNTRY = "country";
    public static final String KEYWORDS_DISTRICT = "district";
    public static final String KEYWORDS_PROVINCE = "province";
    private int f1146a;
    private int f1147b;
    private String f1148c;
    private String f1149d;
    private boolean f1150e;
    private boolean f1151f;

    static class C03251 implements Creator<DistrictSearchQuery> {
        C03251() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1170a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1171a(i);
        }

        public DistrictSearchQuery m1170a(Parcel parcel) {
            boolean z;
            boolean z2 = true;
            DistrictSearchQuery districtSearchQuery = new DistrictSearchQuery();
            districtSearchQuery.setKeywords(parcel.readString());
            districtSearchQuery.setKeywordsLevel(parcel.readString());
            districtSearchQuery.setPageNum(parcel.readInt());
            districtSearchQuery.setPageSize(parcel.readInt());
            if (parcel.readByte() == (byte) 1) {
                z = true;
            } else {
                z = false;
            }
            districtSearchQuery.setShowChild(z);
            if (parcel.readByte() != (byte) 1) {
                z2 = false;
            }
            districtSearchQuery.setShowBoundary(z2);
            return districtSearchQuery;
        }

        public DistrictSearchQuery[] m1171a(int i) {
            return new DistrictSearchQuery[i];
        }
    }

    public void setShowBoundary(boolean z) {
        this.f1151f = z;
    }

    public boolean isShowBoundary() {
        return this.f1151f;
    }

    public DistrictSearchQuery() {
        this.f1146a = 0;
        this.f1147b = 20;
        this.f1150e = true;
        this.f1151f = false;
    }

    public DistrictSearchQuery(String str, String str2, int i) {
        this.f1146a = 0;
        this.f1147b = 20;
        this.f1150e = true;
        this.f1151f = false;
        this.f1148c = str;
        this.f1149d = str2;
        this.f1146a = i;
    }

    public DistrictSearchQuery(String str, String str2, int i, boolean z, int i2) {
        this(str, str2, i);
        this.f1150e = z;
        this.f1147b = i2;
    }

    public int getPageNum() {
        return this.f1146a;
    }

    public void setPageNum(int i) {
        this.f1146a = i;
    }

    public int getPageSize() {
        return this.f1147b;
    }

    public void setPageSize(int i) {
        this.f1147b = i;
    }

    public String getKeywords() {
        return this.f1148c;
    }

    public void setKeywords(String str) {
        this.f1148c = str;
    }

    public String getKeywordsLevel() {
        return this.f1149d;
    }

    public void setKeywordsLevel(String str) {
        this.f1149d = str;
    }

    public boolean isShowChild() {
        return this.f1150e;
    }

    public void setShowChild(boolean z) {
        this.f1150e = z;
    }

    public boolean checkLevels() {
        if (this.f1149d == null) {
            return false;
        }
        if (this.f1149d.trim().equals("country") || this.f1149d.trim().equals(KEYWORDS_PROVINCE) || this.f1149d.trim().equals(KEYWORDS_CITY) || this.f1149d.trim().equals(KEYWORDS_DISTRICT) || this.f1149d.trim().equals(KEYWORDS_BUSINESS)) {
            return true;
        }
        return false;
    }

    public boolean checkKeyWords() {
        if (this.f1148c == null || this.f1148c.trim().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i;
        int i2 = 1231;
        int i3 = 0;
        int i4 = ((this.f1151f ? 1231 : 1237) + 31) * 31;
        if (this.f1148c == null) {
            i = 0;
        } else {
            i = this.f1148c.hashCode();
        }
        i = (i + i4) * 31;
        if (this.f1149d != null) {
            i3 = this.f1149d.hashCode();
        }
        i = (((((i + i3) * 31) + this.f1146a) * 31) + this.f1147b) * 31;
        if (!this.f1150e) {
            i2 = 1237;
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
        DistrictSearchQuery districtSearchQuery = (DistrictSearchQuery) obj;
        if (this.f1151f != districtSearchQuery.f1151f) {
            return false;
        }
        if (this.f1148c == null) {
            if (districtSearchQuery.f1148c != null) {
                return false;
            }
        } else if (!this.f1148c.equals(districtSearchQuery.f1148c)) {
            return false;
        }
        if (this.f1149d == null) {
            if (districtSearchQuery.f1149d != null) {
                return false;
            }
        } else if (!this.f1149d.equals(districtSearchQuery.f1149d)) {
            return false;
        }
        if (this.f1146a != districtSearchQuery.f1146a) {
            return false;
        }
        if (this.f1147b != districtSearchQuery.f1147b) {
            return false;
        }
        if (this.f1150e != districtSearchQuery.f1150e) {
            return false;
        }
        return true;
    }

    public boolean weakEquals(DistrictSearchQuery districtSearchQuery) {
        if (this == districtSearchQuery) {
            return true;
        }
        if (districtSearchQuery == null) {
            return false;
        }
        if (this.f1148c == null) {
            if (districtSearchQuery.f1148c != null) {
                return false;
            }
        } else if (!this.f1148c.equals(districtSearchQuery.f1148c)) {
            return false;
        }
        if (this.f1149d == null) {
            if (districtSearchQuery.f1149d != null) {
                return false;
            }
        } else if (!this.f1149d.equals(districtSearchQuery.f1149d)) {
            return false;
        }
        if (this.f1147b != districtSearchQuery.f1147b) {
            return false;
        }
        if (this.f1150e != districtSearchQuery.f1150e) {
            return false;
        }
        if (this.f1151f != districtSearchQuery.f1151f) {
            return false;
        }
        return true;
    }

    public DistrictSearchQuery clone() {
        try {
            super.clone();
        } catch (Throwable e) {
            C0390i.m1594a(e, "DistrictSearchQuery", "clone");
        }
        DistrictSearchQuery districtSearchQuery = new DistrictSearchQuery(this.f1148c, this.f1149d, this.f1146a, this.f1150e, this.f1147b);
        districtSearchQuery.setShowBoundary(this.f1151f);
        return districtSearchQuery;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2;
        int i3 = 1;
        parcel.writeString(this.f1148c);
        parcel.writeString(this.f1149d);
        parcel.writeInt(this.f1146a);
        parcel.writeInt(this.f1147b);
        if (this.f1150e) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        parcel.writeByte((byte) i2);
        if (!this.f1151f) {
            i3 = 0;
        }
        parcel.writeByte((byte) i3);
    }
}
