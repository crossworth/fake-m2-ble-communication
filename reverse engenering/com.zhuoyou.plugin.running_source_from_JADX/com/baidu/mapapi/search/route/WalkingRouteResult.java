package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.core.TaxiInfo;
import java.util.ArrayList;
import java.util.List;

public class WalkingRouteResult extends SearchResult implements Parcelable {
    public static final Creator<WalkingRouteResult> CREATOR = new C0575u();
    private List<WalkingRouteLine> f1793a;
    private TaxiInfo f1794b;
    private SuggestAddrInfo f1795c;

    WalkingRouteResult() {
    }

    protected WalkingRouteResult(Parcel parcel) {
        this.f1793a = new ArrayList();
        parcel.readList(this.f1793a, WalkingRouteLine.class.getClassLoader());
        this.f1794b = (TaxiInfo) parcel.readParcelable(TaxiInfo.class.getClassLoader());
        this.f1795c = (SuggestAddrInfo) parcel.readParcelable(SuggestAddrInfo.class.getClassLoader());
    }

    WalkingRouteResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1655a(TaxiInfo taxiInfo) {
        this.f1794b = taxiInfo;
    }

    void m1656a(SuggestAddrInfo suggestAddrInfo) {
        this.f1795c = suggestAddrInfo;
    }

    void m1657a(List<WalkingRouteLine> list) {
        this.f1793a = list;
    }

    public int describeContents() {
        return 0;
    }

    public List<WalkingRouteLine> getRouteLines() {
        return this.f1793a;
    }

    public SuggestAddrInfo getSuggestAddrInfo() {
        return this.f1795c;
    }

    public TaxiInfo getTaxiInfo() {
        return this.f1794b;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.f1793a);
        parcel.writeParcelable(this.f1794b, 1);
        parcel.writeParcelable(this.f1795c, 1);
    }
}
