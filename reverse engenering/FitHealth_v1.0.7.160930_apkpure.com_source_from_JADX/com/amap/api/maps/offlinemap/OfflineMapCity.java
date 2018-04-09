package com.amap.api.maps.offlinemap;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class OfflineMapCity extends City {
    public static final Creator<OfflineMapCity> CREATOR = new C0310b();
    private String f4242a = "";
    private long f4243b = 0;
    private int f4244c = 6;
    private String f4245d = "";
    private int f4246e = 0;

    public String getUrl() {
        return this.f4242a;
    }

    public void setUrl(String str) {
        this.f4242a = str;
    }

    public long getSize() {
        return this.f4243b;
    }

    public void setSize(long j) {
        this.f4243b = j;
    }

    public int getState() {
        return this.f4244c;
    }

    public void setState(int i) {
        this.f4244c = i;
    }

    public String getVersion() {
        return this.f4245d;
    }

    public void setVersion(String str) {
        this.f4245d = str;
    }

    public int getcompleteCode() {
        return this.f4246e;
    }

    public void setCompleteCode(int i) {
        this.f4246e = i;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.f4242a);
        parcel.writeLong(this.f4243b);
        parcel.writeInt(this.f4244c);
        parcel.writeString(this.f4245d);
        parcel.writeInt(this.f4246e);
    }

    public OfflineMapCity(Parcel parcel) {
        super(parcel);
        this.f4242a = parcel.readString();
        this.f4243b = parcel.readLong();
        this.f4244c = parcel.readInt();
        this.f4245d = parcel.readString();
        this.f4246e = parcel.readInt();
    }
}
