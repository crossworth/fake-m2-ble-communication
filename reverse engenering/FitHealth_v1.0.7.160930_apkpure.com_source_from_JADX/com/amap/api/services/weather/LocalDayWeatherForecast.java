package com.amap.api.services.weather;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LocalDayWeatherForecast implements Parcelable {
    public static final Creator<LocalDayWeatherForecast> CREATOR = new C04351();
    private String f1653a;
    private String f1654b;
    private String f1655c;
    private String f1656d;
    private String f1657e;
    private String f1658f;
    private String f1659g;
    private String f1660h;
    private String f1661i;
    private String f1662j;

    static class C04351 implements Creator<LocalDayWeatherForecast> {
        C04351() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1719a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1720a(i);
        }

        public LocalDayWeatherForecast m1719a(Parcel parcel) {
            return new LocalDayWeatherForecast(parcel);
        }

        public LocalDayWeatherForecast[] m1720a(int i) {
            return null;
        }
    }

    public String getDate() {
        return this.f1653a;
    }

    public void setDate(String str) {
        this.f1653a = str;
    }

    public String getWeek() {
        return this.f1654b;
    }

    public void setWeek(String str) {
        this.f1654b = str;
    }

    public String getDayWeather() {
        return this.f1655c;
    }

    public void setDayWeather(String str) {
        this.f1655c = str;
    }

    public String getNightWeather() {
        return this.f1656d;
    }

    public void setNightWeather(String str) {
        this.f1656d = str;
    }

    public String getDayTemp() {
        return this.f1657e;
    }

    public void setDayTemp(String str) {
        this.f1657e = str;
    }

    public String getNightTemp() {
        return this.f1658f;
    }

    public void setNightTemp(String str) {
        this.f1658f = str;
    }

    public String getDayWindDirection() {
        return this.f1659g;
    }

    public void setDayWindDirection(String str) {
        this.f1659g = str;
    }

    public String getNightWindDirection() {
        return this.f1660h;
    }

    public void setNightWindDirection(String str) {
        this.f1660h = str;
    }

    public String getDayWindPower() {
        return this.f1661i;
    }

    public void setDayWindPower(String str) {
        this.f1661i = str;
    }

    public String getNightWindPower() {
        return this.f1662j;
    }

    public void setNightWindPower(String str) {
        this.f1662j = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1653a);
        parcel.writeString(this.f1654b);
        parcel.writeString(this.f1655c);
        parcel.writeString(this.f1656d);
        parcel.writeString(this.f1657e);
        parcel.writeString(this.f1658f);
        parcel.writeString(this.f1659g);
        parcel.writeString(this.f1660h);
        parcel.writeString(this.f1661i);
        parcel.writeString(this.f1662j);
    }

    public LocalDayWeatherForecast(Parcel parcel) {
        this.f1653a = parcel.readString();
        this.f1654b = parcel.readString();
        this.f1655c = parcel.readString();
        this.f1656d = parcel.readString();
        this.f1657e = parcel.readString();
        this.f1658f = parcel.readString();
        this.f1659g = parcel.readString();
        this.f1660h = parcel.readString();
        this.f1661i = parcel.readString();
        this.f1662j = parcel.readString();
    }
}
