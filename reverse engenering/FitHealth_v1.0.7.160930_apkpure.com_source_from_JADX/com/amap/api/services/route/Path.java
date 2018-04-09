package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Path implements Parcelable {
    public static final Creator<Path> CREATOR = new C04211();
    private float f1607a;
    private long f1608b;

    static class C04211 implements Creator<Path> {
        C04211() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1691a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1692a(i);
        }

        public Path m1691a(Parcel parcel) {
            return new Path(parcel);
        }

        public Path[] m1692a(int i) {
            return null;
        }
    }

    public float getDistance() {
        return this.f1607a;
    }

    public void setDistance(float f) {
        this.f1607a = f;
    }

    public long getDuration() {
        return this.f1608b;
    }

    public void setDuration(long j) {
        this.f1608b = j;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.f1607a);
        parcel.writeLong(this.f1608b);
    }

    public Path(Parcel parcel) {
        this.f1607a = parcel.readFloat();
        this.f1608b = parcel.readLong();
    }
}
