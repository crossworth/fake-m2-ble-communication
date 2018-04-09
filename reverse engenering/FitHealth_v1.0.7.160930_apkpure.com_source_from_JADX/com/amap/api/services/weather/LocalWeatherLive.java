package com.amap.api.services.weather;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LocalWeatherLive implements Parcelable {
    public static final Creator<LocalWeatherLive> CREATOR = new C04371();
    private String f1670a;
    private String f1671b;
    private String f1672c;
    private String f1673d;
    private String f1674e;
    private String f1675f;
    private String f1676g;
    private String f1677h;
    private String f1678i;

    static class C04371 implements Creator<LocalWeatherLive> {
        C04371() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1723a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1724a(i);
        }

        public LocalWeatherLive m1723a(Parcel parcel) {
            return new LocalWeatherLive(parcel);
        }

        public LocalWeatherLive[] m1724a(int i) {
            return null;
        }
    }

    public LocalWeatherLive(Parcel parcel) {
        this.f1670a = parcel.readString();
        this.f1671b = parcel.readString();
        this.f1672c = parcel.readString();
        this.f1673d = parcel.readString();
        this.f1674e = parcel.readString();
        this.f1675f = parcel.readString();
        this.f1676g = parcel.readString();
        this.f1677h = parcel.readString();
        this.f1678i = parcel.readString();
    }

    public String getProvince() {
        return this.f1670a;
    }

    public String getCity() {
        return this.f1671b;
    }

    public String getAdCode() {
        return this.f1672c;
    }

    public String getWeather() {
        return this.f1673d;
    }

    public String getTemperature() {
        return this.f1674e;
    }

    public String getWindDirection() {
        return this.f1675f;
    }

    public String getWindPower() {
        return this.f1676g;
    }

    public String getHumidity() {
        return this.f1677h;
    }

    public String getReportTime() {
        return this.f1678i;
    }

    public void setProvince(String str) {
        this.f1670a = str;
    }

    public void setCity(String str) {
        this.f1671b = str;
    }

    public void setAdCode(String str) {
        this.f1672c = str;
    }

    public void setWeather(String str) {
        this.f1673d = str;
    }

    public void setTemperature(String str) {
        this.f1674e = str;
    }

    public void setWindDirection(String str) {
        this.f1675f = str;
    }

    public void setWindPower(String str) {
        this.f1676g = str;
    }

    public void setHumidity(String str) {
        this.f1677h = str;
    }

    public void setReportTime(String str) {
        this.f1678i = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1670a);
        parcel.writeString(this.f1671b);
        parcel.writeString(this.f1672c);
        parcel.writeString(this.f1673d);
        parcel.writeString(this.f1674e);
        parcel.writeString(this.f1675f);
        parcel.writeString(this.f1676g);
        parcel.writeString(this.f1677h);
        parcel.writeString(this.f1678i);
    }
}
