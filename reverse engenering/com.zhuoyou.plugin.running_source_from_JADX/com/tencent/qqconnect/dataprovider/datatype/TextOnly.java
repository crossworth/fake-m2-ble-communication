package com.tencent.qqconnect.dataprovider.datatype;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ProGuard */
public class TextOnly implements Parcelable {
    public static final Creator<TextOnly> CREATOR = new C13641();
    private String f4265a;

    /* compiled from: ProGuard */
    static class C13641 implements Creator<TextOnly> {
        C13641() {
        }

        public TextOnly createFromParcel(Parcel parcel) {
            return new TextOnly(parcel);
        }

        public TextOnly[] newArray(int i) {
            return new TextOnly[i];
        }
    }

    public TextOnly(String str) {
        this.f4265a = str;
    }

    public String getText() {
        return this.f4265a;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f4265a);
    }

    private TextOnly(Parcel parcel) {
        this.f4265a = parcel.readString();
    }
}
