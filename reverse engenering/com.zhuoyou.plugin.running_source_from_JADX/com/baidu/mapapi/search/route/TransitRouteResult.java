package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.core.TaxiInfo;
import java.util.ArrayList;
import java.util.List;

public final class TransitRouteResult extends SearchResult implements Parcelable {
    public static final Creator<TransitRouteResult> CREATOR = new C0572r();
    private TaxiInfo f1781a;
    private List<TransitRouteLine> f1782b;
    private SuggestAddrInfo f1783c;

    TransitRouteResult() {
    }

    protected TransitRouteResult(Parcel parcel) {
        this.f1781a = (TaxiInfo) parcel.readParcelable(TaxiInfo.class.getClassLoader());
        this.f1782b = new ArrayList();
        parcel.readList(this.f1782b, TransitRouteLine.class.getClassLoader());
        this.f1783c = (SuggestAddrInfo) parcel.readParcelable(SuggestAddrInfo.class.getClassLoader());
    }

    TransitRouteResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1645a(TaxiInfo taxiInfo) {
        this.f1781a = taxiInfo;
    }

    void m1646a(SuggestAddrInfo suggestAddrInfo) {
        this.f1783c = suggestAddrInfo;
    }

    void m1647a(List<TransitRouteLine> list) {
        this.f1782b = list;
    }

    public int describeContents() {
        return 0;
    }

    public List<TransitRouteLine> getRouteLines() {
        return this.f1782b;
    }

    public SuggestAddrInfo getSuggestAddrInfo() {
        return this.f1783c;
    }

    public TaxiInfo getTaxiInfo() {
        return this.f1781a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f1781a, 1);
        parcel.writeList(this.f1782b);
        parcel.writeParcelable(this.f1783c, 1);
    }
}
