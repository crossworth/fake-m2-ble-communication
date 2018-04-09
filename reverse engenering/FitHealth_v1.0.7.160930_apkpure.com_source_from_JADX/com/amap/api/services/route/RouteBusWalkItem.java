package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;

public class RouteBusWalkItem extends WalkPath implements Parcelable {
    public static final Creator<RouteBusWalkItem> CREATOR = new C04231();
    private LatLonPoint f5413a;
    private LatLonPoint f5414b;

    static class C04231 implements Creator<RouteBusWalkItem> {
        C04231() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1695a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1696a(i);
        }

        public RouteBusWalkItem m1695a(Parcel parcel) {
            return new RouteBusWalkItem(parcel);
        }

        public RouteBusWalkItem[] m1696a(int i) {
            return null;
        }
    }

    public LatLonPoint getOrigin() {
        return this.f5413a;
    }

    public void setOrigin(LatLonPoint latLonPoint) {
        this.f5413a = latLonPoint;
    }

    public LatLonPoint getDestination() {
        return this.f5414b;
    }

    public void setDestination(LatLonPoint latLonPoint) {
        this.f5414b = latLonPoint;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.f5413a, i);
        parcel.writeParcelable(this.f5414b, i);
    }

    public RouteBusWalkItem(Parcel parcel) {
        super(parcel);
        this.f5413a = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
        this.f5414b = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
    }
}
