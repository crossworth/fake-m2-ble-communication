package com.amap.api.services.cloud;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CloudImage implements Parcelable {
    public static final Creator<CloudImage> CREATOR = new C03171();
    private String f1065a;
    private String f1066b;
    private String f1067c;

    static class C03171 implements Creator<CloudImage> {
        C03171() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1143a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1144a(i);
        }

        public CloudImage m1143a(Parcel parcel) {
            return new CloudImage(parcel);
        }

        public CloudImage[] m1144a(int i) {
            return new CloudImage[i];
        }
    }

    public CloudImage(String str, String str2, String str3) {
        this.f1065a = str;
        this.f1066b = str2;
        this.f1067c = str3;
    }

    public CloudImage(Parcel parcel) {
        this.f1065a = parcel.readString();
        this.f1066b = parcel.readString();
        this.f1067c = parcel.readString();
    }

    public String getId() {
        return this.f1065a;
    }

    public void setId(String str) {
        this.f1065a = str;
    }

    public String getPreurl() {
        return this.f1066b;
    }

    public void setPreurl(String str) {
        this.f1066b = str;
    }

    public String getUrl() {
        return this.f1067c;
    }

    public void setUrl(String str) {
        this.f1067c = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1065a);
        parcel.writeString(this.f1066b);
        parcel.writeString(this.f1067c);
    }
}
