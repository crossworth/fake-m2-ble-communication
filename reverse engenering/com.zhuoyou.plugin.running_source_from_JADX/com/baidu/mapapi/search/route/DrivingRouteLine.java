package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.RouteStep;
import java.util.ArrayList;
import java.util.List;

public class DrivingRouteLine extends RouteLine<DrivingStep> implements Parcelable {
    public static final Creator<DrivingRouteLine> CREATOR = new C0558d();
    private boolean f1681b;
    private List<RouteNode> f1682c;
    private int f1683d;
    private int f1684e;

    public static class DrivingStep extends RouteStep implements Parcelable {
        public static final Creator<DrivingStep> CREATOR = new C0559e();
        List<LatLng> f1671c;
        int[] f1672d;
        private int f1673e;
        private RouteNode f1674f;
        private RouteNode f1675g;
        private String f1676h;
        private String f1677i;
        private String f1678j;
        private String f1679k;
        private int f1680l;

        protected DrivingStep(Parcel parcel) {
            super(parcel);
            this.f1673e = parcel.readInt();
            this.f1674f = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            this.f1675g = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            this.f1676h = parcel.readString();
            this.f1677i = parcel.readString();
            this.f1678j = parcel.readString();
            this.f1679k = parcel.readString();
            this.f1680l = parcel.readInt();
            this.f1671c = parcel.createTypedArrayList(LatLng.CREATOR);
            this.f1672d = parcel.createIntArray();
        }

        void m1579a(int i) {
            this.f1673e = i;
        }

        void m1580a(RouteNode routeNode) {
            this.f1674f = routeNode;
        }

        void m1581a(String str) {
            this.f1677i = str;
        }

        void m1582a(List<LatLng> list) {
            this.f1671c = list;
        }

        void m1583a(int[] iArr) {
            this.f1672d = iArr;
        }

        void m1584b(int i) {
            this.f1680l = i;
        }

        void m1585b(RouteNode routeNode) {
            this.f1675g = routeNode;
        }

        void m1586b(String str) {
            this.f1678j = str;
        }

        void m1587c(String str) {
            this.f1679k = str;
        }

        public int describeContents() {
            return 0;
        }

        public int getDirection() {
            return this.f1673e;
        }

        public RouteNode getEntrance() {
            return this.f1674f;
        }

        public String getEntranceInstructions() {
            return this.f1677i;
        }

        public RouteNode getExit() {
            return this.f1675g;
        }

        public String getExitInstructions() {
            return this.f1678j;
        }

        public String getInstructions() {
            return this.f1679k;
        }

        public int getNumTurns() {
            return this.f1680l;
        }

        public int[] getTrafficList() {
            return this.f1672d;
        }

        public List<LatLng> getWayPoints() {
            if (this.mWayPoints == null) {
                this.mWayPoints = CoordUtil.decodeLocationList(this.f1676h);
            }
            return this.f1671c;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.f1673e);
            parcel.writeParcelable(this.f1674f, 1);
            parcel.writeParcelable(this.f1675g, 1);
            parcel.writeString(this.f1676h);
            parcel.writeString(this.f1677i);
            parcel.writeString(this.f1678j);
            parcel.writeString(this.f1679k);
            parcel.writeInt(this.f1680l);
            parcel.writeTypedList(this.f1671c);
            parcel.writeIntArray(this.f1672d);
        }
    }

    DrivingRouteLine() {
    }

    protected DrivingRouteLine(Parcel parcel) {
        super(parcel);
        this.f1681b = parcel.readByte() != (byte) 0;
        this.f1682c = new ArrayList();
        parcel.readList(this.f1682c, RouteNode.class.getClassLoader());
        this.f1683d = parcel.readInt();
        this.f1684e = parcel.readInt();
    }

    void m1588a(List<RouteNode> list) {
        this.f1682c = list;
    }

    public int describeContents() {
        return 0;
    }

    public int getCongestionDistance() {
        return this.f1683d;
    }

    public int getLightNum() {
        return this.f1684e;
    }

    public List<RouteNode> getWayPoints() {
        return this.f1682c;
    }

    @Deprecated
    public boolean isSupportTraffic() {
        return this.f1681b;
    }

    public void setCongestionDistance(int i) {
        this.f1683d = i;
    }

    public void setLightNum(int i) {
        this.f1684e = i;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.setType(TYPE.DRIVESTEP);
        super.writeToParcel(parcel, i);
        parcel.writeByte(this.f1681b ? (byte) 1 : (byte) 0);
        parcel.writeList(this.f1682c);
        parcel.writeInt(this.f1683d);
        parcel.writeInt(this.f1684e);
    }
}
