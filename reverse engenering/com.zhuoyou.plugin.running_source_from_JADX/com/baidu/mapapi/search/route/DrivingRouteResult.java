package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.core.TaxiInfo;
import java.util.ArrayList;
import java.util.List;

public final class DrivingRouteResult extends SearchResult implements Parcelable {
    public static final Creator<DrivingRouteResult> CREATOR = new C0560f();
    private List<DrivingRouteLine> f1695a;
    private List<TaxiInfo> f1696b;
    private TaxiInfo f1697c;
    private SuggestAddrInfo f1698d;

    DrivingRouteResult() {
    }

    protected DrivingRouteResult(Parcel parcel) {
        this.f1695a = new ArrayList();
        parcel.readTypedList(this.f1695a, DrivingRouteLine.CREATOR);
        this.f1696b = new ArrayList();
        parcel.readTypedList(this.f1696b, TaxiInfo.CREATOR);
        this.f1698d = (SuggestAddrInfo) parcel.readParcelable(SuggestAddrInfo.class.getClassLoader());
    }

    DrivingRouteResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1589a(SuggestAddrInfo suggestAddrInfo) {
        this.f1698d = suggestAddrInfo;
    }

    void m1590a(List<DrivingRouteLine> list) {
        this.f1695a = list;
    }

    public int describeContents() {
        return 0;
    }

    public List<DrivingRouteLine> getRouteLines() {
        return this.f1695a;
    }

    public SuggestAddrInfo getSuggestAddrInfo() {
        return this.f1698d;
    }

    @Deprecated
    public TaxiInfo getTaxiInfo() {
        return this.f1697c;
    }

    public List<TaxiInfo> getTaxiInfos() {
        return this.f1696b;
    }

    public void setTaxiInfos(List<TaxiInfo> list) {
        this.f1696b = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.f1695a);
        parcel.writeTypedList(this.f1696b);
        parcel.writeParcelable(this.f1698d, 1);
    }
}
