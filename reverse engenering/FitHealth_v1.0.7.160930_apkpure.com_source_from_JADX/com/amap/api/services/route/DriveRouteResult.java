package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.route.RouteSearch.DriveRouteQuery;
import java.util.ArrayList;
import java.util.List;

public class DriveRouteResult extends RouteResult implements Parcelable {
    public static final Creator<DriveRouteResult> CREATOR = new C04191();
    private float f4404a;
    private List<DrivePath> f4405b = new ArrayList();
    private DriveRouteQuery f4406c;

    static class C04191 implements Creator<DriveRouteResult> {
        C04191() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1687a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1688a(i);
        }

        public DriveRouteResult m1687a(Parcel parcel) {
            return new DriveRouteResult(parcel);
        }

        public DriveRouteResult[] m1688a(int i) {
            return new DriveRouteResult[i];
        }
    }

    public float getTaxiCost() {
        return this.f4404a;
    }

    public void setTaxiCost(float f) {
        this.f4404a = f;
    }

    public List<DrivePath> getPaths() {
        return this.f4405b;
    }

    public void setPaths(List<DrivePath> list) {
        this.f4405b = list;
    }

    public DriveRouteQuery getDriveQuery() {
        return this.f4406c;
    }

    public void setDriveQuery(DriveRouteQuery driveRouteQuery) {
        this.f4406c = driveRouteQuery;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeFloat(this.f4404a);
        parcel.writeTypedList(this.f4405b);
        parcel.writeParcelable(this.f4406c, i);
    }

    public DriveRouteResult(Parcel parcel) {
        super(parcel);
        this.f4404a = parcel.readFloat();
        this.f4405b = parcel.createTypedArrayList(DrivePath.CREATOR);
        this.f4406c = (DriveRouteQuery) parcel.readParcelable(DriveRouteQuery.class.getClassLoader());
    }
}
