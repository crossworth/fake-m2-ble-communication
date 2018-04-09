package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import java.util.List;

public class IndoorRouteResult extends SearchResult {
    public static final Creator<IndoorRouteResult> CREATOR = new C0562h();
    private List<IndoorRouteLine> f1714a;

    IndoorRouteResult() {
    }

    protected IndoorRouteResult(Parcel parcel) {
        super(parcel);
        this.f1714a = parcel.createTypedArrayList(IndoorRouteLine.CREATOR);
    }

    IndoorRouteResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1597a(List<IndoorRouteLine> list) {
        this.f1714a = list;
    }

    public int describeContents() {
        return 0;
    }

    public List<IndoorRouteLine> getRouteLines() {
        return this.f1714a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.f1714a);
    }
}
