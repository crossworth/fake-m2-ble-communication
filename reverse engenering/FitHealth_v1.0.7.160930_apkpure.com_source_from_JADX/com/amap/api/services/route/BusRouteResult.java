package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.route.RouteSearch.BusRouteQuery;
import java.util.ArrayList;
import java.util.List;

public class BusRouteResult extends RouteResult implements Parcelable {
    public static final Creator<BusRouteResult> CREATOR = new C04141();
    private float f4396a;
    private List<BusPath> f4397b = new ArrayList();
    private BusRouteQuery f4398c;

    static class C04141 implements Creator<BusRouteResult> {
        C04141() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1677a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1678a(i);
        }

        public BusRouteResult m1677a(Parcel parcel) {
            return new BusRouteResult(parcel);
        }

        public BusRouteResult[] m1678a(int i) {
            return new BusRouteResult[i];
        }
    }

    public float getTaxiCost() {
        return this.f4396a;
    }

    public void setTaxiCost(float f) {
        this.f4396a = f;
    }

    public List<BusPath> getPaths() {
        return this.f4397b;
    }

    public void setPaths(List<BusPath> list) {
        this.f4397b = list;
    }

    public BusRouteQuery getBusQuery() {
        return this.f4398c;
    }

    public void setBusQuery(BusRouteQuery busRouteQuery) {
        this.f4398c = busRouteQuery;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeFloat(this.f4396a);
        parcel.writeTypedList(this.f4397b);
        parcel.writeParcelable(this.f4398c, i);
    }

    public BusRouteResult(Parcel parcel) {
        super(parcel);
        this.f4396a = parcel.readFloat();
        this.f4397b = parcel.createTypedArrayList(BusPath.CREATOR);
        this.f4398c = (BusRouteQuery) parcel.readParcelable(BusRouteQuery.class.getClassLoader());
    }
}
