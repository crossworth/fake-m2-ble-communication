package com.baidu.mapapi.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ParcelItem implements Parcelable {
    public static final Creator<ParcelItem> CREATOR = new C0512c();
    private Bundle f1462a;

    public int describeContents() {
        return 0;
    }

    public Bundle getBundle() {
        return this.f1462a;
    }

    public void setBundle(Bundle bundle) {
        this.f1462a = bundle;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.f1462a);
    }
}
