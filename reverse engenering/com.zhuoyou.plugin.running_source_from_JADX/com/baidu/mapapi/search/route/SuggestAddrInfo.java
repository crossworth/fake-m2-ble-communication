package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import java.util.List;

public class SuggestAddrInfo implements Parcelable {
    public static final Creator<SuggestAddrInfo> CREATOR = new C0569o();
    private List<PoiInfo> f1762a;
    private List<PoiInfo> f1763b;
    private List<List<PoiInfo>> f1764c;
    private List<CityInfo> f1765d;
    private List<CityInfo> f1766e;
    private List<List<CityInfo>> f1767f;

    protected SuggestAddrInfo() {
    }

    SuggestAddrInfo(Parcel parcel) {
        this.f1762a = parcel.readArrayList(PoiInfo.class.getClassLoader());
        this.f1763b = parcel.readArrayList(PoiInfo.class.getClassLoader());
        this.f1764c = parcel.readArrayList(PoiInfo.class.getClassLoader());
        this.f1765d = parcel.readArrayList(CityInfo.class.getClassLoader());
        this.f1766e = parcel.readArrayList(CityInfo.class.getClassLoader());
        this.f1767f = parcel.readArrayList(CityInfo.class.getClassLoader());
    }

    void m1633a(List<PoiInfo> list) {
        this.f1762a = list;
    }

    void m1634b(List<PoiInfo> list) {
        this.f1763b = list;
    }

    void m1635c(List<List<PoiInfo>> list) {
        this.f1764c = list;
    }

    void m1636d(List<CityInfo> list) {
        this.f1765d = list;
    }

    public int describeContents() {
        return 0;
    }

    void m1637e(List<CityInfo> list) {
        this.f1766e = list;
    }

    void m1638f(List<List<CityInfo>> list) {
        this.f1767f = list;
    }

    public List<CityInfo> getSuggestEndCity() {
        return this.f1766e;
    }

    public List<PoiInfo> getSuggestEndNode() {
        return this.f1763b;
    }

    public List<CityInfo> getSuggestStartCity() {
        return this.f1765d;
    }

    public List<PoiInfo> getSuggestStartNode() {
        return this.f1762a;
    }

    public List<List<CityInfo>> getSuggestWpCity() {
        return this.f1767f;
    }

    public List<List<PoiInfo>> getSuggestWpNode() {
        return this.f1764c;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.f1762a);
        parcel.writeList(this.f1763b);
        parcel.writeList(this.f1764c);
        parcel.writeList(this.f1765d);
        parcel.writeList(this.f1766e);
        parcel.writeList(this.f1767f);
    }
}
