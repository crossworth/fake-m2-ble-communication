package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import java.util.ArrayList;
import java.util.List;

public class WalkRouteResult extends RouteResult implements Parcelable {
    public static final Creator<WalkRouteResult> CREATOR = new C04331();
    private List<WalkPath> f4415a = new ArrayList();
    private WalkRouteQuery f4416b;

    static class C04331 implements Creator<WalkRouteResult> {
        C04331() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1715a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1716a(i);
        }

        public WalkRouteResult m1715a(Parcel parcel) {
            return new WalkRouteResult(parcel);
        }

        public WalkRouteResult[] m1716a(int i) {
            return new WalkRouteResult[i];
        }
    }

    public List<WalkPath> getPaths() {
        return this.f4415a;
    }

    public void setPaths(List<WalkPath> list) {
        this.f4415a = list;
    }

    public WalkRouteQuery getWalkQuery() {
        return this.f4416b;
    }

    public void setWalkQuery(WalkRouteQuery walkRouteQuery) {
        this.f4416b = walkRouteQuery;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.f4415a);
        parcel.writeParcelable(this.f4416b, i);
    }

    public WalkRouteResult(Parcel parcel) {
        super(parcel);
        this.f4415a = parcel.createTypedArrayList(WalkPath.CREATOR);
        this.f4416b = (WalkRouteQuery) parcel.readParcelable(WalkRouteQuery.class.getClassLoader());
    }
}
