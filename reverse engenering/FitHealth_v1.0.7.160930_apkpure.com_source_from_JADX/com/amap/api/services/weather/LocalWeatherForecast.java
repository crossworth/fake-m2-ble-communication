package com.amap.api.services.weather;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class LocalWeatherForecast implements Parcelable {
    public static final Creator<LocalWeatherForecast> CREATOR = new C04361();
    private String f1663a;
    private String f1664b;
    private String f1665c;
    private String f1666d;
    private List<LocalDayWeatherForecast> f1667e = new ArrayList();

    static class C04361 implements Creator<LocalWeatherForecast> {
        C04361() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1721a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1722a(i);
        }

        public LocalWeatherForecast m1721a(Parcel parcel) {
            return new LocalWeatherForecast(parcel);
        }

        public LocalWeatherForecast[] m1722a(int i) {
            return null;
        }
    }

    public String getProvince() {
        return this.f1663a;
    }

    public void setProvince(String str) {
        this.f1663a = str;
    }

    public String getCity() {
        return this.f1664b;
    }

    public void setCity(String str) {
        this.f1664b = str;
    }

    public String getAdCode() {
        return this.f1665c;
    }

    public void setAdCode(String str) {
        this.f1665c = str;
    }

    public String getReportTime() {
        return this.f1666d;
    }

    public void setReportTime(String str) {
        this.f1666d = str;
    }

    public List<LocalDayWeatherForecast> getWeatherForecast() {
        return this.f1667e;
    }

    public void setWeatherForecast(List<LocalDayWeatherForecast> list) {
        this.f1667e = list;
    }

    public LocalWeatherForecast(Parcel parcel) {
        this.f1663a = parcel.readString();
        this.f1664b = parcel.readString();
        this.f1665c = parcel.readString();
        this.f1666d = parcel.readString();
        this.f1667e = parcel.readArrayList(LocalWeatherForecast.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1663a);
        parcel.writeString(this.f1664b);
        parcel.writeString(this.f1665c);
        parcel.writeString(this.f1666d);
        parcel.writeList(this.f1667e);
    }
}
