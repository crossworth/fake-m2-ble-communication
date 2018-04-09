package com.droi.sdk.core.priv;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ParcelableBinder implements Parcelable {
    public static final Creator<ParcelableBinder> CREATOR = new C08901();
    IBinder mBinder;

    static class C08901 implements Creator<ParcelableBinder> {
        C08901() {
        }

        public ParcelableBinder createFromParcel(Parcel parcel) {
            return new ParcelableBinder(parcel);
        }

        public ParcelableBinder[] newArray(int i) {
            return new ParcelableBinder[i];
        }
    }

    public ParcelableBinder(IBinder iBinder) {
        this.mBinder = iBinder;
    }

    private ParcelableBinder(Parcel parcel) {
        this.mBinder = parcel.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public IBinder getBinder() {
        return this.mBinder;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mBinder);
    }
}
