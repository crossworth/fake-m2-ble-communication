package com.baidu.mapapi.search.sug;

import android.os.Parcel;
import android.os.Parcelable.Creator;

final class C0582a implements Creator<SuggestionResult> {
    C0582a() {
    }

    public SuggestionResult m1772a(Parcel parcel) {
        return new SuggestionResult(parcel);
    }

    public SuggestionResult[] m1773a(int i) {
        return new SuggestionResult[i];
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return m1772a(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return m1773a(i);
    }
}
