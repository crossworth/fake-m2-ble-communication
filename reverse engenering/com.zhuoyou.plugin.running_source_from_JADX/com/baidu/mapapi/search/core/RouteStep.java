package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class RouteStep implements Parcelable {
    public static final Creator<RouteStep> CREATOR = new C0530h();
    int f1502a;
    int f1503b;
    protected List<LatLng> mWayPoints;

    protected RouteStep() {
    }

    protected RouteStep(Parcel parcel) {
        this.f1502a = parcel.readInt();
        this.f1503b = parcel.readInt();
        this.mWayPoints = new ArrayList();
        parcel.readList(this.mWayPoints, LatLng.class.getClassLoader());
        if (this.mWayPoints.size() == 0) {
            this.mWayPoints = null;
        }
    }

    public int describeContents() {
        return 0;
    }

    public int getDistance() {
        return this.f1502a;
    }

    public int getDuration() {
        return this.f1503b;
    }

    public List<LatLng> getWayPoints() {
        return this.mWayPoints;
    }

    public void setDistance(int i) {
        this.f1502a = i;
    }

    public void setDuration(int i) {
        this.f1503b = i;
    }

    public void setWayPoints(List<LatLng> list) {
        this.mWayPoints = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1502a);
        parcel.writeInt(this.f1503b);
        parcel.writeList(this.mWayPoints);
    }
}
