package com.baidu.mapapi.search.sug;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.sug.SuggestionResult.SuggestionInfo;

final class C0583b implements Creator<SuggestionInfo> {
    C0583b() {
    }

    public SuggestionInfo m1774a(Parcel parcel) {
        return new SuggestionInfo(parcel);
    }

    public SuggestionInfo[] m1775a(int i) {
        return new SuggestionInfo[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1774a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1775a(i);
    }
}
