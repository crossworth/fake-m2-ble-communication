package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;
import com.baidu.mapapi.search.route.BikingRouteLine.BikingStep;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;
import java.util.List;

public class RouteLine<T extends RouteStep> implements Parcelable {
    TYPE f1541a;
    private RouteNode f1542b;
    private RouteNode f1543c;
    private String f1544d;
    private List<T> f1545e;
    private int f1546f;
    private int f1547g;

    protected enum TYPE {
        DRIVESTEP(0),
        TRANSITSTEP(1),
        WALKSTEP(2),
        BIKINGSTEP(3);
        
        private int f1540a;

        private TYPE(int i) {
            this.f1540a = i;
        }

        private int m1433a() {
            return this.f1540a;
        }
    }

    protected RouteLine() {
    }

    protected RouteLine(Parcel parcel) {
        int readInt = parcel.readInt();
        this.f1542b = (RouteNode) parcel.readValue(RouteNode.class.getClassLoader());
        this.f1543c = (RouteNode) parcel.readValue(RouteNode.class.getClassLoader());
        this.f1544d = parcel.readString();
        switch (readInt) {
            case 0:
                this.f1545e = parcel.createTypedArrayList(DrivingStep.CREATOR);
                break;
            case 1:
                this.f1545e = parcel.createTypedArrayList(TransitStep.CREATOR);
                break;
            case 2:
                this.f1545e = parcel.createTypedArrayList(WalkingStep.CREATOR);
                break;
            case 3:
                this.f1545e = parcel.createTypedArrayList(BikingStep.CREATOR);
                break;
        }
        this.f1546f = parcel.readInt();
        this.f1547g = parcel.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public List<T> getAllStep() {
        return this.f1545e;
    }

    public int getDistance() {
        return this.f1546f;
    }

    public int getDuration() {
        return this.f1547g;
    }

    public RouteNode getStarting() {
        return this.f1542b;
    }

    public RouteNode getTerminal() {
        return this.f1543c;
    }

    public String getTitle() {
        return this.f1544d;
    }

    protected TYPE getType() {
        return this.f1541a;
    }

    public void setDistance(int i) {
        this.f1546f = i;
    }

    public void setDuration(int i) {
        this.f1547g = i;
    }

    public void setStarting(RouteNode routeNode) {
        this.f1542b = routeNode;
    }

    public void setSteps(List<T> list) {
        this.f1545e = list;
    }

    public void setTerminal(RouteNode routeNode) {
        this.f1543c = routeNode;
    }

    public void setTitle(String str) {
        this.f1544d = str;
    }

    protected void setType(TYPE type) {
        this.f1541a = type;
    }

    public void writeToParcel(Parcel parcel, int i) {
        if (this.f1541a != null) {
            parcel.writeInt(this.f1541a.m1433a());
        } else {
            parcel.writeInt(10);
        }
        parcel.writeValue(this.f1542b);
        parcel.writeValue(this.f1543c);
        parcel.writeString(this.f1544d);
        if (this.f1541a != null) {
            parcel.writeTypedList(this.f1545e);
        }
        parcel.writeInt(this.f1546f);
        parcel.writeInt(this.f1547g);
    }
}
