package com.tencent.open.yyb;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ProGuard */
public class ShareModel implements Parcelable {
    public static final Creator<ShareModel> CREATOR = new C13581();
    public String f4256a;
    public String f4257b;
    public String f4258c;
    public String f4259d;

    /* compiled from: ProGuard */
    static class C13581 implements Creator<ShareModel> {
        C13581() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m3983a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m3984a(i);
        }

        public ShareModel m3983a(Parcel parcel) {
            ShareModel shareModel = new ShareModel();
            shareModel.f4256a = parcel.readString();
            shareModel.f4257b = parcel.readString();
            shareModel.f4258c = parcel.readString();
            shareModel.f4259d = parcel.readString();
            return shareModel;
        }

        public ShareModel[] m3984a(int i) {
            return null;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f4256a);
        parcel.writeString(this.f4257b);
        parcel.writeString(this.f4258c);
        parcel.writeString(this.f4259d);
    }
}
