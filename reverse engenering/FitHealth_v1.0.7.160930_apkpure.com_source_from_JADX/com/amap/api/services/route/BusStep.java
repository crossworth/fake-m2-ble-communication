package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class BusStep implements Parcelable {
    public static final Creator<BusStep> CREATOR = new C04151();
    private RouteBusWalkItem f1586a;
    private List<RouteBusLineItem> f1587b = new ArrayList();
    private Doorway f1588c;
    private Doorway f1589d;

    static class C04151 implements Creator<BusStep> {
        C04151() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1679a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1680a(i);
        }

        public BusStep m1679a(Parcel parcel) {
            return new BusStep(parcel);
        }

        public BusStep[] m1680a(int i) {
            return null;
        }
    }

    public RouteBusWalkItem getWalk() {
        return this.f1586a;
    }

    public void setWalk(RouteBusWalkItem routeBusWalkItem) {
        this.f1586a = routeBusWalkItem;
    }

    @Deprecated
    public RouteBusLineItem getBusLine() {
        if (this.f1587b == null || this.f1587b.size() == 0) {
            return null;
        }
        return (RouteBusLineItem) this.f1587b.get(0);
    }

    public List<RouteBusLineItem> getBusLines() {
        return this.f1587b;
    }

    @Deprecated
    public void setBusLine(RouteBusLineItem routeBusLineItem) {
        if (this.f1587b != null) {
            if (this.f1587b.size() == 0) {
                this.f1587b.add(routeBusLineItem);
            }
            this.f1587b.set(0, routeBusLineItem);
        }
    }

    public void setBusLines(List<RouteBusLineItem> list) {
        this.f1587b = list;
    }

    public Doorway getEntrance() {
        return this.f1588c;
    }

    public void setEntrance(Doorway doorway) {
        this.f1588c = doorway;
    }

    public Doorway getExit() {
        return this.f1589d;
    }

    public void setExit(Doorway doorway) {
        this.f1589d = doorway;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.f1586a, i);
        parcel.writeTypedList(this.f1587b);
        parcel.writeParcelable(this.f1588c, i);
        parcel.writeParcelable(this.f1589d, i);
    }

    public BusStep(Parcel parcel) {
        this.f1586a = (RouteBusWalkItem) parcel.readParcelable(RouteBusWalkItem.class.getClassLoader());
        this.f1587b = parcel.createTypedArrayList(RouteBusLineItem.CREATOR);
        this.f1588c = (Doorway) parcel.readParcelable(Doorway.class.getClassLoader());
        this.f1589d = (Doorway) parcel.readParcelable(Doorway.class.getClassLoader());
    }
}
