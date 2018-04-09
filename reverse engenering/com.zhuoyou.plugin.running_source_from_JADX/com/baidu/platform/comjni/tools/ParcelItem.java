package com.baidu.platform.comjni.tools;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ParcelItem implements Parcelable {
    public static final Creator<ParcelItem> CREATOR = new C0681b();
    private Bundle f2246a;

    public int describeContents() {
        return 0;
    }

    public Bundle getBundle() {
        return this.f2246a;
    }

    public void setBundle(Bundle bundle) {
        this.f2246a = bundle;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.f2246a);
    }
}
