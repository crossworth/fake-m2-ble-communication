package com.baidu.mapapi.search.district;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import java.util.ArrayList;
import java.util.List;

public class DistrictResult extends SearchResult implements Parcelable {
    public static final Creator<DistrictResult> CREATOR = new C0539a();
    private LatLng f1565a = null;
    private List<List<LatLng>> f1566b = null;
    private int f1567c;
    private String f1568d = null;

    DistrictResult() {
    }

    protected DistrictResult(Parcel parcel) {
        super(parcel);
        this.f1565a = (LatLng) parcel.readParcelable(LatLng.class.getClassLoader());
        int readInt = parcel.readInt();
        if (readInt > 0) {
            this.f1566b = new ArrayList();
            for (int i = 0; i < readInt; i++) {
                this.f1566b.add(parcel.createTypedArrayList(LatLng.CREATOR));
            }
        }
        this.f1567c = parcel.readInt();
        this.f1568d = parcel.readString();
    }

    DistrictResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1465a(int i) {
        this.f1567c = i;
    }

    void m1466a(LatLng latLng) {
        this.f1565a = latLng;
    }

    void m1467a(String str) {
        this.f1568d = str;
    }

    void m1468a(List<List<LatLng>> list) {
        this.f1566b = list;
    }

    public int describeContents() {
        return 0;
    }

    public LatLng getCenterPt() {
        return this.f1565a;
    }

    public int getCityCode() {
        return this.f1567c;
    }

    public String getCityName() {
        return this.f1568d;
    }

    public List<List<LatLng>> getPolylines() {
        return this.f1566b;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.f1565a, i);
        parcel.writeInt(this.f1566b == null ? 0 : this.f1566b.size());
        for (List writeTypedList : this.f1566b) {
            parcel.writeTypedList(writeTypedList);
        }
        parcel.writeInt(this.f1567c);
        parcel.writeString(this.f1568d);
    }
}
