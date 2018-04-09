package com.baidu.mapapi.search.poi;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import java.util.List;

public class PoiIndoorResult extends SearchResult implements Parcelable {
    public static final Creator<PoiIndoorResult> CREATOR = new C0550b();
    int f1633a;
    int f1634b;
    private List<PoiIndoorInfo> f1635c;

    PoiIndoorResult() {
    }

    protected PoiIndoorResult(Parcel parcel) {
        super(parcel);
        this.f1633a = parcel.readInt();
        this.f1634b = parcel.readInt();
    }

    public PoiIndoorResult(ERRORNO errorno) {
        super(errorno);
    }

    public int describeContents() {
        return 0;
    }

    public int getPageNum() {
        return this.f1634b;
    }

    public int getPoiNum() {
        return this.f1633a;
    }

    public List<PoiIndoorInfo> getmArrayPoiInfo() {
        return this.f1635c;
    }

    public void setPageNum(int i) {
        this.f1634b = i;
    }

    public void setPoiNum(int i) {
        this.f1633a = i;
    }

    public void setmArrayPoiInfo(List<PoiIndoorInfo> list) {
        this.f1635c = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.f1633a);
        parcel.writeInt(this.f1634b);
    }
}
