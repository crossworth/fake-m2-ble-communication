package com.amap.api.maps.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import p031u.aly.au;

public class CircleOptionsCreator implements Creator<CircleOptions> {
    public CircleOptions createFromParcel(Parcel parcel) {
        boolean z = true;
        CircleOptions circleOptions = new CircleOptions();
        Bundle readBundle = parcel.readBundle();
        circleOptions.center(new LatLng(readBundle.getDouble(au.f3570Y), readBundle.getDouble(au.f3571Z)));
        circleOptions.radius(parcel.readDouble());
        circleOptions.strokeWidth(parcel.readFloat());
        circleOptions.strokeColor(parcel.readInt());
        circleOptions.fillColor(parcel.readInt());
        circleOptions.zIndex((float) parcel.readInt());
        if (parcel.readByte() != (byte) 1) {
            z = false;
        }
        circleOptions.visible(z);
        circleOptions.f836a = parcel.readString();
        return circleOptions;
    }

    public CircleOptions[] newArray(int i) {
        return new CircleOptions[i];
    }
}
