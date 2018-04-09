package com.amap.api.maps.offlinemap;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;

public class OfflineMapProvince extends Province {
    public static final Creator<OfflineMapProvince> CREATOR = new C0311c();
    private String f4248a;
    private int f4249b = 6;
    private long f4250c;
    private String f4251d;
    private int f4252e = 0;
    private ArrayList<OfflineMapCity> f4253f;

    public String getUrl() {
        return this.f4248a;
    }

    public void setUrl(String str) {
        this.f4248a = str;
    }

    public int getState() {
        return this.f4249b;
    }

    public void setState(int i) {
        this.f4249b = i;
    }

    public long getSize() {
        return this.f4250c;
    }

    public void setSize(long j) {
        this.f4250c = j;
    }

    public String getVersion() {
        return this.f4251d;
    }

    public void setVersion(String str) {
        this.f4251d = str;
    }

    public int getcompleteCode() {
        return this.f4252e;
    }

    public void setCompleteCode(int i) {
        this.f4252e = i;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.f4248a);
        parcel.writeInt(this.f4249b);
        parcel.writeLong(this.f4250c);
        parcel.writeString(this.f4251d);
        parcel.writeInt(this.f4252e);
        parcel.writeTypedList(this.f4253f);
    }

    public ArrayList<OfflineMapCity> getCityList() {
        if (this.f4253f == null) {
            return new ArrayList();
        }
        return this.f4253f;
    }

    public void setCityList(ArrayList<OfflineMapCity> arrayList) {
        this.f4253f = arrayList;
    }

    public OfflineMapProvince(Parcel parcel) {
        super(parcel);
        this.f4248a = parcel.readString();
        this.f4249b = parcel.readInt();
        this.f4250c = parcel.readLong();
        this.f4251d = parcel.readString();
        this.f4252e = parcel.readInt();
        this.f4253f = parcel.createTypedArrayList(OfflineMapCity.CREATOR);
    }
}
