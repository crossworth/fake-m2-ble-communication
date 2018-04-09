package com.baidu.mapapi.search.poi;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import java.util.List;

public class PoiResult extends SearchResult implements Parcelable {
    public static final Creator<PoiResult> CREATOR = new C0551c();
    private int f1643a = 0;
    private int f1644b = 0;
    private int f1645c = 0;
    private int f1646d = 0;
    private List<PoiInfo> f1647e;
    private List<CityInfo> f1648f;
    private List<PoiAddrInfo> f1649g;
    private boolean f1650h = false;

    PoiResult() {
    }

    PoiResult(Parcel parcel) {
        this.f1643a = parcel.readInt();
        this.f1644b = parcel.readInt();
        this.f1645c = parcel.readInt();
        this.f1646d = parcel.readInt();
        this.f1647e = parcel.readArrayList(PoiInfo.class.getClassLoader());
        this.f1648f = parcel.readArrayList(CityInfo.class.getClassLoader());
    }

    PoiResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1530a(int i) {
        this.f1643a = i;
    }

    void m1531a(List<PoiInfo> list) {
        this.f1647e = list;
    }

    void m1532a(boolean z) {
        this.f1650h = z;
    }

    void m1533b(int i) {
        this.f1644b = i;
    }

    void m1534b(List<PoiAddrInfo> list) {
        this.f1649g = list;
    }

    void m1535c(int i) {
        this.f1645c = i;
    }

    void m1536c(List<CityInfo> list) {
        this.f1648f = list;
    }

    void m1537d(int i) {
        this.f1646d = i;
    }

    public int describeContents() {
        return 0;
    }

    public List<PoiAddrInfo> getAllAddr() {
        return this.f1649g;
    }

    public List<PoiInfo> getAllPoi() {
        return this.f1647e;
    }

    public int getCurrentPageCapacity() {
        return this.f1645c;
    }

    public int getCurrentPageNum() {
        return this.f1643a;
    }

    public List<CityInfo> getSuggestCityList() {
        return this.f1648f;
    }

    public int getTotalPageNum() {
        return this.f1644b;
    }

    public int getTotalPoiNum() {
        return this.f1646d;
    }

    public boolean isHasAddrInfo() {
        return this.f1650h;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1643a);
        parcel.writeInt(this.f1644b);
        parcel.writeInt(this.f1645c);
        parcel.writeInt(this.f1646d);
        parcel.writeList(this.f1647e);
        parcel.writeList(this.f1648f);
    }
}
