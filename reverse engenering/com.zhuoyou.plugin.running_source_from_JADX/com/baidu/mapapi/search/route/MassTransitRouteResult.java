package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.mapapi.search.core.TaxiInfo;
import com.baidu.mapapi.search.core.TransitResultNode;
import java.util.ArrayList;
import java.util.List;

public final class MassTransitRouteResult extends SearchResult implements Parcelable {
    public static final Creator<MassTransitRouteResult> CREATOR = new C0566l();
    private TransitResultNode f1746a;
    private TransitResultNode f1747b;
    private TaxiInfo f1748c;
    private int f1749d;
    private List<MassTransitRouteLine> f1750e;
    private SuggestAddrInfo f1751f;

    MassTransitRouteResult() {
    }

    MassTransitRouteResult(Parcel parcel) {
        this.f1746a = (TransitResultNode) parcel.readParcelable(TransitResultNode.class.getClassLoader());
        this.f1747b = (TransitResultNode) parcel.readParcelable(TransitResultNode.class.getClassLoader());
        this.f1748c = (TaxiInfo) parcel.readParcelable(TaxiInfo.class.getClassLoader());
        this.f1749d = parcel.readInt();
        this.f1750e = new ArrayList();
        parcel.readList(this.f1750e, MassTransitRouteLine.class.getClassLoader());
        this.f1751f = (SuggestAddrInfo) parcel.readParcelable(SuggestAddrInfo.class.getClassLoader());
    }

    MassTransitRouteResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1607a(int i) {
        this.f1749d = i;
    }

    void m1608a(TransitResultNode transitResultNode) {
        this.f1746a = transitResultNode;
    }

    void m1609a(SuggestAddrInfo suggestAddrInfo) {
        this.f1751f = suggestAddrInfo;
    }

    void m1610a(List<MassTransitRouteLine> list) {
        this.f1750e = list;
    }

    void m1611b(TransitResultNode transitResultNode) {
        this.f1747b = transitResultNode;
    }

    public int describeContents() {
        return 0;
    }

    public TransitResultNode getDestination() {
        return this.f1747b;
    }

    public TransitResultNode getOrigin() {
        return this.f1746a;
    }

    public List<MassTransitRouteLine> getRouteLines() {
        return this.f1750e;
    }

    public SuggestAddrInfo getSuggestAddrInfo() {
        return this.f1751f;
    }

    public TaxiInfo getTaxiInfo() {
        return this.f1748c;
    }

    public int getTotal() {
        return this.f1749d;
    }

    public void setTaxiInfo(TaxiInfo taxiInfo) {
        this.f1748c = taxiInfo;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f1746a, 1);
        parcel.writeParcelable(this.f1747b, 1);
        parcel.writeParcelable(this.f1748c, 1);
        parcel.writeInt(this.f1749d);
        parcel.writeList(this.f1750e);
        parcel.writeParcelable(this.f1751f, 1);
    }
}
