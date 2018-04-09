package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusStationItem;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.List;

public class RouteBusLineItem extends BusLineItem implements Parcelable {
    public static final Creator<RouteBusLineItem> CREATOR = new C04221();
    private BusStationItem f4407a;
    private BusStationItem f4408b;
    private List<LatLonPoint> f4409c = new ArrayList();
    private int f4410d;
    private List<BusStationItem> f4411e = new ArrayList();
    private float f4412f;

    static class C04221 implements Creator<RouteBusLineItem> {
        C04221() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1693a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1694a(i);
        }

        public RouteBusLineItem m1693a(Parcel parcel) {
            return new RouteBusLineItem(parcel);
        }

        public RouteBusLineItem[] m1694a(int i) {
            return null;
        }
    }

    public BusStationItem getDepartureBusStation() {
        return this.f4407a;
    }

    public void setDepartureBusStation(BusStationItem busStationItem) {
        this.f4407a = busStationItem;
    }

    public BusStationItem getArrivalBusStation() {
        return this.f4408b;
    }

    public void setArrivalBusStation(BusStationItem busStationItem) {
        this.f4408b = busStationItem;
    }

    public List<LatLonPoint> getPolyline() {
        return this.f4409c;
    }

    public void setPolyline(List<LatLonPoint> list) {
        this.f4409c = list;
    }

    public int getPassStationNum() {
        return this.f4410d;
    }

    public void setPassStationNum(int i) {
        this.f4410d = i;
    }

    public List<BusStationItem> getPassStations() {
        return this.f4411e;
    }

    public void setPassStations(List<BusStationItem> list) {
        this.f4411e = list;
    }

    public float getDuration() {
        return this.f4412f;
    }

    public void setDuration(float f) {
        this.f4412f = f;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.f4407a, i);
        parcel.writeParcelable(this.f4408b, i);
        parcel.writeTypedList(this.f4409c);
        parcel.writeInt(this.f4410d);
        parcel.writeTypedList(this.f4411e);
        parcel.writeFloat(this.f4412f);
    }

    public RouteBusLineItem(Parcel parcel) {
        super(parcel);
        this.f4407a = (BusStationItem) parcel.readParcelable(BusStationItem.class.getClassLoader());
        this.f4408b = (BusStationItem) parcel.readParcelable(BusStationItem.class.getClassLoader());
        this.f4409c = parcel.createTypedArrayList(LatLonPoint.CREATOR);
        this.f4410d = parcel.readInt();
        this.f4411e = parcel.createTypedArrayList(BusStationItem.CREATOR);
        this.f4412f = parcel.readFloat();
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = super.hashCode() * 31;
        if (this.f4408b == null) {
            i = 0;
        } else {
            i = this.f4408b.hashCode();
        }
        i = (i + hashCode) * 31;
        if (this.f4407a != null) {
            i2 = this.f4407a.hashCode();
        }
        return i + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RouteBusLineItem routeBusLineItem = (RouteBusLineItem) obj;
        if (this.f4408b == null) {
            if (routeBusLineItem.f4408b != null) {
                return false;
            }
        } else if (!this.f4408b.equals(routeBusLineItem.f4408b)) {
            return false;
        }
        if (this.f4407a == null) {
            if (routeBusLineItem.f4407a != null) {
                return false;
            }
            return true;
        } else if (this.f4407a.equals(routeBusLineItem.f4407a)) {
            return true;
        } else {
            return false;
        }
    }
}
