package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class SearchCity implements Parcelable {
    public static final Creator<SearchCity> CREATOR = new C04301();
    private String f1627a;
    private String f1628b;
    private String f1629c;

    static class C04301 implements Creator<SearchCity> {
        C04301() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1709a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1710a(i);
        }

        public SearchCity m1709a(Parcel parcel) {
            return new SearchCity(parcel);
        }

        public SearchCity[] m1710a(int i) {
            return null;
        }
    }

    public String getSearchCityName() {
        return this.f1627a;
    }

    public void setSearchCityName(String str) {
        this.f1627a = str;
    }

    public String getSearchCitycode() {
        return this.f1628b;
    }

    public void setSearchCitycode(String str) {
        this.f1628b = str;
    }

    public String getSearchCityAdCode() {
        return this.f1629c;
    }

    public void setSearchCityhAdCode(String str) {
        this.f1629c = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1627a);
        parcel.writeString(this.f1628b);
        parcel.writeString(this.f1629c);
    }

    public SearchCity(Parcel parcel) {
        this.f1627a = parcel.readString();
        this.f1628b = parcel.readString();
        this.f1629c = parcel.readString();
    }
}
