package com.tencent.open.yyb;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ProGuard */
public class ShareModel implements Parcelable {
    public static final Creator<ShareModel> CREATOR = new C0816a();
    public String f2754a;
    public String f2755b;
    public String f2756c;
    public String f2757d;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f2754a);
        parcel.writeString(this.f2755b);
        parcel.writeString(this.f2756c);
        parcel.writeString(this.f2757d);
    }
}
