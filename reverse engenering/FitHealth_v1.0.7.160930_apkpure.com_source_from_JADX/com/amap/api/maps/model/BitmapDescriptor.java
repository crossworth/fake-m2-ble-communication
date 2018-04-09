package com.amap.api.maps.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.amap.api.mapcore.util.dj;

public final class BitmapDescriptor implements Parcelable, Cloneable {
    public static final BitmapDescriptorCreator CREATOR = new BitmapDescriptorCreator();
    int f828a = 0;
    int f829b = 0;
    Bitmap f830c;

    BitmapDescriptor(Bitmap bitmap) {
        if (bitmap != null) {
            this.f828a = bitmap.getWidth();
            this.f829b = bitmap.getHeight();
            this.f830c = m1078a(bitmap, dj.m559a(this.f828a), dj.m559a(this.f829b));
        }
    }

    private BitmapDescriptor(Bitmap bitmap, int i, int i2) {
        this.f828a = i;
        this.f829b = i2;
        this.f830c = bitmap;
    }

    public BitmapDescriptor clone() {
        try {
            return new BitmapDescriptor(Bitmap.createBitmap(this.f830c), this.f828a, this.f829b);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public Bitmap getBitmap() {
        return this.f830c;
    }

    public int getWidth() {
        return this.f828a;
    }

    public int getHeight() {
        return this.f829b;
    }

    private Bitmap m1078a(Bitmap bitmap, int i, int i2) {
        return dj.m566a(bitmap, i, i2);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f830c, i);
        parcel.writeInt(this.f828a);
        parcel.writeInt(this.f829b);
    }

    public void recycle() {
        if (this.f830c != null && !this.f830c.isRecycled()) {
            this.f830c.recycle();
            this.f830c = null;
        }
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (this.f830c == null || this.f830c.isRecycled() || obj == null) {
            return z;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return z;
        }
        BitmapDescriptor bitmapDescriptor = (BitmapDescriptor) obj;
        if (bitmapDescriptor.f830c == null || bitmapDescriptor.f830c.isRecycled() || this.f828a != bitmapDescriptor.getWidth() || this.f829b != bitmapDescriptor.getHeight()) {
            return z;
        }
        try {
            return this.f830c.sameAs(bitmapDescriptor.f830c);
        } catch (Throwable th) {
            return z;
        }
    }
}
