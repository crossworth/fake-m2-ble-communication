package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import java.util.ArrayList;
import java.util.List;

public class BikingRouteResult extends SearchResult implements Parcelable {
    public static final Creator<BikingRouteLine> CREATOR = new C0557c();
    private List<BikingRouteLine> f1669a;
    private SuggestAddrInfo f1670b;

    BikingRouteResult() {
    }

    protected BikingRouteResult(Parcel parcel) {
        this.f1669a = new ArrayList();
        parcel.readList(this.f1669a, BikingRouteLine.class.getClassLoader());
        this.f1670b = (SuggestAddrInfo) parcel.readParcelable(SuggestAddrInfo.class.getClassLoader());
    }

    BikingRouteResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1577a(SuggestAddrInfo suggestAddrInfo) {
        this.f1670b = suggestAddrInfo;
    }

    void m1578a(List<BikingRouteLine> list) {
        this.f1669a = list;
    }

    public int describeContents() {
        return 0;
    }

    public List<BikingRouteLine> getRouteLines() {
        return this.f1669a;
    }

    public SuggestAddrInfo getSuggestAddrInfo() {
        return this.f1670b;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.f1669a);
        parcel.writeParcelable(this.f1670b, 1);
    }
}
