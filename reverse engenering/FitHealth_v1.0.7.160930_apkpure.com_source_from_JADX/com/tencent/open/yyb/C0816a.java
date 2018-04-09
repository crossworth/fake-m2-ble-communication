package com.tencent.open.yyb;

import android.os.Parcel;
import android.os.Parcelable.Creator;

/* compiled from: ProGuard */
class C0816a implements Creator<ShareModel> {
    C0816a() {
    }

    public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m2599a(parcel);
    }

    public /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return m2600a(i);
    }

    public ShareModel m2599a(Parcel parcel) {
        ShareModel shareModel = new ShareModel();
        shareModel.f2754a = parcel.readString();
        shareModel.f2755b = parcel.readString();
        shareModel.f2756c = parcel.readString();
        shareModel.f2757d = parcel.readString();
        return shareModel;
    }

    public ShareModel[] m2600a(int i) {
        return null;
    }
}
