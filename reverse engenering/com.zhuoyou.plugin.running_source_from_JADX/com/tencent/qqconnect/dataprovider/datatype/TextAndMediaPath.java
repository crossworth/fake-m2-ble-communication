package com.tencent.qqconnect.dataprovider.datatype;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ProGuard */
public class TextAndMediaPath implements Parcelable {
    public static final Creator<TextAndMediaPath> CREATOR = new C13631();
    private String f4263a;
    private String f4264b;

    /* compiled from: ProGuard */
    static class C13631 implements Creator<TextAndMediaPath> {
        C13631() {
        }

        public TextAndMediaPath createFromParcel(Parcel parcel) {
            return new TextAndMediaPath(parcel);
        }

        public TextAndMediaPath[] newArray(int i) {
            return new TextAndMediaPath[i];
        }
    }

    public TextAndMediaPath(String str, String str2) {
        this.f4263a = str;
        this.f4264b = str2;
    }

    public String getText() {
        return this.f4263a;
    }

    public String getMediaPath() {
        return this.f4264b;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f4263a);
        parcel.writeString(this.f4264b);
    }

    private TextAndMediaPath(Parcel parcel) {
        this.f4263a = parcel.readString();
        this.f4264b = parcel.readString();
    }
}
