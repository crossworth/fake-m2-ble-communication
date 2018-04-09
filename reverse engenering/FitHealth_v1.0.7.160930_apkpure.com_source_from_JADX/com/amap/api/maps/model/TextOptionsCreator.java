package com.amap.api.maps.model;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import p031u.aly.au;

public class TextOptionsCreator implements Creator<TextOptions> {
    public TextOptions createFromParcel(Parcel parcel) {
        boolean z = true;
        TextOptions textOptions = new TextOptions();
        textOptions.f947a = parcel.readString();
        Bundle readBundle = parcel.readBundle();
        textOptions.position(new LatLng(readBundle.getDouble(au.f3570Y), readBundle.getDouble(au.f3571Z)));
        textOptions.text(parcel.readString());
        textOptions.typeface(Typeface.defaultFromStyle(parcel.readInt()));
        textOptions.rotate(parcel.readFloat());
        textOptions.align(parcel.readInt(), parcel.readInt());
        textOptions.backgroundColor(parcel.readInt());
        textOptions.fontColor(parcel.readInt());
        textOptions.fontSize(parcel.readInt());
        textOptions.zIndex(parcel.readFloat());
        if (parcel.readByte() != (byte) 1) {
            z = false;
        }
        textOptions.visible(z);
        try {
            Parcelable parcelable = parcel.readBundle().getParcelable("obj");
            if (parcelable != null) {
                textOptions.setObject(parcelable);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return textOptions;
    }

    public TextOptions[] newArray(int i) {
        return new TextOptions[i];
    }
}
